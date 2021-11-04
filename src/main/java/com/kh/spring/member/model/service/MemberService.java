package com.kh.spring.member.model.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.RequestEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import com.kh.spring.common.mail.MailSender;
import com.kh.spring.member.model.dto.Member;
import com.kh.spring.member.model.repository.MemberRepository;
import com.kh.spring.member.validator.JoinForm;

import lombok.Data;

@Service
@Data //롬복..
public class MemberService {
	
	@Autowired //Autowired: 자동으로 빈에있는 memberRepository담아줌
	private MemberRepository memberRepository; //프록시 객체가 담겨있음
	
	@Autowired
	private MailSender mailSender;
	
	@Autowired
	private RestTemplate http;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	


	public void insertMember(JoinForm form) {
		memberRepository.insertMember(form);
	
		
	}

	public Member memberLoginImpl(Member member) {
		return memberRepository.memberLoginImpl(member);
		
	}
	
	public Member selectMemberByUserID(String userId) {
		return memberRepository.selectMemberByUserId(userId);
		
	}

	
	//메일 발송을 위한
	public void authenticateUserByEmail(JoinForm form, String token) {
		
		MultiValueMap<String, String> body= new LinkedMultiValueMap<String, String>();
		body.add("userId", form.getUserId());
		body.add("persistToken", token);
		body.add("mailTemplate", "join-auth-mail");
		
		
		RequestEntity<MultiValueMap<String, String>> request
		= RequestEntity.post("http://localhost:8989/mail")
		.header("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8")
		.body(body);
		
		String htmlTxt = http.exchange(request, String.class).getBody();
		mailSender.send(form.getEmail(), "회원가입을 축하합니다", htmlTxt);
		
		
		
		
	}


}
