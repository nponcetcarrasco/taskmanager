package com.taskmanager.task.impl;

import com.taskmanager.task.ITask;
import com.taskmanager.task.ITaskConfiguration;
import com.taskmanager.task.TaskFlowStatus;

public class DummyFailTask implements ITask {

	@Override
	public TaskFlowStatus execute(ITaskConfiguration configuration) {
		return TaskFlowStatus.FAIL;
	}

	@Override
	public void onFailure() {
		// rollbacking something
	}

}
