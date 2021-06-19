package com.test.taskmanager;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.Before;
import org.junit.Test;

import com.taskmanager.model.Task;
import com.taskmanager.model.TaskStatus;
import com.taskmanager.repository.TaskRepository;
import com.taskmanager.repository.impl.OnMemoryTaskRepository;
import com.taskmanager.runner.ITaskRunner;
import com.taskmanager.runner.impl.TaskRunner;
import com.taskmanager.task.impl.DummyFailTask;
import com.taskmanager.task.impl.DummyFailUnexpectedTask;
import com.taskmanager.task.impl.DummyTask;

public class TaskRunnerTest {

	ITaskRunner runner;
	TaskRepository<Task> repository;

	@Before
	public void init() {
		repository = new OnMemoryTaskRepository();
		runner = new TaskRunner(repository);
	}

	@Test
	public void testRunnerRunOK() {
		Task task = new Task<>();
		task.setId(1L);
		task.setStatus(TaskStatus.RUNNING);
		task.setTaskClass(DummyTask.class);
		runner.run(task);
		task = repository.get(1L).orElse(null);
		assertNotNull(task);
		assertEquals(TaskStatus.SUCCESS, task.getStatus());
	}

	@Test
	public void testRunnerRunOKTaskFail() {
		Task task = new Task<>();
		task.setId(2L);
		task.setStatus(TaskStatus.RUNNING);
		task.setTaskClass(DummyFailTask.class);
		runner.run(task);
		task = repository.get(2L).orElse(null);
		assertNotNull(task);
		assertEquals(TaskStatus.FAILED, task.getStatus());
	}

	@Test
	public void testRunnerRunOKTaskFailUnexpected() {
		Task task = new Task<>();
		task.setId(3L);
		task.setStatus(TaskStatus.RUNNING);
		task.setTaskClass(DummyFailUnexpectedTask.class);
		runner.run(task);
		task = repository.get(3L).orElse(null);
		assertNotNull(task);
		assertEquals(TaskStatus.FAILED, task.getStatus());
	}

}
