package com.gemserk.resources.tests;

import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JButton;
import javax.swing.JFrame;

import com.gemserk.resources.Resource;
import com.gemserk.resources.ResourceManager;
import com.gemserk.resources.ResourceManagerImpl;
import com.gemserk.resources.ResourcesBuilder;
import com.gemserk.resources.datasources.DataSourceProvider;
import com.gemserk.resources.datasources.DataSourceProvider.Source;
import com.gemserk.resources.monitor.FileMonitorAction;
import com.gemserk.resources.monitor.FileUtils;
import com.gemserk.resources.monitor.FilesMonitor;
import com.gemserk.resources.monitor.FilesMonitorImpl;
import com.gemserk.resources.monitor.handlers.ReloadResourceWhenFileModified;

public class ResourcesMonitorSample {

	@SuppressWarnings({ "serial", "unused" })
	public static void main(String[] args) {

		final ResourceManager<String> resourceManager = new ResourceManagerImpl<String>();

		ResourcesBuilder resourcesBuilder = new ResourcesBuilder(resourceManager, new DataSourceProvider(Source.ClassPath)) {
			{
				java2d.image("BlackCompanyLogo", "logo-gemserk-512x116.png");
			}
		};

		final FilesMonitor filesMonitor = new FilesMonitorImpl();

		// filesMonitor.monitor(FileUtils.classPathFile("logo-gemserk-512x116.png"), new ReloadResourceWhenFileModified(resourceManager.get("BlackCompanyLogo")));

		new FileMonitorAction(filesMonitor).with(new ReloadResourceWhenFileModified(resourceManager.get("BlackCompanyLogo"))).monitor(FileUtils.classPathFile("logo-gemserk-512x116.png"));

		new Thread() {

			@Override
			public void run() {

				try {
					while (true) {
						filesMonitor.checkModifiedFiles();
						sleep(500);
					}
				} catch (InterruptedException e) {
					e.printStackTrace();
				}

			}

		}.start();

		JFrame frame = new JFrame() {
			{

				setSize(640, 480);
				setVisible(true);

				setLayout(new GridLayout(1, 1));

				addWindowListener(new WindowAdapter() {
					@Override
					public void windowClosing(WindowEvent e) {
						System.exit(0);
					}
				});

				add(new JButton() {
					Resource<Image> imageResource = resourceManager.get("BlackCompanyLogo");

					@Override
					public void paint(java.awt.Graphics g) {
						super.paint(g);
						Image image = imageResource.get();
						g.drawImage(image, getWidth() / 2 - image.getWidth(null) / 2, getHeight() / 2 - image.getHeight(null) / 2, null);
					}

				});

			}
		};

		frame.setVisible(true);
		frame.repaint();
	}
}
