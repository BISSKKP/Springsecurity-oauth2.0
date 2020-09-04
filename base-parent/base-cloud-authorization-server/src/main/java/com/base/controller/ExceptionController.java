package com.base.controller;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.common.exceptions.OAuth2Exception;
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
	
	
	
	
}
