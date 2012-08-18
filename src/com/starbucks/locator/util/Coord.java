package com.starbucks.locator.util;

public class Coord {

	private double _lat;
	private double _lng;

	public Coord(double lat, double lng) {
		_lat = lat;
		_lng = lng;
	}

	public double getLat() {
		return _lat;
	}

	public double getLng() {
		return _lng;
	}

}
