package com.kh.spring.member.validator;

public class JoinForm {

	private String userId;
	private String password;
	private String email;
	private String tell;

	public JoinForm() {
		// TODO Auto-generated constructor stub
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getTell() {
		return tell;
	}

	public void setTell(String tell) {
		this.tell = tell;
	}

	@Override
	public String toString() {
		return "JoinForm [userId=" + userId + ", password=" + password + ", email=" + email + ", tell=" + tell + "]";
	}

}
