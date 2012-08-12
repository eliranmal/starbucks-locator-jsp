package com.starbucks.locator.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.starbucks.locator.util.SblConstants;

public class RoutingService extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public void init() throws ServletException {
	}

	protected void service(HttpServletRequest req, HttpServletResponse res)
			throws ServletException, IOException {

		String cmd = req.getParameter("cmd");

		String nextServlet;
		
		if (cmd == null || cmd.trim().isEmpty()) {
			return;
		}
		
		if (cmd.equals(SblConstants.REQ_PARAM_VALUE_LOCATE)) {
			
		}
		
		if (req.getParameter("load") == null) {
			try {
				c = (DispatchingCommand) Class.forName(
						"commands.dispatching." + cmd).newInstance();
				c.handle(req, res);
			} catch (InstantiationException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
			if (!req.getParameter("cmd").equals("Login")
					&& req.getParameter("status") != null
					&& req.getParameter("status").equals("show")) {
				nextServlet = "/StatusResponse";
			} else {
				nextServlet = "/ViewManager";
			}
		} else {
			if (req.getParameter("load").equals("false")) {
				nextServlet = "/ViewManager";
			} else {
				nextServlet = "/Loader";
			}
		}

		getServletContext().getRequestDispatcher(nextServlet).forward(req, res);
	}

}
