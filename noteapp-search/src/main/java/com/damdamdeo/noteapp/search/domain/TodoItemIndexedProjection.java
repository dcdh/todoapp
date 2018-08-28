package com.damdamdeo.noteapp.search.domain;

import java.util.logging.Level;
import java.util.logging.Logger;

import javax.enterprise.context.Dependent;
import javax.inject.Inject;

import org.axonframework.config.ProcessingGroup;
import org.axonframework.eventhandling.AllowReplay;
import org.axonframework.eventhandling.EventHandler;

import com.damdamdeo.noteapp.api.event.ToDoItemCompletedEvent;
import com.damdamdeo.noteapp.api.event.ToDoItemCreatedEvent;
import com.damdamdeo.noteapp.api.event.ToDoItemDeadlineExpiredEvent;

@Dependent
@AllowReplay(true)
@ProcessingGroup("TodoItemIndexedProjection")
public class TodoItemIndexedProjection {

	@Inject
	private Logger logger;

	@Inject
	private TodoItemIndexedRepository todoItemIndexedRepository;

	@EventHandler
	public void on(final ToDoItemCreatedEvent event) {
		logger.log(Level.INFO, "Applying: {0}.", event);
		final DefaultTodoItem todoItemCreated = new DefaultTodoItem(event.getTodoId(),
				event.getDescription());
		todoItemIndexedRepository.save(todoItemCreated);
	}

	@EventHandler
	public void on(final ToDoItemCompletedEvent event) {
		logger.log(Level.INFO, "Applying: {0}.", event);
		final DefaultTodoItem todoItemToMarkAsCompleted = todoItemIndexedRepository.findByTodoId(event.getTodoId());
		todoItemToMarkAsCompleted.markAsCompletd();
		todoItemIndexedRepository.save(todoItemToMarkAsCompleted);
	}

	@EventHandler
	public void on(final ToDoItemDeadlineExpiredEvent event) {
		logger.log(Level.INFO, "Applying: {0}.", event);
		final DefaultTodoItem todoItemToMarkAsOverdue = todoItemIndexedRepository.findByTodoId(event.getTodoId());
		todoItemToMarkAsOverdue.markAsOverdue();
		todoItemIndexedRepository.save(todoItemToMarkAsOverdue);
	}

}
