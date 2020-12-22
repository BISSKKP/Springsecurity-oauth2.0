package com.base.security.app.social;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.SecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.social.connect.UsersConnectionRepository;
import org.springframework.social.security.SocialUserDetailsService;
import org.springframework.stereotype.Component;

/**
 * Description：校验openId的配置类---》将校验规则等配置到spring-security过滤器链中
 */
@Component
public class OpenIdAuthenticationSecurityConfig extends SecurityConfigurerAdapter<DefaultSecurityFilterChain, HttpSecurity> {

    @Autowired
    private AuthenticationSuccessHandler authenticationSuccessHandler;

    @Autowired
    private AuthenticationFailureHandler seAuthenticationFailHandler;

    @Autowired
    private SocialUserDetailsService NRSCDetailsService;

    @Lazy
    @Autowired
    private UsersConnectionRepository baseJdbcUsersConnectionRepository;//名字不可替换

    @Override
    public void configure(HttpSecurity http) throws Exception {
//        BaseConnectionRepository usersConnectionRepository = new NrscJdbcUsersConnectionRepository();
        OpenIdAuthenticationFilter openIdAuthenticationFilter = new OpenIdAuthenticationFilter();
        openIdAuthenticationFilter.setAuthenticationManager(http.getSharedObject(AuthenticationManager.class));
        openIdAuthenticationFilter.setAuthenticationSuccessHandler(authenticationSuccessHandler);
        openIdAuthenticationFilter.setAuthenticationFailureHandler(seAuthenticationFailHandler);

        OpenIdAuthenticationProvider openIdAuthenticationProvider = new OpenIdAuthenticationProvider();
        openIdAuthenticationProvider.setUserDetailsService(NRSCDetailsService);
        openIdAuthenticationProvider.setUsersConnectionRepository(baseJdbcUsersConnectionRepository);

        http.authenticationProvider(openIdAuthenticationProvider)
                .addFilterAfter(openIdAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

    }
    
    /**
     * 社交登录流程
     * 
     * 
     *1.请求地址： http://127.0.0.1:8061/authentication/openid
     *
     *	//设置authorization  类型 basic auth
				//填写username   clientid
				//填写 password   client-secret
     *
     *   formdata 格式
				//providerId      qq/weixin //由系统配置得到
				//openId        对应数据库中 providerUserid
     * 
     * {
		    "access_token": "0a1a5a9c-40a1-450e-b548-d0f57341a35f",
		    "token_type": "bearer",
		    "refresh_token": "7a7b1a92-e138-4989-adbe-0455610a4315",
		    "expires_in": 43199
		}
     * 
     */
}