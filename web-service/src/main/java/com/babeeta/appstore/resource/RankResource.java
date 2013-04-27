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
import com.babeeta.appstore.entity.Catalog;
import com.babeeta.appstore.entity.Catalog.AppViewType;
import com.babeeta.appstore.service.AppReduceService;
import com.babeeta.appstore.service.AppService;

/**
 * 排行榜
 * 
 * @author leon
 * 
 */
@Path("/rank")
@Controller("rankResource")
@Scope("prototype")
public class RankResource {

	private AppReduceService appReduceService;

	private AppService appService;

	/**
	 * 最高评分的排行榜
	 * 
	 * @return
	 */
	@Path("hot")
	@GET
	@Produces({ "application/app-simple-list+json" })
	public Response getRankByHot(@QueryParam("offset") int offset,
			@QueryParam("limit") int limit) {
		limit = limit > 100 ? 100 : limit == 0 ? 20 : limit;
		List<App> apps = appService.topApp("hot", offset, limit);
		long total = appService.countAllPublished();
		boolean lastPage = (total <= (offset + limit));
		List<Catalog.TabView> views = new ArrayList<Catalog.TabView>();
		views.add(new Catalog.TabView("最热", AppViewType.scrollList, "", true));
		views.add(new Catalog.TabView("最新", AppViewType.scrollList, "", false));
		return Response.ok(
				appReduceService.getAppPageList(apps, total, lastPage, views))
				.build();
	}

	/**
	 * 最新更新时间的排行榜
	 * 
	 * @return
	 */
	@Path("new")
	@GET
	@Produces({ "application/app-simple-list+json" })
	public Response getRankByNew(@QueryParam("offset") int offset,
			@QueryParam("limit") int limit) {
		limit = limit > 100 ? 100 : limit == 0 ? 20 : limit;
		List<App> apps = appService.topApp("new", offset, limit);
		long total = appService.countAllPublished();
		boolean lastPage = (total <= (offset + limit));
		List<Catalog.TabView> views = new ArrayList<Catalog.TabView>();
		views.add(new Catalog.TabView("最新", AppViewType.scrollList, "", true));
		views.add(new Catalog.TabView("最热", AppViewType.scrollList, "", false));
		return Response.ok(
				appReduceService.getAppPageList(apps, total, lastPage, views))
				.build();
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
