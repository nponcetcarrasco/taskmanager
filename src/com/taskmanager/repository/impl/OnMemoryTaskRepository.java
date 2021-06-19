package com.taskmanager.repository.impl;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import com.taskmanager.model.Task;
import com.taskmanager.repository.TaskRepository;

public class OnMemoryTaskRepository implements TaskRepository {

	private Map<Long, Task> tasks;
	private Long sequenceID;

	public OnMemoryTaskRepository() {
		tasks = new HashMap<>();
		sequenceID = 1L;
	}

	@Override
	public Task save(Task task) {
		if (task.getId() == null) {
			task.setId(sequenceID++);
		}
		synchronized (tasks) {
			tasks.put(task.getId(), task);
		}
		return task;
	}

	@Override
	public Optional<Task> get(Long id) {
		synchronized (tasks) {
			return Optional.of(tasks.get(id));
		}
	}

}
