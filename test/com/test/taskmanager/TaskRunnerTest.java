package com.test.taskmanager;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.Before;
import org.junit.Test;

import com.taskmanager.model.Task;
import com.taskmanager.model.TaskStatus;
import com.taskmanager.repository.Repository;
import com.taskmanager.repository.impl.OnMemoryTaskRepository;
import com.taskmanager.runner.ITaskRunner;
import com.taskmanager.runner.impl.TaskRunner;
import com.taskmanager.task.impl.DummyFailTask;
import com.taskmanager.task.impl.DummyFailUnexpectedTask;
import com.taskmanager.task.impl.DummyTask;
import com.taskmanager.task.impl.DummyTaskConfiguration;

public class TaskRunnerTest {

	ITaskRunner runner;
	Repository<Task> repository;

	@Before
	public void init() {
		repository = new OnMemoryTaskRepository();
	}

	@Test
	public void testRunnerRunOK() {
		Task task = new Task();
		task.setId(1L);
		task.setStatus(TaskStatus.RUNNING);
		task.setTaskClass(DummyTask.class);
		task.setConfigurationClass(DummyTaskConfiguration.class);
		runner = new TaskRunner(task, repository);
		runner.run();
		task = repository.get(1L).orElse(null);
		assertNotNull(task);
		assertEquals(TaskStatus.SUCCESS, task.getStatus());
	}

	@Test
	public void testRunnerRunOKTaskFail() {
		Task task = new Task();
		task.setId(2L);
		task.setStatus(TaskStatus.RUNNING);
		task.setTaskClass(DummyFailTask.class);
		task.setConfigurationClass(DummyTaskConfiguration.class);
		runner = new TaskRunner(task, repository);
		runner.run();
		task = repository.get(2L).orElse(null);
		assertNotNull(task);
		assertEquals(TaskStatus.FAILED, task.getStatus());
	}

	@Test
	public void testRunnerRunOKTaskFailUnexpected() {
		Task task = new Task();
		task.setId(3L);
		task.setStatus(TaskStatus.RUNNING);
		task.setTaskClass(DummyFailUnexpectedTask.class);
		task.setConfigurationClass(DummyTaskConfiguration.class);
		runner = new TaskRunner(task, repository);
		runner.run();
		task = repository.get(3L).orElse(null);
		assertNotNull(task);
		assertEquals(TaskStatus.FAILED, task.getStatus());
	}

}
