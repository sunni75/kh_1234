package com.example.demo.config;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import com.example.demo.vo.MemberVO;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@Component
public class SessionInterceptor implements HandlerInterceptor {

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		// 1. 세션 체크
		HttpSession session = request.getSession(false);
		// 2. 체크해서 세션 값이 있으면 로그인 한거다
		if (session != null) {
			MemberVO userInfo = (MemberVO) session.getAttribute("userInfo");
			if (userInfo != null) return true;
		}
		// 3. 세션 값이 없으면 null 로그인 페이지로 이동
		response.sendRedirect(request.getContextPath() + "/member/login");
		return false;
	}

}
