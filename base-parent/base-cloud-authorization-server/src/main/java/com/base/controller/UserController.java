package com.base.controller;

import java.security.Principal;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.base.common.ajax.AjaxJson;


@RestController
public class UserController {
	
	@GetMapping("/user")
    public AjaxJson user(Principal user){
        return AjaxJson.success("user", user);
    }
	
	

}
