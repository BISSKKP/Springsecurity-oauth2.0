package com.base.security.demo.session;

import java.io.IOException;

import javax.servlet.ServletException;

import org.springframework.security.web.session.SessionInformationExpiredEvent;
import org.springframework.security.web.session.SessionInformationExpiredStrategy;

import com.base.security.browser.session.AbstractSessionStrategy;

public class SeesionExpire extends AbstractSessionStrategy implements SessionInformationExpiredStrategy {

	public SeesionExpire(String invalidSessionUrl) {
		super(invalidSessionUrl);
	}

	@Override
	public void onExpiredSessionDetected(SessionInformationExpiredEvent event) throws IOException, ServletException {
		
		//用户的session失效时
		event.getResponse().setContentType("application/json;charset=UTF-8");
		event.getResponse().getWriter().write("并发登陆--demo 重写");
		
		
	}

}
