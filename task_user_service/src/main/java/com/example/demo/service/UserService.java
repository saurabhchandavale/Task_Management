package com.example.demo.service;

import java.util.List;

import com.example.demo.modal.User;

public interface UserService {

	public User getUserProfile(String jwt);
	public List<User> getAllUsers();


}
