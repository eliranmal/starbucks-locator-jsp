package com.starbucks.locator.model.lifecycle;


public class AppLifecycleFacade {

	/*
	 * singleton instance
	 */
	private static final AppLifecycleFacade _sli = new AppLifecycleFacade();
	
	private DBLifecycleManager _dblf;

	/*
	 * prevent initialization
	 */
	private AppLifecycleFacade() {
		assertSysEvnVarsSet();
		_dblf = new DBLifecycleManagerImpl();
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
	public static AppLifecycleFacade getInstance() {
		return _sli;
	}
	
	public void bootstrapDatabase() {
		_dblf.bootstrapDatabase();
	}

	public void teardownDatabase() {
		_dblf.teardownDatabase();
	}
	
}
