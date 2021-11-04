package com.kh.spring.member;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

@WebAppConfiguration
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"file:src/main/webapp/WEB-INF/spring/**/*-context.xml"}) 
public class MemberServiceTest {
	
	
	@Autowired
	PasswordEncoder passwordEncoder;
	
	Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Test
	public void passwordEncoderTest() {
		String password = "123qwe!@#";
		
		String passwordEncoding = passwordEncoder.encode(password);
		logger.debug(passwordEncoding);
		
		String passwordEncoding2 = passwordEncoder.encode(password);
		logger.debug(passwordEncoding2);
		
		
		//원래 패스워드가 앞으로 와야함.
		logger.debug("매칭 결과: " + passwordEncoder.matches(password, passwordEncoding));
		
	}

}
