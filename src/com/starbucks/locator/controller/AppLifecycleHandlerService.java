package com.starbucks.locator.controller;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;

import com.starbucks.locator.model.facade.AppLifecycleFacade;

@SuppressWarnings("serial")
public class AppLifecycleHandlerService extends HttpServlet {

	private AppLifecycleFacade slb;
	
	@Override
	public void init() throws ServletException {
		super.init();
		slb = AppLifecycleFacade.getInstance();
		slb.bootstrapDatabase();
	}

	@Override
	public void destroy() {
		super.destroy();
		slb.teardownDatabase();
	}

}
