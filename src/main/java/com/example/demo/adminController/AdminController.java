package com.example.demo.adminController;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.example.demo.service.impl.MemberServiceImpl;
import com.example.demo.vo.MemberVO;

import lombok.extern.slf4j.Slf4j;

@Controller
@RequestMapping("/admin")
@Slf4j
public class AdminController {
	
	@Autowired
	private MemberServiceImpl memberService;
	
	//public AdminController(MemberService memberService) {
	//	this.memberService = memberService;
	//}

	@GetMapping("/dashboard")
	public ModelAndView dashboard() {
		ModelAndView mav = new ModelAndView();
		mav.setViewName("admin/dashboard");
		return mav;
	}
	
	// memberList?userID=abcd
	// http://localhost:8080/admin/memberList?userID=abcd
	// @RequestParam 어노테이션으로 파라미터를 넣는다.
	// @RequestParam value는 url의 키값, required는 필수 여부, defaultValue는 값이 없을때 사용할 기본값
	@GetMapping("/memberList")
	public ModelAndView memberList(
			
			@ModelAttribute MemberVO memberVO
			
			
			//@RequestParam(value = "userID", required = false, defaultValue = "") String userID,
			//@RequestParam(value = "password", required = false, defaultValue = "") String password
	) {
		
		log.info("=====================");
		log.info(memberVO.toString());
		log.info("=====================");
		
		// TRACE : 가장 세밀한 로그 - 메소드의 시작, 끝, 변수상태, 매개변수 등등
		// DEBUG : 내부 상황 추척
		// INFO : 일반적인 정보 출력
		// WARN : 경고 로그 출력 (향후 문제가 발생할 가능성이 있는 경우 출력)
		// ERROR : 치명적 오류가 발생했을떄 출력 *******
		
		List<MemberVO> list = memberService.selectList(memberVO);
		ModelAndView mav = new ModelAndView();
		mav.setViewName("admin/memberList");
		mav.addObject("title", "회원목록");
		mav.addObject("list", list);
		return mav;
	}
	
}
