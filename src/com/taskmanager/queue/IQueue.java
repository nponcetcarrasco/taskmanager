package com.taskmanager.queue;

import com.taskmanager.dispatcher.ITaskDispatcher;
import com.taskmanager.model.Task;

public interface IQueue {
	public boolean enqueue(Task task);
	public Task dequeue();
	public int size();
	public boolean subscribe(ITaskDispatcher dispatcher);
}
