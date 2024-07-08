package com.example.demo.service;

import java.util.List;

import com.example.demo.model.Task;
import com.example.demo.shared.TaskStatus;

public interface TaskService {
	
	public Task createTask(Task task, String requesterRole) throws Exception;
	public Task getTaskById(long Id) throws Exception;
	public List<Task> getTaskByStatus(TaskStatus status) throws Exception;
	public Task updateTask(long Id, Task task, long userId) throws Exception;
	public void deleteTask(long Id) throws Exception;
	public Task assignedToUser(long userId, long taskId) throws Exception;
	public List<Task> assignedUserTask(long userId, TaskStatus status) throws Exception;
	public Task completeTask(long taskId) throws Exception;
	public List<Task> getAllTasks(TaskStatus status);
		

}
