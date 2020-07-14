package com.base.security.demo.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {
	
	
	
	@GetMapping("/t")
	public String test(){
		
		return "OK";
	} 
	
	@GetMapping("/info")
	public Map<String, Object> info(Authentication authen,@AuthenticationPrincipal UserDetails user){
		
		Map<String, Object> map=new HashMap<String, Object>();
		
		map.put("key", "value");
		
		
		map.put("userInfo", SecurityContextHolder.getContext().getAuthentication());
		
		map.put("userInfo2", authen);
		
		map.put("userInfo3", user);
		
		return map;
	}

}
