package com.base.security.browser.authentication;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import com.base.common.ajax.AjaxUtils;
import com.base.security.core.properties.LoginType;
import com.base.security.core.properties.SecurityProperties;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.extern.slf4j.Slf4j;

/**
 * 登录请求成功的处理器
 * 
 * @author lqq
 *
 */
@Component
@Slf4j
public class AuthenticationSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {

	@Autowired
	private ObjectMapper objectMapper;
	
	@Autowired
	private SecurityProperties securityPorperties;
	
	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
			Authentication authentication) throws ServletException, IOException {
		
		log.info("登录成功："+objectMapper.writeValueAsString(authentication));
		
		LoginType loginType=securityPorperties.getBrowser().getLoginType();
		if(LoginType.JSON.equals(loginType)||(LoginType.MIX.equals(loginType)&&AjaxUtils.isAjaxRequest(request))){
			//只返回json
			response.setCharacterEncoding("UTF-8"); 
			response.setContentType("application/json;charset=UTF-8");
			response.getWriter().write(objectMapper.writeValueAsString(authentication));
		}else{
			super.onAuthenticationSuccess(request, response, authentication);
		}
	}
	
	
	
}
