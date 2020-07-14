package com.base.security.browser.session;

import java.io.IOException;

import javax.servlet.ServletException;

import org.springframework.security.web.session.SessionInformationExpiredEvent;
import org.springframework.security.web.session.SessionInformationExpiredStrategy;

/**
 * 如果设置的session并发策略为一个账户第二次登陆会将第一次给踢下来
 * 则第一次登陆的用户再访问我们的项目时会进入到该类
 * event里封装了request、response信息
 * @author lqq
 *
 */
public class BaseExpiredSessionStrategy extends AbstractSessionStrategy implements SessionInformationExpiredStrategy {

	public BaseExpiredSessionStrategy(String invalidSessionUrl) {
		super(invalidSessionUrl);
	}

	@Override
	public void onExpiredSessionDetected(SessionInformationExpiredEvent event) throws IOException, ServletException {
		
		//用户的session失效时
//		event.getResponse().setContentType("application/json;charset=UTF-8");
//		event.getResponse().getWriter().write("并发登陆");
		onSessionInvalid(event.getRequest(), event.getResponse());
	}

	
	@Override
	protected boolean isConcurrency() {
		return true;
	}
	
}
