package com.taskmanager.dispatcher.impl;

import com.taskmanager.dispatcher.ITaskDispatcher;
import com.taskmanager.model.Task;
import com.taskmanager.model.TaskStatus;
import com.taskmanager.queue.IQueue;
import com.taskmanager.repository.Repository;
import com.taskmanager.runner.impl.TaskRunner;

public class TaskDispatcher implements ITaskDispatcher {

	private Repository<Task> repository;

	public TaskDispatcher(Repository<Task> repository){
		this.repository = repository;
	}

	@Override
	public void dispatch(IQueue queue) {
		if (queue == null) return;
		Task task = queue.dequeue();
		if (task == null) return;
		task.setStatus(TaskStatus.RUNNING);
		this.repository.save(task);
		new Thread(new TaskRunner(task, repository)).start();
	}

}
