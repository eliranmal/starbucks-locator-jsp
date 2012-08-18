package com.starbucks.locator.model.lifecycle;

import java.io.File;
import java.io.FileNotFoundException;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.swing.table.TableModel;

import com.starbucks.locator.model.dataaccess.DBConnection;
import com.starbucks.locator.model.dataaccess.LocationsDBManager;
import com.starbucks.locator.model.dto.Location;
import com.starbucks.locator.model.runtime.StarbucksLocatorException;
import com.starbucks.locator.util.CSVParser;
import com.starbucks.locator.util.DBConstants;
import com.starbucks.locator.util.Helper;

public class DBLifecycleFacadeImpl implements DBLifecycleFacade {

	private static final String USA_STARBUCKS_FILE_PATH = System.getenv("STARBUCKS_LOCATOR_HOME") + "/resources/initial-data/";
	private static final String USA_STARBUCKS_FILE_NAME = "USA-Starbucks.csv";
	private static final String STMT_DROP_TABLE = "DROP TABLE " + DBConstants.TABLE_NAME;
	private static String STMT_CREATE_TABLE;

	private static Connection _conn;
	

	public DBLifecycleFacadeImpl() {
		initVariables();
		try {
			_conn = DBConnection.connect();
		} catch (StarbucksLocatorException e1) {
			e1.printStackTrace();
		}
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
		sb.append(DBConstants.COL_NAME_ADDRESS);
		sb.append("))");

		STMT_CREATE_TABLE = sb.toString();
	}

	@Override
	public void bootstrapDatabase() {
		boolean tableCreated = false;
		try {
			tableCreated = createLocationsTable(_conn);
		} catch (StarbucksLocatorException e) {
			e.printStackTrace();
		}
		
		if (tableCreated) {
			System.out.println("> table " + DBConstants.TABLE_NAME + " created.");
			try {
				boolean tablePopulated = populateLocationsTable(_conn);
				if (tablePopulated) {
					System.out.println("> table " + DBConstants.TABLE_NAME + " populated with initial data.");
				}
			} catch (StarbucksLocatorException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * Creates the locations table.
	 * 
	 * @return {@code true} if action was performed successfully, {@code false} otherwise.
	 * @throws StarbucksLocatorException
	 */
	private static boolean createLocationsTable(Connection conn) throws StarbucksLocatorException {
		boolean tableExist = Helper.isTableExist(conn);
		if (tableExist) {
			return false;
		}
		try {
			Statement stmt = conn.createStatement();
			stmt.executeUpdate(STMT_CREATE_TABLE);
			stmt.close();
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
			throw new StarbucksLocatorException("SQL exception thrown, could not create table");
		}
	}

	/**
	 * Populates the locations table with initial data from a CSV file.
	 * 
	 * @param conn
	 * @return {@code true} if action was performed successfully, {@code false} otherwise.
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
		
		List<Location> locationList = new ArrayList<Location>();
		for (int x = 0; x < tableModel.getRowCount(); x++) {
			Location l = new Location();
			for (int y = 0; y < tableModel.getColumnCount(); y++) {
				String columnName = tableModel.getColumnName(y);
				Object valObj = tableModel.getValueAt(x, y);
				populateLocationDTO(columnName, valObj, l);
			}
//			System.out.println("location: " + l);
			locationList.add(l);
		}
		
		
		// filter duplicates (3 found)
		Set<Location> locationSet = new HashSet<Location>(locationList);
		int duplicatesCount = locationList.size() - locationSet.size();
		boolean containedDuplicates = duplicatesCount != 0;
		if (containedDuplicates) {
			System.out.println("> > > > > " + duplicatesCount
					+ " duplicate locations found, filtered by address as UID :)");
		}
		
		return LocationsDBManager.getInstance().addLocations(locationSet, conn);
	}

	private static void populateLocationDTO(String columnName, Object valObj, Location l) {
		
		if (valObj == null) {
			return;
		}
		
		String val = String.valueOf(valObj);
		if (columnName.equalsIgnoreCase(DBConstants.COL_NAME_LNG)) {
			l.setLng(Double.valueOf(val));
		} else if (columnName.equalsIgnoreCase(DBConstants.COL_NAME_LAT)) {
			l.setLat(Double.valueOf(val));
		} else if (columnName.equalsIgnoreCase(DBConstants.COL_NAME_CITY)) {
			l.setCity(val);
		} else if (columnName.equalsIgnoreCase(DBConstants.COL_NAME_ADDRESS)) {
			l.setAddress(val);
		}
	}

	public void teardownDatabase() {
		try {
			boolean tableRemoved = removeLocationsTable(_conn);
			if (tableRemoved) {
				System.out.println("> table " + DBConstants.TABLE_NAME + " removed.");
			}
		} catch (StarbucksLocatorException e) {
			e.printStackTrace();
		}
		
	}

	/**
	 * Invokes {@link #dropLocationsTable(Connection)} only if the table exists.
	 * 
	 * @param conn
	 * @return {@code true} if action was performed successfully, {@code false} otherwise.
	 * @throws StarbucksLocatorException
	 */
	public static boolean removeLocationsTable(Connection conn) throws StarbucksLocatorException {
		boolean tableExist = Helper.isTableExist(conn);
		if (!tableExist) {
			return false;
		}
		dropLocationsTable(conn);
		return true;
	}

	/**
	 * Performs a {@code drop} operation.
	 * 
	 * @param conn
	 * @throws StarbucksLocatorException
	 */
	private static void dropLocationsTable(Connection conn) throws StarbucksLocatorException {
		try {
			Statement stmt = conn.createStatement();
			stmt.executeUpdate(STMT_DROP_TABLE);
			stmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
			throw new StarbucksLocatorException("SQL exception thrown, could not drop table "
					+ DBConstants.TABLE_NAME + ".");
		}
	}
}
