package com.base.security.core.social.qq.connect;

import org.springframework.social.oauth2.AbstractOAuth2ServiceProvider;

import com.base.security.core.social.qq.api.QQ;
import com.base.security.core.social.qq.api.QQImpl;


public class QQServiceProvider extends AbstractOAuth2ServiceProvider<QQ> {

	private String appId;
	
	private static final String authorizeUrl="https://graph.qq.com/oauth2.0/authorize";
	
	private static final String accessTokenUrl="https://graph.qq.com/oauth2.0/token";

	public QQServiceProvider(String appId ,String appSecret) {
		//重写template
		super(new QQOAuth2Template(appId, appSecret, authorizeUrl, accessTokenUrl));
		this.appId=appId;
	}

	@Override
	public QQ getApi(String accessToken) {
		
		return new QQImpl(accessToken, appId);
	}

}
