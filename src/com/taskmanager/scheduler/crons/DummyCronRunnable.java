package com.taskmanager.scheduler.crons;

import java.time.Duration;
import java.time.LocalDateTime;

import com.taskmanager.scheduler.ITaskScheduler;
import com.taskmanager.task.TaskCronSchedule;

public class DummyCronRunnable implements Runnable {

	private TaskCronSchedule cronSchedule;
	private Long taskId;
	private ITaskScheduler taskScheduler;

	public DummyCronRunnable(TaskCronSchedule cronSchedule, Long taskId, ITaskScheduler taskScheduler) {
		this.cronSchedule=cronSchedule;
		this.taskId = taskId;
		this.taskScheduler = taskScheduler;
	}

	@Override
	public void run() {
		LocalDateTime now = LocalDateTime.now();
		Duration duration = Duration.between(now, cronSchedule.getScheduleTime());
		if (duration.toMillis() > 0) {
			try {
				Thread.sleep(duration.toMillis());
			} catch (InterruptedException e) {
				// do nothing
				Thread.currentThread().interrupt();
			}
		}
		this.taskScheduler.schedule(taskId);
	}

}
