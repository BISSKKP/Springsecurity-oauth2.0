package com.base.security.demo.config;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.redis.core.RedisOperations;
import org.springframework.session.data.redis.RedisOperationsSessionRepository;
import org.springframework.session.data.redis.config.annotation.web.http.RedisHttpSessionConfiguration;

@SuppressWarnings("deprecation")
@Configuration
public class RedisSessionConfig extends RedisHttpSessionConfiguration {
	
	
    @Value("${server.session.timeout:1800}")//配置文件参数名
    private int sessionTimeout;

 
	@Primary
    @Bean
    public RedisOperationsSessionRepository sessionRepository(
        @Qualifier("sessionRedisTemplate") RedisOperations<Object, Object> sessionRedisTemplate,
        ApplicationEventPublisher applicationEventPublisher) {
		RedisOperationsSessionRepository sessionRepository = new RedisOperationsSessionRepository(sessionRedisTemplate);
        sessionRepository.setApplicationEventPublisher(applicationEventPublisher);
        sessionRepository.setDefaultMaxInactiveInterval(sessionTimeout);
        return sessionRepository;
    }

   
}
