package com.taskmanager.task;

public interface ITask {
	public TaskFlowStatus execute(ITaskConfiguration options);
	public void onFailure();
}
