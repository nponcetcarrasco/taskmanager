package com.test.taskmanager;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import java.time.LocalDateTime;

import org.junit.Test;

import com.taskmanager.model.Task;
import com.taskmanager.model.TaskStatus;
import com.taskmanager.queue.IQueue;
import com.taskmanager.queue.impl.ListQueueImpl;
import com.taskmanager.repository.impl.OnMemoryTaskRepository;
import com.taskmanager.scheduler.ITaskScheduler;
import com.taskmanager.scheduler.impl.TaskScheduler;
import com.taskmanager.task.ITask;
import com.taskmanager.task.ITaskConfiguration;
import com.taskmanager.task.TaskCronSchedule;
import com.taskmanager.task.TaskFlowStatus;

public class TaskSchedulerTest {

	@Test
	public void testScheduleOK() {
		ITaskScheduler scheduler = new TaskScheduler(new OnMemoryTaskRepository(), new ListQueueImpl());
		Long taskID = scheduler.schedule(TestSleep1sTask.class, DummyTaskConfiguration.class);
		assertEquals(Long.valueOf(1), taskID);
		Long anotherTaskID = scheduler.schedule(AnotherTestTask.class, DummyTaskConfiguration.class);
		assertEquals(Long.valueOf(2), anotherTaskID);
	}

	@Test
	public void testScheduleOKcheckstatusOK() {
		ITaskScheduler scheduler = new TaskScheduler(new OnMemoryTaskRepository(), new ListQueueImpl());
		Long taskID = scheduler.schedule(TestSleep1sTask.class, DummyTaskConfiguration.class);
		assertEquals(Long.valueOf(1), taskID);
		assertEquals(TaskStatus.QUEUED, scheduler.status(taskID));
	}


	@Test
	public void testScheduleWithCronOKcheckstatusOK() {
		ITaskScheduler scheduler = new TaskScheduler(new OnMemoryTaskRepository(), new ListQueueImpl());
		Long taskID = scheduler.schedule(TestSleep1sTask.class, DummyTaskConfiguration.class, new TaskCronSchedule(LocalDateTime.now().plusSeconds(5)));
		assertEquals(Long.valueOf(1), taskID);
		assertEquals(TaskStatus.QUEUED, scheduler.status(taskID));
	}

	@Test
	public void testScheduleWithCronOKwaitForUpdateOK() throws InterruptedException {
		IQueue queue = new ListQueueImpl();
		ITaskScheduler scheduler = new TaskScheduler(new OnMemoryTaskRepository(), queue);
		Long taskID = scheduler.schedule(TestSleep1sTask.class, DummyTaskConfiguration.class, new TaskCronSchedule(LocalDateTime.now().plusSeconds(2)));
		assertEquals(0, queue.size());
		assertEquals(Long.valueOf(1), taskID);
		assertEquals(TaskStatus.QUEUED, scheduler.status(taskID));
		Thread.sleep(3000);
		assertEquals(1, queue.size());
		Task task = queue.dequeue();
		assertNotNull(task);
		assertEquals(Long.valueOf(1), task.getId());
	}

	@Test
	public void testScheduleWithCronNotAddedToQueueYet() throws InterruptedException {
		IQueue queue = new ListQueueImpl();
		ITaskScheduler scheduler = new TaskScheduler(new OnMemoryTaskRepository(), queue);
		scheduler.schedule(TestSleep1sTask.class, DummyTaskConfiguration.class, new TaskCronSchedule(LocalDateTime.now().plusSeconds(3)));
		assertNull(queue.dequeue());
	}

	@Test
	public void testScheduleWithCronNotAddedToQueueYetButOtherTaskAddedOK() throws InterruptedException {
		IQueue queue = new ListQueueImpl();
		ITaskScheduler scheduler = new TaskScheduler(new OnMemoryTaskRepository(), queue);
		scheduler.schedule(TestSleep1sTask.class, DummyTaskConfiguration.class, new TaskCronSchedule(LocalDateTime.now().plusSeconds(3)));
		assertEquals(0, queue.size());
		assertNull(queue.dequeue());
		scheduler.schedule(AnotherTestTask.class, DummyTaskConfiguration.class);
		assertEquals(1, queue.size());
		Task task = queue.dequeue();
		assertNotNull(task);
		assertEquals(Long.valueOf(2), task.getId());
		assertEquals(0, queue.size());
		Thread.sleep(5000);
		assertEquals(1, queue.size());
		task = queue.dequeue();
		assertNotNull(task);
		assertEquals(Long.valueOf(1), task.getId());
	}


	class TestSleep1sTask implements ITask {
		@Override
		public TaskFlowStatus execute(ITaskConfiguration configuration) {
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
		public TaskFlowStatus execute(ITaskConfiguration configuration) {
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

	class DummyTaskConfiguration implements ITaskConfiguration{
		// No config
	}

}
