package com.damdamdeo.noteapp.search.interfaces.facade.impl;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.inject.Inject;

import com.damdamdeo.noteapp.search.domain.TodoItemIndexedRepository;
import com.damdamdeo.noteapp.search.interfaces.facade.TodoItemFacade;
import com.damdamdeo.noteapp.search.interfaces.facade.dto.TodoItemDto;

public class DefaultTodoItemFacade implements TodoItemFacade {

	@Inject
	private TodoItemIndexedRepository todoItemIndexedRepository;

	@Override
	public List<TodoItemDto> search(final String description) {
		return todoItemIndexedRepository
				.search(description)
				.stream()
				.map(TodoItemDto::new)
				.collect(Collectors.toList());
	}

	@Override
	public TodoItemDto findByTodoId(final String todoId) {
		return Optional.ofNullable(todoItemIndexedRepository.findByTodoId(todoId))
				.map(TodoItemDto::new)
				.orElseThrow(() -> new IllegalStateException(String.format("Unknown todo item '%s'", todoId)));
	}

}
