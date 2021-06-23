package com.taskmanager.scheduler.impl;

import java.util.Optional;

import com.taskmanager.model.Task;
import com.taskmanager.model.TaskStatus;
import com.taskmanager.queue.IQueue;
import com.taskmanager.repository.Repository;
import com.taskmanager.scheduler.ITaskScheduler;
import com.taskmanager.task.ITask;
import com.taskmanager.task.ITaskConfiguration;

public class TaskScheduler implements ITaskScheduler {

	private Repository<Task> repository;
	private IQueue queue;

	public TaskScheduler(Repository<Task> repository, IQueue queue) {
		this.repository = repository;
		this.queue = queue;
	}

	@Override
	public Long schedule(Class<? extends ITask> taskClass, Class<? extends ITaskConfiguration> configurationClass) {
		Task task = new Task();
		task.setTaskClass(taskClass);
		task.setConfigurationClass(configurationClass);
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
