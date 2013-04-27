package com.babeeta.appstore.resource;

import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.apache.http.client.HttpClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.babeeta.appstore.entity.App;
import com.babeeta.appstore.entity.Device;
import com.babeeta.appstore.service.AppService;
import com.babeeta.appstore.service.DeviceService;
import com.babeeta.appstore.service.push.Message;
import com.babeeta.appstore.service.push.MessagePush;

@Path("/push")
@Scope(value = "prototype")
@Controller("pushResource")
public class PushResource {

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

	private MessagePush messagePush;
	private DeviceService deviceService;
	private AppService appService;

	@Path("/app/{id}")
	@POST
	public Response getApkUrl(@HeaderParam("Authorization") String token,
			@PathParam("id") String id) {
		try {
			token = extractToken(token);
		} catch (Exception e) {
			return Response.status(Status.UNAUTHORIZED).build();
		}
		Device device = deviceService.findDeviceByToken(token);
		if (device == null) {
			return Response.status(Status.NOT_FOUND).build();
		}
		App app = appService.getById(id);
		if (app == null) {
			return Response.status(Status.NOT_FOUND).build();
		}
		String apkId = app.getDetail().getApkId();
		String url = "http://static.shanhubay.com/android/apk/"
				+ apkId.substring(0, 2) + "/" + apkId.substring(2, 4) + "/"
				+ apkId + ".apk";
		HttpClient client = messagePush.getHttpConnection();
		messagePush.pushMessage(client, device.getClientId(),
				Message.getAppDownloadUrl(url));
		return Response.ok().build();
	}

	@Autowired
	public void setAppService(AppService appService) {
		this.appService = appService;
	}

	@Autowired
	public void setDeviceService(DeviceService deviceService) {
		this.deviceService = deviceService;
	}

	@Autowired
	public void setMessagePush(MessagePush messagePush) {
		this.messagePush = messagePush;
	}
}
