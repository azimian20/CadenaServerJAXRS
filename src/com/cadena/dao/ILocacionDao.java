package com.cadena.dao;

public interface ILocacionDao {
	public String getLatestLocation() throws Exception;

	public void addLocation(String nmea);

}
