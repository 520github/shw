package com.babeeta.appstore.resource;

import java.util.Map;

import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.babeeta.appstore.entity.Device;
import com.babeeta.appstore.entity.Marker;
import com.babeeta.appstore.entity.MarkerCounter;
import com.babeeta.appstore.entity.MarkerDomain;
import com.babeeta.appstore.service.DeviceRegistService;
import com.babeeta.appstore.service.MarkerService;

/**
 * 标记应用
 * 
 * @author chongf
 * 
 */
@Path("/")
@Scope(value = "prototype")
@Controller("markerResource")
public class MarkerResource {

	/**
	 * 判断token格式是否合法
	 * 
	 * @param token
	 * @throws WebApplicationException
	 */
	private static String extractToken(String token)
			throws Exception {
		if (token == null || !token.startsWith("Token")) {
			throw new Exception();
		}
		return token.replace("Token ", "");
	}

	private static JsonObject reduceMarker(final Marker m,
			final MarkerCounter mc) {
		@SuppressWarnings("unused")
		JsonObject o = new JsonObject() {
			public Map<String, Long> getCounter() {
				return mc == null ? null : mc.getCounter();
			}

			public String getCurrentValue() {
				return m == null ? null : m.getValue();
			}
		};
		return o;
	}

	private DeviceRegistService deviceRegistService;

	private MarkerService markerService;

	@Path("/marker/{domain}/{id}")
	@GET
	@Produces("application/json")
	public Response getMarker(@HeaderParam("Authorization") String token,
			@PathParam("domain") String domain, @PathParam("id") String id) {
		Device device = null;
		try {
			token = extractToken(token);
			device = deviceRegistService.findDeviceByToken(token);
			if (device == null) {
				throw new Exception();
			}
		} catch (Exception e) {
			return Response.status(Status.UNAUTHORIZED).build();
		}
		Marker m = markerService.getMark(device.getId(), domain, id);
		MarkerCounter mc = markerService.getMarkerCounter(domain, id);
		return Response.ok(reduceMarker(m, mc)).build();
	}

	@Path("/marker/{domain}/{id}")
	@DELETE
	@Produces("application/json")
	public Response removeMarker(@HeaderParam("Authorization") String token,
			@PathParam("domain") String domain, @PathParam("id") String id) {
		Device device = null;
		try {
			token = extractToken(token);
			device = deviceRegistService.findDeviceByToken(token);
			if (device == null) {
				throw new Exception();
			}
		} catch (Exception e) {
			return Response.status(Status.UNAUTHORIZED).build();
		}
		markerService.removeMarker(device.getId(), domain, id,
				MarkerDomain.CounterMethod.device);
		return Response.ok().build();
	}

	@Autowired
	@Required
	public void setDeviceRegistService(DeviceRegistService deviceRegistService) {
		this.deviceRegistService = deviceRegistService;
	}

	@Autowired
	@Required
	public void setMarkerService(MarkerService markerService) {
		this.markerService = markerService;
	}

	@Path("/marker/{domain}/{id}")
	@PUT
	@Produces("application/json")
	public Response submitMarker(@HeaderParam("Authorization") String token,
			@PathParam("domain") String domain, @PathParam("id") String id,
			String value) {
		Device device = null;
		try {
			token = extractToken(token);
			device = deviceRegistService.findDeviceByToken(token);
			if (device == null) {
				throw new Exception();
			}
		} catch (Exception e) {
			return Response.status(Status.UNAUTHORIZED).build();
		}
		if (value == null || (!value.equals("positive")
				&& !value.equals("negative"))) {
			return Response.status(400).build();
		}
		markerService.mark(device.getId(), domain, id, value,
				MarkerDomain.CounterMethod.device);
		return Response.ok().build();
	}

}