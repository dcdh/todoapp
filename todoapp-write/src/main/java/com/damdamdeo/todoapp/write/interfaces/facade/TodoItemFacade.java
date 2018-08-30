package com.damdamdeo.todoapp.write.interfaces.facade;

import com.damdamdeo.todoapp.write.interfaces.facade.dto.TodoItemDto;

public interface TodoItemFacade {

	TodoItemDto createTodoItem(String todoId, String description);

	TodoItemDto completeTodoItem(String todoId);

}
