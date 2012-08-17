package com.starbucks.locator.model.bootstrap;

import java.io.File;
import java.io.FileNotFoundException;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.table.TableModel;

import com.starbucks.locator.model.dataaccess.DBConnection;
import com.starbucks.locator.model.runtime.StarbucksLocatorException;
import com.starbucks.locator.util.CSVParser;
import com.starbucks.locator.util.DBConstants;
import com.starbucks.locator.util.Helper;

public class DBLoader {

	private static final DBLoader dbl = new DBLoader();
	
	private static final String USA_STARBUCKS_FILE_PATH = System.getenv("STARBUCKS_LOCATOR_HOME") + "/resources/initial-data/";
	private static final String USA_STARBUCKS_FILE_NAME = "USA-Starbucks.csv";
	private static String STMT_CREATE_TABLE;

	/**
	 * prevent initialization
	 */
	private DBLoader() {
		initVariables();
	}

	/**
	 * provide single point access
	 * @return
	 */
	public static DBLoader getInstance() {
		return dbl;
	}
	
	private void initVariables() {
		StringBuilder sb = new StringBuilder();
		sb.append("CREATE TABLE ");
		sb.append(DBConstants.TABLE_NAME);
		sb.append(" (");
		sb.append(DBConstants.COL_NAME_LNG);
		sb.append(" DOUBLE, ");
		sb.append(DBConstants.COL_NAME_LAT);
		sb.append(" DOUBLE, ");
		sb.append(DBConstants.COL_NAME_CITY);
		sb.append(" VARCHAR(200), ");
		sb.append(DBConstants.COL_NAME_ADDRESS);
		sb.append(" VARCHAR(400), ");
		sb.append("PRIMARY KEY (");
		sb.append(DBConstants.COL_NAME_LNG);
		sb.append(",");
		sb.append(DBConstants.COL_NAME_LAT);
		sb.append("))");

		STMT_CREATE_TABLE = sb.toString();
	}

	public static void bootstrapDatabase() {
		Connection conn;
		try {
			conn = DBConnection.connect();

			boolean tableCreated = false;
			try {
				tableCreated = createLocationsTable(conn);
			} catch (StarbucksLocatorException e) {
				e.printStackTrace();
			}
			
			if (tableCreated) {
				System.out.println("> table created...");
				try {
					boolean tablePopulated = populateLocationsTable(conn);
					if (tablePopulated) {
						System.out.println("> table populated with initial data...");
					}
				} catch (StarbucksLocatorException e) {
					e.printStackTrace();
				}
			}

		} catch (StarbucksLocatorException e1) {
			e1.printStackTrace();
		}
	}

	/**
	 * Creates the locations table.
	 * 
	 * @return {@code true} if a new table was created, {@code false} otherwise.
	 * @throws StarbucksLocatorException
	 */
	private static boolean createLocationsTable(Connection conn) throws StarbucksLocatorException {
		try {
			Statement stmt = conn.createStatement();
			stmt.executeUpdate(STMT_CREATE_TABLE);
			stmt.close();
			return true;
		} catch (SQLException e) {
			boolean tableAlreadyExist = Helper.isTableExist(e);
			if (tableAlreadyExist) {
				return false;
			}
			e.printStackTrace();
			throw new StarbucksLocatorException("SQL exception thrown, could not create table");
		}
	}

	/**
	 * Populates the locations table with initial data from a CSV file.
	 * 
	 * @param conn
	 * @return {@code true} if the table was populated, {@code false} otherwise.
	 * @throws StarbucksLocatorException
	 */
	private static boolean populateLocationsTable(Connection conn) throws StarbucksLocatorException {

		TableModel tableModel = null;
		try {
			// parse initial data from CSV file
			tableModel = CSVParser
					.parse(new File(USA_STARBUCKS_FILE_PATH + USA_STARBUCKS_FILE_NAME));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			return false;
		}

		if (tableModel == null) {
			return false;
		}
		
/*		// Print all the columns of the table, followed by a new line.
		for (int x = 0; x < t.getColumnCount(); x++) {
			System.out.print(t.getColumnName(x) + " ");
		}
		System.out.println();

		// Print all the data from the table.
		for (int x = 0; x < t.getRowCount(); x++) {
			for (int y = 0; y < t.getColumnCount(); y++) {
				System.out.print(t.getValueAt(x, y) + " ");
			}
			System.out.println();
		}
*/		
		return true;
	}

	public static void clearDatabase() {
		// TODO Auto-generated method stub
		
	}
}
