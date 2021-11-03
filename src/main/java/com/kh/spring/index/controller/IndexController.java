package com.kh.spring.index.controller;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class IndexController {
	
	
	
	
	
	@GetMapping("/") //@WebServlet("/") 역할
	public String index() {

		
		//Controller 메서드의 return타입
		//void  : 해당 메서드가 호출된 url의 경로와 같은 위치에 있는 jsp파일로 요청을 재지정
		//		ex) 요청 ulr: /index/index   => WEB-INF/views/index/index.jsp
		//String : 반환하는 값이 jsp파일의 위치 
		//		ex)return "/index/index"   => WEB-INF/views/index/index.jsp
		//ModelAndView : Model객체 + view(jsp파일의 경로)
		

		//(jsp 경로 : servlet-context에 설정한 경로)
		//
		
		
		
		return "index";
		
		
			
	}

}
