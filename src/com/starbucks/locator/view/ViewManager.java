package com.starbucks.locator.view;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServlet;

import com.starbucks.locator.model.dto.Transferable;
import com.starbucks.locator.util.AppConstants;

@SuppressWarnings("serial")
public class ViewManager extends HttpServlet {

	@Override
	public void service(ServletRequest req, ServletResponse res) throws ServletException,
			IOException {

		String cmd = req.getParameter(AppConstants.REQ_PARAM_NAME_COMMAND);

		Object ajaxRes = req.getAttribute("ajaxResponse");
		Transferable resText = (Transferable) ajaxRes;

		// TODO format resText if necessary
		
		PrintWriter out;
		out = res.getWriter();
		out.write(resText.asString());
		out.flush();

	}

}
