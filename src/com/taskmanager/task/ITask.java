package com.taskmanager.task;

public interface ITask {
	public TaskFlowStatus execute();
	public void onFailure();
}
