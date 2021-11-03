package com.kh.spring.common.validator;

import java.util.HashMap;
import java.util.Map;

import org.springframework.validation.Errors;
import org.springframework.validation.FieldError;

public class ValidateResult {
	
	Map<String, Object> error; 
	
	public ValidateResult() {
		error = new HashMap<String,Object>();
	}
	
	public void addError(Errors errors) {
		//getDefaultMessage는 필요 없지만 나중을 위해 넣음.
		for(FieldError fieldError : errors.getFieldErrors()) {
			
			error.put(fieldError.getField(), fieldError.getDefaultMessage());
			
		}
	}

	public Map<String, Object> getError() {
		return error;
	}
	
	
}
