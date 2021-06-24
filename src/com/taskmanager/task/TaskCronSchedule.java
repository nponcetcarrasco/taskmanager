package com.taskmanager.task;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class TaskCronSchedule {
	private LocalDateTime scheduleTime;
}
