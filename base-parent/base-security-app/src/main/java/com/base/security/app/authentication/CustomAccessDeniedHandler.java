package com.base.security.app.authentication;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import com.base.common.ajax.AjaxJson;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * 自定义 授权失败的返回信息
 * @author lqq
 *
 */
@Component("customAccessDeniedHandler")
public class CustomAccessDeniedHandler implements AccessDeniedHandler {

	@Override
	public void handle(HttpServletRequest request, HttpServletResponse response,
			AccessDeniedException accessDeniedException) throws IOException, ServletException {
		response.setContentType("application/json;charset=UTF-8");
		
		AjaxJson j=new AjaxJson();
		j.setSuccess(false);
		j.setMsg("权限不足");
		j.put("data",accessDeniedException.getMessage());
		 j.put("path", request.getServletPath());
		
		response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
		response.getWriter().write(new ObjectMapper().writeValueAsString(j));
	}

}
