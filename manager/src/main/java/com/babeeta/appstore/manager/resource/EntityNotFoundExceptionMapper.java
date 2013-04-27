package com.babeeta.appstore.manager.resource;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import org.springframework.stereotype.Component;

import com.babeeta.appstore.dao.EntityNotFoundException;

/**
 * 用于处理EntityNotFoundException的Resteasy Handler
 * 
 * @author leon
 * 
 */
@Component
@Provider
public class EntityNotFoundExceptionMapper implements
		ExceptionMapper<EntityNotFoundException> {

	@Override
	public Response toResponse(EntityNotFoundException exception) {
		return Response.status(Status.NOT_FOUND).build();
	}

}
