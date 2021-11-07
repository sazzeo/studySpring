package com.kh.spring.board;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.kh.spring.member.model.dto.Member;

@WebAppConfiguration
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"file:src/main/webapp/WEB-INF/spring/**/*-context.xml"}) 
public class BoardControllerTest {
	

	@Autowired
	WebApplicationContext wac ; //서블릿 Context
	MockMvc mockMvc;

	Logger logger = LoggerFactory.getLogger(this.getClass());
	
	

	@Before
	public void setup() {
		mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();
	}
	
	
	@Test
	public void fileUpload() throws Exception {
		
		MockMultipartFile file1 = new MockMultipartFile("files", "ofn.txt" , null , "firstFile".getBytes());
		MockMultipartFile file2 = new MockMultipartFile("files", "ofn2.txt" , null , "secondFile".getBytes());
		Member member = new Member();
		member.setUserId("zo4870");
		mockMvc.perform(multipart("/board/upload")
				.file(file1)
				.file(file2)
				.param("title", "게시클 테스트")
				.param("content", "본문")
				.sessionAttr("authentication" , member)
				).andExpect(status().is3xxRedirection())
		.andDo(print());
		
	}

}
