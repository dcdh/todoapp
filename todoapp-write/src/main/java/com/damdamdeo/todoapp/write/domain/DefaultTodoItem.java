package com.damdamdeo.todoapp.write.domain;

import org.axonframework.commandhandling.model.AggregateIdentifier;
import org.axonframework.eventsourcing.EventSourcingHandler;

import com.damdamdeo.todoapp.api.Status;
import com.damdamdeo.todoapp.api.TodoItem;
import com.damdamdeo.todoapp.api.event.ToDoItemCompletedEvent;
import com.damdamdeo.todoapp.api.event.ToDoItemCreatedEvent;
import com.damdamdeo.todoapp.api.event.ToDoItemDeadlineExpiredEvent;
import com.damdamdeo.todoapp.write.domain.command.CreateToDoItemCommand;
import com.damdamdeo.todoapp.write.domain.command.MarkCompletedCommand;
import com.damdamdeo.todoapp.write.domain.command.MarkToDoItemOverdueCommand;

import static org.axonframework.commandhandling.model.AggregateLifecycle.apply;

import java.lang.invoke.MethodHandles;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.axonframework.cdi.stereotype.Aggregate;

@Aggregate
public class DefaultTodoItem implements TodoItem {

	private static final Logger logger = Logger.getLogger(
			MethodHandles.lookup().lookupClass().getName());

	@AggregateIdentifier
	private String todoId;

	private String description;

	private Status status;

	protected DefaultTodoItem() {}

	public DefaultTodoItem(final CreateToDoItemCommand command) {
		logger.log(Level.INFO, "Handling: {0}.", command);
		apply(new ToDoItemCreatedEvent(command.getTodoId(),
				command.getDescription()));
	}

	public TodoItem handle(final MarkCompletedCommand command) {
		logger.log(Level.INFO, "Handling: {0}.", command);
		apply(new ToDoItemCompletedEvent(command.getTodoId()));
		return this;
	}

	public TodoItem handle(final MarkToDoItemOverdueCommand command) {
		logger.log(Level.INFO, "Handling: {0}.", command);
		apply(new ToDoItemDeadlineExpiredEvent(command.getTodoId()));
		return this;
	}

	@EventSourcingHandler
	public void on(final ToDoItemCreatedEvent event) {
		logger.log(Level.INFO, "Applying: {0}.", event);
		this.todoId = event.getTodoId();
		this.description = event.getDescription();
		this.status = Status.CREATED;
	}

	@EventSourcingHandler
	public void on(final ToDoItemCompletedEvent event) {
		logger.log(Level.INFO, "Applying: {0}.", event);
		this.status = Status.COMPLETED;
	}

	@EventSourcingHandler
	public void on(final ToDoItemDeadlineExpiredEvent event) {
		logger.log(Level.INFO, "Applying: {0}.", event);
		this.status = Status.OVERDUE;
	}

	@Override
	public String todoId() {
		return todoId;
	}

	@Override
	public String description() {
		return description;
	}

	@Override
	public Status status() {
		return status;
	}

}
