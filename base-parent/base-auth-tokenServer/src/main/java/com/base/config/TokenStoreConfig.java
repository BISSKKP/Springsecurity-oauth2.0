package com.base.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;
import org.springframework.security.oauth2.provider.token.store.redis.RedisTokenStore;

import com.base.jwt.BaseJwtEnhancer;

/**
 * 将token 保存再redis中
 * @author lqq
 *
 */
@Configuration
public class TokenStoreConfig {
	
	@Autowired
	private RedisConnectionFactory redisConnectionFactory;
	
	 /***
     * 将RedisTokenStore注入到spring容器
     * 当yml配置文件里配置了base.security.oauth2.tokenStore = redis时 ---> 下面的配置生效
     * @return
     */
	@Bean
	@ConditionalOnProperty(prefix="base.security.oauth2",name="tokenStore",havingValue="redis")
	public TokenStore redisTokenStore(){
		return new RedisTokenStore(redisConnectionFactory);
	}
	
	@Configuration
	// matchIfMissing = true 没有配置的时候就是默认配置
	@ConditionalOnProperty(prefix="base.security.oauth2",name="tokenStore",havingValue="jwt", matchIfMissing = true )
	public static class JwtTokenConfig{
		
		
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
		
		/***
        * 将自定义的TokenEnhancer注入到spring容器 --- 》可以覆盖该bean，实现自己的需求
        * 提供其他系统覆盖
        * @return
        */
		@Bean
		@ConditionalOnMissingBean(name="jwtTokenEnhancer")
		public TokenEnhancer jwtTokenEnhancer(){
			
			return new BaseJwtEnhancer();
		}
		
		
		
	}
	

}
