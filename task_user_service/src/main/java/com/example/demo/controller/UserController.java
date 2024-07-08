package com.example.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.config.JwtProvider;
import com.example.demo.modal.User;
import com.example.demo.repository.UserRepository;
import com.example.demo.request.LoginRequest;
import com.example.demo.response.AuthResponse;
import com.example.demo.service.UserService;
import com.example.demo.service.impl.CustomerUserServiceImplementation;

@RestController
@RequestMapping("/api/user")
public class UserController {
	@Autowired
	private UserService userService;
	@GetMapping("/profile")
	public ResponseEntity<User> getUserProfile(@RequestHeader("Authorization") String jwt){
		System.out.println("In getuserprofile");
		User user = userService.getUserProfile(jwt);
		return new ResponseEntity<User>(user,HttpStatus.OK);
	}
	
	@GetMapping()
	public ResponseEntity<List<User>> getUsers(@RequestHeader("Authorization") String jwt){
		System.out.println("In getuserprofile");
		List<User> user = userService.getAllUsers();
		return new ResponseEntity<List<User>>(user,HttpStatus.OK);
	}
	
	
	
	
	

}
