package com.damdamdeo.todoapp.write.domain.command.handler;

import java.util.function.Function;

import javax.inject.Inject;

import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.commandhandling.model.Repository;

import com.damdamdeo.todoapp.api.TodoItem;
import com.damdamdeo.todoapp.write.domain.DefaultTodoItem;
import com.damdamdeo.todoapp.write.domain.command.CreateToDoItemCommand;

public class CreateToDoItemCommandHandler {

	@Inject
	private Repository<DefaultTodoItem> todoItemRepository;

	@CommandHandler
	public TodoItem handle(final CreateToDoItemCommand command) throws Exception {
		return todoItemRepository.newInstance(() -> new DefaultTodoItem(command)).invoke(Function.identity());
	}

}
