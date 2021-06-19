package com.taskmanager.scheduler.impl;

import java.util.Optional;

import com.taskmanager.model.Task;
import com.taskmanager.model.TaskStatus;
import com.taskmanager.queue.IQueue;
import com.taskmanager.repository.TaskRepository;
import com.taskmanager.scheduler.ITaskScheduler;

public class TaskScheduler implements ITaskScheduler {

	private TaskRepository repository;
	private IQueue queue;

	public TaskScheduler(TaskRepository repository, IQueue queue) {
		this.repository = repository;
		this.queue = queue;
	}

	@Override
	public <T> Long schedule(Class<T> taskClass) {
		Task<T> task = new Task<>();
		task.setTaskClass(taskClass);
		task.setStatus(TaskStatus.QUEUED);
		task = this.repository.save(task);
		this.queue.enqueue(task);
		return task.getId();
	}

	@Override
	public TaskStatus status(Long taskID) {
		if (taskID == null) return null;
		Optional<Task> task = this.repository.get(taskID);
		if (task.isPresent()) {
			return task.get().getStatus();
		}
		return null;
	}

}
