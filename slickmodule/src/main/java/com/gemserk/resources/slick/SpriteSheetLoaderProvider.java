package com.gemserk.resources.slick;

import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;

import com.gemserk.resources.ResourceLoader;
import com.gemserk.resources.ResourceLoaderProvider;

public class SpriteSheetLoaderProvider extends ResourceLoaderProvider<String, SpriteSheet> {

	public static class SpriteSheetResourceLoader extends ResourceLoader<SpriteSheet> {

		private final String file;

		private final int tw;

		private final int th;

		SpriteSheetResourceLoader(String image, int tw, int th) {
			this.file = image;
			this.tw = tw;
			this.th = th;
		}

		@Override
		public SpriteSheet load() {
			try {
				return new SpriteSheet(file, tw, th);
			} catch (SlickException e) {
				throw new RuntimeException(e);
			}
		}
	}

	private SpriteSheetResourceLoader getSpriteSheetLoader(String fileValues) {
		String[] values = fileValues.split(",");

		if (values.length != 3)
			throw new RuntimeException("Invalid values for spritesheet " + fileValues);

		String file = values[0];
		int width = Integer.parseInt(values[1]);
		int height = Integer.parseInt(values[2]);

		SpriteSheetResourceLoader resourceLoader = new SpriteSheetResourceLoader(file, width, height);
		return resourceLoader;
	}

	@Override
	public ResourceLoader<SpriteSheet> get(String id) {
		return getSpriteSheetLoader(id);
	}

}