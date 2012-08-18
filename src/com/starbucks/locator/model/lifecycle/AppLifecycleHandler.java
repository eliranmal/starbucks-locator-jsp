package com.starbucks.locator.model.lifecycle;


public class AppLifecycleHandler {

	/*
	 * singleton instance
	 */
	private static final AppLifecycleHandler _sli = new AppLifecycleHandler();
	
	private DBLifecycleFacade _dblf;

	/*
	 * prevent initialization
	 */
	private AppLifecycleHandler() {
		assertSysEvnVarsSet();
		_dblf = new DBLifecycleFacadeImpl();
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
	public static AppLifecycleHandler getInstance() {
		return _sli;
	}
	
	public void bootstrapDatabase() {
		_dblf.bootstrapDatabase();
	}

	public void teardownDatabase() {
		_dblf.teardownDatabase();
	}
	
}
