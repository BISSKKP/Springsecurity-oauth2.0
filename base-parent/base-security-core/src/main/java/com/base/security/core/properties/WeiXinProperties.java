package com.base.security.core.properties;

import com.base.security.core.social.support.SocialProperties;

public class WeiXinProperties extends SocialProperties {
	private String providerId="weixin";

	public String getProviderId() {
		return providerId;
	}

	public void setProviderId(String providerId) {
		this.providerId = providerId;
	}
	
	
}
