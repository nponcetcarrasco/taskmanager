package com.taskmanager.scheduler;

import com.taskmanager.model.TaskStatus;

public interface ITaskScheduler {
	public <T> Long schedule(Class<T> taskClass);
	public TaskStatus status(Long taskID);
}
