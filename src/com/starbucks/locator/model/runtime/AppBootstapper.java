package com.starbucks.locator.model.runtime;

import com.starbucks.locator.model.bootstrap.DBLoader;

public class AppBootstapper {

	private static final AppBootstapper sli = new AppBootstapper();

	/**
	 * prevent initialization
	 */
	private AppBootstapper() {
		assertSysEvnVarsSet();
		DBLoader.getInstance();
	}
	
	private void assertSysEvnVarsSet() {

		String starbucksLocatorHome = System.getenv("STARBUCKS_LOCATOR_HOME");
		assert starbucksLocatorHome != null && !starbucksLocatorHome.trim().isEmpty();
		System.out.println("> STARBUCKS_LOCATOR_HOME=[" + starbucksLocatorHome + "]");
	}

	/**
	 * provide single point access
	 * @return
	 */
	public static AppBootstapper getInstance() {
		return sli;
	}
	
	public void initDatabase() {
		DBLoader.bootstrapDatabase();
	}

	public void clearDatabase() {
		DBLoader.clearDatabase();
	}
	
}
