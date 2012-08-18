package com.starbucks.locator.model.lifecycle;

public interface DBLifecycleManager {

	public void bootstrapDatabase();
	public void teardownDatabase();
}