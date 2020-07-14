package com.base.security.app.exception;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.oauth2.common.exceptions.InvalidTokenException;
import org.springframework.security.web.AuthenticationEntryPoint;

import com.base.common.ajax.AjaxJson;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.extern.slf4j.Slf4j;

/**
 * 授权码 失效 时 返回自定义消息
 * @author lqq
 *
 */
@Slf4j
public class AuthExceptionEntryPoint implements AuthenticationEntryPoint {

	@Override
	public void commence(HttpServletRequest request, HttpServletResponse response,
			AuthenticationException authException) throws IOException, ServletException {

		log.info("访问此资源需要完全的身份验证");
		Throwable cause = authException.getCause();
		AjaxJson j=new AjaxJson();
		j.setSuccess(false);
		 if(cause instanceof InvalidTokenException) {
	            j.setErrorCode("401");
	            j.setMsg("无效的token");
	        }else{
	        	 j.setErrorCode("401");
		            j.setMsg("访问此资源需要完全的身份验证");
	        }
		 j.put("path", request.getServletPath());
		 j.put("data", authException.getMessage());
		 response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
		 response.setContentType("application/json;charset=UTF-8");
		 response.getWriter().write(new ObjectMapper().writeValueAsString(j));
		
	}

}
