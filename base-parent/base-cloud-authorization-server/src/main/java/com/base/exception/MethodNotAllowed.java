package com.base.exception;

import org.springframework.security.oauth2.common.exceptions.OAuth2Exception;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

/**
 * 方法没有权限
 * @author lqq
 *
 */
@JsonSerialize(using = CustomOauthExceptionSerializer.class)
public class MethodNotAllowed extends OAuth2Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = -434062149890250261L;

	public MethodNotAllowed(String msg) {
		super(msg);
	}

	public MethodNotAllowed(String message, Exception ase) {
		super(message, ase);
	}

}
