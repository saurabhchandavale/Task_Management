package com.example.demo.service.impl;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.model.Task;
import com.example.demo.repositort.TaskRepository;
import com.example.demo.service.TaskService;
import com.example.demo.shared.TaskStatus;

@Service
public class TaskServiceImpl implements TaskService {
	@Autowired
	private TaskRepository taskRepository;

	@Override
	public Task createTask(Task task, String requesterRole) throws Exception {
		if (!requesterRole.equalsIgnoreCase("ROLE_ADMIN")) {
			throw new Exception("Only admin can create task");
		}
			System.out.println("SCE32: Task status  " + TaskStatus.PENDING);
		task.setStatus(TaskStatus.PENDING);
		task.setCreatedAt(LocalDateTime.now());
		// task.setDeadLine(LocalDateTime.MAX);
		return taskRepository.save(task);
	}

	@Override
	public Task getTaskById(long Id) throws Exception {
		return taskRepository.findById(Id).orElseThrow(() -> new Exception("Task not found with ID " + Id));
	}

	@Override
	public List<Task> getTaskByStatus(TaskStatus status) throws Exception {
		List<Task> allTask = taskRepository.findAll();
		List<Task> filteredTaks = allTask.stream()
				.filter(task -> status == null || task.getStatus().name().equalsIgnoreCase(status.toString()))
				.collect(Collectors.toList());

		return filteredTaks;
	}

	@Override
	public Task updateTask(long Id, Task task, long userId) throws Exception {
		Task existingTask = getTaskById(Id);

		if (task.getTitle() != null) {
			existingTask.setTitle(task.getTitle());
		}
		if (task.getCreatedAt() != null) {
			existingTask.setCreatedAt(task.getCreatedAt());
		}
		if (task.getDeadLine() != null) {
			existingTask.setDeadLine(task.getDeadLine());
		}
		if (task.getImage() != null) {
			existingTask.setImage(task.getImage());
		}

		if (task.getStatus() != null) {
			existingTask.setStatus(task.getStatus());
		}
		if (task.getDescription() != null) {
			existingTask.setDescription(task.getDescription());
		}
		return taskRepository.save(existingTask);
	}

	@Override
	public void deleteTask(long Id) throws Exception {
		Task existingTask = getTaskById(Id);
		taskRepository.deleteById(Id);

	}

	@Override
	public Task assignedToUser(long userId, long taskId) throws Exception {
		Task task = getTaskById(taskId);
		System.out.println("SCE32 task " + task.toString() + "user id " + userId);
		task.setAssignedUserId(userId);
		task.setStatus(TaskStatus.ASSIGNED);
		return taskRepository.save(task);
	}

	@Override
	public List<Task> assignedUserTask(long userId, TaskStatus status) throws Exception {
		List<Task> assignedTaskToUser = taskRepository.findByAssignedUserId(userId);

		List<Task> filteredTaks = assignedTaskToUser.stream()
				.filter(task -> status == null || task.getStatus().name().equalsIgnoreCase(status.toString()))
				.collect(Collectors.toList());

		return filteredTaks;

	}

	@Override
	public Task completeTask(long taskId) throws Exception {
		Task task = getTaskById(taskId);
		task.setStatus(TaskStatus.DONE);
		return taskRepository.save(task);
	}

	@Override
	public List<Task> getAllTasks(TaskStatus status) {
		List<Task> all = taskRepository.findAll();
		List<Task> filteredTaks = all.stream()
				.filter(task -> status == null || task.getStatus().name().equalsIgnoreCase(status.toString()))
				.collect(Collectors.toList());

		return filteredTaks;
	}

}
