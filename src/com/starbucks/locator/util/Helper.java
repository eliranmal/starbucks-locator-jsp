package com.starbucks.locator.util;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Helper {

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
	
}
