package com.starbucks.locator.controller;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;

import com.starbucks.locator.model.runtime.StarbucksLocatorBootstapper;

@SuppressWarnings("serial")
public class LoaderService extends HttpServlet {

	private StarbucksLocatorBootstapper slb;
	
	@Override
	public void init() throws ServletException {
		super.init();
		slb = StarbucksLocatorBootstapper.getInstance();
		slb.initDatabase();
	}

	@Override
	public void destroy() {
		super.destroy();
		slb.clearDatabase();
	}

}
