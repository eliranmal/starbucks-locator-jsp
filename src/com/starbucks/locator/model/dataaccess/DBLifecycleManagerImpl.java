package com.starbucks.locator.model.dataaccess;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import com.starbucks.locator.model.runtime.StarbucksLocatorException;
import com.starbucks.locator.util.DBConstants;
import com.starbucks.locator.util.Helper;

public class DBLifecycleManagerImpl implements DBLifecycleManager {

	private static final DBLifecycleManagerImpl _dblm = new DBLifecycleManagerImpl();
	
	private static final String STMT_DROP_TABLE = "DROP TABLE " + DBConstants.TABLE_NAME;
	private static String STMT_CREATE_TABLE;


	private DBLifecycleManagerImpl() {
		initVariables();
	}

	public static DBLifecycleManagerImpl getInstance() {
		return _dblm;
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

	/**
	 * Performs a create operation.
	 * 
	 * @return {@code true} if action was performed successfully, {@code false} otherwise.
	 * @throws StarbucksLocatorException
	 */
	public boolean createLocationsTable(Connection conn) throws StarbucksLocatorException {
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
	 * Performs a {@code drop} operation.
	 * 
	 * @param conn
	 * @throws StarbucksLocatorException
	 */
	public void dropLocationsTable(Connection conn) throws StarbucksLocatorException {
		try {
			Statement stmt = conn.createStatement();
			stmt.executeUpdate(STMT_DROP_TABLE);
			stmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
			throw new StarbucksLocatorException("SQL exception thrown, could not drop table '"
					+ DBConstants.TABLE_NAME + "'.");
		}
	}
}
