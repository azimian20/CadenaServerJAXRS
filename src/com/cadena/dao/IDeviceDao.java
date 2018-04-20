package com.cadena.dao;

import java.util.List;
import com.cadena.model.Device;

public interface IDeviceDao {
	public List<Device> getDevices(Long userId) throws Exception;

}
