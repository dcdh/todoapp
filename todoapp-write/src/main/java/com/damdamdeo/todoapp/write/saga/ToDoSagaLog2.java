package com.damdamdeo.todoapp.write.saga;

import java.lang.invoke.MethodHandles;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.axonframework.eventhandling.saga.EndSaga;
import org.axonframework.eventhandling.saga.SagaEventHandler;
import org.axonframework.eventhandling.saga.StartSaga;

import com.damdamdeo.todoapp.api.event.ToDoItemCompletedEvent;
import com.damdamdeo.todoapp.api.event.ToDoItemCreatedEvent;
import com.damdamdeo.todoapp.api.event.ToDoItemDeadlineExpiredEvent;

public class ToDoSagaLog2 {

	private static final Logger logger = Logger.getLogger(
			MethodHandles.lookup().lookupClass().getName());

	@StartSaga
	@SagaEventHandler(associationProperty = "todoId")
	public void onToDoItemCreated(final ToDoItemCreatedEvent event) {
		logger.log(Level.INFO, "SagaEventHandling: {0}.", event);
	}

	@EndSaga
	@SagaEventHandler(associationProperty = "todoId")
	public void onDeadlineExpired(final ToDoItemDeadlineExpiredEvent event) {
		logger.log(Level.INFO, "SagaEventHandling: {0}.", event);
	}

	@EndSaga
	@SagaEventHandler(associationProperty = "todoId")
	public void onToDoItemCompleted(final ToDoItemCompletedEvent event) {
		logger.log(Level.INFO, "SagaEventHandling: {0}.", event);
	}

}
