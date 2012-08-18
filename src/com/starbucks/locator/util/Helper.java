package com.starbucks.locator.util;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Helper {

	/*
	 * Database utilities
	 */

	/**
	 * Concludes the existence of a table by checking the SQL state.
	 * 
	 * @param e The SQL exception thrown when trying to create a table.
	 * @return Whether the exception denotes an existing table.
	 */
	public static boolean isTableExist(SQLException e) {
		// NOTE: vendor-specific (derby)
		return e.getSQLState().equals("X0Y32");
	}

	/**
	 * Concludes the existence of a table by examining the connection's meta data.
	 * 
	 * @param conn An established DB connection.
	 * @return Whether the table exists.
	 */
	public static boolean isTableExist(Connection conn) {
		ResultSet tables;
		try {
			tables = conn.getMetaData().getTables(
					null, // don't filter by catalog
					"APP", // filter by APP schema
					DBConstants.TABLE_NAME.toUpperCase(), // table name as appears in the DB
					new String[] { "TABLE" } // filter by TABLE type
					);
			if (tables.next()) {
				return true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
		return false;
	}
	
	
	/*
	 * Math utilities
	 */

	public static double findDistance(Coord a, Coord b) {
		double lat1 = a.getLat(),
		       lng1 = a.getLng(),
		       lat2 = b.getLat(),
		       lng2 = b.getLng();
		return findDistance(lat1, lng1, lat2, lng2);
	}
	
	
	// calc on a sphere

	private static final double Rk = 6373; // mean radius of the earth (km) at 39 degrees from the equator
		
	public static double findDistance(double lat1, double lng1, double lat2, double lng2) {
		double lat1Rad, lon1Rad, lat2Rad, lon2Rad, dlat, dlon, a, c, dk, km;

		// convert coordinates to radians
		lat1Rad = Math.toRadians(lat1);
		lon1Rad = Math.toRadians(lng1);
		lat2Rad = Math.toRadians(lat2);
		lon2Rad = Math.toRadians(lng2);

		// find the differences between the coordinates
		dlat = lat2Rad - lat1Rad;
		dlon = lon2Rad - lon1Rad;

		// here's the heavy lifting
		a = Math.pow(Math.sin(dlat / 2), 2) + Math.cos(lat1Rad) * Math.cos(lat2Rad)
				* Math.pow(Math.sin(dlon / 2), 2);
		c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a)); // great circle distance in radians
		dk = c * Rk; // great circle distance in km

		// round the results down to the nearest 1/1000
		km = round(dk);

		return km;
	}

	// round to the nearest 1/1000
	public static double round(double x) {
		return Math.round(x * 1000) / 1000;
	}

	
	// calc on a flat plain
	
	/*	public static double pythagorian(double a, double b) {
			return Math.pow(a, 2) + Math.pow(b, 2);
		}
		
		public static double distance(Coord a, Coord b) {
			double x1 = a.getX(),
			       x2 = b.getX(),
			       y1 = a.getY(),
			       y2 = b.getY();
			return Math.sqrt(pythagorian(x1 - x2, y1 - y2));
		}
	*/	


}
