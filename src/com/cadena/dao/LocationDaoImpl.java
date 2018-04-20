package com.cadena.dao;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.transaction.Transactional;

import org.apache.log4j.Logger;
import org.hibernate.Hibernate;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import com.cadena.config.HibernateConfig;
import com.cadena.model.Device;
import com.cadena.model.DeviceLocations;
import com.cadena.model.Location;
import com.cadena.model.LocationServiceResponse;

public class LocationDaoImpl implements ILocacionDao {

	final static Logger log = Logger.getLogger(LocationDaoImpl.class);
	SessionFactory factory = HibernateConfig.getSessionFactory();

	// @SuppressWarnings("unchecked")
	// @Transactional
	// public Location getLatestLocationObject() throws Exception {
	// Session session = factory.openSession();
	// List<Location> latetstLocations = (List<Location>) session
	// .createQuery("from Location l where l.dateTime=(select MAX(l2.dateTime) from
	// Location l2)").list();
	// session.close();
	// if (latetstLocations.isEmpty()) {
	// throw new Exception("location not found");
	// } else if (latetstLocations.size() > 1) {
	// throw new Exception("error executing query");
	// } else {
	// return latetstLocations.get(0);
	// }
	// }

	@Transactional
	public void addLocation(String nmea, Date dateTime, String messageId, Long deviceId) {
		Session session = factory.openSession();
		Transaction tx = session.beginTransaction();
		Location location = new Location();
		location.setDateTime(dateTime);
		location.setNmeaGps(nmea);
		location.setDeviceId(deviceId);
		location.setMessageId(messageId);
		session.save(location);
		tx.commit();
		session.close();
	}

	@Transactional
	@SuppressWarnings("unchecked")
	public List<Location> getDeviceLatestLocations(Long deviceId) {
		Session session = factory.openSession();
		try {

			List<Location> latetstLocations = (List<Location>) session
					.createQuery("from Location l where l.deviceId=:deviceId order by l.dateTime desc")
					.setParameter("deviceId", deviceId).setMaxResults(10).list();
			Thread.sleep(500);
			return latetstLocations;
		} catch (HibernateException e) {
			log.error(e.getMessage());
			return new ArrayList<>();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return new ArrayList<>();
		} finally {
			session.close();
		}

	}

	@Transactional
	@SuppressWarnings("unchecked")
	public LocationServiceResponse getUserLatestLocations(Long userId) {
		Session session = factory.openSession();
		try {
			LocationServiceResponse locationServiceResponse = new LocationServiceResponse();
			List<Device> userDevices = (List<Device>) session.createQuery("from Device d where d.user.id=:userId")
					.setParameter("userId", userId).setMaxResults(10).list();
			List<DeviceLocations> deviceLocationses = new ArrayList<>();
			for (Device device : userDevices) {
				Hibernate.initialize(device.getUser());
				DeviceLocations deviceLocations = new DeviceLocations(device);
				deviceLocations.setLocations((List<Location>) session.createQuery("from Location l where l.deviceId=:deviceId")
								.setParameter("deviceId", device.getIdLong()).list());
				deviceLocationses.add(deviceLocations);
			}

			locationServiceResponse.deviceLocationses(deviceLocationses);
			Thread.sleep(1000);
			return locationServiceResponse;
		} catch (HibernateException e) {
			e.printStackTrace();
			return null;
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		} finally {
			session.close();
		}

	}
}
