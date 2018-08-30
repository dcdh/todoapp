package com.damdamdeo.noteapp.search.domain;

import org.apache.solr.client.solrj.beans.Field;

import com.damdamdeo.noteapp.api.Status;
import com.damdamdeo.noteapp.api.TodoItem;

public class DefaultTodoItem implements TodoItem {

	@Field(value = "id")
	private String todoId;

	@Field(value = "description_txt_fr")
	private String description;

	@Field(value = "status_s")
	private String status;

	public DefaultTodoItem() {}

	public DefaultTodoItem(final String todoId, final String description) {
		this.todoId = todoId;
		this.description = description;
		this.status = Status.CREATED.name();
	}

	public void markAsCompletd() {
		this.status = Status.COMPLETED.name();
	}

	public void markAsOverdue() {
		this.status = Status.OVERDUE.name();
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
		return Status.valueOf(status);
	}

}
