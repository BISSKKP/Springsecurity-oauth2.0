package com.base.jwt;

import java.util.HashMap;
import java.util.Map;

import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;
import org.springframework.stereotype.Component;

/**
 * 自定义一个TokenEnhancer，在生成JWT时加入一些扩展信息
 * @author lqq
 *
 */
@Component
@ConditionalOnMissingBean(name="tokenEnhancer")
public class BaseJwtEnhancer implements TokenEnhancer{

	@Override
	public OAuth2AccessToken enhance(OAuth2AccessToken accessToken, OAuth2Authentication authentication) {
		 //在jwt里加入一个company信息
        Map<String, Object> info = new HashMap<>();
        info.put("company", "base-");

        ((DefaultOAuth2AccessToken) accessToken).setAdditionalInformation(info);
        return accessToken;
	}
	

}
