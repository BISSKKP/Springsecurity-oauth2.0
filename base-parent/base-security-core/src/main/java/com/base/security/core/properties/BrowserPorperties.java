package com.base.security.core.properties;

import com.base.security.core.constants.SecurityConstants;

public class BrowserPorperties {
	
	private String loginPage=SecurityConstants.DEFAULT_LOGIN_PAGE_URL;
	
	private String signUpUrl=SecurityConstants.DEFAULT_SIGNUP_URL;
	
	/**
	 * 处理退出登录的地址
	 */
	private String dealLogoutUrl=SecurityConstants.DEFAULT_LOGOUT_URL;
	
	private String logoutUrl;
	
	private LoginType loginType = LoginType.MIX;
	
	private SessionProperties session=new SessionProperties();
	
	
	private int  rememberMeSeconds=3600;

	public String getLoginPage() {
		return loginPage;
	}

	public void setLoginPage(String loginPage) {
		this.loginPage = loginPage;
	}

	public LoginType getLoginType() {
		return loginType;
	}

	public void setLoginType(LoginType loginType) {
		this.loginType = loginType;
	}

	public int getRememberMeSeconds() {
		return rememberMeSeconds;
	}

	public void setRememberMeSeconds(int rememberMeSeconds) {
		this.rememberMeSeconds = rememberMeSeconds;
	}

	public String getSignUpUrl() {
		return signUpUrl;
	}

	public void setSignUpUrl(String signUpUrl) {
		this.signUpUrl = signUpUrl;
	}

	public SessionProperties getSession() {
		return session;
	}

	public void setSession(SessionProperties session) {
		this.session = session;
	}

	public String getLogoutUrl() {
		return logoutUrl;
	}

	public void setLogoutUrl(String logoutUrl) {
		this.logoutUrl = logoutUrl;
	}

	public String getDealLogoutUrl() {
		return dealLogoutUrl;
	}

	public void setDealLogoutUrl(String dealLogoutUrl) {
		this.dealLogoutUrl = dealLogoutUrl;
	}
	

}
