package com.damdamdeo.todoapp.search.interfaces.rest;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import org.apache.commons.lang3.Validate;

@Provider
public class IllegalStateExceptionMapper implements ExceptionMapper<IllegalStateException> {

	@Override
	public Response toResponse(final IllegalStateException exception) {
		Validate.notNull(exception);
		exception.printStackTrace();
		return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
				.entity(exception.getMessage())
				.build();
	}

}
