package com.starbucks.locator.model.lifecycle;

import java.io.File;
import java.io.FileNotFoundException;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.swing.table.TableModel;

import com.starbucks.locator.model.dataaccess.DBConnection;
import com.starbucks.locator.model.dataaccess.DBLifecycleManagerImpl;
import com.starbucks.locator.model.dataaccess.LocationsManagerImpl;
import com.starbucks.locator.model.dto.Location;
import com.starbucks.locator.model.runtime.StarbucksLocatorException;
import com.starbucks.locator.util.AppConstants;
import com.starbucks.locator.util.CSVParser;
import com.starbucks.locator.util.DBConstants;
import com.starbucks.locator.util.Helper;

public class DBLifecycleProxyImpl implements DBLifecycleProxy {

	private static final String USA_STARBUCKS_FILE_PATH = System.getenv(AppConstants.SYS_ENV_VAR_APP_HOME) + "/resources/initial-data/";
	private static final String USA_STARBUCKS_FILE_NAME = "USA-Starbucks.csv";

	private static Connection _conn;
	

	public DBLifecycleProxyImpl() {
		try {
			_conn = DBConnection.connect();
		} catch (StarbucksLocatorException e1) {
			e1.printStackTrace();
		}
	}

	
	@Override
	public void bootstrapDatabase() {
		boolean tableCreated = false;
		try {
			tableCreated = DBLifecycleManagerImpl.getInstance().createLocationsTable(_conn);
		} catch (StarbucksLocatorException e) {
			e.printStackTrace();
		}
		
		if (tableCreated) {
			System.out.println("> table '" + DBConstants.TABLE_NAME + "' created.");
			try {
				boolean tablePopulated = populateLocationsTable(_conn);
				if (tablePopulated) {
					System.out.println("> table '" + DBConstants.TABLE_NAME + "' populated with initial data.");
				}
			} catch (StarbucksLocatorException e) {
				e.printStackTrace();
			}
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

		// parse to a data structure
		TableModel tableModel = null;
		try {
			// parse initial data from CSV file
			System.out.println("> parsing initial data from CSV file...");
			tableModel = CSVParser
					.parse(new File(USA_STARBUCKS_FILE_PATH + USA_STARBUCKS_FILE_NAME));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			return false;
		}

		if (tableModel == null) {
			return false;
		}
		
		// load initial data to memory
		List<Location> locationList = new ArrayList<Location>();
		for (int x = 0; x < tableModel.getRowCount(); x++) {
			Location l = new Location();
			for (int y = 0; y < tableModel.getColumnCount(); y++) {
				String columnName = tableModel.getColumnName(y);
				Object valObj = tableModel.getValueAt(x, y);
				populateLocationDTO(columnName, valObj, l);
			}
			locationList.add(l);
		}
		
		
		// filter duplicates (3 found)
		Set<Location> locationSet = new HashSet<Location>(locationList);
		int duplicatesCount = locationList.size() - locationSet.size();
		boolean containedDuplicates = duplicatesCount != 0;
		if (containedDuplicates) {
			System.out.println("> > > " + duplicatesCount
					+ " duplicate locations found, filtered by address as UID.");
		}
		
		System.out.println("> loading initial data to database...");
		return LocationsManagerImpl.getInstance().addLocations(locationSet, conn);
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

	@Override
	public void teardownDatabase() {
		try {
			boolean tableRemoved = removeLocationsTable(_conn);
			if (tableRemoved) {
				System.out.println("> table '" + DBConstants.TABLE_NAME + "' removed.");
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
		DBLifecycleManagerImpl.getInstance().dropLocationsTable(conn);
		return true;
	}

}
