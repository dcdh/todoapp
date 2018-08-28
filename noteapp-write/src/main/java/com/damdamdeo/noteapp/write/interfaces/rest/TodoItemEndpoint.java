package com.damdamdeo.noteapp.write.interfaces.rest;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.damdamdeo.noteapp.write.interfaces.facade.TodoItemFacade;
import com.damdamdeo.noteapp.write.interfaces.facade.dto.TodoItemDto;

import io.swagger.annotations.Api;

@Path("/v1/todoItem")
@Api(value = "/v1/todoItem", tags = "todoItem")
public class TodoItemEndpoint {

	@Inject
	private TodoItemFacade todoItemFacade;

	@POST
	@Path("createTodoItem")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.APPLICATION_JSON)
	public TodoItemDto createTodoItem(@FormParam("todoId") final String todoId,
			@FormParam("description") final String description) {
		return todoItemFacade.createTodoItem(todoId, description);
	}

	@POST
	@Path("{todoId}/" + "completeTodoItem")
	@Produces(MediaType.APPLICATION_JSON)
	public TodoItemDto completeTodoItem(@PathParam("todoId") final String todoId) {
		return todoItemFacade.completeTodoItem(todoId);
	}

}
