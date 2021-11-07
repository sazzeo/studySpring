package com.kh.spring.common.interceptor;

import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.servlet.HandlerInterceptor;

import com.kh.spring.common.code.ErrorCode;
import com.kh.spring.common.code.MemberGrade;
import com.kh.spring.common.exception.HandleableException;
import com.kh.spring.member.model.dto.Member;


//servlet-context에서 빈으로 등록해 사용함
public class AuthInterceptor implements HandlerInterceptor {

	public boolean preHandle(HttpServletRequest httpRequest, HttpServletResponse httpResponse, Object handler) {

		String[] uri = httpRequest.getRequestURI().split("/");

		if (uri.length != 0) {

			switch (uri[1]) {
			case "member":
				memberAuthorize(httpRequest, httpResponse, uri);
				break;
			case "admin":
				adminAuthorize(httpRequest, httpResponse, uri);
				break;
			default:
				break;

			}
		}

		return true;

	}

	private void adminAuthorize(HttpServletRequest httpRequest, HttpServletResponse httpResponse, String[] uri) {

		// 비회원과 일반회원, 관리자회원 나누기

		HttpSession session = httpRequest.getSession();
		Member member = (Member) session.getAttribute("authentication");

		// 비회원일 경우
		if (member == null || MemberGrade.valueOf(member.getGrade()).ROLE.equals("user")) {
			throw new HandleableException(ErrorCode.UNAUTHORIZE_PAGE);

		}

		if (MemberGrade.valueOf(member.getGrade()).DESC.equals("super")) {

			return; // 바로 리턴해서 요청한 경로로 보내버림

		}

		// 슈퍼관리자인지 판단해 슈퍼관리자라면 모든 admin페이지에 접근 할 수 있음.

		member.getGrade();
		switch (uri[2]) {
		case "member":
			// 멤버 권한을 가진 사람이 아니면
			if (!MemberGrade.valueOf(member.getGrade()).DESC.equals("member")) {
				throw new HandleableException(ErrorCode.UNAUTHORIZE_PAGE);
			}

			break;
		case "board":
			// 멤버 권한을 가진 사람이 아니면
			if (!MemberGrade.valueOf(member.getGrade()).DESC.equals("board")) {
				throw new HandleableException(ErrorCode.UNAUTHORIZE_PAGE);
			}

			break;
		default:
			break;

		}

	}

	private void memberAuthorize(HttpServletRequest httpRequest, HttpServletResponse httpResponse, String[] uri) {

		HttpSession session = httpRequest.getSession();
		switch (uri[2]) {
		

		case "mypage":
			if (session.getAttribute("authentication") == null) {
				throw new HandleableException(ErrorCode.UNAUTHORIZE_PAGE); // 권한
			}
			break;

		default:
			break;

		}

	}

	public void init(FilterConfig fConfig) throws ServletException {
	}

}
