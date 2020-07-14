package com.base.security.core;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import com.base.security.core.properties.SecurityProperties;

@Configuration
@EnableConfigurationProperties({SecurityProperties.class})//允许注解
public class SecurityConfig {

}
