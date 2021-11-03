package com.kh.spring.admin.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.kh.spring.admin.model.service.AdminMemberService;
import com.kh.spring.member.model.dto.Member;

@Controller
@RequestMapping("admin/member")
public class AdminMemberController {
	
	Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private AdminMemberService admineMemberService;
	
	
	@GetMapping("member-list")
	public void searchAllMembers(Model model) {
		
		//model객체에는 생성자가 없음 : 프레임 워크가 만들어줌 => 주입받아서 .
		
		List<Member> members = admineMemberService.selectAllMembers();
		
		model.addAttribute("members" , members);
		
		for(Member member : members)
			logger.debug("로그찍기" + member.toString());
		
	
	}

}
