package com.kh.spring.member;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import javax.servlet.http.Cookie;
import javax.xml.crypto.Data;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kh.spring.member.model.dto.Member;

@WebAppConfiguration
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"file:src/main/webapp/WEB-INF/spring/**/*-context.xml"}) 
public class MemberControllerTest {
	
	//MockMVC : Http 요청, 응답 상황 테스트를 위한 객체
	//가상의 요청을 보내준다.
	
	@Autowired
	WebApplicationContext wac ; //서블릿 Context
	MockMvc mockMvc;

	Logger logger = LoggerFactory.getLogger(this.getClass());
	
	
	@Before
	public void setup() {
		mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();
	}
	/*
	 * @Test public void searchPassword() throws Exception {
	 * 
	 * mockMvc .perform(get("/search-pw")) .andExpect(status().isOk())
	 * .andDo(print());
	 * 
	 * }
	 */
	
	
	
	@Test
	public void joinTest() throws Exception {
	
	
			mockMvc.perform(post("/member/join")
					.param("userId", "testMethod1123 ")
					.param("password", "!RNgus1234")
					.param("tell", "01088329612")
					.param("email", "testMethod@naver.com"))
			.andExpect(status().isOk())
			.andDo(print());
			

	
		
	}
	
	
	
	//json 요청하기 test
	@Test
	public void joinWithJson() throws Exception {
		
		Member member = new Member();
		member.setUserId("testJson");
		member.setPassword("1234");
		member.setEmail("json@pclass.com");
		member.setTell("010-8832-9612");
		
		//mapper.writeValue
		//mapper.readValue
		ObjectMapper mapper = new ObjectMapper();
		String memberJson = mapper.writeValueAsString(member);
		logger.debug("멤버 제이슨: " +memberJson);
		Member mem = mapper.readValue(memberJson, Member.class);
		logger.debug("제이슨 -> 객체: " + mem);
	
		//json으로 요청 보내보기
		mockMvc.perform(post("/member/join-json")
				.contentType(MediaType.APPLICATION_JSON)
				.content(memberJson)
				)
		.andExpect(status().isOk())
		.andDo(print());
		
		
		
	}
	
	@Test
	public void login() throws Exception {
		
		
		mockMvc.perform(post("/member/login")
				.param("userId", "zo4870")
				.param("password", "1234")
				)
		.andExpect(status().isOk()) //isOk 200만 통과 / 리다이렉트는 302
		.andDo(print());
		
	}
	
	@Test 
	public void mypage() throws Exception {
		
		Member member = new Member();
		member.setUserId("testJson");
		member.setPassword("1234");
		member.setEmail("json@pclass.com");
		member.setTell("010-8832-9612");
		
		//세션이나 쿠키값이 있어야 테스팅 할 수 있는 경우
		//요청할때 담을 수 있다. 
		
		mockMvc.perform(get("/member/mypage")
				.cookie(new Cookie("JSESSIONID" , "123456789"))
				.sessionAttr("authentication" , member)
				)
		
		.andExpect(status().isOk()) //isOk 200만 통과 / 리다이렉트는 302
		.andDo(print());
		
	}
	
	@Test
	public void idCheck() throws Exception {
		
		mockMvc.perform(get("/member/id-check")
				.characterEncoding("UTF-8")
				.param("userId", "DEV")
				)
		.andExpect(status().isOk())
		.andDo(print());
		
		
	}
	
	@Test
	public void sendEmail() throws Exception{
		
		
	
		
		
	}
	
	
	
	

}
