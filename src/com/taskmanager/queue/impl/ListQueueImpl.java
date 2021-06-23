package com.taskmanager.queue.impl;

import java.util.ArrayList;
import java.util.Deque;
import java.util.LinkedList;
import java.util.List;

import com.taskmanager.dispatcher.ITaskDispatcher;
import com.taskmanager.model.Task;
import com.taskmanager.queue.IQueue;

public class ListQueueImpl implements IQueue {

	private Deque<Task> tasks;
	private List<ITaskDispatcher> subscribers;

	public ListQueueImpl() {
		tasks = new LinkedList<>();
		subscribers = new ArrayList<>();
	}

	@Override
	public boolean enqueue(Task task) {
		boolean ok = false;
		synchronized (tasks) {
			ok = this.tasks.add(task);
		}
		if (ok) {
			subscribers.forEach(dispatcher -> dispatcher.dispatch(this));
		}
		return ok;
	}

	@Override
	public Task dequeue() {
		synchronized (tasks) {
			if (tasks.isEmpty()) return null;
			return tasks.removeFirst();
		}
	}

	@Override
	public int size() {
		synchronized (tasks) {
			return tasks.size();
		}
	}

	@Override
	public boolean subscribe(ITaskDispatcher dispatcher) {
		synchronized (subscribers) {
			return this.subscribers.add(dispatcher);
		}
	}

}
