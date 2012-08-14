package com.starbucks.locator.model.dataaccess;

import java.sql.Connection;

import com.starbucks.locator.model.dto.Locations;
import com.starbucks.locator.model.runtime.StarbucksLocatorException;

public interface LocationsManager {

	/*
	 * TODO javadoc
	 */
	
	public Locations getBankBalance(int balanceId, Connection con) throws StarbucksLocatorException;

	public void addBankBalance(Locations b, Connection con) throws StarbucksLocatorException;

}
