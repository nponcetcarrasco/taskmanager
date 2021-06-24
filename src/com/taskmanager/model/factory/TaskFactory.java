package com.taskmanager.model.factory;

import com.taskmanager.model.Task;
import com.taskmanager.model.TaskStatus;
import com.taskmanager.task.ITask;
import com.taskmanager.task.ITaskConfiguration;

public class TaskFactory {

	private TaskFactory() {
	}

	public static Task createForSchedule(Class<? extends ITask> taskClass, Class<? extends ITaskConfiguration> configurationClass) {
		Task task = new Task();
		task.setTaskClass(taskClass);
		task.setConfigurationClass(configurationClass);
		task.setStatus(TaskStatus.QUEUED);
		return task;
	}

}
