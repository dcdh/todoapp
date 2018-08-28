package com.damdamdeo.noteapp.write.interfaces.rest;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;

import org.wildfly.swarm.health.Health;
import org.wildfly.swarm.health.HealthStatus;

@Path("/service")
public class HealthChecks {

	@GET
	@Health
	@Path("/health")
	public HealthStatus check() {
		return HealthStatus.named("server-state").up();
	}

	@GET
	@Path("/ready")
	public Response ready() {
		return Response.status(Response.Status.OK).build();
	}

}
