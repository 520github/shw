package com.babeeta.appstore.resource;

import java.util.List;
import java.util.Map;

import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.babeeta.appstore.entity.Notification;
import com.babeeta.appstore.service.NotificationService;
import com.babeeta.appstore.service.impl.EntityUnExistsException;
import com.babeeta.appstore.service.impl.NotificationUpdateNotAllowedException;

/**
 * 消息通知接口
 * 
 * @author chongf
 * 
 */
@Path("/")
@Scope(value = "prototype")
@Controller("notificationResource")
public class NotificationResource {

	/**
	 * 判断token格式是否合法
	 * 
	 * @param token
	 * @throws WebApplicationException
	 */
	private static String extractToken(String token)
			throws Exception {
		if (token == null || !token.startsWith("Token")) {
			throw new Exception();
		}
		return token.replace("Token ", "");
	}

	private static JsonObject reduceNotifications(final Map<String, Object> map) {
		if (map.isEmpty()) {
			return new JsonObject();
		}
		@SuppressWarnings("unused")
		JsonObject ao = new JsonObject() {

			public Long getBadge() {
				return (Long) map.get("badge");
			}

			@SuppressWarnings( { "unchecked" })
			public List<Notification> getContent() {
				return (List<Notification>) map.get("content");
			}
		};
		return ao;
	}

	private NotificationService notificationService;

	@Path("/notification/{id}")
	@GET
	@Produces("application/json")
	public Response getNotifications(
			@HeaderParam("Authorization") String token,
			@PathParam("id") String id) {
		try {
			token = extractToken(token);
		} catch (Exception e) {
			return Response.status(Status.UNAUTHORIZED).build();
		}
		Notification n = notificationService.findNotificationById(id);
		if (n == null) {
			return Response.status(404).build();
		}
		return Response.ok(n)
				.build();
	}

	// @Path("/notification")
	// @GET
	// @Produces("application/json")
	// public Response getNotifications(
	// @HeaderParam("Authorization") String token,
	// @QueryParam("since") String since, @QueryParam("limit") int limit) {
	// try {
	// token = extractToken(token);
	// } catch (Exception e) {
	// return Response.status(Status.UNAUTHORIZED).build();
	// }
	// Map<String, Object> map = notificationService.getNotificationByToken(
	// token,
	// since, limit == 0 ? 20 : limit > 100 ? 100 : limit);
	// return Response.ok(reduceNotifications(map)).build();
	// }

	@Path("/notification/{id}/read")
	@PUT
	@Produces("application/json")
	public Response putAsRead(
			@HeaderParam("Authorization") String token,
			@PathParam("id") String id, String condition) {
		try {
			token = extractToken(token);
		} catch (Exception e) {
			return Response.status(Status.UNAUTHORIZED).build();
		}
		if (condition == null
				|| (!condition.equals("true") && !condition.equals("false"))) {
			return Response.status(400).build();
		}
		try {
			notificationService.putAsRead(token, id,
					Boolean.parseBoolean(condition));
		} catch (NotificationUpdateNotAllowedException e) {
			return Response.status(403).build();
		} catch (EntityUnExistsException e) {
			return Response.status(404).build();
		}
		return Response.ok().build();
	}

	// @Path("/last-notification")
	// @PUT
	// @Produces("application/json")
	// public Response putLastNotification(
	// @HeaderParam("Authorization") String token,
	// String lastNotificationId) {
	// try {
	// token = extractToken(token);
	// } catch (Exception e) {
	// return Response.status(Status.UNAUTHORIZED).build();
	// }
	// if (lastNotificationId == null) {
	// return Response.status(400).build();
	// }
	// try {
	// notificationService
	// .putLastNotificationID(token, lastNotificationId);
	// } catch (EntityUnExistsException e) {
	// return Response.status(404).build();
	// }
	// return Response.ok().build();
	// }

	@Autowired
	@Required
	public void setNotificationService(NotificationService notificationService) {
		this.notificationService = notificationService;
	}

}
