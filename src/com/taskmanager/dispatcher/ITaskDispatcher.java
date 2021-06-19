package com.taskmanager.dispatcher;

import com.taskmanager.queue.IQueue;

public interface ITaskDispatcher {
	public void dispatch(IQueue queue);
}
