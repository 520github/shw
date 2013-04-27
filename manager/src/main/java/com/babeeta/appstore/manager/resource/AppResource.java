package com.babeeta.appstore.manager.resource;

import java.util.Date;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.babeeta.appstore.dao.EntityNotFoundException;
import com.babeeta.appstore.entity.App;
import com.babeeta.appstore.entity.App.AppStatus;
import com.babeeta.appstore.manager.service.AppService;
import com.babeeta.appstore.manager.service.LockConflictException;

@Path("/api/app")
@Scope("prototype")
@Controller("appResource")
public class AppResource {

	private AppService appService;
	private AuthenticationProvider authenticationProvider;

	@Path("awaiting/{id}/status")
	@PUT
	@Produces("application/json")
	public void audit(@PathParam("id") String id, String status) {
		if ("published".equals(status)) {
			appService.approve(id);
		} else {
			appService.refuse(id);
		}
	}

	@Path("{id}/escapeRank")
	@PUT
	@Produces("application/json")
	public Response escapeRank(@PathParam("id") String id) {
		App app = appService.find(id);
		if (app != null) {
			app.setEscapeRank(app.isEscapeRank() ? false : true);
			appService.toggleSave(app);
		} else {
			return Response.ok(Status.BAD_REQUEST).build();
		}
		return Response.ok(app).build();
	}

	@Path("{status:processed|unprocessed|published}/{id}")
	@GET
	@Produces("application/application+json")
	public App find(@PathParam("id") String id) {
		App app = appService.find(id);
		if (app != null) {
			return appService.find(id);
		} else {
			throw new EntityNotFoundException(App.class, id);
		}
	}

	@Path("all/")
	@GET
	@Produces("application/json")
	public PagedResult<App> findAll(@QueryParam("appid") String appId,
			@QueryParam("catalog") String catalog,
			@QueryParam("language") String language,
			@QueryParam("keyword") String keyword,
			@QueryParam("offset") int offset, @QueryParam("limit") int limit) {
		return new PagedResult<App>().setResult(
				appService.findAll(appId, catalog, language,
						keyword.toLowerCase(), offset, limit)).setTotal(
				appService.countAll(appId, catalog, language,
						keyword.toLowerCase()));

	}

	@Path("awaiting/")
	@GET
	@Produces("application/json")
	public PagedResult<App> findAwaiting(@QueryParam("search") String search,
			@QueryParam("offset") int offset, @QueryParam("limit") int limit) {
		return new PagedResult<App>().setResult(
				appService.findAwaiting(search, offset, limit)).setTotal(
				appService.countAwaiting(search));
	}

	@Path("processed/")
	@GET
	@Produces("application/json")
	public PagedResult<App> findProcessed(@QueryParam("search") String search,
			@QueryParam("catalog") String catalog,
			@QueryParam("offset") int offset, @QueryParam("limit") int limit) {
		return new PagedResult<App>().setResult(
				appService.findProcessed(search, catalog, offset, limit))
				.setTotal(appService.countProcessed(search, catalog));
	}

	@Path("published/")
	@GET
	@Produces("application/json")
	public PagedResult<App> findPushlished(@QueryParam("search") String search,
			@QueryParam("catalog") String catalog,
			@QueryParam("offset") int offset, @QueryParam("limit") int limit) {
		return new PagedResult<App>().setResult(
				appService.findPublished(search, catalog, offset, limit))
				.setTotal(appService.countPublished(search, catalog));
	}

	@Path("unprocessed/")
	@GET
	@Produces("application/json")
	public PagedResult<App> findUnprocessed(
			@QueryParam("search") String search,
			@QueryParam("offset") int offset, @QueryParam("limit") int limit) {
		return new PagedResult<App>().setResult(
				appService.findUnprocessed(search, offset, limit)).setTotal(
				appService.countUnprocessed(search));
	}

	@Path("unprocessed/{id}/editor")
	@PUT
	@Produces("application/json")
	public App lock(@PathParam("id") String id) {
		return appService.lock(id, authenticationProvider.getUsername());
	}

	@Path("best/{id}")
	@PUT
	@Produces("application/json")
	public Response perfectRecommend(@PathParam("id") String id) {
		App app = appService.find(id);
		if (app != null
				&& (app.getStatus().equals(App.AppStatus.published) || app
						.getStatus().equals(App.AppStatus.disabled))) {
			app.setBest(app.isBest() ? false : true);
			if (app.isBest()) {
				app.setBestLastModified(new Date());
			} else {
				app.setBestLastModified(null);
			}
			appService.toggleSave(app);
		} else {
			return Response.ok(Status.BAD_REQUEST).build();
		}
		return Response.ok(app).build();
	}

	@Path("unprocessed/{id}/releaseEdit")
	@PUT
	public void releaselock(@PathParam("id") String id) {
		appService.release(id, authenticationProvider.getUsername());
	}

	@Path("{status:(un)?processed}/{id}")
	@DELETE
	public void remove(@PathParam("id") String id) {
		App app = appService.find(id);
		if (app != null) {
			if (app.getStatus().equals(App.AppStatus.unprocessed)
					&& app.getEditor() != null
					&& !app.getEditor().equals(
							authenticationProvider.getUsername())) {
				throw new LockConflictException(app.getEditor());
			}
			appService.markRemove(id);
		}
	}

	@Path("published/{id}")
	@DELETE
	public void removePublished(@PathParam("id") String id) {
		App app = appService.find(id);
		if (AppStatus.disabled == app.getStatus()) {
			appService.markRemove(id);
		}
	}

	@Path("revert/{id}")
	@PUT
	@Produces("application/json")
	public Response revert(@PathParam("id") String id) {
		App app = appService.find(id);
		if (app == null) {
			throw new EntityNotFoundException(App.class, app.getId());
		}

		if (app.getStatus() == AppStatus.deleted) {
			app.setStatus(AppStatus.unprocessed);
			appService.save(app);
		}

		return Response.ok().build();

	}

	@Path("{status:processed|unprocessed|awaiting|published}/{id}")
	@PUT
	@Consumes({ "application/*+json", "application/json" })
	public void saveAppDescription(App app, @PathParam("id") String id) {
		App org = appService.find(id);
		if (org == null) {
			throw new EntityNotFoundException(App.class, app.getId());
		}

		org.setDescription(app.getDescription());
		org.setCatalog(app.getCatalog());
		org.setReview(app.getReview());
		org.setIntroduction(app.getIntroduction());
		org.setBrand(app.getBrand());
		org.setKeywords(app.getKeywords());

		org.setGroup(app.getGroup().equals("") ? null : app.getGroup());

		if (AppStatus.unprocessed == org.getStatus()
				|| AppStatus.refused == org.getStatus()) {
			org.setStatus(AppStatus.awaiting);
		}

		appService.save(org);
	}

	@Autowired
	@Required
	public void setAppService(AppService appService) {
		this.appService = appService;
	}

	@Autowired
	@Required
	public void setAuthenticationProvider(
			AuthenticationProvider authenticationProvider) {
		this.authenticationProvider = authenticationProvider;
	}

	@Path("published/{id}/status")
	@PUT
	@Produces("application/json")
	public void togglePushlishedStatus(@PathParam("id") String id, String status) {
		if ("published".equals(status)) {
			appService.published(id);
		} else {
			appService.disable(id);
		}
	}

	@Path("awaiting/{id}/note")
	@PUT
	public void updateNote(@PathParam("id") String id, String note) {
		appService.updateNote(id, note);
	}

}