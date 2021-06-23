package com.taskmanager.scheduler;

import com.taskmanager.model.TaskStatus;
import com.taskmanager.task.ITask;
import com.taskmanager.task.ITaskConfiguration;

public interface ITaskScheduler {
	public Long schedule(Class<? extends ITask> taskClass, Class<? extends ITaskConfiguration> configurationClass);
	public TaskStatus status(Long taskID);
}
