package com.cadena.dao;

import java.util.Date;
import java.util.List;

import com.cadena.model.Location;
import com.cadena.model.LocationServiceResponse;

public interface ILocacionDao {
	public List<Location> getDeviceLatestLocations(Long deviceId);
	public void addLocation(String nmea, Date dateTime, String messageId, Long deviceId);
	public LocationServiceResponse getUserLatestLocations(Long userId);
}
