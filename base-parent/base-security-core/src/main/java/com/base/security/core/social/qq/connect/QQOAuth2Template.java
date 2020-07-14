package com.base.security.core.social.qq.connect;

import java.nio.charset.Charset;

import org.apache.commons.lang.StringUtils;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.social.oauth2.AccessGrant;
import org.springframework.social.oauth2.OAuth2Template;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import lombok.extern.slf4j.Slf4j;

/**
 * 重写请求
 * @author lqq
 *
 */
@Slf4j
public class QQOAuth2Template extends OAuth2Template {

	public QQOAuth2Template(String clientId, String clientSecret, String authorizeUrl, String accessTokenUrl) {
		super(clientId, clientSecret, authorizeUrl, accessTokenUrl);
		setUseParametersForClientAuthentication(true);
	}
	
	@Override
	protected AccessGrant postForAccessGrant(String accessTokenUrl, MultiValueMap<String, String> parameters) {
		
		String response=getRestTemplate().postForObject(accessTokenUrl, parameters, String.class);
		log.info("qq授权accesstoken的响应："+response);
		
		String[] items=	StringUtils.splitByWholeSeparatorPreserveAllTokens(response, "&");
		
		String acessToken=StringUtils.substringAfter(items[0], "=");
		Long expiresIn=new Long(StringUtils.substringAfter(items[1], "="));
		String refreshToken=StringUtils.substringAfter(items[2], "=");
		
		return new AccessGrant(acessToken, null, refreshToken, expiresIn);
	}
	
	
	@Override
	protected RestTemplate createRestTemplate() {
		
		RestTemplate restTemplate=super.createRestTemplate();
		
		//添加能够处理contenttype 是TEXT_PLAIN的
		restTemplate.getMessageConverters().add(new StringHttpMessageConverter(Charset.forName("UTF-8")));
		
		return restTemplate;
		
	}

}
