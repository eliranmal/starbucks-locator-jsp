package com.starbucks.locator.controller.commands.impl.async;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.starbucks.locator.controller.commands.AsyncCommand;
import com.starbucks.locator.model.dto.Location;
import com.starbucks.locator.model.dto.Transferable;
import com.starbucks.locator.model.facade.AppActionsFacade;
import com.starbucks.locator.util.AppConstants;
import com.starbucks.locator.util.Helper;

public class GetLocations implements AsyncCommand {

	@Override
	public Transferable handle(HttpServletRequest req, HttpServletResponse res) throws ServletException,
			IOException {
		
		res.setContentType("application/json");
		
		String lat = req.getParameter(AppConstants.REQ_PARAM_NAME_LAT);
		String lng = req.getParameter(AppConstants.REQ_PARAM_NAME_LNG);
		
		AppActionsFacade actions = AppActionsFacade.getInstance();
		
		List<Location> locations = actions.getLocations();
		List<Location> filteredLocations = new ArrayList<Location>();
		
		// filter by adjacency
		for (int i = 0; i < locations.size(); i++) {
			Location l = locations.get(i);
			// lng -122.62832 lat 45.44467
			double distance = Helper.findDistance(Double.parseDouble(lat), Double.parseDouble(lng), l.getLat(), l.getLng());
			if (distance <= AppConstants.FILTER_RANGE_RADIUS_KM) {
				filteredLocations.add(l);
			}
		}
		// TODO consider move range filter to a stored procedure to avoid polling all entries


		List<Object> flattenedLocations = new ArrayList<Object>();
		for (int i = 0; i < filteredLocations.size(); i++) {
			Location l = filteredLocations.get(i);
			Map<String, Object> document = Helper.pojoToDocument(l, Location.class);
			String json = Helper.documentToJson(document);
			flattenedLocations.add(json);
		}
		final String jsonResponse = Helper.arrayToJson(flattenedLocations.toArray(new Object[flattenedLocations.size()]));
		
		return new Transferable() {
			
			@Override
			public String asString() {
				return "{\"data\":" + jsonResponse + "}";
			}
		};
	}

}
