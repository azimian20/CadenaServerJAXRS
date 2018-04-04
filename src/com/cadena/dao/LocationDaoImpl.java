package com.cadena.dao;

import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import com.cadena.config.HibernateConfig;
import com.cadena.model.Location;

public class LocationDaoImpl implements ILocacionDao {

	SessionFactory factory = HibernateConfig.getSessionFactory();

	@SuppressWarnings("unchecked")
	@Transactional
	public String getLatestLocation() throws Exception {
		Session session = factory.openSession();
		List<Location> latetstLocations = (List<Location>) session
				.createQuery("from Location l where l.dateTime=(select MAX(l2.dateTime) from Location l2)").list();
		session.close();
		if (latetstLocations.isEmpty()) {
			return " location not found";
		} else if (latetstLocations.size() > 1) {
			throw new Exception("error executing query");
		} else {
			return latetstLocations.get(0).getgMapGps();
		}
	}
	
	@SuppressWarnings("unchecked")
	@Transactional
	public Location getLatestLocationObject() throws Exception {
		Session session = factory.openSession();
		List<Location> latetstLocations = (List<Location>) session
				.createQuery("from Location l where l.dateTime=(select MAX(l2.dateTime) from Location l2)").list();
		session.close();
		if (latetstLocations.isEmpty()) {
			throw new Exception("location not found");
		} else if (latetstLocations.size() > 1) {
			throw new Exception("error executing query");
		} else {
			return latetstLocations.get(0);
		}
	}

	@Transactional
	public void addLocation(String nmea) {
		Session session = factory.openSession();
		Transaction tx = session.beginTransaction();
		Location location = new Location();
		location.setDateTime(new Date());
		location.setNmeaGps(nmea);
		session.save(location);
		tx.commit();
		session.close();
	}

}
