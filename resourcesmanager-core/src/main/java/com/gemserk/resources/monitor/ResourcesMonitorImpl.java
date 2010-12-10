package com.gemserk.resources.monitor;

import java.util.ArrayList;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ResourcesMonitorImpl implements ResourcesMonitor {

	protected static final Logger logger = LoggerFactory.getLogger(ResourcesMonitorImpl.class);

	ArrayList<ResourceMonitor> resourceMonitors = new ArrayList<ResourceMonitor>();

	public void reloadModifiedResources() {
		for (ResourceMonitor resourceMonitor : resourceMonitors) 
			resourceMonitor.reloadIfModified();
	}

	public void monitor(ResourceMonitor resourceMonitor) {
		resourceMonitors.add(resourceMonitor);
	}

}