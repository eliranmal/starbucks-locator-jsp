package com.starbucks.locator.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.starbucks.locator.controller.commands.AsyncCommand;
import com.starbucks.locator.model.dto.Transferable;

@SuppressWarnings("serial")
public class GenericAsyncService extends HttpServlet {

	protected void service(HttpServletRequest req, HttpServletResponse res)
			throws IOException, ServletException {
		String cmd = req.getParameter("asyncCommand");
		AsyncCommand c;
		try {
			c = (AsyncCommand) Class.forName("commands.impl.async." + cmd)
					.newInstance();
			Transferable resObj = c.handle(req, res);
			
			req.setAttribute("ajaxResponse", resObj);

			getServletContext().getRequestDispatcher("/ViewManager").forward(req, res);
			
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
}
