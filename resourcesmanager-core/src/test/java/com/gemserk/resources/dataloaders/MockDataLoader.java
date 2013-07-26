package com.gemserk.resources.dataloaders;


public class MockDataLoader<T> extends DataLoader<T> {

	private final T t;

	public boolean loaded = false;
	public boolean unloadCalled = false;

	public MockDataLoader(T t) {
		this.t = t;
	}

	@Override
	public T load() {
		loaded = true;
		return t;
	}

	@Override
	public void unload(T t) {
		unloadCalled = true;
		loaded = false;
	}

}