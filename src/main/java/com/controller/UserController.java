package com.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.data_authenticate.LoginRequest;
import com.entity.User;
import com.service.UserService;

@RestController
@RequestMapping("/users")
public class UserController {

	@Autowired
	private UserService userService;
	
	@PostMapping("/register")
	public User saveRegister(@RequestBody User user) {
		
		return userService.register(user);
	}
	
	@PostMapping("/login")
	public String loginUser(@RequestBody LoginRequest request) {
	    return userService.login(request.getEmail(), request.getPassword());
	}
	
}
