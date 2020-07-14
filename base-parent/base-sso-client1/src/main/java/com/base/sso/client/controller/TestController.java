package com.base.sso.client.controller
;

import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {
	
	@GetMapping("/user")
	public Authentication user(Authentication user){
		
		return user;
	}
	
	
	@GetMapping("/user2")
	@PreAuthorize("hasRole('ROLE_ADMIN1')")
	public Authentication user2(Authentication user){
		
		return user;
	}
	
	

}
