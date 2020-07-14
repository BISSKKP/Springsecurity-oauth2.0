package com.base.sso.client.controller;

import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

	
	@GetMapping("/user")
	public Authentication user(Authentication user){
		
		return user;
	}
	
	
	
}
