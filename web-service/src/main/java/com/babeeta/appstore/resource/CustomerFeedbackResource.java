package com.babeeta.appstore.resource;

import java.util.Date;

import javax.ws.rs.Consumes;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.babeeta.appstore.entity.CustomerFeedback;
import com.babeeta.appstore.service.CustomerFeedbackService;

/**
 * 用户反馈
 * 
 * @author chongf
 * 
 */
@Path("/customer-feedback")
@Scope(value = "prototype")
@Controller("customerFeedbackResource")
public class CustomerFeedbackResource {

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

	private CustomerFeedbackService customerFeedbackService;

	@Path("")
	@POST
	@Consumes("application/json")
	public Response postFeedback(@HeaderParam("Authorization") String token,
			CustomerFeedback cfb) {
		if (cfb == null) {
			return Response.status(Status.BAD_REQUEST).build();
		}
		try {
			token = extractToken(token);
		} catch (Exception e) {
			return Response.status(Status.UNAUTHORIZED).build();
		}
		cfb.setDate(new Date());
		cfb.setId(new ObjectId().toString());
		cfb.setToken(token);
		customerFeedbackService.saveCustomerFeedback(cfb);
		return Response.ok().build();
	}

	@Autowired
	@Required
	public void setCustomerFeedbackService(
			CustomerFeedbackService customerFeedbackService) {
		this.customerFeedbackService = customerFeedbackService;
	}
}
