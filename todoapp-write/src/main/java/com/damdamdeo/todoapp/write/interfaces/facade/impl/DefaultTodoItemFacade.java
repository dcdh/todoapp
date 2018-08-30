package com.damdamdeo.todoapp.write.interfaces.facade.impl;

import java.util.Optional;

import javax.enterprise.context.Dependent;
import javax.inject.Inject;

import org.axonframework.commandhandling.gateway.CommandGateway;

import com.damdamdeo.todoapp.api.TodoItem;
import com.damdamdeo.todoapp.write.domain.command.CreateToDoItemCommand;
import com.damdamdeo.todoapp.write.domain.command.MarkCompletedCommand;
import com.damdamdeo.todoapp.write.interfaces.facade.TodoItemFacade;
import com.damdamdeo.todoapp.write.interfaces.facade.dto.TodoItemDto;

@Dependent
public class DefaultTodoItemFacade implements TodoItemFacade {

	@Inject
	private CommandGateway commandGateway;

	@Override
	public TodoItemDto createTodoItem(final String todoId, final String description) {
		return Optional.ofNullable(commandGateway.<TodoItem>sendAndWait(new CreateToDoItemCommand(todoId, description)))
				.map(TodoItemDto::new)
				.get();
	}

	@Override
	public TodoItemDto completeTodoItem(final String todoId) {
		return Optional.ofNullable(commandGateway.<TodoItem>sendAndWait(new MarkCompletedCommand(todoId)))
				.map(TodoItemDto::new)
				.get();
	}


}
