package com.ur.sample.watchSystemLoad.impl;

import javax.swing.Icon;
import javax.swing.ImageIcon;

import com.ur.urcap.api.contribution.toolbar.ToolbarConfiguration;
import com.ur.urcap.api.contribution.toolbar.ToolbarContext;
import com.ur.urcap.api.contribution.toolbar.swing.SwingToolbarContribution;
import com.ur.urcap.api.contribution.toolbar.swing.SwingToolbarService;

public class WatchSystemLoadToolbarService implements SwingToolbarService{

	@Override
	public Icon getIcon() {
		return new ImageIcon(getClass().getResource("/icons/acme_logo.png"));
	}

	@Override
	public void configureContribution(ToolbarConfiguration configuration) {
		configuration.setToolbarHeight(800);
		
	}

	@Override
	public SwingToolbarContribution createToolbar(ToolbarContext context) {
		return new WatchSystemLoadToolbarContribution( context );
	}

}
