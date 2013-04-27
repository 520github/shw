package com.babeeta.appstore.resource;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.babeeta.appstore.entity.App;
import com.babeeta.appstore.entity.Device;
import com.babeeta.appstore.entity.Marker;
import com.babeeta.appstore.entity.MarkerDomain;
import com.babeeta.appstore.service.AppReduceService;
import com.babeeta.appstore.service.AppService;
import com.babeeta.appstore.service.DeviceRegistService;
import com.babeeta.appstore.service.MarkerService;
import com.babeeta.appstore.service.impl.BestAppReduceServiceImpl;

/**
 * 应用
 * 
 * @author leon
 * 
 */
@Path("/")
@Scope(value = "prototype")
@Controller("appResource")
public class AppResource {

	private static List<String> extractEntityIds(List<Marker> markers) {
		List<String> ids = new ArrayList<String>(markers.size());
		for (Marker m : markers) {
			ids.add(m.getId().getEntityId());
		}
		return ids;
	}

	/**
	 * 判断token格式是否合法
	 * 
	 * @param token
	 * @throws WebApplicationException
	 */
	private static String extractToken(String token) throws Exception {
		if (token == null || !token.startsWith("Token")) {
			throw new Exception();
		}
		return token.replace("Token ", "");
	}

	private AppReduceService appReduceService;

	private AppService appService;

	private DeviceRegistService deviceRegistService;

	private MarkerService markerService;

	@Path("/app/marked/{markerDomain}/{markerName}")
	@GET
	@Produces("application/json")
	public Response findMarkedApp(@HeaderParam("Authorization") String token,
			@PathParam("markerDomain") String markerDomain,
			@PathParam("markerName") String markerName,
			@QueryParam("offset") int offset, @QueryParam("limit") int limit) {
		limit = limit > 100 ? 100 : limit == 0 ? 20 : limit;
		Device device = null;
		try {
			token = extractToken(token);
			device = deviceRegistService.findDeviceByToken(token);
			if (device == null) {
				throw new Exception();
			}
		} catch (Exception e) {
			return Response.status(Status.UNAUTHORIZED).build();
		}
		List<Marker> list = markerService.findMarker(device.getId(),
				markerDomain, markerName, offset, limit);
		if (list == null || list.size() == 0) {
			return Response.ok().build();
		}
		long total = markerService.countDeviceMarker(device.getId(),
				markerDomain, markerName);
		Object ret = appReduceService.getAppPageList(
				appService.getByIds(extractEntityIds(list)), total,
				total <= (offset + limit),
				null);
		return Response.ok(ret).build();
	}

	@Path("/app/{id}/url")
	@POST
	public Response getApkUrl(@HeaderParam("Authorization") String token,
			@PathParam("id") String id) {
		try {
			token = extractToken(token);
		} catch (Exception e) {
		}
		App app = appService.getById(id);
		if (app == null) {
			return Response.status(Status.NOT_FOUND).build();
		}

		String apkId = app.getDetail().getApkId();
		String url = "http://static.shanhubay.com/android/apk/"
				+ apkId.substring(0, 2) + "/" + apkId.substring(2, 4) + "/"
				+ apkId + ".apk";
		return Response.ok(url).build();
	}

	/**
	 * 获取精品推荐APP列表
	 * 
	 * @param offset
	 * @param limit
	 * @return
	 */
	@Path("/best")
	@GET
	@Produces("application/json")
	public Response getBest(@QueryParam("offset") int offset,
			@QueryParam("limit") int limit) {
		limit = limit > 100 ? 100 : limit == 0 ? 20 : limit;
		List<App> appList = appService.getAppWithBest(offset, limit);
		long total = appService.countAllBest();
		return Response.ok(
				BestAppReduceServiceImpl
						.getAppPageList(appList, total,
								total <= (offset + limit)))
				.build();
	}

	@Path("/app/{id}")
	@GET
	@Produces("application/json")
	public Response getById(@PathParam("id") String id) {
		final App app = appService.getPublishedById(id);
		if (app == null) {
			return Response.status(Status.NOT_FOUND).build();
		}
		return Response.ok(appReduceService.getDefaultApp(app)).build();
	}

	@Path("/app/{id}/url")
	@GET
	public Response redirectUrl(@HeaderParam("Authorization") String token,
			@PathParam("id") String id) {
		Device device = null;
		try {
			token = extractToken(token);
			device = deviceRegistService.findDeviceByToken(token);
		} catch (Exception e) {
		}
		App app = appService.getById(id);
		if (app == null) {
			return Response.status(Status.NOT_FOUND).build();
		}
		if (device != null) {
			markerService.mark(device.getId(), "app-download", app.getId(),
					"download", MarkerDomain.CounterMethod.click);
		}
		String apkId = app.getDetail().getApkId();
		String url = "http://static.shanhubay.com/android/apk/"
				+ apkId.substring(0, 2) + "/" + apkId.substring(2, 4) + "/"
				+ apkId + ".apk";
		return Response
				.temporaryRedirect(
						URI.create(url))
				.status(302)
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

	@Autowired
	@Required
	public void setDeviceRegistService(DeviceRegistService deviceRegistService) {
		this.deviceRegistService = deviceRegistService;
	}

	@Autowired
	@Required
	public void setMarkerService(MarkerService markerService) {
		this.markerService = markerService;
	}
}