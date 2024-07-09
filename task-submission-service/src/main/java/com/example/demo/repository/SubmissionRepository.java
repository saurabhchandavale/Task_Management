package com.example.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.modal.Submission;

public interface SubmissionRepository extends JpaRepository<Submission, Long>{

	public List<Submission> findByTaskId(long taskId);
}
