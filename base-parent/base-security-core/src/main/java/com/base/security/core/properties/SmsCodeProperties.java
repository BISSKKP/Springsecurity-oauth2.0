package com.base.security.core.properties;

import com.base.security.core.constants.SecurityConstants;

public class SmsCodeProperties {
	
	private int length=4;
	
	private int expireIn=60;
	
	private String url;
	
	/**
	 * 处理url
	 */
	private String dealUrl=SecurityConstants.DEFAULT_LOGIN_PROCESSING_URL_MOBILE;
	

	public int getLength() {
		return length;
	}

	public void setLength(int length) {
		this.length = length;
	}

	public int getExpireIn() {
		return expireIn;
	}

	public void setExpireIn(int expireIn) {
		this.expireIn = expireIn;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getDealUrl() {
		return dealUrl;
	}

	public void setDealUrl(String dealUrl) {
		this.dealUrl = dealUrl;
	}
	
	
	
	
}
