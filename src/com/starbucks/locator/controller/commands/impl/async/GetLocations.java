package com.starbucks.locator.controller.commands.impl.async;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.starbucks.locator.controller.commands.AsyncCommand;
import com.starbucks.locator.model.dto.Location;
import com.starbucks.locator.model.dto.Transferable;
import com.starbucks.locator.model.facade.AppActionsFacade;

public class GetLocations implements AsyncCommand {

	@Override
	public Transferable handle(HttpServletRequest req, HttpServletResponse res) throws ServletException,
			IOException {
		
		AppActionsFacade actions = AppActionsFacade.getInstance();
		
		List<Location> locations = actions.getLocations();
		Location[] locationsArray = locations.toArray(new Location[locations.size()]);
		
		final String locationsArrayStr = Arrays.toString(locationsArray);
		
		return new Transferable() {
			
			@Override
			public String asString() {
				return locationsArrayStr;
			}
		};
	}

}
