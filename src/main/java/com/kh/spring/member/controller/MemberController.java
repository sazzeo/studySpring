package com.kh.spring.member.controller;

import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.util.CookieGenerator;

import com.kh.spring.common.code.ErrorCode;
import com.kh.spring.common.exception.HandleableException;
import com.kh.spring.common.validator.ValidateResult;
import com.kh.spring.member.model.dto.Member;
import com.kh.spring.member.model.service.MemberService;
import com.kh.spring.member.validator.JoinForm;
import com.kh.spring.member.validator.joinFormValidator;

@Controller
@RequestMapping("member")
public class MemberController {
	
	@Autowired
	private MemberService memberService;
	
	private joinFormValidator joinFormValidator;
	
	

	public MemberController(joinFormValidator joinFormValidator) {
		super();
		this.joinFormValidator = joinFormValidator;
	}
	
	
	
	//model의 속성 중 속성명이 joinForm인 속성이 있는 경우 initBinder 메서드 실행
	//modelAttribute => jsp form태그에 설정함.
	@InitBinder(value="joinForm") 
	public void initBinder(WebDataBinder webDataBinder) {
		//WebDataBinder : 객체가 컨트롤러로 넘어가기 전에 파라미터 값들을 
		//객체에 바인드해주는 역할
		webDataBinder.addValidators(joinFormValidator);
		
	}

	
	
	Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@GetMapping("join")
	public void joinForm(Model model) {
		
		//모델 객체 담아준 이유 : 스프링 부트에서 타임리프 쓸 때 예외 발생 방지
		model.addAttribute(new JoinForm())
		.addAttribute("error" , new ValidateResult().getError());
		//현재 필요 없음, 부트를 위해 추가함
		
		//예외 던져보기
		//throw new HandleableException(ErrorCode.AUTHENTICATION_FAILED_ERROR);
		
	}
	
/*
	@PostMapping("/member/join")
	public String join(@RequestParam String userId
			,@RequestParam String password
			,@RequestParam String tell
			,@RequestParam String email) {
	
		System.out.println(userId);
		System.out.println(password);
		System.out.println(tell);
		System.out.println(email);
		
		return "index";
	}
*/	

/*
	@PostMapping("/member/join")
	public String join(String userId
			,String password
			,String tell
			,String email) {
		
		System.out.println(userId);
		System.out.println(password);
		System.out.println(tell);
		System.out.println(email);
		
		return "index";
	}
*/
	
/*
	@PostMapping("/member/join")
	public String join(@ModelAttribute Member member) {
		
		System.out.println(member);
		return "index";
	}
*/
	
	
	//@ReqeustParam ,@ModelAttribute는 form태그 전송시 생략 가능함.
	@PostMapping("join")
	public String join(@Validated JoinForm form 
			, Errors errors //반드시 검증될 객체 바로 뒤에 작성
			, Model model
			, HttpSession session
			, RedirectAttributes redirectAttr ) 
			{
		
		ValidateResult vr = new ValidateResult();
		model.addAttribute("error" , vr.getError());
		
		//joinForm객체는 jsp단에서 만들어서 보내기 때문에..이미 담겨있음
		System.out.println(model.getAttribute("joinForm"));
		
		if(errors.hasErrors()) { //만약 error중에 하나라도 있으면?	
			vr.addError(errors);
			return "member/join"; //join-form으로 되돌려보냄	
		}
		
		
		String token = UUID.randomUUID().toString();
		session.setAttribute("serverToken", token);
		session.setAttribute("userInfo" , form);
		
		memberService.authenticateUserByEmail(form , token);
		
		redirectAttr.addFlashAttribute("message" , "이메일이 발송되었습니다.");
		
		
		//파라미터값과 Member dto의 속성값이 일치하면 자동으로 매칭해줌
		System.out.println(form);
		
		return "redirect:/";
	}
	
	

	@GetMapping("joinImpl")
	public String joinImpl(
			Model model
			) {
		
		model.getAttribute("userInfo");
		//memberService.insertMember(form);
		
		return "member/login";
	}
	
	
	
	//json파싱 테스트해보기
	//@RequestBody : reqeu.getInputStreamReader이 할 모든 과정들을 대신 해서 
	//객체에 담아줌
	@PostMapping("join-json")
	public String joinWithJson(@RequestBody Member member) {
	
		logger.debug(member.toString());
		return "index";
	}
	
	
	//로그인 관련 =================================================
	@GetMapping("login-form")  //mapping 파라미터는 get, post 별로 겹쳐도 됨
	public void loginForm() {}
	
	
	@PostMapping("login")
	public String login(Member member , Model model, HttpSession session, 
			RedirectAttributes redirectAttr) {
		Member authMember = memberService.memberLoginImpl(member);
		
		if(authMember==null) {
			redirectAttr.addFlashAttribute("message", "아이디나 비밀번호가 정확하지 않습니다.");
			return "redirect:/member/login-form";
		
		}
		
		session.setAttribute("authentication" , authMember);
		return "redirect:/member/mypage"; //리다이렉트함
		
	} 
	
	
	//쿠키와 세션값 가져오기
	@GetMapping("mypage")
	public String mypage(@CookieValue(name="JSESSIONID") String sessionId,
					@SessionAttribute(name="authentication") Member member
					,HttpServletResponse response
			)

	{
		
		logger.debug("JSESSIONID 쿠키값: " +sessionId);
		logger.debug("Session 값: " + member);
		
		//쿠키 추가 해보기
		CookieGenerator cookieGenerator = new CookieGenerator();
		cookieGenerator.setCookieName("testCookie");
		cookieGenerator.addCookie(response, "test_cookie");
		
		return "/member/mypage"; 
		//HttpServletResponse 객체 사용시  void 관련 문제 발생
		//따라서 String으로 리턴해줌....
	};
	
	
	@GetMapping("id-check")
	@ResponseBody //얘가 있으면 return값이 getWriter.print()의 역할이 됨.
	public String idCheck(String userId) {
		logger.debug(userId);
		Member member = memberService.selectMemberByUserID(userId);
		
		if(member == null) {
			return "available";
		}
		
		return "disable";
		
	}
	
	
	/*
	model attribute 생성규칙
	
	com.myapp.Product becomes "product"
	com.myapp.MyProduct becomes "myProduct"
	com.myapp.UKProduct becomes "UKProduct"
	 * */
	
	
	
	
	
	

}
