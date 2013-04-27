package com.babeeta.appstore.manager.resource;

import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.babeeta.appstore.entity.App;
import com.babeeta.appstore.manager.service.AppCatcherService;

@Path("/api/app/catcher")
@Scope("prototype")
@Controller("appCatcherResource")
public class AppCatcherResource {

	private AppCatcherService appCatcherService;

	@Path("")
	@PUT
	@Produces("application/json")
	public Response catchApp(String url) {
		App app = appCatcherService.catchIt(url);
		if (app == null) {
			return Response.status(400).build();
		}
		return Response.ok(app).build();
	}

	@Autowired
	public void setAppCatcherService(AppCatcherService appCatcherService) {
		this.appCatcherService = appCatcherService;
	}

}