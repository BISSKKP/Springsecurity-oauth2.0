package com.base.security.core.properties;

import com.base.security.core.constants.SecurityConstants;

import lombok.Data;
@Data
public class SessionProperties {
	
	/**
	 * 同一个用户在系统中最大的session数，默认 1
	 */
	private int maximumSessions=1;
	
	/**
	 * 达到最大session时是否阻止新的登陆请求，默认false，不阻止，新的登录会将老的登陆失效
	 */
	private boolean maxSessionsPreventsLogin;
	
	
	/**
	 * session 失效之后的地址
	 */
	private String sessionInvalidUrl=SecurityConstants.DEFAULT_SESSION_INVALID_URL;
	
	
	
}
