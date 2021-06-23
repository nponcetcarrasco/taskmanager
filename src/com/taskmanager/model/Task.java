package com.taskmanager.model;

import com.taskmanager.task.ITask;
import com.taskmanager.task.ITaskConfiguration;

import lombok.Data;

@Data
public class Task {
	private Long id;
	private Class<? extends ITask> taskClass;
	private Class<? extends ITaskConfiguration> configurationClass;
	private TaskStatus status;
}
