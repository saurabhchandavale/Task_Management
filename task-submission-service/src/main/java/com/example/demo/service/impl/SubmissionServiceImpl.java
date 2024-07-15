package com.example.demo.service.impl;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.modal.Submission;
import com.example.demo.modal.TaskDto;
import com.example.demo.repository.SubmissionRepository;
import com.example.demo.service.SubmissionService;
import com.example.demo.service.TaskService;
import com.example.demo.service.UserService;

@Service
public class SubmissionServiceImpl implements SubmissionService {
	@Autowired
	private SubmissionRepository submissionRepository;
	@Autowired
	private TaskService taskService;
	@Autowired
	private UserService userService;

	@Override
	public Submission submitTask(long taskId, String githubLink, long userId, String jwt) throws Exception {
		TaskDto taskById = taskService.getTaskById(taskId, jwt);
		if (taskById != null) {
			Submission submission = new Submission();
			submission.setTaskId(taskId);
			submission.setUserId(userId);
			submission.setGithubLink(githubLink);
			submission.setSubmiDate(LocalDateTime.now());
			return submissionRepository.save(submission);
		}
		throw new Exception("Task not found with id : " + taskId);
	}

	@Override
	public Submission getTaskSubmissionById(long submissionId) throws Exception {
		return submissionRepository.findById(submissionId)
				.orElseThrow(() -> new Exception("Submission not found by submission id " + submissionId));
	}

	@Override
	public List<Submission> getAllTaskSubmissions() throws Exception {
		return submissionRepository.findAll();
	}

	@Override
	public List<Submission> getTaskSubmissionByTaskId(long taskId) throws Exception {
		return submissionRepository.findByTaskId(taskId);
	}

	@Override
	public Submission acceptDeclineSubmission(long id, String status) throws Exception {
		Submission taskSubmissionById = getTaskSubmissionById(id);
		System.out.println("SCE32: taskSubmissionById " + taskSubmissionById.getTaskId() + status);

		taskSubmissionById.setStatus(status);
		if (status.equalsIgnoreCase("ACCEPT")) {
			taskService.completeTask(taskSubmissionById.getTaskId());
		}
		return submissionRepository.save(taskSubmissionById);
	}

}
