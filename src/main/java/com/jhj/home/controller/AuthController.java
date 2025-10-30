package com.jhj.home.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.jhj.home.entity.User;
import com.jhj.home.service.UserService;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
	
	@Autowired
	private UserService userService;
	
	// 회원 가입
	@PostMapping("/signup")
	public String signup(@RequestParam("username") String username, @RequestParam("password") String password) {
		
		User user = userService.signupUser(username, password);
		
		return "회원가입 완료" + user.getUsername();
	}
	
	// 로그인 후에 로그인 인증 받은 유저만 접근할 수 있는 request
	@GetMapping("/apicheck")
	public String apicheck() {
		
		return "로그인 확인";
	}

}
