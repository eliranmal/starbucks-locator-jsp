package com.starbucks.locator.model.dataaccess;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import com.starbucks.locator.model.runtime.StarbucksLocatorException;

public class DBConnection {
	
	private static Connection con;
	
	public static Connection connect() throws StarbucksLocatorException {
		if (con != null) {
			return con;
		}
		try {
			String starbucksLocatorHome = System.getenv("STARBUCKS_LOCATOR_HOME");
			
			Class.forName("org.apache.derby.jdbc.EmbeddedDriver");
			//			con = DriverManager.getConnection("jdbc:derby:memory:StarbucksLocatorDB;create=true");
			con = DriverManager.getConnection("jdbc:derby:" + starbucksLocatorHome + "/db/StarbucksLocatorDB");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			throw new StarbucksLocatorException("Driver init failed.");
		} catch (SQLException e) {
			e.printStackTrace();
			throw new StarbucksLocatorException("SQL exception thrown.");
		}
		return con;
	}
}
