package com.base.security.core.social.weixin.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.social.connect.ConnectionFactory;
import org.springframework.web.servlet.View;

import com.base.security.core.properties.SecurityProperties;
import com.base.security.core.properties.WeiXinProperties;
import com.base.security.core.social.ConnectViews;
import com.base.security.core.social.support.SocialAutoConfigurerAdapter;
import com.base.security.core.social.weixin.connect.WeiXinConnectionFactory;

/**
 * 
 * @author lqq
 *
 */
@Configuration
@ConditionalOnProperty(prefix = "base.security.social.weixin", name = "app-id")
public class WeiXinAutoConfig extends SocialAutoConfigurerAdapter {

    @Autowired
    private SecurityProperties securityProperties;

    @Override
    protected ConnectionFactory<?> createConnectionFactory() {
        WeiXinProperties weiXinProperties = securityProperties.getSocial().getWeixin();
        return new WeiXinConnectionFactory(weiXinProperties.getProviderId(), weiXinProperties.getAppId(),
                weiXinProperties.getAppSecret());
    }

//    /***
//     * connect/weixinConnected 绑定成功的视图
//     * connect/weixinConnect 解绑的视图
//     *
//     * 两个视图可以写在一起，通过判断Model对象里有没有Connection对象来确定究竟是解绑还是绑定
//     */
//    @Bean({"connect/weixinConnect", "connect/weixinConnected"})
//    //下面的注解的意思是当程序里有名字为weixinConnectedView的bean
//    // 我写的默认的weixinConnectedView这个bean不会生效，也就是你可以写一个更好的bean来覆盖掉我的
//    @ConditionalOnMissingBean(name = "weixinConnectedView")
//    public View weixinConnectedView() {
//        return new NrscConnectView();
//    }

    @Bean({"connect/weixinConnect","connect/weixinConnected"})
    @ConditionalOnMissingBean(name="weixinConnectionView")//提供重写覆盖
    public View weixinConnectedView(){
    	
    	return new ConnectViews();
    }
    
    
}
