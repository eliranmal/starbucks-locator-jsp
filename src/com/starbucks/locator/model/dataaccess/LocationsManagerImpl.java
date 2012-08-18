package com.starbucks.locator.model.dataaccess;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import com.starbucks.locator.model.dto.Location;
import com.starbucks.locator.model.runtime.StarbucksLocatorException;
import com.starbucks.locator.util.DBConstants;

public class LocationsManagerImpl implements LocationsManager {

	private static final LocationsManagerImpl ldbm = new LocationsManagerImpl();
	
	private static final String PS_GET_LOCATION = "SELECT * FROM " + DBConstants.TABLE_NAME
			+ " WHERE " + DBConstants.COL_NAME_ADDRESS + " = ?";
	private static final String PS_GET_LOCATIONS = "SELECT * FROM " + DBConstants.TABLE_NAME;
	private static String PS_ADD_LOCATION;

	private LocationsManagerImpl() {
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
	}

	public static LocationsManagerImpl getInstance() {
		return ldbm;
	}
	
	@Override
	public Location getLocation(String address, Connection con) throws StarbucksLocatorException {
		PreparedStatement ps;
		ResultSet rs;
		Location l = null;
		try {
			ps = con.prepareStatement(PS_GET_LOCATION);
			ps.setString(1, address);
			rs = ps.executeQuery();
			if (rs.next()) {
				double lng = rs.getDouble(1);
				double lat = rs.getDouble(2);
				String city = rs.getString(3);
				l = new Location(lng, lat, city, address);
			}
			rs.close();
			ps.close();
		} catch (SQLException e) {
			e.printStackTrace();
			throw new StarbucksLocatorException("SQL exception thrown");
		}
		return l;
	}
	
	@Override
	public List<Location> getLocations(Connection con) throws StarbucksLocatorException {
		Statement stmt;
		ResultSet rs;
		Location l = null;
		List<Location> locations = new ArrayList<Location>();
		try {
			stmt = con.createStatement();
			rs = stmt.executeQuery(PS_GET_LOCATIONS);
			while (rs.next()) {
				double lng = rs.getDouble(1);
				double lat = rs.getDouble(2);
				String city = rs.getString(3);
				String addr = rs.getString(4);
				l = new Location(lng, lat, city, addr);
				locations.add(l);
			}
			rs.close();
			stmt.close();
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
			ps.close();
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
			throw new StarbucksLocatorException("SQL exception thrown");
		}
	}
}
