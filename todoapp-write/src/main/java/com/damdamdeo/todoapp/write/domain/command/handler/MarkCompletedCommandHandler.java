package com.damdamdeo.todoapp.write.domain.command.handler;

import java.lang.invoke.MethodHandles;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.inject.Inject;

import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.commandhandling.model.Repository;
import org.axonframework.messaging.annotation.MetaDataValue;

import com.damdamdeo.todoapp.api.TodoItem;
import com.damdamdeo.todoapp.write.domain.DefaultTodoItem;
import com.damdamdeo.todoapp.write.domain.command.MarkCompletedCommand;

public class MarkCompletedCommandHandler {

	private static final Logger logger = Logger.getLogger(
			MethodHandles.lookup().lookupClass().getName());

	@Inject
	private Repository<DefaultTodoItem> todoItemRepository;

	@CommandHandler
	public TodoItem handle(final MarkCompletedCommand command, @MetaDataValue("todoItem") final DefaultTodoItem todoItem) {
		logger.log(Level.INFO, this.getClass().getName());
		logger.log(Level.INFO, String.format("MarketCompleted with '%s' from MetaData", todoItem.toString()));
		return todoItemRepository.load(command.getTodoId()).invoke(t -> t.handle(command));
	}

}
