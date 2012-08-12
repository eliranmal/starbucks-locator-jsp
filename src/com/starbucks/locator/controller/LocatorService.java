package com.starbucks.locator.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class LocatorService extends HttpServlet {

	@Override
	protected void service(HttpServletRequest req, HttpServletResponse res)
			throws ServletException, IOException {
//		super.service(req, res);
		
		String paramAddress = req.getParameter("address");
		
		System.out.println(paramAddress);
		
		
	}

	
}
