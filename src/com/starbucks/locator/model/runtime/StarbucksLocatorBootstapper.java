package com.starbucks.locator.model.runtime;

import com.starbucks.locator.model.bootstrap.DBLoader;

public class StarbucksLocatorBootstapper {

	private static final StarbucksLocatorBootstapper sli = new StarbucksLocatorBootstapper();

	/**
	 * prevent initialization
	 */
	private StarbucksLocatorBootstapper() {
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
	public static StarbucksLocatorBootstapper getInstance() {
		return sli;
	}
	
	public void initDatabase() {
		DBLoader.bootstrapDatabase();
	}

	public void clearDatabase() {
		DBLoader.clearDatabase();
	}
	
}
