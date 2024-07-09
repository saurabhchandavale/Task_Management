package com.example.demo.modal;

import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@NoArgsConstructor
@Getter
@Setter
public class Submission {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;
	
	private long taskId;
	
	private String githubLink;
	
	private long userId;
	
	private String status ="PENDING";
	
	private LocalDateTime submiDate;

}
