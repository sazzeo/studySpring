package com.kh.spring.common.code;

public enum Config {
	
	
	DOMAIN("http://localhost:9090"),
	COMPANY_EMAIL("jee4870@gmail.com"),
	SMTP_AUTHENTICATION_ID("jee4870@gmail.com"),
	SMTP_AUTHENTICATION_PASSWORD("123456"),
	UPLOAD_PATH("C:\\CODE\\upload\\");

	
	
	public final String DESC ;
	
	private Config(String DESC) {
		
		this.DESC = DESC;
		
		
	}
	

}
