package com.base.security.app.process;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.stereotype.Component;

import com.base.security.core.SocialConfigurer;
import com.base.security.core.constants.SecurityConstants;

/**
 * Description：
 */
@Component
public class SpringSocialConfigurerPostProcessor implements BeanPostProcessor {

    /***
     * spring启动时所有的bean初始化之前都会调用该方法 --- 可以在bean初始化之前对bean做一些操作
     * @param bean
     * @param beanName
     * @return
     * @throws BeansException
     */
    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        return bean;
    }

    /***
     * spring启动时所有的bean初始化之后都会调用该方法 --- 可以对初始化好的bean做一些修改
     * @param bean
     * @param beanName
     * @return
     * @throws BeansException
     */
    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        if(StringUtils.equals(beanName, "securitySocialConfig")){
        	SocialConfigurer config = (SocialConfigurer)bean;
            config.signupUrl(SecurityConstants.DEFAULT_APP_SOCIAL_SIGNUP_URL);//重写拦截地址
            return config;
        }
        return bean;
    }

}