package com.base.config;

import java.text.ParseException;
import java.util.Date;

import org.apache.commons.lang3.time.DateUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.databind.ObjectMapper;

@Configuration
public class WebConfig implements WebMvcConfigurer {

	@Override 
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry.addResourceHandler("/webjars/**").addResourceLocations("classpath:/META-INF/resources/webjars/");
		//// //本地文件映射
		registry.addResourceHandler("/file/**").addResourceLocations("file:" + "/");
	}	
	

	private CorsConfiguration buildConfig() {
		CorsConfiguration corsConfiguration = new CorsConfiguration();
		corsConfiguration.addAllowedOrigin("*");
		corsConfiguration.setAllowCredentials(true);
		corsConfiguration.addAllowedHeader("*");
		corsConfiguration.addAllowedMethod("*");

		return corsConfiguration;
	}

	/**
	 * 允许所有跨域
	 * @return
	 */
	@Bean
	public CorsFilter corsFilter() {
		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		source.registerCorsConfiguration("/**", buildConfig());
		return new CorsFilter(source);
	}

	/**
	 * 日期格式
	 * 
	 * @return
	 */
	@Bean
	public Converter<String, Date> addNewConvert() {
		return new Converter<String, Date>() {
			@Override
			public Date convert(String source) {
				
				Date date = null;
					try {
						date=DateUtils.parseDate(source, "yyyy-MM-dd HH:mm:ss","yyyy年MM月dd HH:mm:ss","yyyy-MM-dd","yyyy年MM月dd","yyyy/MM/dd");
					} catch (ParseException e) {
						e.printStackTrace();
					}
				return date;
			}
		};
	}
	
	/**
	 * 去掉json中所有为空的字段
	 * @return
	 */
	@Bean
    public MappingJackson2HttpMessageConverter mappingJacksonHttpMessageConverter() {
        final MappingJackson2HttpMessageConverter converter = new MappingJackson2HttpMessageConverter();
        ObjectMapper MAPPER = new ObjectMapper();
        MAPPER.setSerializationInclusion(Include.NON_NULL); 
        converter.setObjectMapper(MAPPER);
        return converter;
    }

}
