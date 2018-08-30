package com.damdamdeo.todoapp.write.interfaces.rest;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import org.apache.commons.lang3.Validate;
import org.axonframework.commandhandling.model.AggregateNotFoundException;

@Provider
public class AggregateNotFoundExceptionMapper implements ExceptionMapper<AggregateNotFoundException> {

	@Override
	public Response toResponse(final AggregateNotFoundException exception) {
		Validate.notNull(exception);
		exception.printStackTrace();
		return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
				.entity(exception.getMessage())
				.build();
	}

}
