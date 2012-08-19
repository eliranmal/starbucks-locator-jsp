package com.starbucks.locator.util;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.starbucks.locator.model.dto.Location;

public class Helper {

	/*
	 * Database utilities
	 */

	/**
	 * Concludes the existence of a table by checking the SQL state.
	 * 
	 * @param e
	 *            The SQL exception thrown when trying to create a table.
	 * @return Whether the exception denotes an existing table.
	 */
	public static boolean isTableExist(SQLException e) {
		// NOTE: vendor-specific (derby)
		return e.getSQLState().equals("X0Y32");
	}

	/**
	 * Concludes the existence of a table by examining the connection's meta
	 * data.
	 * 
	 * @param conn
	 *            An established DB connection.
	 * @return Whether the table exists.
	 */
	public static boolean isTableExist(Connection conn) {
		ResultSet tables;
		try {
			tables = conn.getMetaData().getTables(null, // don't filter by
														// catalog
					"APP", // filter by APP schema
					DBConstants.TABLE_NAME.toUpperCase(), // table name as
															// appears in the DB
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
		double lat1 = a.getLat(), lng1 = a.getLng(), lat2 = b.getLat(), lng2 = b.getLng();
		return findDistance(lat1, lng1, lat2, lng2);
	}

	// calc assuming a sphere

	private static final double Rk = 6373; // mean radius of the earth (km) at
											// 39 degrees from the equator

	public static double findDistance(double lat1, double lng1, double lat2, double lng2) {
		double lat1Rad, lng1Rad, lat2Rad, lng2Rad, dlat, dlon, a, c, dk, km;

		// convert coordinates to radians
		lat1Rad = Math.toRadians(lat1);
		lng1Rad = Math.toRadians(lng1);
		lat2Rad = Math.toRadians(lat2);
		lng2Rad = Math.toRadians(lng2);

		// find the differences between the coordinates
		dlat = lat2Rad - lat1Rad;
		dlon = lng2Rad - lng1Rad;

		// here's the heavy lifting
		a = Math.pow(Math.sin(dlat / 2), 2) + Math.cos(lat1Rad) * Math.cos(lat2Rad)
				* Math.pow(Math.sin(dlon / 2), 2);
		c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a)); // great circle distance in radians
		dk = c * Rk; // great circle distance in km

		// round the results down to the nearest 1/1000
		km = round(dk);

		return km;
	}

	/**
	 * Rounds to the nearest 1/1000
	 */
	public static double round(double x) {
		return Math.round(x * 1000) / 1000;
	}

	// calc assuming a flat plain

/*	public static double pythagorian(double a, double b) {
		return Math.pow(a, 2) + Math.pow(b, 2);
	}

	public static double distance(Coord a, Coord b) {
		double x1 = a.getX(), x2 = b.getX(), y1 = a.getY(), y2 = b.getY();
		return Math.sqrt(pythagorian(x1 - x2, y1 - y2));
	}	
*/

	/*
	 * JSON utilities (generates leniently formatted json)
	 */
	
	public static String arrayToJson(Object[] arr) {
		
		if (arr == null) {
			return null;
		}
		
		StringBuilder json = new StringBuilder();
		
		json.append("[");
		for (Object o : arr) {
			Object value = o;

			String valueStr = "";
			if (value instanceof Object[]) {
				valueStr = Helper.arrayToJson((Object[]) value);
			} else {
				valueStr = String.valueOf(value);
				if (value instanceof String && valueStr.charAt(0) != '{') {
					valueStr = '"' + valueStr + '"';
				}
			}
			
			json.append(valueStr);
			json.append(",");
		}
		int index = json.length() - 1;
		if (json.charAt(index) == ',') {
			json.deleteCharAt(index);
		}
		json.append("]");
		
		return json.toString();
	}

	@SuppressWarnings("unchecked")
	public static String documentToJson(Map<String, Object> map) {

		if (map == null) {
			return null;
		}

		StringBuilder json = new StringBuilder();

		json.append("{");
		for (Entry<String, Object> e : map.entrySet()) {
			String key = e.getKey();
			Object value = e.getValue();

			String valueStr = "";
			if (value instanceof Map) {
				valueStr = Helper.documentToJson((Map<String, Object>) value);
			} else {
				valueStr = String.valueOf(value);
				if (value instanceof String) {
					valueStr = '"' + valueStr + '"';
				}
			}

			json.append('"');
			json.append(key);
			json.append("\":");
			json.append(valueStr);
			json.append(",");
		}
		int index = json.length() - 1;
		if (json.charAt(index) == ',') {
			json.deleteCharAt(index);
		}
		json.append("}");

		return json.toString();
	}

	public static <C> Map<String, Object> pojoToDocument(Object o, Class<C> clazz) {

		Map<String, Object> map = new HashMap<String, Object>();
		Class<? extends Object> rtCls = o.getClass();

		Field[] fields = rtCls.getDeclaredFields();
		try {
			for (int i = 0; i < fields.length; i++) {
				Field f = fields[i];
				String name = f.getName();
//				String methodName = "get" + name.substring(0, 1).toUpperCase()
//				+ name.substring(1, name.length());
				String methodName = "get" + name;
				Object value = rtCls.getMethod(methodName).invoke(o, new Object[] {});
				
				if (value instanceof Map) {
					pojoToDocument(o, clazz);
				}

				map.put(name, value);
			}
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		}

		return map;
	}



	/*
	 * testing
	 */
	public static void main(String[] args) {

/*		Location location = new Location(0.1, 0.2, "city", "addressssss");
		Map<String, Object> document = Helper.pojoToDocument(location, Location.class);
		String json = Helper.documentToJson(document);

		System.out.println("pojo:");
		System.out.println(location);
		System.out.println("document:");
		System.out.println(document);
		System.out.println("json:");
		System.out.println(json);
*/		
		List<Object> locations = new ArrayList<Object>();
		for (int i = 0; i < 10; i++) {
			Location l = new Location(i, i, "city " + i, "address " + i);
			Map<String, Object> document = Helper.pojoToDocument(l, Location.class);
			String json = Helper.documentToJson(document);
			locations.add(json);
		}
		String arrayToJson = Helper.arrayToJson(locations.toArray(new Object[locations.size()]));
		
		System.out.println("array:");
		System.out.println(arrayToJson);
		
	}

}
