package com.example.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.modal.User;


public interface UserRepository extends JpaRepository<User, Long>{

	public User findByEmail(String email);
	
	
}
