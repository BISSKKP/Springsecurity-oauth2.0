package com.base.security.core.social;

import org.springframework.social.security.SocialAuthenticationFilter;


/**
 * 根据不同的环境处理不同请求返回
 */
public interface SocialAuthenticationFilterPostProcessor {

	
	void process(SocialAuthenticationFilter socialAuthenticationFilter);
}
