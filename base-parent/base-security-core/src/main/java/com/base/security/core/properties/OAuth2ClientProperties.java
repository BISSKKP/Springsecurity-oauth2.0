package com.base.security.core.properties;

import lombok.Data;

@Data
public class OAuth2ClientProperties {
	
	
	private String clientId;
	
	private String clientSecret;
	
	//超时 单位：秒  等于0 时 授权码永久
	private int accessTokenValiditySeconds=7200;
	
	/**
	 * 四种授权模式 
	 * "authorization_code", 
	 * "client_credentials", 
	 * "refresh_token",
	 *  "password", 
	 *  "implicit"
	 */
	private String[] authorizedGrantTypes;
	
	/**
	 * read wirte all
	 */
	private String[] scopes;
	
	/**
	 * refresh_token 刷新过期使劲按
	 */
	private int refreshTokenValiditySeconds=7200*2; 
	

}
