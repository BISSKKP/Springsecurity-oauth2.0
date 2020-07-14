package com.base.security.core.properties;

import com.base.security.core.social.support.SocialProperties;

/**
 * qq配置
 * @author lqq
 *
 */
public class QQProperties extends SocialProperties {

	private String providerId="qq";

	public String getProviderId() {
		return providerId;
	}

	public void setProviderId(String providerId) {
		this.providerId = providerId;
	}
	
	
	
	
}
