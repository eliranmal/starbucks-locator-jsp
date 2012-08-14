package com.starbucks.locator.model.dataaccess;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Arrays;

import com.starbucks.locator.model.runtime.StarbucksLocatorException;

public class DBConnection {
	
	private static Connection con;
	
	public static Connection connect() throws StarbucksLocatorException {
		if (con != null) {
			return con;
		}
		try {
			Class.forName("org.apache.derby.jdbc.EmbeddedDriver");
//			con = DriverManager.getConnection("jdbc:derby:memory:StarbucksLocatorDB;create=true");
			con = DriverManager.getConnection("jdbc:derby:StarbucksLocatorDB");
		} catch (ClassNotFoundException e) {
			throw new StarbucksLocatorException("Driver init failed:\n" + Arrays.toString(e.getStackTrace()));
		} catch (SQLException e) {
			throw new StarbucksLocatorException("SQL exception thrown:\n" + Arrays.toString(e.getStackTrace()));
		}
		return con;
	}
}
