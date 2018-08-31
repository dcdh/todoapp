package com.damdamdeo.todoapp.write.domain.command.handler;

import java.lang.invoke.MethodHandles;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.inject.Inject;

import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.commandhandling.model.Repository;

import com.damdamdeo.todoapp.api.TodoItem;
import com.damdamdeo.todoapp.write.domain.DefaultTodoItem;
import com.damdamdeo.todoapp.write.domain.command.MarkCompletedCommand;

public class MarkCompletedCommandHandler {

	private static final Logger logger = Logger.getLogger(
			MethodHandles.lookup().lookupClass().getName());

	@Inject
	private Repository<DefaultTodoItem> todoItemRepository;

	@CommandHandler
	public TodoItem handle(final MarkCompletedCommand command) {
		logger.log(Level.INFO, this.getClass().getName());
		return todoItemRepository.load(command.getTodoId()).invoke(t -> t.handle(command));
	}

}
