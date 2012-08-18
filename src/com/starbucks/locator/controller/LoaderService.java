package com.starbucks.locator.controller;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;

import com.starbucks.locator.model.lifecycle.AppLifecycleHandler;

@SuppressWarnings("serial")
public class LoaderService extends HttpServlet {

	private AppLifecycleHandler slb;
	
	@Override
	public void init() throws ServletException {
		super.init();
		slb = AppLifecycleHandler.getInstance();
		slb.bootstrapDatabase();
	}

	@Override
	public void destroy() {
		super.destroy();
		slb.teardownDatabase();
	}

}
