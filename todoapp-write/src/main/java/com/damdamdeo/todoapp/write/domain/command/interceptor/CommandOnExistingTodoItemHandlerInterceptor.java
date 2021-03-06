package com.damdamdeo.todoapp.write.domain.command.interceptor;

import java.lang.invoke.MethodHandles;
import java.util.Collections;
import java.util.function.Function;
import java.util.logging.Logger;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import org.axonframework.commandhandling.model.Repository;
import org.axonframework.messaging.InterceptorChain;
import org.axonframework.messaging.Message;
import org.axonframework.messaging.MessageHandlerInterceptor;
import org.axonframework.messaging.unitofwork.UnitOfWork;

import com.damdamdeo.todoapp.write.domain.DefaultTodoItem;
import com.damdamdeo.todoapp.write.domain.command.CommandOnExistingTodoItem;

@ApplicationScoped
public class CommandOnExistingTodoItemHandlerInterceptor implements MessageHandlerInterceptor<Message<?>> {

	private static final Logger logger = Logger.getLogger(MethodHandles.lookup().lookupClass().getName());

	@Inject
	private Repository<DefaultTodoItem> todoItemRepository;

	@Override
	public Object handle(final UnitOfWork<? extends Message<?>> unitOfWork, final InterceptorChain interceptorChain)
			throws Exception {
		final Message<?> command = unitOfWork.getMessage();
		if (command.getPayload() instanceof CommandOnExistingTodoItem) {
			final String todoId = ((CommandOnExistingTodoItem) command.getPayload()).getTodoId();
			final DefaultTodoItem todoItem = todoItemRepository.load(todoId).invoke(Function.identity());
			logger.info(String.format("Handling a command on an existing item '%s' on aggregate '%s' by '%s'.", command.toString(),
					todoItem.toString(), this));
			unitOfWork.transformMessage(message -> command.andMetaData(Collections.singletonMap("todoItem", todoItem)));
		}
		return interceptorChain.proceed();
	}

}
