package com.ur.sample.watchSystemLoad.impl;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;

import com.ur.urcap.api.contribution.toolbar.swing.SwingToolbarService;

/**
 * Hello world activator for the OSGi bundle URCAPS contribution
 *
 */
public class Activator implements BundleActivator {
	@Override
	public void start(BundleContext bundleContext) throws Exception {
		bundleContext.registerService(SwingToolbarService.class, new WatchSystemLoadToolbarService(), null);
	}

	@Override
	public void stop(BundleContext bundleContext) throws Exception {
		
	}
}

