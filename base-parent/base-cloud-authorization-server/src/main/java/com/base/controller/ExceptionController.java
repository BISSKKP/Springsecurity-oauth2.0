package com.base.controller;

import org.springframework.security.oauth2.common.exceptions.OAuth2Exception;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import com.base.common.ajax.AjaxJson;


@ControllerAdvice
public class ExceptionController {

	
	
	@ResponseBody
    @ExceptionHandler(value = OAuth2Exception.class)
    public AjaxJson handleOauth2(OAuth2Exception e) {
        return AjaxJson.error(e.getOAuth2ErrorCode(),e.getMessage());
    }
	
	
	
	
}
