package com.base.security.demo.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.social.connect.web.ProviderSignInUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.ServletWebRequest;

import com.base.pojo.sys.SysUser;


@RestController
@RequestMapping("/user")
public class UserController {
	
	
	@Autowired
	private ProviderSignInUtils providerSignInUtils;
	
	@PostMapping("/regist")
	public Map<String, Object> regist(HttpServletRequest request,String password,@RequestParam(required=true)String username){
		
		
		//注册进入social
		providerSignInUtils.doPostSignUp(username,new ServletWebRequest(request));
		
		Map<String, Object> reuslt=new HashMap<String, Object>();
		
		SysUser user=new SysUser();
		user.setPassword(password);
		user.setEmail(username);
		
		reuslt.put("user", user);
		return reuslt;
	}
	
	@GetMapping("/role")
	public Map<String, Object> role(String password,String username){
		
		Map<String, Object> reuslt=new HashMap<String, Object>();
		
		SysUser user=new SysUser();
		user.setPassword(password);
		user.setEmail(username);
		
		reuslt.put("user", user);
		
		return reuslt;
	}
	
	
}
