package com.babeeta.appstore.manager.resource;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import org.springframework.stereotype.Component;

import com.babeeta.appstore.manager.service.LockConflictException;

@Component
@Provider
public class LockConflicExceptionMapper implements
		ExceptionMapper<LockConflictException> {

	@Override
	public Response toResponse(final LockConflictException ex) {
		return Response.status(Status.CONFLICT)
				.entity(new Object() {
					@SuppressWarnings("unused")
					public String getLockOwner() {
						return ex.getLockOwner();
					}
				})
				.build();
	}

}
