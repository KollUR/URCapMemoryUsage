package com.ur.sample.watchSystemLoad.impl;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Font;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;

import com.ur.urcap.api.contribution.toolbar.ToolbarContext;
import com.ur.urcap.api.contribution.toolbar.swing.SwingToolbarContribution;

public class WatchSystemLoadToolbarContribution implements SwingToolbarContribution{
	private static final int VERTICAL_SPACE = 10;
	private static final int HEADER_FONT_SIZE = 24;
	private final ToolbarContext context;
	private Timer uiTimer;
	private Runtime rt = Runtime.getRuntime(); 
	private createGraph myGraph;
	private JLabel labelFreeMB;
	private JLabel labelUsedMB;
	private List<Double> listUsedMB;
	
	
	public WatchSystemLoadToolbarContribution( ToolbarContext context ) {
		this.context = context;
		myGraph = new createGraph( createScoresForGui() );
		myGraph.setPreferredSize( new Dimension( 600,400 ) );
		listUsedMB = new ArrayList<Double>();
	}
	
	@Override
	public void buildUI(JPanel jPanel) {
		jPanel.setLayout(new BoxLayout(jPanel, BoxLayout.Y_AXIS));
		jPanel.add(createHeader());
		jPanel.add(createVerticalSpace());
		jPanel.add(createInfoFreeMB());
		jPanel.add(createVerticalSpace());
		jPanel.add(createInfoUsedMB());
		jPanel.add(myGraph);
		
		
		
		
	}
	
	private Box createHeader() {
		Box headerBox = Box.createHorizontalBox();
		headerBox.setAlignmentX(Component.CENTER_ALIGNMENT);

		JLabel header = new JLabel("My Memory Usage");
		header.setFont(header.getFont().deriveFont(Font.BOLD, HEADER_FONT_SIZE));
		headerBox.add(header);
		return headerBox;
	}
	
	private Component createVerticalSpace() {
		return Box.createRigidArea(new Dimension(0, VERTICAL_SPACE));
	}
	
	private Box createInfoFreeMB() {
		Box infoBox = Box.createHorizontalBox();
		infoBox.setAlignmentX(Component.CENTER_ALIGNMENT);
		
		JLabel infoLabel = new JLabel();
		infoLabel.setText(" Memory available [MB]: ");
		infoBox.add(infoLabel);
		
		this.labelFreeMB = new JLabel();
		this.labelFreeMB.setText(" <default free MB> ");
		infoBox.add(this.labelFreeMB);		
		
		return infoBox;
	}
	
	private Box createInfoUsedMB() {
		Box infoBox = Box.createHorizontalBox();
		infoBox.setAlignmentX(Component.CENTER_ALIGNMENT);
		
		JLabel infoLabel = new JLabel();
		infoLabel.setText(" Memory in use [MB]: ");
		infoBox.add(infoLabel);	
		
		this.labelUsedMB = new JLabel();
		this.labelUsedMB.setText(" < default used MB > ");
		infoBox.add(this.labelUsedMB);
		
		return infoBox;
	}
	
	@Override
	public void openView() {
		this.uiTimer = new Timer(true);
		this.uiTimer.schedule(new TimerTask() {
			
			@Override
			public void run() {
				EventQueue.invokeLater(new Runnable() {
					
					@Override
					public void run() {
						updateUI();						
					}
				});
				
			}
		}, 0, 250);
		
	}

	@Override
	public void closeView() {
		if (this.uiTimer != null) 
			this.uiTimer.cancel();
	}
	
	private long getUsedSystemMemory() {		 
		 long usedMB = (this.rt.totalMemory() - this.rt.freeMemory()) / 1024 / 1024;
		 
		 // only displaying a timespan of the last two minutes
		 if ( listUsedMB.size() == 60 ) {
			 listUsedMB.remove(0);
			 listUsedMB.add( (double) usedMB );
		 }
		 else {
			 listUsedMB.add( (double) usedMB );
		 }
		 
		 return usedMB;
	}

	private long getFreeSystemMemory() {		 
		 long freeMB = this.rt.freeMemory() / 1024 / 1024;
		 return freeMB;
	}
	
	
	private void updateUI() {
		this.updateLabelMemoryFree( Long.toString(getFreeSystemMemory() ) );
		this.updateLabelMemoryUsed( Long.toString(getUsedSystemMemory() ) );
		this.myGraph.setScores(listUsedMB);
		
	}
	
	public void updateLabelMemoryFree( String in ) {
		this.labelFreeMB.setText( in );
	}
	
	public void updateLabelMemoryUsed( String in ) {
		this.labelUsedMB.setText( in );
		
	}
	
	private List<Double> createScoresForGui() {
		List<Double> scores = new ArrayList<>();
        scores.add(0.0);
		return scores;
	}
	
}
