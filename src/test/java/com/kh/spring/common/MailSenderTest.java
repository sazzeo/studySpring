package com.kh.spring.common;

import java.util.Date;
import java.util.Map;

import javax.mail.Message;
import javax.mail.internet.MimeMessage;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.context.WebApplicationContext;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kh.spring.member.model.dto.Member;

@WebAppConfiguration
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "file:src/main/webapp/WEB-INF/spring/**/*-context.xml" })
public class MailSenderTest {

	@Autowired
	WebApplicationContext wac; // 서블릿 Context
	MockMvc mockMvc;

	Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	JavaMailSenderImpl mailSender;

	@Autowired
	RestTemplate http;

	@Autowired
	ObjectMapper mapper;

	@Before
	public void setup() {
		mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();
	}

	@Test
	public void sendEmail() throws Exception {

		MimeMessage msg = mailSender.createMimeMessage();
		msg.setFrom("jee4870@gmail.com");
		msg.setRecipients(Message.RecipientType.TO, "zo9612@naver.com");
		msg.setSubject("메일테스트");
		msg.setSentDate(new Date());
		msg.setText("<h1>스프링에서 보낸 메일 </h1>", "UTF-8", "html");

		mailSender.send(msg);

	}

	@Test
	public void restTemplate() {

		// naver에는 요청하고 받은 응답 정보가 담겨있음.
//		String naver = http.getForObject("https://www.naver.com", String.class);
//		logger.debug(naver);
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
		MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
		map.add("userId", "zo4870");
		map.add("password", "1234");

		String myLogin = http.postForObject("http://localhost:8989/member/login", map, String.class);
		logger.debug(myLogin);

	}

	
	//get방식 요청 테스트
	@Test
	public void restTemplateApi() throws JsonMappingException, JsonProcessingException {

		String uri = "https://dapi.kakao.com//v3/search/book?query=java";
		RequestEntity<Void> request = RequestEntity.get(uri)
				.header("Authorization", "KakaoAK ad889b73002bc35a77975ec4a9090b75").build();

		ResponseEntity<String> response = http.exchange(request, String.class);
		String responseMap = response.getBody();
		JsonNode root = mapper.readTree(responseMap);

		// JsonNode는 속성명으로 바로 접근이 가능함.
		for (JsonNode jsonNode : root.findValues("documents")) {

			logger.debug(jsonNode.asText());
		}

	}
	
	
	//post방식 요청 테스트
	@Test
	public void papagoApi() throws JsonMappingException, JsonProcessingException {
		String uri = "https://openapi.naver.com/v1/papago/n2mt" ;
		
		//파라미터 설정
		MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
		body.add("source", "ko");
		body.add("target", "en");
		body.add("text", "만나서 반갑습니다.");
				
				
		//헤더설정
		RequestEntity<MultiValueMap<String, String>> request = RequestEntity.post(uri)
				.header("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8")
				.header("X-Naver-Client-Id", "la0uWGSGHQ3zwfbnn9nq")
				.header("X-Naver-Client-Secret", "U7rrtA5k4X")
				.body(body);
		
		
		ResponseEntity<String> res = http.exchange(request, String.class); 
		String 번역결과 =res.getBody();
		JsonNode node = mapper.readTree(번역결과);
		logger.debug(node.findValue("translatedText").asText());
		
		
	}

}
