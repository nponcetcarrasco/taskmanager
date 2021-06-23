package com.taskmanager.runner.impl;

import com.taskmanager.model.Task;
import com.taskmanager.model.TaskStatus;
import com.taskmanager.repository.Repository;
import com.taskmanager.runner.ITaskRunner;
import com.taskmanager.task.ITask;
import com.taskmanager.task.ITaskConfiguration;
import com.taskmanager.task.TaskFlowStatus;

public class TaskRunner implements ITaskRunner {

	private Repository<Task> repository;
	private Task task;

	public TaskRunner(Task task, Repository<Task> repository) {
		this.repository = repository;
		this.task = task;
	}

	@Override
	public void run() {
		ITask runnableTask = null;
		try {
			runnableTask = task.getTaskClass().newInstance();
			ITaskConfiguration taskConfiguration = task.getConfigurationClass().newInstance();
			task.setStatus(TaskStatus.SUCCESS);
			TaskFlowStatus finishedStatus = runnableTask.execute(taskConfiguration);
			if (TaskFlowStatus.FAIL.equals(finishedStatus)) {
				runnableTask.onFailure();
				task.setStatus(TaskStatus.FAILED);
			}
		} catch (Exception e) {
			e.printStackTrace();
			if (runnableTask != null) {
				runnableTask.onFailure();
			}
			task.setStatus(TaskStatus.FAILED);
		}
		this.repository.save(task);
	}

}
