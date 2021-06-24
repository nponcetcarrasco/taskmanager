package com.taskmanager.scheduler.impl;

import java.util.Optional;

import com.taskmanager.model.Task;
import com.taskmanager.model.TaskStatus;
import com.taskmanager.model.factory.TaskFactory;
import com.taskmanager.queue.IQueue;
import com.taskmanager.repository.Repository;
import com.taskmanager.scheduler.ITaskScheduler;
import com.taskmanager.scheduler.crons.DummyCronRunnable;
import com.taskmanager.task.ITask;
import com.taskmanager.task.ITaskConfiguration;
import com.taskmanager.task.TaskCronSchedule;

public class TaskScheduler implements ITaskScheduler {

	private Repository<Task> repository;
	private IQueue queue;

	public TaskScheduler(Repository<Task> repository, IQueue queue) {
		this.repository = repository;
		this.queue = queue;
	}

	@Override
	public Long schedule(Class<? extends ITask> taskClass, Class<? extends ITaskConfiguration> configurationClass) {
		Task task = TaskFactory.createForSchedule(taskClass, configurationClass);
		task = this.repository.save(task);
		this.schedule(task);
		return task.getId();
	}

	@Override
	public TaskStatus status(Long taskId) {
		if (taskId == null) return null;
		Optional<Task> task = this.repository.get(taskId);
		if (task.isPresent()) {
			return task.get().getStatus();
		}
		return null;
	}

	@Override
	public void schedule(Long taskId) {
		Optional<Task> task = this.repository.get(taskId);
		if (task.isPresent()) {
			this.schedule(task.get());
		}
	}

	@Override
	public Long schedule(Class<? extends ITask> taskClass, Class<? extends ITaskConfiguration> configurationClass, TaskCronSchedule cronSchedule) {
		Task task = TaskFactory.createForSchedule(taskClass, configurationClass);
		task = this.repository.save(task);
		Long taskId = task.getId();
		new Thread(new DummyCronRunnable(cronSchedule, taskId, this)).start();
		return task.getId();
	}

	public void schedule(Task task) {
		this.queue.enqueue(task);
	}

}
