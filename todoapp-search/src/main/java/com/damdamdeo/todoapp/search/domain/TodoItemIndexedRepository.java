package com.damdamdeo.todoapp.search.domain;

import java.util.List;

public interface TodoItemIndexedRepository {

	List<DefaultTodoItem> search(String description);

	DefaultTodoItem findByTodoId(String todoId);

	DefaultTodoItem save(DefaultTodoItem todoItem);

}
