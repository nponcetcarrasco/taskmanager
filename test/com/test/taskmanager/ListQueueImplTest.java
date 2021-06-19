package com.test.taskmanager;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import com.taskmanager.model.Task;
import com.taskmanager.queue.IQueue;
import com.taskmanager.queue.impl.ListQueueImpl;

public class ListQueueImplTest {

	private IQueue queue;

	@Before
	public void init() {
		queue = new ListQueueImpl();
	}

	@Test
	public void testAddElementToQueueOK() {
		assertTrue(queue.enqueue(new Task()));
		assertEquals(1, queue.size());
	}

	@Test
	public void testAddTwoElementToQueueOK() {
		assertTrue(queue.enqueue(new Task()));
		assertTrue(queue.enqueue(new Task()));
		assertEquals(2, queue.size());
	}

	@Test
	public void testAddElementToQueueAndRetrieveOK() {
		Task task = new Task();
		assertTrue(queue.enqueue(task));
		assertEquals(1, queue.size());
		assertEquals(task, queue.dequeue());
		assertEquals(0, queue.size());
	}

	@Test
	public void testAddTwoElementToQueueAndRetrieveOrderOK() {
		Task task = new Task();
		assertTrue(queue.enqueue(task));
		Task task2 = new Task();
		assertTrue(queue.enqueue(task2));
		assertEquals(2, queue.size());
		assertEquals(task, queue.dequeue());
		assertEquals(1, queue.size());
		assertEquals(task2, queue.dequeue());
		assertEquals(0, queue.size());
	}

	@Test
	public void testRetrieveEmptyQueue() {
		assertNull(queue.dequeue());
	}

}
