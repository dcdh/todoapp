package com.damdamdeo.todoapp.write.domain.command.interceptor;

import java.lang.invoke.MethodHandles;
import java.util.List;
import java.util.function.BiFunction;
import java.util.logging.Logger;

import javax.enterprise.context.Dependent;

import org.axonframework.messaging.Message;
import org.axonframework.messaging.MessageDispatchInterceptor;

import com.damdamdeo.todoapp.write.domain.command.CommandOnExistingTodoItem;

// Repository are not working here because "No UnitOfWork is currently started for this thread."
@Dependent
//Attention same instance in all application
public class CommandOnExistingTodoItemDispatchInterceptor implements MessageDispatchInterceptor<Message<?>> {

	private static final Logger logger = Logger.getLogger(MethodHandles.lookup().lookupClass().getName());

//	@Inject
//	private Repository<DefaultTodoItem> todoItemRepository;

//	@Override
//	public BiFunction<Integer, Message<?>, Message<?>> handle(List<? extends Message<?>> messages) {
//		return (index, command) -> {
//			if (command.getPayload() instanceof CommandOnExistingTodoItem) {
//				final String todoId = ((CommandOnExistingTodoItem) command.getPayload()).getTodoId();
//				final DefaultTodoItem todoItem = todoItemRepository.load(todoId).invoke(Function.identity());
//				logger.info(String.format("Dispatching a command on an existing item '%s' on aggregate '%s'.", command.toString(),
//						todoItem.toString()));
//			}
//			return command;
//		};
//	}
	@Override
	public BiFunction<Integer, Message<?>, Message<?>> handle(List<? extends Message<?>> messages) {
		return (index, command) -> {
			if (command.getPayload() instanceof CommandOnExistingTodoItem) {
				final String todoId = ((CommandOnExistingTodoItem) command.getPayload()).getTodoId();
				logger.info(String.format("Dispatching a command on an existing item '%s' on aggregate '%s' by '%s'.", command.toString(), todoId, this));
			}
			return command;
		};
	}

}
