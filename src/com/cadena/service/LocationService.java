package com.cadena.service;

import java.util.Date;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;
import java.util.stream.Collectors;
import javax.annotation.security.PermitAll;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.container.AsyncResponse;
import javax.ws.rs.container.Suspended;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.apache.log4j.Logger;
import org.glassfish.jersey.server.Uri;
import com.cadena.config.Util;
import com.cadena.dao.DeviceDaoImpl;
import com.cadena.dao.IDeviceDao;
import com.cadena.dao.ILocacionDao;
import com.cadena.dao.LocationDaoImpl;
import com.cadena.model.Device;
import com.cadena.model.DeviceLocations;
import com.cadena.model.Location;
import com.cadena.model.LocationServiceResponse;

//--Reactive Client API with JAX-RS2.1
@Path("/location")
@PermitAll
public class LocationService {
	ILocacionDao locationDao;
	IDeviceDao deviceDao;
	Logger log = Logger.getLogger(LocationService.class);

	@Uri("location/getDevices/{userId}") // --Mainly for internal service usage
	private WebTarget deviceTarget;

	@Uri("location/getLocations/{deviceId}") // --Mainly for internal service usage
	private WebTarget deviceLocationTarget;

	public LocationService() {
		this.locationDao = new LocationDaoImpl();
		this.deviceDao = new DeviceDaoImpl();
	}

	// --Sent from Cadena device(Might be able to use Rx features for streaming)
	// @PermitAll
	@PUT
	@Path("/set/{deviceId}/{messageId}/{nmea}/{dateTime}")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	public Response addLatestLocation(@PathParam("deviceId") Long deviceId, @PathParam("messageId") String messageId,
			@PathParam("nmea") String nmea, @PathParam("dateTime") String dateTime) {
		try {
			Date deviceDate = Util.dateFormatter(dateTime);
			locationDao.addLocation(nmea, deviceDate, messageId, deviceId);
			return Response.ok("Location Added").build();
		} catch (Exception e) {
			log.error(e.getMessage());
			return Response.ok("Failed").build();
		}
	}

	@GET
	@Path("/get/{userId}")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	public void getUserLastLocations(@PathParam("userId") Long userId, @Suspended final AsyncResponse async) {
		long startTime = System.currentTimeMillis();
log.info("----------service request received: "+deviceTarget.getUri().toString());
		CompletionStage<List<Device>> deviceCS = deviceTarget.resolveTemplate("userId", userId).request().rx()
				.get(new GenericType<List<Device>>() {
				});
		log.info("---------log");
		final CompletionStage<List<DeviceLocations>> deviceLocationsCS = deviceCS.thenCompose(devices -> {
			log.info("---------log_devices sieze:"+devices.size());
			List<CompletionStage<DeviceLocations>> deviceLocationsList = devices.stream().map(device -> {
				log.info("---------log");
				final CompletionStage<List<Location>> tempCS = deviceLocationTarget
						.resolveTemplate("deviceId", device.getIdLong()).request().rx()
						.get(new GenericType<List<Location>>() {
						});
				log.info("---------log");
				return CompletableFuture.completedFuture(new DeviceLocations(device)).thenCombine(tempCS,
						DeviceLocations::setLocations);
			}).collect(Collectors.toList());
			log.info("---------log");
			return CompletableFuture
					.allOf(deviceLocationsList.toArray(new CompletableFuture[deviceLocationsList.size()]))
					.thenApply(v -> deviceLocationsList.stream().map(CompletionStage::toCompletableFuture)
							.map(CompletableFuture::join).collect(Collectors.toList()));
		});
		log.info("---------log");
		CompletableFuture.completedFuture(new LocationServiceResponse())
				.thenCombine(deviceLocationsCS, LocationServiceResponse::deviceLocationses)
				.whenCompleteAsync((response, throwable) -> {
					response.setProcessingTime(System.currentTimeMillis() - startTime);
					async.resume(response);
				});
		log.info("---------log");
	}

	@GET
	@Path("/get2/{userId}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getUserLastLocations2(@PathParam("userId") Long userId) {
		try {
			long startTime = System.currentTimeMillis();
			LocationServiceResponse locationServiceResponse = locationDao.getUserLatestLocations(userId);
			locationServiceResponse.setProcessingTime(System.currentTimeMillis() - startTime);
			return Response.ok(locationServiceResponse).build();
		} catch (Exception e) {
			log.error(e.getMessage());
			return null;
		}
	}

	@GET
	@Path("/getDevices/{userId}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getDevices(@PathParam("userId") Long userId) {
		log.info("---------getDevices Service Started");
		try {
			List<Device> devices = deviceDao.getDevices(userId);
			Response response = Response.ok(devices).build();
			return response;
		} catch (Exception e) {
			log.error(e.getMessage());
			return null;
		}
	}

	@GET
	@Path("/getLocations/{deviceId}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getDeviceLatestLocations(@PathParam("deviceId") Long deviceId) {
		try {
			List<Location> locations = locationDao.getDeviceLatestLocations(deviceId);
			return Response.ok(locations).build();
		} catch (Exception e) {
			log.error(e.getMessage());
			return null;
		}
	}
}
