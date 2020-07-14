package com.base.sso.controller;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpStatus;
import org.springframework.security.web.savedrequest.HttpSessionRequestCache;
import org.springframework.security.web.savedrequest.RequestCache;
import org.springframework.security.web.savedrequest.SavedRequest;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.base.common.ajax.AjaxJson;

import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
public class LoginController {

	private RequestCache requestCache=new HttpSessionRequestCache();
	
	
	@RequestMapping("/authen/require")
	@ResponseStatus(code=HttpStatus.UNAUTHORIZED)
	public AjaxJson requireAuthentication(HttpServletResponse response,HttpServletRequest request) throws IOException{
		SavedRequest savedRequest=requestCache.getRequest(request	, response);
		
		if(savedRequest != null){
			String target=savedRequest.getRedirectUrl();
			log.info("引发跳转的请求是："+target);
		}
		return AjaxJson.error("401", "访问的服务需要身份认证");
	}
	
	
	
	
}
