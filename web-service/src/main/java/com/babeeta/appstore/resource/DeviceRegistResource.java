package com.babeeta.appstore.resource;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.bson.types.ObjectId;
import org.codehaus.jettison.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.babeeta.appstore.entity.App;
import com.babeeta.appstore.entity.AppTrack;
import com.babeeta.appstore.entity.Device;
import com.babeeta.appstore.entity.Device.InstallApp;
import com.babeeta.appstore.service.AppReduceService;
import com.babeeta.appstore.service.DeviceRegistService;
import com.babeeta.appstore.service.MarkerService;

/**
 * 设备注册
 * 
 * @author papertiger
 * 
 */
@Path("/device")
@Scope(value = "prototype")
@Controller("deviceRegistResource")
public class DeviceRegistResource {

	/**
	 * 判断token格式是否合法
	 * 
	 * @param token
	 * @throws WebApplicationException
	 */
	private static String extractToken(String token) {
		if (token == null || !token.startsWith("Token ")) {
			return "";
		}
		return token.replace("Token ", "");
	}

	private AppReduceService appReduceService;

	private DeviceRegistService deviceRegistService;

	private MarkerService markerService;

	private void mergeAdd(List<InstallApp> apps, List<InstallApp> oldList) {
		for (InstallApp n : apps) {
			boolean flag = false;
			for (InstallApp o : oldList) {
				if (o.getPackageName().equals(n.getPackageName())) {
					flag = true;
					o.setVersionCode(n.getVersionCode());
					o.setAppSource(n.getAppSource());
					break;
				}
			}
			if (!flag) {
				oldList.add(n);
			}
		}
	}

	private void mergeDelete(List<InstallApp> apps, List<InstallApp> oldList) {
		for (InstallApp n : apps) {
			for (InstallApp o : oldList) {
				if (o.getPackageName().equals(n.getPackageName())) {
					oldList.remove(o);
					break;
				}
			}
		}
	}

	private void mergeUpdate(List<InstallApp> apps, List<InstallApp> oldList) {
		for (InstallApp n : apps) {
			for (InstallApp o : oldList) {
				if (o.getPackageName().equals(n.getPackageName())) {
					n.setAppSource(o.getAppSource());
				}
			}
		}
	}

	@Path("/installed/")
	@PUT
	@Consumes({ "application/json" })
	public Response deviceApps(@HeaderParam("Authorization") String token,
			List<Device.InstallApp> apps) {

		if (!(token = extractToken(token)).equals("")) {
			Device device = deviceRegistService.findDeviceByToken(token);
			if (device != null) {
				mergeUpdate(apps,
						(device.getApps() == null ? new ArrayList<InstallApp>()
								: device.getApps()));
				device.setApps(apps);
				deviceRegistService.saveDevice(device);
			}
		} else {
			return Response.status(Status.UNAUTHORIZED).build();
		}
		return Response.ok().build();
	}

	@Path("/track/install/")
	@POST
	@Consumes({ "application/json" })
	public Response deviceAppsIntallLog(
			@HeaderParam("Authorization") String token,
			List<Device.InstallApp> apps) {

		if (!(token = extractToken(token)).equals("")) {
			Device device = deviceRegistService.findDeviceByToken(token);
			if (device != null) {
				mergeAdd(apps,
						(device.getApps() == null ? new ArrayList<InstallApp>()
								: device.getApps()));
				deviceRegistService.saveDevice(device);
				deviceRegistService.saveAppTrack(device.getId(),
						AppTrack.enumOperateType.install, apps);
			}
		} else {
			return Response.status(Status.UNAUTHORIZED).build();
		}
		return Response.ok().build();
	}

	@Path("/track/uninstall/")
	@POST
	@Consumes({ "application/json" })
	public Response deviceAppsUninstallLog(
			@HeaderParam("Authorization") String token,
			List<Device.InstallApp> apps) {

		if (!(token = extractToken(token)).equals("")) {
			Device device = deviceRegistService.findDeviceByToken(token);
			if (device != null) {
				mergeDelete(apps,
						(device.getApps() == null ? new ArrayList<InstallApp>()
								: device.getApps()));
				deviceRegistService.saveDevice(device);
				deviceRegistService.saveAppTrack(device.getId(),
						AppTrack.enumOperateType.uninstall, apps);
			}
		} else {
			return Response.status(Status.UNAUTHORIZED).build();
		}
		return Response.ok().build();
	}

	@Path("/clientid/")
	@PUT
	@Consumes({ "text/plain" })
	public Response deviceClientId(@HeaderParam("Authorization") String token,
			String clientId) {
		if (!(token = extractToken(token)).equals("")) {
			Device device = deviceRegistService.findDeviceByToken(token);
			if (device != null) {
				device.setClientId(clientId);
				deviceRegistService.saveDevice(device);
			}
		} else {
			return Response.status(Status.UNAUTHORIZED).build();
		}
		return Response.ok().build();
	}

