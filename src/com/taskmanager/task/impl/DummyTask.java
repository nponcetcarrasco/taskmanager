package com.taskmanager.task.impl;

import com.taskmanager.task.ITask;
import com.taskmanager.task.TaskFlowStatus;

public class DummyTask implements ITask {

	@Override
	public TaskFlowStatus execute() {
		return TaskFlowStatus.SUCCESS;
	}

	@Override
	public void onFailure() {
		// do nothing
	}

}
