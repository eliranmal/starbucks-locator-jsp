package com.starbucks.locator.model.dataaccess;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import com.starbucks.locator.model.dto.Location;
import com.starbucks.locator.model.runtime.StarbucksLocatorException;
import com.starbucks.locator.util.DBConstants;

public class LocationsDBManager implements LocationsManager {

	private static final LocationsDBManager ldbm = new LocationsDBManager();
	
	private static String PS_GET_LOCATION;
	private static String PS_ADD_LOCATION;
	private static String PS_GET_LOCATIONS_BY_RANGE;

	private LocationsDBManager() {
		PS_GET_LOCATION = "SELECT * FROM " + DBConstants.TABLE_NAME + " WHERE balance_id = ?";
		StringBuilder sb = new StringBuilder();
		sb.append("INSERT INTO ");
		sb.append(DBConstants.TABLE_NAME);
		sb.append(" (");
		sb.append(DBConstants.COL_NAME_LNG);
		sb.append(", ");
		sb.append(DBConstants.COL_NAME_LAT);
		sb.append(", ");
		sb.append(DBConstants.COL_NAME_CITY);
		sb.append(", ");
		sb.append(DBConstants.COL_NAME_ADDRESS);
		sb.append(") VALUES (?,?,?,?)");
		PS_ADD_LOCATION = sb.toString();
		PS_GET_LOCATIONS_BY_RANGE = "SELECT * FROM deposits WHERE cust_id = ?"; // TODO change to match locations table
	}

	public static LocationsDBManager getInstance() {
		return ldbm;
	}
	
	// TODO pass range to filter
	@Override
	public List<Location> getLocations(int balanceId, Connection con) throws StarbucksLocatorException {
		PreparedStatement ps;
		ResultSet rs;
		Location l = null;
		List<Location> locations = new ArrayList<Location>();
		try {
			ps = con.prepareStatement(PS_GET_LOCATION);
			ps.setInt(1, balanceId);
			rs = ps.executeQuery();
			if (rs.next()) {
				l = new Location(); // TODO pass entries to constructor
				locations.add(l);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw new StarbucksLocatorException("SQL exception thrown");
		}
		return locations;
	}

	@Override
	public boolean addLocations(Set<Location> locations, Connection con) throws StarbucksLocatorException {

		PreparedStatement ps = null;
		try {
			ps = con.prepareStatement(PS_ADD_LOCATION);
			for (Location l : locations) {
				ps.setDouble(1, l.getLng());
				ps.setDouble(2, l.getLat());
				ps.setString(3, l.getCity());
				ps.setString(4, l.getAddress());
				ps.addBatch();
			}
			int[] batchResult = ps.executeBatch();
			for (int i = 0; i < batchResult.length; i++) {
				if (batchResult[i] < 0) {
					return false;
				}
			}
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
			throw new StarbucksLocatorException("SQL exception thrown");
		} finally {
			try {
				if (ps != null) {
					ps.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
				throw new StarbucksLocatorException("SQL exception thrown");
			}
		}
	}
}
