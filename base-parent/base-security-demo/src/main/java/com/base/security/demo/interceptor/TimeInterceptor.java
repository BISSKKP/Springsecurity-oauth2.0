package com.base.security.demo.interceptor;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.HandlerInterceptor;

public class TimeInterceptor implements HandlerInterceptor{

	public final String TIME_INTERCEPTER="time_interceptor";
	
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		
		request.setAttribute(TIME_INTERCEPTER, new Date());
		
		return HandlerInterceptor.super.preHandle(request, response, handler);
	}
	
	
	
	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
		
	long  date_before=	((Date)request.getAttribute(TIME_INTERCEPTER)).getTime();
		
		System.out.println("interceptor 拦截器  请求耗时："+(new Date().getTime()-date_before));
		
		HandlerInterceptor.super.afterCompletion(request, response, handler, ex);
	}
	
	
}
