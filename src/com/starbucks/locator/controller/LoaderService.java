package com.starbucks.locator.controller;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;

import com.starbucks.locator.model.runtime.AppBootstapper;

@SuppressWarnings("serial")
public class LoaderService extends HttpServlet {

	private AppBootstapper slb;
	
	@Override
	public void init() throws ServletException {
		super.init();
		slb = AppBootstapper.getInstance();
		slb.initDatabase();
	}

	@Override
	public void destroy() {
		super.destroy();
		slb.clearDatabase();
	}

}
