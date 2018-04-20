package com.cadena.config;

import java.net.Inet4Address;
import java.net.UnknownHostException;
import java.util.Date;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.GenericType;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import com.cadena.model.Device;
import com.cadena.model.Location;
import com.cadena.model.LocationServiceResponse;
import com.cadena.model.User;

public class Generator {

	//static Log log = LogFactory.getLog(Generator.class);

	public static void main(String[] args) {

		// --Networking:
		try {
			System.out.println("IP Adress:" + Inet4Address.getLocalHost().getHostAddress());
		} catch (UnknownHostException e1) {
			e1.printStackTrace();
		}
		
//		insertTestData();
//		loadTestData();
		
		
		// ----Service tester

//		 try { // --calling Cadena sent service (blocking)
//			 List<LocationServiceResponse> result = ClientBuilder.newClient()
//				 .target("http://localhost:8088/CadenaServerJAXRS/rest/location/get/27").request().get(new GenericType<List<LocationServiceResponse>>() {});
//		 System.out.println("___ Service Response:" + result);
//		 } catch (Exception e) {
//			 e.printStackTrace();
//		 }
		
		
		
		 	final class ObjectPrinter{
		 		ObjectPrinter printObject(List<LocationServiceResponse> devices) {
					 System.out.println("96");
				 for (LocationServiceResponse device : devices) {
					 System.out.println("98");
					System.out.println(device.getDeviceLocationses());
				 }
				 	return this;
			 	}
			 public void setData(String val){
				 System.out.println(val);
			 	}
		 	}
		 	
		 	Client client = ClientBuilder.newClient();
			 WebTarget locationTarget =
			 client.target("http://localhost:8088/CadenaServerJAXRS/rest/location/get/27");
			 CompletionStage<List<LocationServiceResponse>> locationCS =
			 locationTarget.request().rx()
			 .get(new GenericType<List<LocationServiceResponse>>() {});

			CompletableFuture.completedFuture(new ObjectPrinter())
				.thenCombine(locationCS, ObjectPrinter::printObject)
				.whenCompleteAsync((response, throwable) -> {
					response.setData(System.currentTimeMillis()+"");
					throwable.printStackTrace();
				});

		
				
//		Response response = ClientBuilder.newClient()
//				.target("http://localhost:8088/CadenaServerJAXRS/rest/location/getDevices/26")
//				.request().get();
//		System.out.println(response);

	}

	private static void loadTestData() {
		SessionFactory factory = HibernateConfig.getSessionFactory();
		Session session = factory.openSession();
		List<User> devices=session.createQuery("from User").list();
		for (User device : devices) {
			System.out.println(device.getUsername());
		}
		session.close();
	}
	private static void insertTestData() {
		SessionFactory factory = HibernateConfig.getSessionFactory();
		Session session = factory.openSession();
		Transaction tx = session.beginTransaction();

		User jon = new User();
		jon.setfName("Jon");
		jon.setlName("Ferei");
		jon.setUsername("jon");
		jon.setPassword("123");
		session.save(jon);

		Device cadena1 = new Device();
		cadena1.setName("jon's cadena 1");
		cadena1.setPhoneNumber("+4798845911");
		cadena1.setUser(jon);
		session.save(cadena1);

		for (int i = 1; i <= 10; i++) {
			Location location = new Location();
			location.setDateTime(new Date());
			location.setgMapGps("test GPS 1 data " + i);
			location.setNmeaGps("test NMEA 1 data " + i);
			location.setMessageId("hard 1 inserted " + i);
			location.setDeviceId(cadena1.getIdLong());
			session.save(location);
		}

		Device cadena2 = new Device();
		cadena2.setName("jon's cadena 2");
		cadena2.setPhoneNumber("+4793973504");
		cadena2.setUser(jon);
		session.save(cadena2);

		for (int i = 1; i <= 10; i++) {
			Location location = new Location();
			location.setDateTime(new Date());
			location.setgMapGps("test GPS 2 data " + i);
			location.setNmeaGps("test NMEA 2 data " + i);
			location.setMessageId("hard 2 inserted " + i);
			location.setDeviceId(cadena2.getIdLong());
			session.save(location);
		}

		tx.commit();
		session.close();
	}

	/*
	 * 
	 * insert into T_DOMAINENTITY(id,C_DESCRIPTION) values(1,'user admin'); insert
	 * into T_PERSON(id,C_LNAME,C_FNAME) values(1,'Ferei', 'jon'); insert into
	 * T_USER(id,C_USERNAME,C_PASSWORD) values(1, 'jon',
	 * '$2a$10$JwJtPkeEWBjctypZv7mwheRIb5JwXdKtRRBucNdnZuKRpUuFLKutG'); insert into
	 * T_DOMAINENTITY(id,C_DESCRIPTION) values(2,'role admin'); insert into
	 * T_AUTHORITIES(id,C_AUTHORITY,C_USER) values(2,'ROLE_ADMIN',1);
	 */

}

