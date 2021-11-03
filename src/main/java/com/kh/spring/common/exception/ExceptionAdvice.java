package com.kh.spring.common.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

@Component
@ControllerAdvice(basePackages = "com.kh.spring") //advice :공통 관심사를 모듈화한 객체
public class ExceptionAdvice {
	
	// ControllerAdvice :모든 컨트롤러의 공통 관심사를 모듈화 할 것임
	// => 얘도 컨트롤러임
	//basekPackages = 설정한 경로 아래 모든 컨트롤러들을 대상으로 처리
	// 현재 공통관심사 = '예외처리' => 모든 컨트롤러의 예외처리를 여기서 처리
	

	
	Logger logger = LoggerFactory.getLogger(this.getClass());
	
	
	//속성값 : 저 class의 예외가 발생할떄만 이게 실행 됨.
	//기본 상태코드는 200번, 예외가 발생했으므로 응답 상태코드를 직접 500번으로 지정해 준다.
	@ResponseStatus(code = HttpStatus.INTERNAL_SERVER_ERROR) //internal_server_error : 500번에러
	@ExceptionHandler(HandleableException.class)
	public String  handleableExceptionProcess(HandleableException e , Model model) {
		
		model.addAttribute("msg" , e.error.MESSAGE);
		model.addAttribute("url" , e.error.URL);
		return "error/result";
		
		
	}
	
	
	
	//DataAccessException : 스프링이 이미 만들어 놓음.
	@ResponseStatus(code = HttpStatus.INTERNAL_SERVER_ERROR) //internal_server_error : 500번에러
	@ExceptionHandler(DataAccessException.class)
	public String dataAccessExceptionProcess(DataAccessException e , Model model) {
		
		logger.error(e.getMessage());
		model.addAttribute("msg" , "데이터베이스 접근 도중 예외가 발생했습니다.");
		model.addAttribute("url" , "/");
		
		return "error/result";
	}
	

}
