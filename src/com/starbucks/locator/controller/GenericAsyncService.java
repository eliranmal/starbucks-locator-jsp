package com.starbucks.locator.controller;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.starbucks.locator.controller.commands.AsyncCommand;
import com.starbucks.locator.model.dto.Transferable;
import com.starbucks.locator.util.AppConstants;

@SuppressWarnings("serial")
public class GenericAsyncService extends HttpServlet {

	protected void service(HttpServletRequest req, HttpServletResponse res)
			throws IOException, ServletException {
		String cmd = req.getParameter(AppConstants.REQ_PARAM_NAME_COMMAND);
		AsyncCommand c;
		try {
			c = (AsyncCommand) Class.forName("com.starbucks.locator.controller.commands.impl.async." + cmd)
					.newInstance();
			Transferable resObj = c.handle(req, res);
	
			PrintWriter out;
			out = res.getWriter();
			out.write(resObj.asString());
			out.flush();

//			req.setAttribute(AppConstants.REQ_ATTR_NAME_AJAX_RES, resObj);
//			getServletContext().getRequestDispatcher("/ViewManager").forward(req, res);
			
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
}
