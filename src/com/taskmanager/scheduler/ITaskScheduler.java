package com.taskmanager.scheduler;

import com.taskmanager.model.TaskStatus;
import com.taskmanager.task.ITask;
import com.taskmanager.task.ITaskConfiguration;
import com.taskmanager.task.TaskCronSchedule;

public interface ITaskScheduler {
	public Long schedule(Class<? extends ITask> taskClass, Class<? extends ITaskConfiguration> configurationClass);
	public Long schedule(Class<? extends ITask> taskClass, Class<? extends ITaskConfiguration> configurationClass, TaskCronSchedule cronSchedule);
	public void schedule(Long taskId);
	public TaskStatus status(Long taskID);
}
