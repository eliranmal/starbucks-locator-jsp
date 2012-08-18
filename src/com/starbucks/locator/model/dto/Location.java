package com.starbucks.locator.model.dto;

public class Location implements Comparable<Location> {

	private double _lng;
	private double _lat;
	private String _city;
	private String _address;
	
	/**
	 * Represented by the address field.
	 */
	private int _uid;

	public Location() {
		_lng = 0.0;
		_lat = 0.0;

		postConstruct();
	}

	public Location(double lng, double lat, String city, String address) {
		_lng = lng;
		_lat = lat;
		_city = city;
		_address = address;
		
		postConstruct();
	}
	
	private void postConstruct() {
		_uid = this.hashCode();
	}

	public int getUid() {
		return _uid;
	}

	public double getLng() {
		return _lng;
	}

	public void setLng(double lng) {
		_lng = lng;
	}

	public double getLat() {
		return _lat;
	}

	public void setLat(double lat) {
		_lat = lat;
	}

	public String getCity() {
		return _city;
	}

	public void setCity(String city) {
		_city = city;
	}

	public String getAddress() {
		return _address;
	}

	public void setAddress(String address) {
		_address = address;
	}

	@Override
	public String toString() {
		return "Location [_lng=" + _lng + ", _lat=" + _lat + ", _city=" + _city + ", _address="
				+ _address + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((_address == null) ? 0 : _address.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Location other = (Location) obj;
		if (_address == null) {
			if (other._address != null)
				return false;
		} else if (!_address.equals(other._address))
			return false;
		return true;
	}

	@Override
	public int compareTo(Location o) {
		return o.equals(this) ? 0 : -1;
	}

}