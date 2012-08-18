package com.starbucks.locator.model.facade;

import com.starbucks.locator.model.lifecycle.DBLifecycleProxy;
import com.starbucks.locator.model.lifecycle.DBLifecycleProxyImpl;
import com.starbucks.locator.util.AppConstants;


public class AppLifecycleFacade {

	/*
	 * singleton instance
	 */
	private static final AppLifecycleFacade _sli = new AppLifecycleFacade();
	
	private DBLifecycleProxy _dblf;

	/*
	 * prevent initialization
	 */
	private AppLifecycleFacade() {
		assertSysEvnVarsSet();
		_dblf = new DBLifecycleProxyImpl();
	}
	
	private void assertSysEvnVarsSet() {

		String starbucksLocatorHome = System.getenv(AppConstants.SYS_ENV_VAR_APP_HOME);
		assert starbucksLocatorHome != null && !starbucksLocatorHome.trim().isEmpty();

		System.out.println("> " + AppConstants.SYS_ENV_VAR_APP_HOME + "=[" + starbucksLocatorHome + "]");
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
