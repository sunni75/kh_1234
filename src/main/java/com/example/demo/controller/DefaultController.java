package com.example.demo.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.example.demo.vo.MemberVO;

import lombok.extern.slf4j.Slf4j;

@Controller
@RequestMapping("/dir")
// 롬복으로 로그 확인하는 어노테이션
// 예 : log.debug("내용");
// @Slf4j
public class DefaultController {
	
	// @Slf4j 를 사용하지 않는 일반적인 로그 출력 맴버 변수 설정
	private final Logger log = LoggerFactory.getLogger(this.getClass());

	/**
	 * 기본적인 jsp 리턴
	 * @return
	 */
	@GetMapping("/path1")
	public String path1() {
		// jsp확장자는 생략
		log.info("sfdsfd");
		return "html(jsp)파일 경로";
	}
	
	/**
	 * ModelAndView에 jsp와 데이터를 함께 리턴
	 * @return
	 */
	@GetMapping("/path2")
	public ModelAndView path2() {
		ModelAndView mav = new ModelAndView();
		mav.addObject("key이름", "key의 값이 들어간다(int, string, Object)");
		mav.setViewName("html(jsp)파일 경로");
		return mav;
	}
	
	/**
	 * 리턴타입이 스트링일 경우 데이터 오브젝트를 넣는법
	 * @param model
	 * @return
	 */
	@GetMapping("/path3")
	public String path3(Model model) {
		model.addAttribute("key이름", "key의 값이 들어간다(int, string, Object)");
		return "html(jsp)파일 경로";
	}
	
	/**
	 * GET또는 POST로 파라미터 받는법 1
	 * @param model
	 * @param value1
	 * @return
	 */
	@GetMapping("/path4")
	public String path4(
			Model model,
			@RequestParam(value = "key명", required = true, defaultValue = "기본값") String value1
			) {
		model.addAttribute("key이름", "key의 값이 들어간다(int, string, Object)");
		return "html(jsp)파일 경로";
	}
	
	/**
	 * GET또는 POST로 파라미터 받는법 2
	 * @param memberVO
	 * @return
	 */
	@GetMapping("/path5")
	public String path5(@ModelAttribute MemberVO memberVO) {
		return "html(jsp)파일 경로";
	}
	
	/**
	 * 연산 후 리다이렉트 또는 포워드
	 * 리다이렉트와 포워드 모두 URL을 이동하는 기능
	 * redirect : 단순 URL이동 (파라미터 설정가능)
	 * forward : 파라미터를 기본적으로 모조리 포함해서 URL 이동
	 * @return
	 */
	@GetMapping("/path6")
	public ModelAndView path6(@ModelAttribute MemberVO memberVO) {
		ModelAndView mav = new ModelAndView();
		
		// 만약 파라미터로 받은 데이터가 있을 경우 리다이렉트는 파라미터를 코드로 모두 작성해줘야 한다.
		mav.setViewName("redirect:/member/login?userID=" + memberVO.getUserID());
		// 만약파라미터로 받은 데이터가 있을 경우 포워드는 파라미터가 자동으로 붙는다.
		mav.setViewName("forward:/member/login");
		
		return mav;
	}
	
	/**
	 * @PostMapping은 사용자가 작성한 폼을 URL에 파라미터로 포함하지 않는다. 
	 * @param memberVO
	 * @return
	 */
	@PostMapping("/path7")
	public String path7(@ModelAttribute MemberVO memberVO) {
		// 무언가 연산 수행 후 리다이렉트
		return "redirect:/member/list";
	}
	
	/**
	 * URL이 GET, POST 모두 사용할 경우 @RequestMapping 사용
	 * @return
	 */
	@RequestMapping("/path8")
	public String path8() {
		return "html(jsp)파일 경로";
	}
	
	
}
