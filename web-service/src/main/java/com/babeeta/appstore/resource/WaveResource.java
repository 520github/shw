package com.babeeta.appstore.resource;

import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

/**
 * 摇摇乐
 * 
 * @author papertiger
 * 
 */
@Path("/random")
@Scope(value = "prototype")
@Controller("waveResource")
public class WaveResource {

	@Path("/url")
	@GET
	@Produces("application/json")
	public Response getRandomApp() {
		return Response.status(HttpServletResponse.SC_MOVED_TEMPORARILY)
				.header("Location", "http://android-api-0.shanhubay.com/wave")
				.build();
	}
}
