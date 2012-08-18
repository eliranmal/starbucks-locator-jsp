package com.starbucks.locator.model.dataaccess;

import java.sql.Connection;

import com.starbucks.locator.model.runtime.StarbucksLocatorException;

public interface DBLifecycleManager {

	public boolean createLocationsTable(Connection conn) throws StarbucksLocatorException;
	public void dropLocationsTable(Connection conn) throws StarbucksLocatorException;
}