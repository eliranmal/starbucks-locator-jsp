package com.starbucks.locator.view;

import java.io.IOException;

import javax.servlet.Servlet;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.starbucks.locator.util.StarbucksLocatorConstants;

@SuppressWarnings("serial")
public class ViewManager extends HttpServlet implements Servlet {

	public void init() throws ServletException {
	}

	protected void service(HttpServletRequest request, HttpServletResponse response) {

		String cmd = request.getParameter(StarbucksLocatorConstants.REQ_PARAM_NAME_COMMAND);
		
/*		if (cmd.equals("contactPage")) {
		}
*/

		try {
			getServletContext().getRequestDispatcher("/index.jsp").forward(request, response);
		} catch (ServletException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

}
