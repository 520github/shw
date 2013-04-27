package com.babeeta.appstore.manager.resource;

import java.util.Calendar;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.babeeta.appstore.entity.BestApp;
import com.babeeta.appstore.manager.service.BestAppService;

@Path("/api/bestapp")
@Controller("bestappResource")
@Scope("prototype")
public class BestAppResource {
	private BestAppService bestAppService;

	@Path("/")
	@POST
	@Consumes("text/plain")
	public Response addBestApp(String id) {
		if (id != null && id.trim().length() > 0) {
			BestApp b = new BestApp();
			b.setId(id.trim());
			b.setCreateTime(Calendar.getInstance().getTime());
			bestAppService.save(b);
		}
		return Response.ok().build();
	}

	@Path("/")
	@DELETE
	@Consumes("text/plain")
	public Response delete(String id) {
		bestAppService.delete(id);
		return Response.ok().build();
	}

	@Path("/")
	@GET
	@Produces("application/json")
	public Response getTask() {
		return Response.ok(bestAppService.findAList()).build();
	}

	@Autowired
	public void setBestAppService(BestAppService bestAppService) {
		this.bestAppService = bestAppService;
	}

}
