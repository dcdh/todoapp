package com.damdamdeo.noteapp.search.interfaces.facade.dto;

import com.damdamdeo.noteapp.api.Status;
import com.damdamdeo.noteapp.api.TodoItem;

public class TodoItemDto {

	private String todoId;

	private String description;

	private Status status;

	public TodoItemDto() {}

	public TodoItemDto(final TodoItem todoItem) {
		this.todoId = todoItem.todoId();
		this.description = todoItem.description();
		this.status = todoItem.status();
	}

	public String getTodoId() {
		return todoId;
	}

	public void setTodoId(String todoId) {
		this.todoId = todoId;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

}
