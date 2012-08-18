package com.starbucks.locator.model.facade;

import java.util.List;

import com.starbucks.locator.model.dataaccess.DBConnection;
import com.starbucks.locator.model.dataaccess.LocationsManager;
import com.starbucks.locator.model.dataaccess.LocationsManagerImpl;
import com.starbucks.locator.model.dto.Location;
import com.starbucks.locator.model.runtime.StarbucksLocatorException;


public class AppActionsFacade {

	/*
	 * singleton instance
	 */
	private static final AppActionsFacade _aaf = new AppActionsFacade();
	
	private LocationsManager _lm = LocationsManagerImpl.getInstance();

	/*
	 * prevent initialization
	 */
	private AppActionsFacade() {
	}
	
	/**
	 * provide single point access
	 * @return
	 */
	public static AppActionsFacade getInstance() {
		return _aaf;
	}
	
	public List<Location> getLocations() {
		List<Location> locations = null;
		try {
			locations = _lm.getLocations(DBConnection.connect());
		} catch (StarbucksLocatorException e) {
			e.printStackTrace();
		}
		return locations;
	}

}
