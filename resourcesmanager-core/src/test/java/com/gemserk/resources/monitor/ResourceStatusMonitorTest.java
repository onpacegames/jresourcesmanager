package com.gemserk.resources.monitor;

import static org.junit.Assert.assertThat;

import org.hamcrest.core.IsEqual;
import org.junit.Test;

import com.gemserk.resources.MockResource;
import com.gemserk.resources.dataloaders.MockDataLoader;

public class ResourceStatusMonitorTest {
	
	@Test
	public void shouldDetectWhenResourceWasLoaded() {
		MockResource resource = new MockResource(new MockDataLoader("DATA"));
		
		ResourceStatusMonitor resourceStatusMonitor = new ResourceStatusMonitor(resource);
		
		resourceStatusMonitor.checkChanges();
		assertThat(resourceStatusMonitor.wasLoaded(), IsEqual.equalTo(false));
		
		resource.load();
		
		resourceStatusMonitor.checkChanges();
		assertThat(resourceStatusMonitor.wasLoaded(), IsEqual.equalTo(true));
	}
	
	@Test
	public void shouldDetectWhenResourceWasLoadedOnlyOnce() {
		MockResource resource = new MockResource(new MockDataLoader("DATA"));
		
		ResourceStatusMonitor resourceStatusMonitor = new ResourceStatusMonitor(resource);
		
		resource.load();
		resourceStatusMonitor.checkChanges();
		resourceStatusMonitor.checkChanges();
		
		assertThat(resourceStatusMonitor.wasLoaded(), IsEqual.equalTo(false));
	}
	
	@Test
	public void shouldNotDetectWasLoadedIfWasLoadedBefore() {
		MockResource resource = new MockResource(new MockDataLoader("DATA"));
		resource.load();
		
		ResourceStatusMonitor resourceStatusMonitor = new ResourceStatusMonitor(resource);
		
		resourceStatusMonitor.checkChanges();
		assertThat(resourceStatusMonitor.wasLoaded(), IsEqual.equalTo(false));
	}

}
