package com.example.demo.advice;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

import com.example.demo.vo.MemberVO;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@ControllerAdvice
public class SessionAdviceHandler {
	
	// session을 스프링 프레임워크에서 전역으로 관리하게 된다.
	@ModelAttribute
	public void addSessionAttributes(HttpServletRequest request, Model model) {
		HttpSession session = request.getSession(false);
		// 세션 정보 체크 후 세션이 있으면 model의 attribute 메서드를 이용해서 넣어준다.
		if (session != null) {
			MemberVO userInfo = (MemberVO) session.getAttribute("userInfo");
			model.addAttribute("userInfo", userInfo);
		}
	}
	
}
