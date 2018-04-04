package com.cadena.service;

import javax.annotation.security.PermitAll;
import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.cadena.dao.ILocacionDao;
import com.cadena.dao.LocationDaoImpl;
import com.cadena.model.Location;
import com.cadena.model.User;

//--Reactive Client API with JAX-RS2.1
@Path("/location")
@PermitAll
public class LocationService {
	ILocacionDao locationDao;

	public LocationService() {
		this.locationDao = new LocationDaoImpl();
	}
	/*
	 * public LocationServie() { // --this goes to mobile side:
	 * CompletionStage<Response> response =
	 * ClientBuilder.newClient().target("http://localhost:8080/service-url")
	 * .request().rx().get();
	 * 
	 * }
	 * 
	 * // --Asked by the mobile App.
	 * 
	 * @GET
	 * 
	 * @Path("/{id}")
	 * 
	 * @Produces(MediaType.APPLICATION_JSON) public Response
	 * getLatestLocation(@PathParam("id") String id) {
	 * 
	 * System.out.println("service reached"); // --TODO: Reading location data from
	 * database. return Response.ok("your id is: " + id).build();
	 * 
	 * }
	 */

	// --Sent from Cadena device
	// @PermitAll
	@PUT
	@Path("/set/{id}/{nmea}/{dateTime}")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	public Response addLatestLocation(@PathParam("id") String id, @PathParam("nmea") String nmea,
			@PathParam("dateTime") String dateTime) {
		try {
			locationDao.addLocation(nmea);
			return Response.ok("Location Added").build();
		} catch (Exception e) {
			System.out.println(e);
			return null;
		}

	}

	// @PermitAll
	@GET
	@Path("/get")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getLatestLocationObject() {
		try {
			Location location = locationDao.getLatestLocationObject();
			System.out.println("Location Service 76_________dateformate before serialization: "+location.getDateTime().toString());
			return Response.ok(location).build();
		} catch (Exception e) {
			System.out.println(e);
			return null;
		}

	}

}
