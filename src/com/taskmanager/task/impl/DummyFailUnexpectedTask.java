package com.taskmanager.task.impl;

import com.taskmanager.task.ITask;
import com.taskmanager.task.ITaskConfiguration;
import com.taskmanager.task.TaskFlowStatus;

public class DummyFailUnexpectedTask implements ITask {

	private boolean fail = true;

	@Override
	public TaskFlowStatus execute(ITaskConfiguration configuration) {
		if (fail) throw new NullPointerException("crash");
		return TaskFlowStatus.SUCCESS;
	}

	@Override
	public void onFailure() {
		// rollback of something
	}

}
