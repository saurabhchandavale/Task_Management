package com.example.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.model.Task;
import com.example.demo.model.UserDto;
import com.example.demo.service.TaskService;
import com.example.demo.service.UserService;
import com.example.demo.shared.TaskStatus;

@RestController
@RequestMapping("/api/tasks")
public class TaskController {
	
	@Autowired
	private TaskService taskService;
	@Autowired
	private UserService userService;
	
	@PostMapping("/createTask")
	public ResponseEntity<Task> createTask(@RequestBody Task task, @RequestHeader("Authorization") String jwt) throws Exception{
		UserDto userProfile = userService.getUserProfile(jwt);
		
		Task createdTask  = taskService.createTask(task, userProfile.getRole());
		return new ResponseEntity<>(createdTask,HttpStatus.CREATED);
		
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<Task> getTaskById(@PathVariable long id, @RequestHeader("Authorization") String jwt) throws Exception{
		UserDto userProfile = userService.getUserProfile(jwt);
		
		Task task  = taskService.getTaskById(id);
		return new ResponseEntity<>(task,HttpStatus.OK);
		
	}
	
	@GetMapping("/")
	public ResponseEntity<List<Task>> getAllTasks(@RequestParam(required = false) TaskStatus status
			, @RequestHeader("Authorization") String jwt) throws Exception{
		UserDto userProfile = userService.getUserProfile(jwt);
		
		List<Task> task  = taskService.getAllTasks(status);
		return new ResponseEntity<List<Task>>(task,HttpStatus.OK);
		
	}
	
	@PutMapping("/{id}/user/{userId}/assigned")
	public ResponseEntity<Task> assignedTaskToUser(@PathVariable long id,
			@PathVariable long userId
			, @RequestHeader("Authorization") String jwt) throws Exception{
		UserDto userProfile = userService.getUserProfile(jwt);
		
		Task task  = taskService.assignedToUser(userId, id);
		return new ResponseEntity<Task>(task,HttpStatus.OK);
		
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<Task> updateTask(@PathVariable long id,
			@RequestBody Task task,
			@RequestHeader("Authorization") String jwt) throws Exception{
		UserDto userProfile = userService.getUserProfile(jwt);
		
		Task updatedTask  = taskService.updateTask(id, task, userProfile.getId());
		return new ResponseEntity<Task>(updatedTask,HttpStatus.OK);
		
	}
	
	@PutMapping("/{id}/complete")
	public ResponseEntity<Task> completeTask(@PathVariable long id) throws Exception {
		
		Task completedTask  = taskService.completeTask(id);
		return new ResponseEntity<>(completedTask,HttpStatus.OK);
		
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<Task> deleteTask(@PathVariable long id) throws Exception{
		
		taskService.deleteTask(id);
		return new ResponseEntity<Task>(HttpStatus.NO_CONTENT);
		
	}

	


}
