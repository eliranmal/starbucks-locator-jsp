package com.starbucks.locator.controller.commands.impl.async;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.starbucks.locator.controller.commands.AsyncCommand;
import com.starbucks.locator.model.dto.Transferable;

public class GetLocations implements AsyncCommand {

	@Override
	public Transferable handle(HttpServletRequest req, HttpServletResponse res) throws ServletException,
			IOException {
		
		return null;
	}

}
