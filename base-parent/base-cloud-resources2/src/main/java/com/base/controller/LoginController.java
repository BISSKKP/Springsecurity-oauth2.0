package com.base.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.base.common.ajax.AjaxJson;

@RestController
public class LoginController {

	
	
	@PostMapping("/login")
	public AjaxJson login(String password,String username) {
		
		
		return new AjaxJson();
	}
	
	
	
}
