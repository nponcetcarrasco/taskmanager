package com.taskmanager.runner.impl;

import com.taskmanager.model.Task;
import com.taskmanager.model.TaskStatus;
import com.taskmanager.repository.TaskRepository;
import com.taskmanager.runner.ITaskRunner;
import com.taskmanager.task.ITask;
import com.taskmanager.task.TaskFlowStatus;

public class TaskRunner implements ITaskRunner {

	private TaskRepository repository;

	public TaskRunner(TaskRepository repository) {
		this.repository = repository;
	}

	@Override
	public void run(Task task) {
		ITask runnableTask = null;
		try {
			runnableTask = (ITask) task.getTaskClass().newInstance();
			task.setStatus(TaskStatus.SUCCESS);
			TaskFlowStatus finishedStatus = runnableTask.execute();
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
