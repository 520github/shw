package com.babeeta.appstore.resource;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.babeeta.appstore.service.ConfigurationService;

/**
 * 客户端启动的基础数据
 * 
 * @author leon
 * 
 */
@Path("/base")
@Controller("baseDataResource")
@Scope("prototype")
public class BaseDataResource {

	private ConfigurationService configurationService;

	@Path("")
	@GET
	@Produces("application/json")
	public Response getBaseData() {
		String baseResource = configurationService.getBaseResource();
		return Response.ok(baseResource).build();
	}

	@Autowired
	@Required
	public void setConfigurationService(
			ConfigurationService configurationService) {
		this.configurationService = configurationService;
	}
}
