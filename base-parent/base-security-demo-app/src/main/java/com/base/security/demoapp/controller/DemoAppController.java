package com.base.security.demoapp.controller;


import java.io.UnsupportedEncodingException;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.social.connect.web.ProviderSignInUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.ServletWebRequest;

import com.base.security.app.util.AppSignUpUtils;
import com.base.security.core.properties.SecurityProperties;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureException;
import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
public class DemoAppController
{
	
	@Autowired
	private AppSignUpUtils appSignUpUtils;
	
	@Autowired
	private ProviderSignInUtils providerSignInUtils;
	
	@Autowired
	private SecurityProperties securityProperties;
	
	
	@GetMapping("/t")
	public Object test(){
		
		
		return "Ok";
	}
	
	
	@GetMapping("/info")
	public Object info(Authentication authen,HttpServletRequest request) throws ExpiredJwtException, MalformedJwtException, SignatureException, IllegalArgumentException, UnsupportedEncodingException{
		
		String headr=request.getHeader("Authorization");
		
		String token =StringUtils.substringAfter(headr, "bearer ");
		
		Claims  claims= (Claims) Jwts.parser().setSigningKey(securityProperties.getOauth2().getJwtSigningKey().getBytes("UTF-8")).parse(token).getBody();
		log.info("jwt 额外参数："+claims);
		System.out.println(claims.get("company"));
		
		return authen!=null?authen:"没有认证信息";
	}
	
	@GetMapping("/user/regist")
	public Object regist(HttpServletRequest request,String password,@RequestParam(required=true)String username){
		
		//注册进入social
		appSignUpUtils.doPostSignUp(new ServletWebRequest(request), username);
		
		return "注册";
	}
	
	

}
