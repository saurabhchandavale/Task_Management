package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.config.JwtProvider;
import com.example.demo.modal.User;
import com.example.demo.repository.UserRepository;
import com.example.demo.request.LoginRequest;
import com.example.demo.response.AuthResponse;
import com.example.demo.service.impl.CustomerUserServiceImplementation;

@RestController
@RequestMapping("/auth")
public class AuthController {

	@Autowired
	private UserRepository userrepository;
	@Autowired
	private PasswordEncoder passwordEncoder;
	@Autowired
	private CustomerUserServiceImplementation customeruserDetails;
	
	@PostMapping("/signup")
	public ResponseEntity<AuthResponse> createUserHandler(@RequestBody User user) throws Exception{
		String email = user.getEmail();
		String password = user.getPassword();
		String fullname = user.getFullname();
		String role = user.getRole();
		
		User byEmail = userrepository.findByEmail(email);
		
		if(byEmail!=null)
		{
			throw new Exception("Email is already in Use");
		}
		
		User createUser = new User();
		createUser.setEmail(email);
		createUser.setPassword(passwordEncoder.encode(password));
		createUser.setFullname(fullname);
		createUser.setRole(role);
		
		User save = userrepository.save(createUser);
		
		Authentication authentication = new UsernamePasswordAuthenticationToken(email, password);
		SecurityContextHolder.getContext().setAuthentication(authentication);
		
		String token = JwtProvider.generateToken(authentication);
		
		AuthResponse authResponse = new AuthResponse();
		authResponse.setJwt(token);
		authResponse.setMessage("Register Success");
		authResponse.setStatus(true);
		
		return new ResponseEntity<AuthResponse>(authResponse, HttpStatus.OK);
		
	}
	
	@PostMapping("/signin")
	public ResponseEntity<AuthResponse> userSignIn(@RequestBody LoginRequest loginRequest){
		
		String userName = loginRequest.getEmail();
		String password = loginRequest.getPassword();
		
		Authentication authentication = authenticate(userName,password);
		SecurityContextHolder.getContext().setAuthentication(authentication);
		
		String token = JwtProvider.generateToken(authentication);
		AuthResponse authResponse = new AuthResponse();
		
		authResponse.setMessage("Login Success");
		authResponse.setJwt(token);
		authResponse.setStatus(true);
		
		return new ResponseEntity<AuthResponse>(authResponse,HttpStatus.OK);
		
	}

	private Authentication authenticate(String userName, String password) {
		// TODO Auto-generated method stub
		
		UserDetails userDetails = customeruserDetails.loadUserByUsername(userName);
		
		if(userDetails == null) {
			throw new BadCredentialsException("Invalid username or password");
		}
		if(!passwordEncoder.matches(password, userDetails.getPassword())) {
			throw new BadCredentialsException("Invalid password");
		}
		return new UsernamePasswordAuthenticationToken(userDetails, null,userDetails.getAuthorities());
	}
}
