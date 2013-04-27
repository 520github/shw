package com.babeeta.sheepcounter.manager.resource;

import javax.ws.rs.Consumes;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.babeeta.sheepcounter.entity.Url;
import com.babeeta.sheepcounter.manager.service.SheepCounterService;

@Path("/api/sheepcounter")
@Scope("prototype")
@Controller("sheepCounterResource")
public class SheepCounterResource {

	private SheepCounterService sheepCounterService;

	@Path("")
	@PUT
	@Consumes("application/json")
	@Produces("application/json")
	public Response saveUrl(Url url) {
		return Response.ok(sheepCounterService.saveUrl(url)).build();
	}

	@Autowired
	public void setSheepCounterService(SheepCounterService sheepCounterService) {
		this.sheepCounterService = sheepCounterService;
	}

}