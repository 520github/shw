package com.babeeta.appstore.resource;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
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

import com.babeeta.appstore.entity.App;
import com.babeeta.appstore.entity.ApplicationMarketDetail.Score;
import com.babeeta.appstore.entity.Catalog;
import com.babeeta.appstore.entity.GroupItem;
import com.babeeta.appstore.service.AppReduceService;
import com.babeeta.appstore.service.AppService;
import com.babeeta.appstore.service.CatalogService;

/**
 * 分类目录
 * 
 * @author leon
 * 
 */
@Path("/catalog/")
@Scope("prototype")
@Controller("catalogResource")
public class CatalogResource {

	private AppService appService;

	private CatalogService catalogService;

	private AppReduceService appReduceService;

	private List<Object> reduceApps(List<App> apps) {
		List<Object> list = new ArrayList<Object>();
		for (final App app : apps) {
			@SuppressWarnings("unused")
			Object o = new JsonObject() {

				public String getId() {
					return app.getId();
				}

				public String getIntroduction() {
					return app.getIntroduction();
				}

				public String getLogo() {
					return app.getDetail().getLogo();
				}

				public String getName() {
					return app.getDetail().getName();
				}

				public String getPrice() {
					return app.getDetail().getPrice();
				}

				public Score getScore() {
					Score score = app.getDetail()
							.getWholeVersionScore();
					if (score == null) {
						score = new Score().setStar(0).setVote(0);
					}
					return score;
				}
			};
			list.add(o);
		}
		return list;
	}

	private List<Object> reduceCatalogs(List<Catalog> catalogs) {
		List<Object> list = new ArrayList<Object>();
		for (final Catalog catalog : catalogs) {
			@SuppressWarnings("unused")
			Object o = new JsonObject() {
				public String getDefaultView() {
					return catalog.getDefaultView().name();
				}

				public String getDescription() {
					return catalog.getDescription();
				}

				public String getIcon() {
					return catalog.getIcon();
				}

				public String getName() {
					return catalog.getName();
				}

			};
			list.add(o);
		}
		return list;
	}

	private List<Object> reduceGroupItems(List<GroupItem> orgGis) {
		List<Object> bis = new ArrayList<Object>();
		for (final GroupItem gi : orgGis) {
			@SuppressWarnings("unused")
			Object o = new JsonObject() {
				public List<Object> getApps() {
					List<Object> list = Collections.emptyList();
					if (gi.getApps() != null) {
						if (gi.getApps().size() > 3) {
							list = reduceApps(gi.getApps().subList(0, 3));
						} else {
							list = reduceApps(gi.getApps());
						}
					}
					return list;
				}

				public String getGroup() {
					return gi.getGroup();
				}

				public boolean getHasMoreApp() {
					return gi.isHasMoreApp();
				}
			};
			bis.add(o);
		}
		return bis;
	}

	private JsonObject reduceViewGroupItems(final Catalog catalog,
			final List<GroupItem> orgGis, final String currentView) {
		@SuppressWarnings("unused")
		JsonObject jo = new JsonObject() {
			public List<JsonObject> getContent() {
				List<JsonObject> bis = new ArrayList<JsonObject>();
				for (final GroupItem gi : orgGis) {
					JsonObject o = new JsonObject() {
						public List<Object> getApps() {
							List<Object> list = Collections.emptyList();
							if (gi.getApps() != null) {
								if (gi.getApps().size() > 3) {
									list = reduceApps(gi.getApps()
											.subList(0, 3));
								} else {
									list = reduceApps(gi.getApps());
								}
							}
							return list;
						}

						public String getGroup() {
							return gi.getGroup();
						}

						public boolean getHasMoreApp() {
							return gi.isHasMoreApp();
						}
					};
					bis.add(o);
				}
				return bis;
			}

			public List<Catalog.TabView> getViews() {
				List<Catalog.TabView> views = catalog.getView();
				for (Catalog.TabView view : views) {
					view.setDefaultView(view.getName().equals(currentView));
				}
				return views;
			}
		};

		return jo;
	}

	@Path("/{catalogName}/app/hot/")
	@GET
	@Produces({ "application/json", "application/app-simple-list+json" })
	public Response getAppByCatalogOrderByVote(
			@HeaderParam("accept") String accept,
			@PathParam("catalogName") String catalogName,
			@QueryParam("offset") int offset, @QueryParam("limit") int limit) {
		Catalog catalog = catalogService.getCatalog(catalogName);
		if (catalog == null) {
			return Response.status(Status.NOT_FOUND).build();
		}
		if (!catalog.isEnabled()) {
			return Response.status(Status.FORBIDDEN).build();
		}
		List<App> apps = appService.getAppByCatalogOrderByVote(catalogName,
				offset,
				limit == 0 ? 20 : limit);
		if (accept != null
				&& accept.toLowerCase().equals(
						"application/app-simple-list+json")) {
			// return Response.ok(reduceViewApps(catalog, apps,
			// "热门",appService.countPublishedByCatalogName(catalogName),offset)).build();
			long total = appService.countPublishedByCatalogName(catalogName);
			List<Catalog.TabView> views = catalog.getView();
			for (Catalog.TabView view : views) {
				view.setDefaultView(view.getName().equals("热门"));
			}
			return Response.ok(
					appReduceService.getAppPageList(apps, total,
							((offset + apps.size()) >= total), views)).build();
		}
		return Response.ok(reduceApps(apps)).build();
	}

