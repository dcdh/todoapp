package com.damdamdeo.noteapp.search.interfaces.rest;

import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.damdamdeo.noteapp.search.interfaces.facade.TodoItemFacade;
import com.damdamdeo.noteapp.search.interfaces.facade.dto.TodoItemDto;

import io.swagger.annotations.Api;

@Path("/v1/todoItem")
@Api(value = "/v1/todoItem", tags = "todoItem")
public class TodoItemEndpoint {

	@Inject
	private TodoItemFacade todoItemFacade;

	@POST
	@Path("search")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.APPLICATION_JSON)
	public List<TodoItemDto> search(@FormParam("description") final String description) {
		return todoItemFacade.search(description);
	}

	@GET
	@Path("{todoId}")
	@Produces(MediaType.APPLICATION_JSON)
	public TodoItemDto findByTodoId(@PathParam("todoId") final String todoId) {
		return todoItemFacade.findByTodoId(todoId);
	}

}
