package com.taskmanager.model;

import lombok.Data;

@Data
public class Task<T> {
	private Long id;
	private Class<T> taskClass;
	private TaskStatus status;
}
