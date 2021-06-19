package com.taskmanager.dispatcher;

import com.taskmanager.model.Task;
import com.taskmanager.model.TaskStatus;
import com.taskmanager.queue.IQueue;
import com.taskmanager.repository.TaskRepository;
import com.taskmanager.runner.ITaskRunner;

public class TaskDispatcher implements ITaskDispatcher {

	private TaskRepository repository;
	private ITaskRunner taskRunner;

	public TaskDispatcher(TaskRepository repository, ITaskRunner taskRunner){
		this.repository = repository;
		this.taskRunner = taskRunner;
	}

	@Override
	public void dispatch(IQueue queue) {
		if (queue == null) return;
		Task task = queue.dequeue();
		if (task == null) return;
		task.setStatus(TaskStatus.RUNNING);
		this.repository.save(task);
		this.taskRunner.run(task);
	}

}
