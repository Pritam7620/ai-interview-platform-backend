package com.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.entity.User;
import com.repository.UserRepo;

@Service
public class UserService {

	@Autowired
	private UserRepo userRepo;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	public User register(User user) {
		
		user.setPassword(passwordEncoder.encode(user.getPassword()));
		
		return userRepo.save(user);
	}
	
	
	public User login(String email , String password) {
		
		 User user = userRepo.findByEmail(email);

		    if (user == null) {
		        throw new RuntimeException("User not found");
		    }

		    if (!passwordEncoder.matches(password, user.getPassword())) {
		        throw new RuntimeException("Invalid password");
		    }

		    return user;
	}
}
