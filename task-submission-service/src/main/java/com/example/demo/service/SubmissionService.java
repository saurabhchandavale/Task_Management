package com.example.demo.service;

import java.util.List;

import com.example.demo.modal.Submission;

public interface SubmissionService {

	public Submission submitTask(long taskId,String githubLink,long userId, String jwt) throws Exception;
	
	public Submission getTaskSubmissionById(long submissionId) throws Exception;
	
	public List<Submission> getAllTaskSubmissions() throws Exception;
	
	public List<Submission> getTaskSubmissionByTaskId(long taskId) throws Exception;
	
	public Submission acceptDeclineSubmission(Long id, String status) throws Exception;

}
