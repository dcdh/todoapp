package com.damdamdeo.noteapp.write.domain.command.handler;

import java.util.function.Function;

import javax.inject.Inject;

import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.commandhandling.model.Repository;

import com.damdamdeo.noteapp.api.TodoItem;
import com.damdamdeo.noteapp.write.domain.DefaultTodoItem;
import com.damdamdeo.noteapp.write.domain.command.CreateToDoItemCommand;

public class CreateToDoItemCommandHandler {

	@Inject
	private Repository<DefaultTodoItem> todoItemRepository;

	@CommandHandler
	public TodoItem handle(final CreateToDoItemCommand command) throws Exception {
		return todoItemRepository.newInstance(() -> new DefaultTodoItem(command)).invoke(Function.identity());
	}

}
