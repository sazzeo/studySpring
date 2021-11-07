package com.kh.spring.common.mail.handler;

import java.util.Map;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;


//메일 템플릿 보내기
@Controller
public class MailHandler {
	
	
	@PostMapping("mail")
	public String writeMailTemplate(@RequestParam Map<String, String> template) {
		System.out.println("템플릿: " + template.get("mailTemplate"));
		return "mail-template/" +template.get("mailTemplate");
		
	};
	

}
 