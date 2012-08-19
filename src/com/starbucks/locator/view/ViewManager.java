package com.starbucks.locator.view;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServlet;

@SuppressWarnings("serial")
public class ViewManager extends HttpServlet {

	@Override
	public void service(ServletRequest req, ServletResponse res) throws ServletException,
			IOException {

		// not used - ajax redirects back with no dynamic updates to the document
	}

}
