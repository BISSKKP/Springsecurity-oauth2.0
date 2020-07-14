package com.base.security.app.process;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.social.security.SocialAuthenticationFilter;
import org.springframework.stereotype.Component;

import com.base.security.core.social.SocialAuthenticationFilterPostProcessor;

/**
 * Description：设置app下springsocial走的成功处理器
 */
@Component
public class AppSocialAuthenticationFilterPostProcessor implements SocialAuthenticationFilterPostProcessor {
    //认证成功后返回token的成功处理器
    @Autowired
    private AuthenticationSuccessHandler authenticationSuccessHandler;
    @Override
    public void process(SocialAuthenticationFilter socialAuthenticationFilter) {
        socialAuthenticationFilter.setAuthenticationSuccessHandler(authenticationSuccessHandler);
    }
}
