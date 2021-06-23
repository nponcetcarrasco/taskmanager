package com.test.taskmanager;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.Before;
import org.junit.Test;

import com.taskmanager.dispatcher.ITaskDispatcher;
import com.taskmanager.dispatcher.impl.TaskDispatcher;
import com.taskmanager.model.Task;
import com.taskmanager.model.TaskStatus;
import com.taskmanager.queue.IQueue;
import com.taskmanager.queue.impl.ListQueueImpl;
import com.taskmanager.repository.Repository;
import com.taskmanager.repository.impl.OnMemoryTaskRepository;
import com.taskmanager.task.impl.DummyTask;
import com.taskmanager.task.impl.DummyTaskConfiguration;

public class TaskDispatcherTest {

	ITaskDispatcher dispatcher;
	ITaskDispatcher dispatcher2;
	IQueue queue;
	Repository<Task> repository;

	@Before
	public void init() {
		repository = new OnMemoryTaskRepository();
		dispatcher = new TaskDispatcher(repository);
		dispatcher2 = new TaskDispatcher(repository);
		queue = new ListQueueImpl();
	}

	@Test
	public void testDispatcherOKManualCall() {
		Task task = new Task();
		task.setId(1L);
		task.setTaskClass(DummyTask.class);
		task.setConfigurationClass(DummyTaskConfiguration.class);
		task.setStatus(TaskStatus.QUEUED);
		queue.enqueue(task);
		dispatcher.dispatch(queue);
		task = repository.get(1L).orElse(null);
		assertNotNull(task);
		assertEquals(TaskStatus.RUNNING, task.getStatus());
	}

	@Test
	public void testDispatcherOKviaQueueSubscription() {
		queue.subscribe(dispatcher);
		Task task = new Task();
		task.setId(2L);
		task.setTaskClass(DummyTask.class);
		task.setConfigurationClass(DummyTaskConfiguration.class);
		task.setStatus(TaskStatus.QUEUED);
		queue.enqueue(task);
		task = repository.get(2L).orElse(null);
		assertNotNull(task);
		assertEquals(TaskStatus.RUNNING, task.getStatus());
	}

	@Test
	public void testmultipleDispatcherOKviaQueueSubscription() {
		queue.subscribe(dispatcher);
		queue.subscribe(dispatcher2);
		Task task = new Task();
		task.setId(3L);
		task.setTaskClass(DummyTask.class);
		task.setConfigurationClass(DummyTaskConfiguration.class);
		task.setStatus(TaskStatus.QUEUED);
		queue.enqueue(task);
		task = repository.get(3L).orElse(null);
		assertNotNull(task);
		assertEquals(TaskStatus.RUNNING, task.getStatus());
	}

}
