package com.example.demo.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.config.JwtProvider;
import com.example.demo.modal.User;
import com.example.demo.repository.UserRepository;
import com.example.demo.service.UserService;
@Service
public class UserServiceImpl implements UserService {
	@Autowired
	private UserRepository userRepository;

	@Override
	public User getUserProfile(String jwt) {
		// TODO Auto-generated method stub
		String emailFromJwtToken = JwtProvider.getEmailFromJwtToken(jwt);
		return userRepository.findByEmail(emailFromJwtToken);
	}

	@Override
	public List<User> getAllUsers() {
		// TODO Auto-generated method stub
		return userRepository.findAll();
	}

}
