package com.babeeta.appstore.resource;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Response;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.babeeta.appstore.entity.App;
import com.babeeta.appstore.service.AppReduceService;
import com.babeeta.appstore.service.AppService;

/**
 * 搜索
 * 
 * @author chongf
 * 
 */
@Path("/search")
@Scope(value = "prototype")
@Controller("searchResource")
public class SearchResource {

	private AppReduceService appReduceService;

	private AppService appService;

	@Path("/")
	@GET
	@Produces("application/json")
	public Response search(@QueryParam("keyword") String keyword,
			@QueryParam("offset") int offset,
			@QueryParam("limit") int limit) {
		if (keyword == null || "null".equalsIgnoreCase(keyword)) {
			return Response.status(Response.Status.BAD_REQUEST).build();
		}
		limit = limit > 100 ? 100 : limit == 0 ? 20 : limit;
		int total = appService.countSearch(keyword);
		List<App> appList = appService.search(keyword, offset, limit);
		if (appList == null) {
			appList = new ArrayList<App>();
			total = 0;
		}
		Object o = appReduceService.getAppPageList(
				appList, total,
				(total <= (offset + limit)), null);
		return Response.ok(o).build();
	}

	@Autowired
	@Required
	public void setAppReduceService(AppReduceService appReduceService) {
		this.appReduceService = appReduceService;
	}

	@Autowired
	@Required
	public void setAppService(AppService appService) {
		this.appService = appService;
	}
}
