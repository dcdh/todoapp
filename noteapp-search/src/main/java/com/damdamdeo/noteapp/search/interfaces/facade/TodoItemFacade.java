package com.damdamdeo.noteapp.search.interfaces.facade;

import java.util.List;

import com.damdamdeo.noteapp.search.interfaces.facade.dto.TodoItemDto;

public interface TodoItemFacade {

	List<TodoItemDto> search(String description);

	TodoItemDto findByTodoId(String todoId);

}
