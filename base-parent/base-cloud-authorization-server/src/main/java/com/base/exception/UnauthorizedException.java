package com.base.exception;

import org.springframework.security.oauth2.common.exceptions.OAuth2Exception;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

/**
 * 没有授权异常
 * @author lqq
 *
 */
@JsonSerialize(using = CustomOauthExceptionSerializer.class)
public class UnauthorizedException extends OAuth2Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6032598885002082856L;

	public UnauthorizedException(String msg) {
		super(msg);
	}

	public UnauthorizedException(String message, Exception e) {
		super(message,e);
	} 
	

}
