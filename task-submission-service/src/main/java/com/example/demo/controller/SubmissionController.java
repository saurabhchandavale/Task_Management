package com.example.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.modal.Submission;
import com.example.demo.modal.UserDto;
import com.example.demo.service.SubmissionService;
import com.example.demo.service.TaskService;
import com.example.demo.service.UserService;

@RestController
@RequestMapping("/api/submission")
public class SubmissionController {
	@Autowired
	private SubmissionService submissionService;
	@Autowired
	private TaskService taskService;
	@Autowired
	private UserService userService;

	@PostMapping("/submitTask")
	public ResponseEntity<Submission> submitTask(@RequestParam long taskId, @RequestParam String githubLink,
			@RequestHeader("Authorization") String jwt) throws Exception {
		System.out.println("SCE32 : ----" + taskId);
		UserDto userProfile = userService.getUserProfile(jwt);
		Submission submitTask = submissionService.submitTask(taskId, githubLink, userProfile.getId(), jwt);
		return new ResponseEntity<Submission>(submitTask, HttpStatus.CREATED);
	}

	@GetMapping("/{Id}")
	public ResponseEntity<Submission> getSubmissionById(@PathVariable long Id,
			@RequestHeader("Authorization") String jwt) throws Exception {
		UserDto userProfile = userService.getUserProfile(jwt);
		Submission taskSubmissionById = submissionService.getTaskSubmissionById(Id);
		return new ResponseEntity<Submission>(taskSubmissionById, HttpStatus.OK);

	}

	@GetMapping("/")
	public ResponseEntity<List<Submission>> getAllSubmissions(@RequestHeader("Authorization") String jwt)
			throws Exception {
		UserDto userProfile = userService.getUserProfile(jwt);
		List<Submission> allTaskSubmissions = submissionService.getAllTaskSubmissions();
		return new ResponseEntity<List<Submission>>(allTaskSubmissions, HttpStatus.OK);

	}

	@GetMapping("/task/{taskId}")
	public ResponseEntity<List<Submission>> getAllSubmissionsByTaskId(@PathVariable long taskId,
			@RequestHeader("Authorization") String jwt) throws Exception {
		UserDto userProfile = userService.getUserProfile(jwt);
		List<Submission> taskSubmissionByTaskId = submissionService.getTaskSubmissionByTaskId(taskId);
		return new ResponseEntity<List<Submission>>(taskSubmissionByTaskId, HttpStatus.OK);

	}

	@PutMapping("/{Id}")
	public ResponseEntity<Submission> acceptOrDeclineSubmission(@PathVariable long Id,
			@RequestParam("status") String status, @RequestHeader("Authorization") String jwt) throws Exception {
		System.out.println("SCE32: status " + status + " Id " + Id);
		UserDto userProfile = userService.getUserProfile(jwt);
		Submission acceptDeclineSubmission = submissionService.acceptDeclineSubmission(Id, status);
		return new ResponseEntity<Submission>(acceptDeclineSubmission, HttpStatus.CREATED);

	}
}
