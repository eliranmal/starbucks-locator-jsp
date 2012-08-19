package com.starbucks.locator.model.dto;

public class Location implements Comparable<Location> {

	/*
	 * initial caps naming for the sake of pojo-document(-json) parsing, see Helper class under json utils.
	 */
	private double Lng;
	private double Lat;
	private String City;
	private String Address;

	
	/**
	 * default initialization
	 */
	public Location() {
		this.Lng = 0.0;
		this.Lat = 0.0;
		this.City = "";
		this.Address = "";
	}

	public Location(double lng, double lat, String city, String address) {
		this.Lng = lng;
		this.Lat = lat;
		this.City = city;
		this.Address = address;
	}

	
	public double getLng() {
		return Lng;
	}

	public void setLng(double lng) {
		this.Lng = lng;
	}

	public double getLat() {
		return Lat;
	}

	public void setLat(double lat) {
		this.Lat = lat;
	}

	public String getCity() {
		return City;
	}

	public void setCity(String city) {
		this.City = city;
	}

	public String getAddress() {
		return Address;
	}

	public void setAddress(String address) {
		this.Address = address;
	}

	@Override
	public String toString() {
		return "Location [lng=" + Lng + ", lat=" + Lat + ", city=" + City + ", address=" + Address
				+ "]";
	}


	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((Address == null) ? 0 : Address.hashCode());
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
		if (Address == null) {
			if (other.Address != null)
				return false;
		} else if (!Address.equals(other.Address))
			return false;
		return true;
	}

	@Override
	public int compareTo(Location o) {
		return o.equals(this) ? 0 : -1;
	}

}