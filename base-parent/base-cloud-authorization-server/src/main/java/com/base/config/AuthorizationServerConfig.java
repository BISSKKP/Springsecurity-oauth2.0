package com.base.config;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;

import com.base.utils.CustomPasswordEncoder;

@Configuration
@EnableAuthorizationServer
public class AuthorizationServerConfig extends AuthorizationServerConfigurerAdapter {

    // 认证管理器
    @Autowired
    private AuthenticationManager authenticationManager;
    // redis连接工厂
    /*@Autowired
    private JedisConnectionFactory JedisConnectionFactory;*/
    
    
    @Autowired
    private DataSource dataSource;
    /**
     * 令牌存储
     * @return redis令牌存储对象
     */
    /*@Bean
    public TokenStore tokenStore() {
        return new RedisTokenStore(JedisConnectionFactory);
    }

    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
        endpoints.authenticationManager(this.authenticationManager);
        endpoints.tokenStore(tokenStore());
    }*/

    /***
     * 配置JwtTokenStore ---> TokenStore只负责token的存储，不负责token的生成
     * @return
     */
	@Bean
	public TokenStore jwtTokenStore(){
		return new JwtTokenStore(jwtAccessTokenConverter());
	}
	
	 /***
     * JwtAccessTokenConverter 其实就是一个TokenEnhancer
     * 通过阅读源码可知：TokenEnhancer是对生成的Token进行后续处理的（或者说增强），
     * 其实JwtAccessTokenConverter就是将默认生成的token做进一步处理使其成为一个JWT
     * @return
     */
	@Bean
	public JwtAccessTokenConverter jwtAccessTokenConverter(){
		
		 JwtAccessTokenConverter converter = new JwtAccessTokenConverter();
            //加入密签 --- 一定要保护好
            converter.setSigningKey("base");
            return converter;
	}

    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
        endpoints.tokenStore(jwtTokenStore()).
        accessTokenConverter(jwtAccessTokenConverter())
        .reuseRefreshTokens(true)
        . authenticationManager(this.authenticationManager);
    }

    /**
     * OAuth 授权端点开放
     */
    @Override
    public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {
        security
                // 开启/oauth/token_key验证端口无权限访问
                .tokenKeyAccess("permitAll()")
                // 开启/oauth/check_token验证端口认证权限访问
                .checkTokenAccess("isAuthenticated()")
                //主要是让/oauth/token支持client_id以及client_secret作登录认证
                .allowFormAuthenticationForClients();
    }
    /**
     * OAuth 配置客户端详情信息
     */
    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
//        clients.jdbc(dataSource).passwordEncoder(new CustomPasswordEncoder());//密码不选择加密
    	 
    	clients.inMemory()
         .withClient("client")
         .secret("client-secret")
         .authorizedGrantTypes("refresh_token", "password")
         .scopes("all")
         .accessTokenValiditySeconds(1200)
         .refreshTokenValiditySeconds(50000);
    	
    	
    }


}
