package com.example.demo.repositort;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.model.Task;

public interface TaskRepository extends JpaRepository<Task, Long> {
	
	public List<Task> findByAssignedUserId(long assignedUserId);

}
