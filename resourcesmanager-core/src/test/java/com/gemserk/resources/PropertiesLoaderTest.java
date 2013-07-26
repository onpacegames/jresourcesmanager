package com.gemserk.resources;

import static org.easymock.EasyMock.createMock;
import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.replay;
import static org.easymock.EasyMock.verify;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.junit.Test;

import com.gemserk.resources.dataloaders.PropertiesDataLoader;
import com.gemserk.resources.datasources.DataSource;

public class PropertiesLoaderTest {
	@Test
	public void shouldLoadPropertiesFromDataSource() {
		try (InputStream inputStream = getClass().getClassLoader().getResourceAsStream("test.properties")) {
			DataSource propertiesDataSource = createMock(DataSource.class);
			expect(propertiesDataSource.getInputStream()).andReturn(inputStream);
			replay(propertiesDataSource);
	
			PropertiesDataLoader propertiesLoader = new PropertiesDataLoader(propertiesDataSource);
			Properties properties = propertiesLoader.load();
			assertNotNull(properties);
	
			String value1 = (String) properties.get("key1");
			assertEquals("value1", value1);
	
			verify(propertiesDataSource);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}