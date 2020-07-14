package com.base.security.core.properties;


import lombok.Data;
@Data
public class OAuth2Properties {
	
	private OAuth2ClientProperties[] clients={};
	
	/***
     * 指定使用哪个TokenStore
     */
    private String tokenStore;
    
    /***
     *  jwt的密签 --- 发出去的令牌需要它签名，验证令牌时也用它来验
     *  ★一定要保护好，别人知道了就可以拿着它来签发你的jwt令牌了
     */
    private String jwtSigningKey = "base_lqq";

}
