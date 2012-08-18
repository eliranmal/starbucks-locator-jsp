package com.starbucks.locator.model.dataaccess;

import java.sql.Connection;
import java.util.List;
import java.util.Set;

import com.starbucks.locator.model.dto.Location;
import com.starbucks.locator.model.runtime.StarbucksLocatorException;

public interface LocationsManager {
	
	public List<Location> getLocations(int balanceId, Connection con) throws StarbucksLocatorException;

	public boolean addLocations(Set<Location> b, Connection con) throws StarbucksLocatorException;
}
