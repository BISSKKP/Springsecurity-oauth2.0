package com.base.sso.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;

import com.base.sso.utils.PasswordEncoderUtils;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
@Order(1)
public class SpringSecurityConfig extends WebSecurityConfigurerAdapter { 

	@Autowired
	private UserDetailsService userDetailsService;
	
	
	  /***
     * 可以像资源服务器一样进行配置，然后访问认证服务器时就会根据下面的配置进行认证+授权
     * @param http
     * @throws Exception
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        //security5+ 认证默认为表单了也就是http.formLogin()
        http.formLogin()
        .loginPage("/authen/require")
        .loginProcessingUrl("/dologin")
        .and()
        
        .authorizeRequests()
        .antMatchers("/oauth/**","/login/**").permitAll()
        
        //其他的都需要认证
        .anyRequest().authenticated()
       
        
        .and().exceptionHandling().accessDeniedHandler(new SsoAccessDeniedHandler())
        
        .and().cors()
        //// 关闭跨域保护;
        .and()
        .csrf().disable();
        ;
        
        
        
    }
    
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
    	
    	
    	auth.userDetailsService(userDetailsService)
    	.passwordEncoder(new PasswordEncoderUtils());
    	
    }
    
    @Bean
    @Override
    protected AuthenticationManager authenticationManager() throws Exception {
    	return super.authenticationManager();
    }
    
    @Override
    public void configure(WebSecurity web) throws Exception {
    	//解决静态资源被拦截的问题
        web.ignoring().antMatchers("/asserts/**");
    }
    
    
    
    
}
