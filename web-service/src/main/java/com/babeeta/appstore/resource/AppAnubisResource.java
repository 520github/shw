package com.babeeta.appstore.resource;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.babeeta.appstore.entity.AppJudgement;
import com.babeeta.appstore.entity.AppJudgementVote;
import com.babeeta.appstore.entity.DateFormatterRFC;
import com.babeeta.appstore.service.AppJudgementService;
import com.babeeta.appstore.service.AppJudgementVoteService;
import com.babeeta.appstore.service.DeviceService;

@Path("/app-anubis/")
@Controller("appAnubisResource")
@Scope("prototype")
public class AppAnubisResource {

	private class CurrentVote {
		private Date date;
		private String value;

		public Date getDate() {
			return date;
		}

		public String getValue() {
			return value;
		}

		public void setDate(Date date) {
			this.date = date;
		}

		public void setValue(String value) {
			this.value = value;
		}
	}

	private final static String FINISHED_STATUS = "finished";

	private final static String NORMAL_STATUS = "normal";

	private static String extractToken(String token) throws Exception {
		if (token == null || !token.startsWith("Token")) {
			throw new Exception();
		}
		return token.replace("Token ", "");
	}

	@Autowired
	private AppJudgementService appJudgementService;

	@Autowired
	private AppJudgementVoteService appJudgementVoteService;
	@Autowired
	private DeviceService deviceService;

	@GET
	@Produces("application/json")
	@Path("/judgement/{id}")
	public Response getAppAnubis(@PathParam("id") String appJudgementId,
			@HeaderParam("Authorization") String authToken) {
		try {
			authToken = extractToken(authToken);
		} catch (Exception e) {
			return Response.status(Status.UNAUTHORIZED).build();
		}

		if (deviceService.findDeviceByToken(authToken) == null) {
			return Response.status(Status.UNAUTHORIZED).build();
		}

		AppJudgement appJudgement = appJudgementService
				.getAppJudgementById(appJudgementId);

		if (appJudgement == null) {
			return Response.status(Status.NOT_FOUND).build();
		}

		if (appJudgement.isAvailable() == false) {
			return Response.status(Status.NOT_FOUND).build();
		}

		return Response
				.ok(reduceAppJudgement(appJudgementService
						.getAppJudgementById(appJudgementId),
						appJudgementVoteService.getAppJudgementVote(authToken,
								appJudgementId))).build();

	}

	@GET
	@Produces("application/json")
	@Path("/list/history")
	public Response getAppAnubisHistoryList(
			@HeaderParam("Authorization") String authToken) {
		try {
			authToken = extractToken(authToken);
		} catch (Exception e) {
			return Response.status(Status.UNAUTHORIZED).build();
		}
		if (deviceService.findDeviceByToken(authToken) == null) {
			return Response.status(Status.UNAUTHORIZED).build();
		}
		return Response.ok(
				reduceAppJudgementList(appJudgementService
						.getAppJudgementHistoryList())).build();
	}

	@GET
	@Produces("application/json")
	@Path("/list/on-going")
	public Response getAppAnubisOngoingList(
			@HeaderParam("Authorization") String authToken) {

		try {
			authToken = extractToken(authToken);
			System.out.println("token extracted:" + authToken);
		} catch (Exception e) {
			e.printStackTrace();
			return Response.status(Status.UNAUTHORIZED).build();
		}
		if (deviceService.findDeviceByToken(authToken) == null) {
			System.out.println("cannot find device by the token:" + authToken);
			return Response.status(Status.UNAUTHORIZED).build();
		}
		return Response.ok(
				reduceAppJudgementList(appJudgementService
						.getAppJudgementOngoingList())).build();
	}

	@JsonSerialize
	public Object reduceAppJudgement(final AppJudgement appJudgement,
			final AppJudgementVote appJudgementVote) {
		@SuppressWarnings("unused")
		Object o = new JsonObject() {
			@JsonSerialize(using = DateFormatterRFC.class)
			public Date getBegin() {
				return appJudgement.getBegin();
			}

			public List<com.babeeta.appstore.entity.AppJudgement.Candidate> getCandidateList() {
				return appJudgementService.getCandidates(appJudgement.getId());
			}

			public CurrentVote getCurrentVote() {
				if (appJudgementVote == null) {
					return null;
				} else {
					CurrentVote currentVote = new CurrentVote();
					currentVote.setDate(appJudgementVote.getDate());
					currentVote.setValue(appJudgementVote.getValue());
					return currentVote;
				}
			}

			public String getDesc() {
				return appJudgement.getDesc();
			}

			@JsonSerialize(using = DateFormatterRFC.class)
			public Date getEnd() {
				return appJudgement.getEnd();
			}

			public String getId() {
				return appJudgement.getId();
			}

			public String getStatus() {
				return (new Date().after(getEnd())) ? FINISHED_STATUS
						: NORMAL_STATUS;
			}

			public String getTitle() {
				return appJudgement.getTitle();
			}

		};

		return o;
	}

	public List<Object> reduceAppJudgementList(
			List<AppJudgement> appJudgementList) {
		List<Object> list = new ArrayList<Object>();

		for (final AppJudgement appJudgement : appJudgementList) {
			@SuppressWarnings("unused")
			Object o = new JsonObject() {
				@JsonSerialize(using = DateFormatterRFC.class)
				public Date getBegin() {
					return appJudgement.getBegin();
				}

				public List<com.babeeta.appstore.entity.AppJudgement.Candidate> getCandidateList() {
					return appJudgementService.getCandidates(appJudgement
							.getId());
				}

				public String getDesc() {
					return appJudgement.getDesc();
				}

				@JsonSerialize(using = DateFormatterRFC.class)
				public Date getEnd() {
					return appJudgement.getEnd();
				}

				public String getId() {
					return appJudgement.getId();
				}

				public String getTitle() {
					return appJudgement.getTitle();
				}

			};

			list.add(o);
		}

		return list;

	}

	public void setAppJudgementService(AppJudgementService appJudgementService) {
		this.appJudgementService = appJudgementService;
	}

	@POST
	@Produces("application/json")
	@Path("/judgement/{id}")
	public Response voteAppAnubis(@PathParam("id") String appJudgementId,
			@HeaderParam("Authorization") String authToken, String appId) {
		try {
			authToken = extractToken(authToken);
		} catch (Exception e) {
			return Response.status(Status.UNAUTHORIZED).build();
		}

		if (deviceService.findDeviceByToken(authToken) == null) {
			return Response.status(Status.UNAUTHORIZED).build();
		}

		if (appJudgementService.getAppJudgementById(appJudgementId) == null) {
			return Response.status(Status.NOT_FOUND).build();
		}
		appJudgementService.voteAppJudgement(authToken, appId, appJudgementId);

		return Response
				.ok(reduceAppJudgement(appJudgementService
						.getAppJudgementById(appJudgementId),
						appJudgementVoteService.getAppJudgementVote(authToken,
								appJudgementId))).build();
	}
}
