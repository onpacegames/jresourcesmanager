package com.gemserk.resources.slick;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import com.gemserk.resources.ResourceManager;
import com.gemserk.resources.dataloaders.DataLoader;
import com.gemserk.resources.datasources.ClassPathDataSource;
import com.gemserk.resources.monitor.FileMonitorFactory;
import com.gemserk.resources.monitor.ResourceMonitor;
import com.gemserk.resources.monitor.ResourcesMonitor;
import com.gemserk.resources.resourceloaders.ResourceLoaderImpl;
import com.gemserk.resources.slick.dataloaders.SlickAnimationLoader;
import com.google.inject.Inject;

@SuppressWarnings("unchecked")
public class PropertiesAnimationLoader {

	ResourceManager resourceManager;

	ResourcesMonitor resourcesMonitor;

	@Inject
	public void setResourceManager(ResourceManager resourceManager) {
		this.resourceManager = resourceManager;
	}

	@Inject
	public void setResourcesMonitor(ResourcesMonitor resourcesMonitor) {
		this.resourcesMonitor = resourcesMonitor;
	}

	public void load(String propertiesFile) {
		try {
			Properties properties = new Properties();
			InputStream propertiesInputStream = new ClassPathDataSource(propertiesFile).getInputStream();
			properties.load(propertiesInputStream);

			for (Object keyObj : properties.keySet()) {
				String id = (String) keyObj;
				String value = properties.getProperty(id);

				String[] values = value.split(",");
				String file = values[0];
				int width = Integer.parseInt(values[1]);
				int height = Integer.parseInt(values[2]);
				final int time = Integer.parseInt(values[3]);
				final int framesCount = Integer.parseInt(values[4]);

				DataLoader dataLoader = new SlickAnimationLoader(file, width, height, time, framesCount, false);
				resourceManager.add(id, new ResourceLoaderImpl(dataLoader));

				// // mark the resource for reloading whenever the properties file was modified
				// resourcesMonitor.monitor(new ResourceMonitor(resourceManager.get(id), FileMonitorFactory.classPathFileMonitor(propertiesFile)));

				// or the resource file itself
				resourcesMonitor.monitor(new ResourceMonitor(resourceManager.get(id), FileMonitorFactory.classPathFileMonitor(file)));
			}

		} catch (IOException e) {
			throw new RuntimeException("failed to load animations from " + propertiesFile, e);
		}
	}

}