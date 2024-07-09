package com.example.demo.modal;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.example.demo.shared.TaskStatus;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TaskDto {
	
private long id;
	
	private String title;
	private String description;
	private String image;
	private long assignedUserId;
	private List<String> tech_stack = new ArrayList<>();
	private TaskStatus status;
	private LocalDateTime deadLine;
	private LocalDateTime createdAt;

}
