package com.base.security.browser.authentication;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import com.base.common.ajax.AjaxUtils;
import com.base.security.core.properties.LoginType;
import com.base.security.core.properties.SecurityProperties;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.extern.slf4j.Slf4j;

/**
 * 处理的登录失败
 * @author ACID
 *
 */
@Component
@Slf4j
public class SeAuthenticationFailHandler extends SimpleUrlAuthenticationFailureHandler {

	//extends SimpleUrlAuthenticationFailureHandler
		//implements AuthenticationFailureHandler

	@Autowired
	private ObjectMapper objectMapper;
	
	@Autowired
	private SecurityProperties securityProperties;
	
	@Override
	public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
			AuthenticationException exception) throws IOException, ServletException {
		
		log.info("登录失败的信息："+objectMapper.writeValueAsString(exception));
		LoginType loginType=securityProperties.getBrowser().getLoginType();
		if(LoginType.JSON.equals(loginType)||(LoginType.MIX.equals(loginType)&&AjaxUtils.isAjaxRequest(request))){
			//只返回json
			response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
			response.setCharacterEncoding("UTF-8"); 
			response.setContentType("application/json;charset=UTF-8");
			response.getWriter().write(objectMapper.writeValueAsString(exception.getMessage()));
		}else{
			//不是json 调用父类页面跳转
			super.onAuthenticationFailure(request, response, exception);
		}
		
		
		
	}

}
