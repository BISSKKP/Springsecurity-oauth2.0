package com.base.security;


import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.base.DemoAppApplication;
import com.base.security.core.properties.SecurityProperties;

@RunWith(SpringRunner.class)
@SpringBootTest(classes=DemoAppApplication.class)
public class AppTes {
	
	@Autowired
	private SecurityProperties securityProperties;
	
	@Test
	public void test(){
		securityProperties.getOauth2().getClients();
		System.out.println("ss");
		
	}

}
