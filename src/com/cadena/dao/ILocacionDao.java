package com.cadena.dao;

import com.cadena.model.Location;

public interface ILocacionDao {
	public String getLatestLocation() throws Exception;
	public Location getLatestLocationObject() throws Exception;

	public void addLocation(String nmea);

}
