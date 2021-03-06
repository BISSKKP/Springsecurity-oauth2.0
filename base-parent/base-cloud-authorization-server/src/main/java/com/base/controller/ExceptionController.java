package com.base.controller;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.RedisConnectionFailureException;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.common.exceptions.OAuth2Exception;
import org.springframework.security.oauth2.common.exceptions.UnapprovedClientAuthenticationException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import com.base.common.ajax.AjaxJson;
import com.base.exception.CustomExceptionTranslator;


@ControllerAdvice
public class ExceptionController {

	@Autowired
	private CustomExceptionTranslator customExceptionTranslator;
	
	/**
	 * 处理 认证失败信息
	 * @param e
	 * @return
	 * @throws IOException
	 */
	@ResponseBody
    @ExceptionHandler(value = OAuth2Exception.class)
    public AjaxJson handleOauth2(OAuth2Exception e) throws IOException {
		
		
		ResponseEntity<OAuth2Exception> res=	customExceptionTranslator.handleOAuth2Exception(e);
		
        return AjaxJson.error(res.getStatusCode().value()+"",res.getBody().getMessage());
    }
	
	
	@ResponseBody
    @ExceptionHandler(value = UnapprovedClientAuthenticationException.class)
	 public AjaxJson handleOauth3(UnapprovedClientAuthenticationException e) throws IOException {
		
		 return AjaxJson.error(403+"",e.getMessage());
	}
	
	
	@ResponseBody
    @ExceptionHandler(value = RedisConnectionFailureException.class)
	 public AjaxJson redisHandler(RedisConnectionFailureException e) throws IOException {
		
		 return AjaxJson.error("500","连接异常，请稍后重试");
	}
	
	
	
}
