package com.base.security.browser.logout;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;

import com.base.common.ajax.AjaxJson;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.extern.slf4j.Slf4j;

/**
 * 退出登录成功处理器
 * @author lqq
 *
 */
@Slf4j
public class BaseLogoutSuccessHandler implements LogoutSuccessHandler {
	
	private String logoutUrl;
	
	private ObjectMapper objectMapper=new ObjectMapper();

	@Override
	public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication)
			throws IOException, ServletException {
	
		log.info("退出成功");
		if(StringUtils.isNotBlank(logoutUrl)){
			response.sendRedirect(logoutUrl);
		}else{
			response.setContentType("application/json;charset=UTF-8");
			response.getWriter().write(objectMapper.writeValueAsString(buildLogoutMsg()));
		}
		
	}

	public String getLogoutUrl() {
		return logoutUrl;
	}

	public void setLogoutUrl(String logoutUrl) {
		this.logoutUrl = logoutUrl;
	}

	/**
	 * 构建返回信息
	 * @return
	 */
	public AjaxJson buildLogoutMsg(){
		AjaxJson j=new AjaxJson();
		j.setMsg("退出登录成功");
		return j;
	}
	
	
}