	@Path("/register/")
	@POST
	@Consumes({ "application/json" })
	@Produces("application/json")
	public Response deviceRegist(Device device) {
		if (device == null || device.getImei() == null) {
			return Response.status(400).build();
		}
		Device org = deviceRegistService.findDeviceByImei(device.getImei());
		if (org != null) {
			device.setId(org.getId());
			device.setToken(org.getToken());
		} else {
			device.setId(new ObjectId().toString());
			device.setToken(UUID.randomUUID().toString());
		}

		deviceRegistService.saveDevice(device);
		return Response.ok("{\"token\":\"" + device.getToken() + "\"}").build();
	}

	@Path("/counter")
	@GET
	@Produces("application/json")
	public Response getCounter(@HeaderParam("Authorization") String token)
			throws JSONException {
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
		final String did = device.getId();
		@SuppressWarnings("unused")
		Object o = new Object() {
			public long getAppDownloads() {
				return markerService.countDeviceMarker(did, "app-download",
						"download");
			}

			public long getAppVotePositive() {
				return markerService.countDeviceMarker(did, "app-vote",
						"positive");
			}
		};
		return Response.ok(o).build();
	}

	@Path("/app/laste")
	@GET
	@Produces("application/json")
	public Response getLasteApps(@HeaderParam("Authorization") String token) {
		if (!(token = extractToken(token)).equals("")) {
			List<App> apps = deviceRegistService.getLasteApps(token);
			List<Object> list = new ArrayList<Object>();
			for (final App app : apps) {
				list.add(appReduceService.reduceApp(app, true));
			}

			return Response.ok(list).build();
		} else {
			return Response.status(Status.UNAUTHORIZED).build();
		}
	}

	@Path("/push")
	@GET
	@Produces("application/json")
	public Response getPushAlertState(@HeaderParam("Authorization") String token)
			throws JSONException {
		try {
			token = extractToken(token);
		} catch (Exception e) {
			return Response.status(Status.UNAUTHORIZED).build();
		}
		Device d = deviceRegistService.findDeviceByToken(token);
		if (d == null) {
			return Response.status(Status.UNAUTHORIZED).build();
		}
		return Response.ok(d.isPushAlert()).build();
	}

	@Path("/push")
	@PUT
	@Produces("application/json")
	public Response putPushAlertState(
			@HeaderParam("Authorization") String token,
			String condition)
			throws JSONException {
		try {
			token = extractToken(token);
		} catch (Exception e) {
			return Response.status(Status.UNAUTHORIZED).build();
		}
		if (condition == null
				|| (!condition.equals("true") && !condition.equals("false"))) {
			return Response.status(400).build();
		}
		Device d = deviceRegistService.findDeviceByToken(token);
		if (d == null) {
			return Response.status(Status.UNAUTHORIZED).build();
		}
		d.setPushAlert(Boolean.parseBoolean(condition));
		deviceRegistService.saveDevice(d);
		return Response.ok().build();
	}

	// @Path("/install/{packageName}")
	// @PUT
	// @Consumes({ "application/json" })
	// public Response installApp(@HeaderParam("Authorization") String token,
	// Device.InstallApp app) {
	// if (!(token = extractToken(token)).equals("")) {
	// app.setDate(Calendar.getInstance().getTime());
	// deviceRegistService.modifyDeviceInstallApp(token, app);
	// // 记录APP安装记录
	// deviceRegistService.saveAppTrack(token,
	// AppTrack.enumOperateType.install, app);
	// } else {
	// return Response.status(Status.UNAUTHORIZED).build();
	// }
	// return Response.ok(app).build();
	// }

	@Autowired
	public void setAppReduceService(AppReduceService appReduceService) {
		this.appReduceService = appReduceService;
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

	// @Path("/install/{packageName}")
	// @DELETE
	// @Consumes({ "application/json" })
	// public Response uninstallApp(@HeaderParam("Authorization") String token,
	// @PathParam("packageName") String packageName) {
	// Device.InstallApp app;
	// if (!(token = extractToken(token)).equals("")) {
	// app = deviceRegistService
	// .deleteDeviceInstallApp(token, packageName);
	// // 记录APP卸载记录
	// if (Strings.isNullOrEmpty(app.getPackageName())) {
	// app.setPackageName(packageName);
	// }
	// deviceRegistService.saveAppTrack(token,
	// AppTrack.enumOperateType.uninstall, app);
	// } else {
	// return Response.status(Status.UNAUTHORIZED).build();
	// }
	// return Response.ok(app).build();
	// }
}
