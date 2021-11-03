package com.kh.spring.member.validator;

import java.util.regex.Pattern;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.kh.spring.member.model.repository.MemberRepository;

//스프링에서 제공해주는 valitator 체킹

@Component  //Spring bean으로 등록
public class joinFormValidator implements Validator{
	
	
	
	//final선언 하는 이유 : Thread Safe하게 만들기 위해서
	private final MemberRepository memberRepository;
	
	
	public joinFormValidator(MemberRepository memberRepository) {
		this.memberRepository = memberRepository;

	}
	
	
	@Override
	public boolean supports(Class<?> clazz) {

		//클래스들 중에서 내가 지정한 클래스와 같은 클래스만 밸리데이터 체킹함.
		
		//Member로 설정하면 Member 넘어올때마다 체킹하므로, 벨리데이터만을 위한
		//JoinForm객체를 만들어줬음.
		return JoinForm.class.equals(clazz);
		//JoinForm일때만 아래 메소드 실행 됨.
	}

	@Override
	public void validate(Object target, Errors errors) {
		
		
		
		JoinForm form = (JoinForm) target;
		
		//1. 아이디 존재 유무
		
		if(memberRepository.selectMemberByUserId(form.getUserId())!=null) {
			
			errors.rejectValue("userId", "error-userId" , "이미 존재하는 아이디입니다.");
			
		}

		
		
		//2. 비밀번호가 8글자 이상 , 숫자 영문자 특수문자 조합인지 확인
		Boolean valid = Pattern.matches("(?=.*[a-zA-Z])(?=.*[0-9])(?=.*[^a-zA-Zㄱ-힣0-9]).{8,}", form.getPassword());
		if (!valid) {
			//비밀번호가 영수특수문자 조합의 8자리 이상의 문자열 만족 못하면?
			errors.rejectValue("password", "error-password", "비밀번호는 8글자 이상, 영특수문자의 조합입니다.");
		}

		
		
		
		//3. 이메일 존재 유무 (생략)
		
		
		//4. 휴대폰 존재 유무
		
		valid = Pattern.matches("^\\d{9,11}$", form.getTell());
		if (!valid) {
			errors.rejectValue("tell", "error-tell" , "전화번호는 9~11자리의 숫자입니다.");
		}

		
	}

}
