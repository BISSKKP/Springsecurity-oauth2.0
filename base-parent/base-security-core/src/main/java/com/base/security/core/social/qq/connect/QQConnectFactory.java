package com.base.security.core.social.qq.connect;

import org.springframework.social.connect.support.OAuth2ConnectionFactory;

import com.base.security.core.social.qq.api.QQ;
/**
 * 连接工厂
 * @author lqq
 *
 */
public class QQConnectFactory extends OAuth2ConnectionFactory<QQ> {

	public QQConnectFactory(String providerId,String appId,String appSecret) {
		super(providerId, new QQServiceProvider(appId, appSecret), new QQAdapter());
	}

}
