package com.jhj.home.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.jhj.home.entity.User;
import com.jhj.home.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService {
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private final PasswordEncoder passwordEncoder;
	
	public User signupUser(String username, String password) {
		
		User user = new User();
		user.setUsername(username);
		user.setPassword(passwordEncoder.encode(password));
		
		return userRepository.save(user);
	}

}
