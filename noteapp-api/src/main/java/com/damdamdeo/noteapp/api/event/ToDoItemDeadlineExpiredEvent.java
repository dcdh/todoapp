/*
 * Copyright (c) 2010-2014. Axon Framework
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.damdamdeo.noteapp.api.event;

import java.util.Objects;

/**
 * @author Allard Buijze
 */
public class ToDoItemDeadlineExpiredEvent {

	private final String todoId;

	public ToDoItemDeadlineExpiredEvent(final String todoId) {
		this.todoId = Objects.requireNonNull(todoId);
	}

	public String getTodoId() {
		return todoId;
	}

	@Override
	public String toString() {
		return "ToDoItemDeadlineExpiredEvent(" + todoId + ")";
	}
}
