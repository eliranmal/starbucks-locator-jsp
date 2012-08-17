package com.starbucks.locator.model.dataaccess;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.starbucks.locator.model.dto.Location;
import com.starbucks.locator.model.runtime.StarbucksLocatorException;
import com.starbucks.locator.util.DBConstants;

public class LocationsDBManager implements LocationsManager {

	private static final String getBalancePS = "SELECT * FROM " + DBConstants.TABLE_NAME + " WHERE balance_id = ?";
	private static final String addBalancePS = "INSERT INTO bank_balance (action, amount, [date], cust_id) VALUES (?,?,?,?)";
	private static final String GET_LOCATIONS_BY_RANGE_PS = "SELECT * FROM deposits WHERE cust_id = ?"; // TODO

	public LocationsDBManager() {
	}

	@Override
	public List<Location> getLocations(int balanceId, Connection con) throws StarbucksLocatorException {
		PreparedStatement ps;
		ResultSet rs;
		Location l = null;
		List<Location> locations = new ArrayList<Location>();
		try {
			ps = con.prepareStatement(getBalancePS);
			ps.setInt(1, balanceId);
			rs = ps.executeQuery();
			if (rs.next()) {
				l = new Location(); // TODO
				locations.add(l);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw new StarbucksLocatorException("SQL exception thrown");
		}
		return locations;
	}

	@Override
	public void addLocations(List<Location> b, Connection con) throws StarbucksLocatorException {
		// TODO Auto-generated method stub
		
	}
}
