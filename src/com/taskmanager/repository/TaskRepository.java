package com.taskmanager.repository;

import java.util.Optional;

import com.taskmanager.model.Task;

public interface TaskRepository<T> {
	public Task<T> save(Task<T> task);
	public Optional<Task<T>> get(Long id);
}
