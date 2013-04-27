package com.babeeta.appstore.manager.resource;

import java.util.Date;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.QueryParam;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.babeeta.appstore.entity.Broadcast;
import com.babeeta.appstore.manager.service.BroadcastController;
import com.babeeta.appstore.manager.service.BroadcastService;

@Path("/api/broadcast")
@Controller
public class BroadcastResource {

	@Autowired
	private AuthenticationProvider authenticationProvider;
	@Autowired
	private BroadcastService broadcastService;

	@Path("/")
	@POST
	@Consumes("application/json")
	public Broadcast createNew(Broadcast broadcast) {
		broadcast.setDate(new Date());
		broadcast.setOperator(authenticationProvider.getUsername());
		BroadcastController broadcastController = broadcastService
				.createNewBroadcast(broadcast);
		return broadcastController.getBroadcast();
	}

	@Path("/")
	@GET
	public PagedResult<Broadcast> findAll(@QueryParam("limit") int limit,
			@QueryParam("offset") int offset) {
		return broadcastService.findAll(limit, offset);
	}

	@Path("/{id}/command")
	@POST
	public Broadcast runCommand(@PathParam("id") String id, String command) {
		BroadcastController broadcastController = broadcastService
				.findBroadcastControllerById(id);
		broadcastController.run(command);
		return broadcastController.getBroadcast();
	}
}
