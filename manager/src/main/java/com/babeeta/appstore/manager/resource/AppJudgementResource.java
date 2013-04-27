package com.babeeta.appstore.manager.resource;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Response;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.babeeta.appstore.entity.AppJudgement;
import com.babeeta.appstore.manager.service.AppJudgementService;

@Path("/api/app/judgement")
@Scope("prototype")
@Controller("appJudgementResource")
public class AppJudgementResource {

	private AppJudgementService appJudgementService;
	private AuthenticationProvider authenticationProvider;

	@Path("/")
	@GET
	@Produces("application/json")
	public Response findAll(@QueryParam("search") String search,
			@QueryParam("offset") int offset, @QueryParam("limit") int limit) {
		PagedResult<AppJudgement> pr = new PagedResult<AppJudgement>()
				.setResult(
						appJudgementService.findAppJudgements(search, limit,
								offset)).setTotal(
						appJudgementService.countAll(search));
		return Response.ok(pr).build();
	}

	@Path("/{id}")
	@GET
	@Produces("application/json")
	public Response getById(@PathParam("id") String id) {
		return Response.ok(appJudgementService.findById(id)).build();
	}

	@Path("/")
	@PUT
	@Consumes("application/json")
	public Response save(AppJudgement aj) {
		aj.setOperator(authenticationProvider.getUsername());
		if (aj.getId() != null) {
			AppJudgement org = appJudgementService.findById(aj.getId());
			if (org == null) {
				return Response.status(Response.Status.BAD_REQUEST).build();
			}
			org.setTitle(aj.getTitle());
			org.setDesc(aj.getDesc());
			org.setBegin(aj.getBegin());
			org.setEnd(aj.getEnd());
			appJudgementService.save(org);
		} else {
			appJudgementService.save(aj);
		}

		return Response.ok().build();
	}

	@Required
	@Autowired
	public void setAppJudgementService(AppJudgementService appJudgementService) {
		this.appJudgementService = appJudgementService;
	}

	@Required
	@Autowired
	public void setAuthenticationProvider(
			AuthenticationProvider authenticationProvider) {
		this.authenticationProvider = authenticationProvider;
	}

	@Path("/available/{id}")
	@PUT
	public Response toggleAvailable(@PathParam("id") String id) {
		AppJudgement aj = appJudgementService.findById(id);
		aj.setAvailable(!aj.isAvailable());
		appJudgementService.save(aj);
		return Response.ok(aj).build();
	}
}
