package com.starbucks.locator.model.lifecycle;

public interface DBLifecycleFacade {

	public void bootstrapDatabase();
	public void teardownDatabase();
}