package com.kh.spring.member.model.service;



import com.kh.spring.member.model.dto.Member;
import com.kh.spring.member.validator.JoinForm;

public interface MemberService {
	
	void insertMember(JoinForm form);

	
	//로그인
	Member memberLoginImpl(Member member);
	
	Member selectMemberByUserID(String userId);

	
	//메일 발송을 위한
	void authenticateUserByEmail(JoinForm form, String token);
	

}
