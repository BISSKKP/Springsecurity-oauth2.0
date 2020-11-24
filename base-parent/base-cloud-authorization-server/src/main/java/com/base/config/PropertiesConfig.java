package com.base.config;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import com.base.properties.BaseProperties;
/**
 * 允许开启配置文件
 * @author vsupa
 *
 */
@Configuration
@EnableConfigurationProperties(BaseProperties.class)
public class PropertiesConfig {

}
