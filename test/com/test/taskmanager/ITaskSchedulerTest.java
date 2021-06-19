package com.test.taskmanager;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.taskmanager.model.TaskStatus;
import com.taskmanager.queue.impl.ListQueueImpl;
import com.taskmanager.repository.impl.OnMemoryTaskRepository;
import com.taskmanager.scheduler.ITaskScheduler;
import com.taskmanager.scheduler.impl.TaskScheduler;
import com.taskmanager.task.ITask;
import com.taskmanager.task.TaskFlowStatus;

public class ITaskSchedulerTest {

	@Test
	public void testScheduleOK() {
		ITaskScheduler scheduler = new TaskScheduler(new OnMemoryTaskRepository(), new ListQueueImpl());
		Long taskID = scheduler.schedule(TestTask.class);
		assertEquals(Long.valueOf(1), taskID);
		Long anotherTaskID = scheduler.schedule(AnotherTestTask.class);
		assertEquals(Long.valueOf(2), anotherTaskID);
	}

	@Test
	public void testScheduleOKcheckstatusOK() {
		ITaskScheduler scheduler = new TaskScheduler(new OnMemoryTaskRepository(), new ListQueueImpl());
		Long taskID = scheduler.schedule(TestTask.class);
		assertEquals(Long.valueOf(1), taskID);
		assertEquals(TaskStatus.QUEUED, scheduler.status(taskID));
	}

	class TestTask implements ITask {
		@Override
		public TaskFlowStatus execute() {
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				return TaskFlowStatus.FAIL;
			}
			return TaskFlowStatus.SUCCESS;
		}
		@Override
		public void onFailure() {
			// Nothing
		}

	}

	class AnotherTestTask implements ITask {

		@Override
		public TaskFlowStatus execute() {
			try {
				Thread.sleep(2000);
			} catch (InterruptedException e) {
				return TaskFlowStatus.FAIL;
			}
			return TaskFlowStatus.SUCCESS;
		}
		@Override
		public void onFailure() {
			// Nothing
		}

	}

}