	/**
	 * 分类下的App列表
	 * 
	 * @param catalogName
	 *            分类名称
	 * @param maxId
	 *            最大的id，即分页的第一个元素
	 * @param count
	 *            返回结果的最大数量
	 * @return
	 */
	@Path("{catalogName}/app")
	@GET
	@Produces({ "application/json", "application/app-simple-list+json" })
	public Response getAppListByCatalog(@HeaderParam("accept") String accept,
			@PathParam("catalogName") String catalogName,
			@QueryParam("offset") int offset, @QueryParam("limit") int limit) {
		Catalog catalog = catalogService.getCatalog(catalogName);
		if (catalog == null) {
			// 请求不存在的类名，返回404
			return Response.status(Status.NOT_FOUND).build();
		}
		if (!catalog.isEnabled()) {
			return Response.status(Status.FORBIDDEN).build();
		}
		List<App> apps = appService.getByCatalog(catalog, offset,
				limit == 0 ? 20 : limit);
		if (accept != null
				&& accept.toLowerCase().equals(
						"application/app-simple-list+json")) {
			long total = appService.countPublishedByCatalogName(catalogName);
			return Response.ok(
					appReduceService.getAppPageList(apps, total,
							((offset + apps.size()) >= total),
							catalog.getView())).build();
		}
		return Response.ok(reduceApps(apps)).build();
	}

	@Path("{catalogName}/app/group/{groupName}")
	@GET
	@Produces({ "application/json" })
	public Response getAppListByCatalogAndGroupMore(
			@PathParam("catalogName") String catalogName,
			@PathParam("groupName") String groupName,
			@QueryParam("offset") int offset, @QueryParam("limit") int limit) {
		Catalog catalog = catalogService.getCatalog(catalogName);
		if (catalog == null) {
			return Response.status(Status.NOT_FOUND).build();
		}
		if (!catalog.isEnabled()) {
			return Response.status(Status.FORBIDDEN).build();
		}

		List<App> list = appService.getByCatalogAndGroup(
				catalogName, groupName, offset, limit);
		return Response.ok(reduceApps(list)).build();
	}

	/**
	 * 二级分类目录
	 * 
	 * @param parentName
	 * @return
	 */
	@Path("{parentName}/catalog/")
	@GET
	@Produces({ "application/json" })
	public Response getCatalogByParent(
			@PathParam("parentName") String parentName) {
		Catalog parent = catalogService.getCatalog(parentName);
		if (parent == null || !parent.isRoot()) {
			// 请求目标必须时一级分类名称
			return Response.status(Status.NOT_FOUND).build();
		}
		if (!parent.isEnabled()) {
			return Response.status(Status.FORBIDDEN).build();
		}
		List<Catalog> catalogs = catalogService.getSecondariesByRoot(parent);
		return Response.ok(reduceCatalogs(catalogs)).build();
	}

	/**
	 * 分类下以brand分组的app展示
	 * 
	 * @param catalogName
	 *            分类名称
	 * @param maxId
	 *            最大的id，即分页的第一个元素
	 * @param count
	 *            返回结果的最大数量
	 * @return
	 */
	@Path("{catalogName}/app/group")
	@GET
	@Produces({ "application/json", "application/app-grouped-list+json" })
	public Response getGroupedAppListByCatalog(
			@HeaderParam("accept") String accept,
			@PathParam("catalogName") String catalogName,
			@QueryParam("offset") int offset, @QueryParam("limit") int limit) {
		Catalog catalog = catalogService.getCatalog(catalogName);
		if (catalog == null) {
			return Response.status(Status.NOT_FOUND).build();
		}
		if (!catalog.isEnabled()) {
			return Response.status(Status.FORBIDDEN).build();
		}
		List<GroupItem> gis = appService.getByCatalogGroupByBrand(catalogName,
				offset, limit > 100 ? 100 : limit);
		if (accept != null
				&& accept.toLowerCase().equals(
						"application/app-grouped-list+json")) {
			return Response.ok(reduceViewGroupItems(catalog, gis, "品牌应用"))
					.build();
		}
		return Response.ok(reduceGroupItems(gis)).build();
	}

	/**
	 * 分类下的App列表
	 * 
	 * @param catalogName
	 *            分类名称
	 * @param maxId
	 *            最大的id，即分页的第一个元素
	 * @param count
	 *            返回结果的最大数量
	 * @return
	 */
	@Path("{catalogName}/app/new")
	@GET
	@Produces({ "application/json", "application/app-simple-list+json" })
	public Response getNewAppListByCatalog(
			@HeaderParam("accept") String accept,
			@PathParam("catalogName") String catalogName,
			@QueryParam("offset") int offset, @QueryParam("limit") int limit) {
		Catalog catalog = catalogService.getCatalog(catalogName);
		if (catalog == null) {
			// 请求不存在的类名，返回404
			return Response.status(Status.NOT_FOUND).build();
		}
		if (!catalog.isEnabled()) {
			return Response.status(Status.FORBIDDEN).build();
		}
		List<App> apps = appService.getByCatalog(catalog, offset,
				limit == 0 ? 20 : limit);
		if (accept != null
				&& accept.toLowerCase().equals(
						"application/app-simple-list+json")) {
			long total = appService.countPublishedByCatalogName(catalogName);
			List<Catalog.TabView> views = catalog.getView();
			for (Catalog.TabView view : views) {
				view.setDefaultView(view.getName().equals("新鲜"));
			}
			return Response.ok(
					appReduceService.getAppPageList(apps, total,
							((offset + apps.size()) >= total), views)).build();
		}
		return Response.ok(reduceApps(apps)).build();
	}

	/**
	 * 一级分类目录
	 * 
	 * @param parentName
	 * @return
	 */
	@Path("")
	@GET
	@Produces({ "application/json" })
	public Response getRootCatalog() {
		List<Catalog> catalogList = catalogService.getRoots();
		return Response.ok(reduceCatalogs(catalogList)).build();

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

	@Autowired
	@Required
	public void setCatalogService(CatalogService catalogService) {
		this.catalogService = catalogService;
	}
}