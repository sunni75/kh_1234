package com.example.demo.advice;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.servlet.NoHandlerFoundException;

import com.example.demo.vo.MemberVO;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

// 일반적인 MVC 컨트롤러에서 발생하는 예외를 처리하며, 뷰를 반환하는 방식이다. (JSP, Thyeleaf 등등)
@ControllerAdvice
public class GlobalExceptionHandler {
	
	

	/**
	 * 404 에러
	 * @param ex
	 * @param model
	 * @return
	 */
	@ExceptionHandler(NoHandlerFoundException.class)
	public String handle404(NoHandlerFoundException ex, Model model) {
		// 속성, html 경로
		model.addAttribute("message", "페이지를 찾을 수 없습니다.");
		return "error/404";
	}
	
	/**
	 * 500 에러
	 * @param ex
	 * @param model
	 * @return
	 */
	//@ExceptionHandler(Exception.class)
	//public String handle500(Exception ex, Model model) {
		// 속성, html 경로
	//	model.addAttribute("message", "Internal Server Error");
	//	return "error/500";
	//}
	
	
	
}
