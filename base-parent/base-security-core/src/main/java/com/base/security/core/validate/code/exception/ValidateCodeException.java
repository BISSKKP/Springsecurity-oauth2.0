package com.base.security.core.validate.code.exception;

import org.springframework.security.core.AuthenticationException;

/**
 * 验证码错误时处理异常
 * @author ACID
 *
 */
public class ValidateCodeException extends AuthenticationException  {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6227291100825303671L;

	public ValidateCodeException(String msg) {
		super(msg);
	}

	
	
}
