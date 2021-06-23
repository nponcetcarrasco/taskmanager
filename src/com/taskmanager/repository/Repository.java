package com.taskmanager.repository;

import java.util.Optional;

public interface Repository<T> {
	public T save(T task);
	public Optional<T> get(Long id);
}
