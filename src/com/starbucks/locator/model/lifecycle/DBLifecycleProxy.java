package com.starbucks.locator.model.lifecycle;

public interface DBLifecycleProxy {

	public void bootstrapDatabase();
	public void teardownDatabase();
}