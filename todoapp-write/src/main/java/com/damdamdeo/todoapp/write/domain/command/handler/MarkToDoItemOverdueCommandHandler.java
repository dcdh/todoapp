package com.damdamdeo.todoapp.write.domain.command.handler;

import javax.inject.Inject;

import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.commandhandling.model.Repository;

import com.damdamdeo.todoapp.api.TodoItem;
import com.damdamdeo.todoapp.write.domain.DefaultTodoItem;
import com.damdamdeo.todoapp.write.domain.command.MarkToDoItemOverdueCommand;

public class MarkToDoItemOverdueCommandHandler {

	@Inject
	private Repository<DefaultTodoItem> todoItemRepository;

	@CommandHandler
	public TodoItem handle(final MarkToDoItemOverdueCommand command) {
		return todoItemRepository.load(command.getTodoId()).invoke(t -> t.handle(command));
	}

}
