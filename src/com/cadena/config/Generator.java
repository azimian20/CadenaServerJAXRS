package com.cadena.config;

import java.net.ResponseCache;
import java.util.Date;
import java.util.concurrent.CompletionStage;

import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.Form;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.hibernate.Session;
import org.hibernate.SessionFactory;

import com.cadena.model.User;

public class Generator {

	public static void main(String[] args) {
		/*
		 * HibernateConfig hConfig = new HibernateConfig(); Session session =
		 * hConfig.getTransactionManager().getSessionFactory().openSession(); User user
		 * = new User(); user.setUsername("jon"); user.setPassword(new
		 * BCryptPasswordEncoder().encode("123")); Authorities authorities = new
		 * Authorities(); authorities.setAuthority("ROLE_ADMIN"); Set<Authorities>
		 * authoritiesSet = new HashSet<>(); authoritiesSet.add(authorities);
		 * user.setAuthorities(authoritiesSet); session.save(user); session.close();
		 * System.out.println("user inserted"); System.out.println(new
		 * BCryptPasswordEncoder().encode("123"));
		 */
		// ----Service tester
		/*
		 * WebTarget locationTarget; String myId = locationTarget.resolveTemplate("id",
		 * 10).request().get(String.class);
		 * System.out.println("Service tested, responce is" + myId);
		 */

		/*
		 * CompletionStage<String> response = ClientBuilder.newClient()
		 * .target("http://localhost:8080/CadenaServerReactive/getLocation/2").request()
		 * .rx() .get(new GenericType<String>() { });
		 * System.out.println("Service tested, responce is" + response);
		 */

		try { // --calling Cadena sent service

			SessionFactory factory = HibernateConfig.getSessionFactory();

			String id = "id1";
			String nmea = "5956.6182N,1043.0844E";
			String dateTime = new Date().toString();
			Form form = new Form();
			form.param("id", "id_sent");
			form.param("coordinates", "coordinates_sent");
			form.param("information", "information_sent");
			String result = ClientBuilder.newClient()
//					.target("http://localhost:8080/CadenaServerJAXRS/rest/location/get?id=id1&nmea=1&dateTime=1")
					.target("http://localhost:8080/CadenaServerJAXRS/rest/location/get")
					.request().get(String.class);
			// .put(Entity.entity(form, MediaType.APPLICATION_FORM_URLENCODED_TYPE),
			// User.class);
			// .get(User.class);
			System.out.println("___ Service Response:" + result);
		} catch (Exception e) {
			e.printStackTrace();
		}

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
