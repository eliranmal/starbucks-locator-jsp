package com.starbucks.locator.model.dto;

public class Location {

	private String _address;
	private String _city;
	private double _lat;
	private double _lng;

	public Location() {
		super();
	}
	
	public Location(String address, String city, double lat, double lng) {
		super();
		_address = address;
		_city = city;
		_lat = lat;
		_lng = lng;
	}

	public String get_address() {
		return _address;
	}

	public void set_address(String _address) {
		this._address = _address;
	}

	public String get_city() {
		return _city;
	}

	public void set_city(String _city) {
		this._city = _city;
	}

	public double get_lat() {
		return _lat;
	}

	public void set_lat(double _lat) {
		this._lat = _lat;
	}

	public double get_lng() {
		return _lng;
	}

	public void set_lng(double _lng) {
		this._lng = _lng;
	}

}