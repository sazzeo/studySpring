package com.kh.spring.admin.model.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kh.spring.admin.model.repository.AdmineMemberRepository;
import com.kh.spring.member.model.dto.Member;

@Service
public class AdminMemberService {

	@Autowired
	private AdmineMemberRepository admineMemberRepository;
	
	public List<Member> selectAllMembers() {
		
		return admineMemberRepository.selectAllMembers(); 
		
	}
	

}
