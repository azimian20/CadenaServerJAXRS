package com.cadena.dao;

import java.util.List;
import javax.transaction.Transactional;

import org.apache.log4j.Logger;
import org.hibernate.Hibernate;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import com.cadena.config.HibernateConfig;
import com.cadena.model.Device;

public class DeviceDaoImpl implements IDeviceDao {

	SessionFactory factory = HibernateConfig.getSessionFactory();
	Logger log = Logger.getLogger(DeviceDaoImpl.class);
	// final static Logger log = Logger.getLogger(DeviceDaoImpl.class);

	@SuppressWarnings("unchecked")
	@Transactional
	public List<Device> getDevices(Long userId) {
		Session session = factory.openSession();
		log.info("-----log_userId:" + userId);
		try {
			List<Device> devices = session.createQuery("from Device d where d.user.id=:userId")
					.setParameter("userId", userId).list();
			for (Device device : devices) {
				Hibernate.initialize(device.getUser());
			}
			log.info("-----devices size:" + devices);
			Thread.sleep(500);
			return devices;
		} catch (HibernateException e) {
			// log.error(e.getMessage());
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
