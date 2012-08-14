package com.starbucks.locator.model.bootstrap;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.starbucks.locator.model.dataaccess.DBConnection;
import com.starbucks.locator.model.runtime.StarbucksLocatorException;

public class DBLoader {

	public static void loadDB() {

		Connection conn;
		try {
			String[] tableNames = { "tableA", "tableB" };
			String[] createTableStmts = new String[] { "alter table create 'starbucks-locator-locations'" };
			conn = DBConnection.connect();
			for (int ctr = 0; ctr < tableNames.length; ctr++) {
				PreparedStatement pStmt = conn
						.prepareStatement("SELECT t.starbucks-locator-locations FROM sys.systables t WHERE t.starbucks-locator-locations = ?");
				pStmt.setString(1, tableNames[ctr]);
				ResultSet rs = pStmt.executeQuery();
				if (!rs.next()) {
					// Create the table
					Statement stmt = conn.createStatement();
					stmt.executeUpdate(createTableStmts[ctr]);
					stmt.close();
				}
				rs.close();
				pStmt.close();
			}
		} catch (SQLException e) {
			throw new RuntimeException("Problem starting the app...", e);
		} catch (StarbucksLocatorException e) {
			e.printStackTrace();
		}
	}

}
