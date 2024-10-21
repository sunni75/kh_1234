package com.example.demo.commonController;

import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.json.JsonParser;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.example.demo.payload.request.ChangePwRequest;
import com.example.demo.payload.request.JoinRequest;
import com.example.demo.service.impl.MemberServiceImpl;
import com.example.demo.util.StringUtil;
import com.example.demo.vo.MemberVO;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;

@Controller
@RequestMapping("/member")
@Slf4j
public class CommonController {

	@Autowired
	private MemberServiceImpl memberService;
	
	// 로그인 페이지 - 추후 구현
	@RequestMapping("/login")
	public ModelAndView login(
			@RequestParam(value = "userID", required = false, defaultValue = "") String userID,
			@RequestParam(value = "password", required = false, defaultValue = "") String password
			) {
		ModelAndView mav = new ModelAndView();
		mav.setViewName("member/login");
		mav.addObject("title", "로그인 페이지");
		mav.addObject("userID", userID);
		mav.addObject("password", password);
		return mav;
	}
	
	@PostMapping("/loginProc")
	public String loginProc(
			@ModelAttribute MemberVO memberVO,
			Model model,
			HttpServletRequest request
			//@RequestParam(value = "userID", required = true, defaultValue = "") String userID,
			//@RequestParam(value = "password", required = true, defaultValue = "") String password
			) {
		//log.info("아이디 : " + userID);
		//log.info("비번 : " + password);
		MemberVO result = memberService.selectOne(memberVO);
		// log.info(result.toString());
		if (result != null) {
			// 세션 부여
			log.info("로그인 성공");
			HttpSession session = request.getSession();
			session.setAttribute("userInfo", result);
			return "redirect:/board/list";
		} else {
			// 로그인 실패
			log.info("로그인 실패");
			
			// redirect : 지정한 URL로 파라미터를 포함해서 GET 방식으로 호출한다.
			// mav.setViewName("redirect:/member/login?userID=" + memberVO.getUserID() + "&password=" + memberVO.getPassword());
			// forward : 지정한 URL로 파라미터를 포함해서 GET 또는 POST 방식으로 호출한다.
		}
		
		return "forward:/member/login";
	}
	
	@GetMapping("/logout")
	public String logout(HttpServletRequest request) {
		HttpSession session = request.getSession();
		// 세션 삭제
		session.invalidate();
		return "redirect:/";
	}
	
	/**
	 * 회원가입 양식
	 * @return
	 */
	@GetMapping("/join")
	public ModelAndView join() {
		ModelAndView mav = new ModelAndView();
		mav.setViewName("common/joinForm");
		return mav;
	}
	
	/**
	 * 회원가입 처리
	 * @param memberVO
	 */
	@PostMapping("/joinProc")
	public void joinProc(@ModelAttribute MemberVO memberVO) {
		memberService.insert(memberVO);
	}
	
	/**
	 * 회원가입 비동기 처리
	 * @param joinRequest
	 * @RequestBody 어노테이션이 있어야 post 형식의 데이터를 받을 수 있다.
	 * @return
	 */
	@PostMapping("/joinProc2")
	@ResponseBody
	public ResponseEntity<?> joinProc2(
			@RequestBody JoinRequest joinRequest
			) {
		
		log.info(joinRequest.toString());
		// HashMap<String, Object> result = memberService.memberJoin(joinRequest);
		// return ResponseEntity.ok(result);
		return ResponseEntity.ok(memberService.memberJoin(joinRequest));
	}
	
	/**
	 * 회원정보수정
	 * @param memberVO
	 */
	@PostMapping("/updateProc")
	public void updateProc(@ModelAttribute MemberVO memberVO) {
		memberService.update(memberVO);
	}
	
	/**
	 * 회원 삭제
	 * @param memberVO
	 */
	@PostMapping("/deleteProc")
	public void deleteProc(@ModelAttribute MemberVO memberVO) {
		memberService.delete(memberVO);
	}
	
	@GetMapping("/memberDrop")
	public void memberDrop(
			@RequestParam(value = "idx", required = true, defaultValue = "0") Long idx
			) {
		memberService.memberDrop(idx);
	}
	
	/**
	 * 비동기 통신 아이디 중복 확인
	 * @return
	 */
	@GetMapping("/checkUserID/{userID}")
	@ResponseBody
	public ResponseEntity<?> checkUserID(
			@PathVariable String userID
			) {
		HashMap<String, Object> result = memberService.checkUserID(userID);
		return ResponseEntity.ok(result);
	}
	
	/**
	 * 비동기 통신 이메일 중복 확인
	 * @param email
	 * @return
	 */
	@GetMapping("/checkEmail/{email}")
	@ResponseBody
	public ResponseEntity<?> checkEmail(
			@PathVariable String email
			) {
		HashMap<String, Object> result = memberService.checkEmail(email);
		return ResponseEntity.ok(result);
	}
	
	@GetMapping("/findID")
	public String findID() {
		return "common/findID";
	}
	
	/**
	 * 이메일로 아이디 찾기
	 * @param email
	 * @return
	 */
	@GetMapping("/findID/{email}")
	@ResponseBody
	public ResponseEntity<?> findIDByEmail(@PathVariable String email) {
		return ResponseEntity.ok(memberService.findID(email));
	}
	
	@GetMapping("/findPW")
	public String findPW() {
		return "common/findPW";
	}
	
	@PostMapping("/changePW")
	@ResponseBody
	public ResponseEntity<?> changePW(
			@RequestBody ChangePwRequest changePwRequest) {
		return ResponseEntity.ok(memberService.changePW(changePwRequest));
	}
	
	@PostMapping("/test")
	@ResponseBody
	public ResponseEntity<?> test(
			@RequestBody HashMap<String, Object> map) {
		
		log.info(map.toString());
		
		StringUtil.printMap("test", map);
		
		return ResponseEntity.ok("sdfsdfsdffds");
	}
	
	
	/**
	 * 사용자 정보 수정 페이지
	 * @param request
	 * @return
	 */
	@RequestMapping("/myInfo")
	public ModelAndView myInfo(HttpServletRequest request) {
		ModelAndView mav = new ModelAndView();
		mav.setViewName("common/myInfo");
		
		HttpSession session = request.getSession();
		MemberVO memberVO = (MemberVO) session.getAttribute("userInfo");
		
		mav.addObject("userInfo", memberVO);
		
		return mav;
	}
	
	/**
	 * 회원정보 수정
	 * @param request
	 * @param memberVO
	 * @return
	 */
	@PostMapping("/updateInfo")
	public ModelAndView updateInfo(
			HttpServletRequest request,
			@ModelAttribute MemberVO memberVO
			) {
		log.info(memberVO.toString());
		
		boolean result = memberService.updateInfo(request, memberVO);
		
		ModelAndView mav = new ModelAndView();
		
		if (result) {
			mav.setViewName("redirect:/");
		} else {
			mav.setViewName("forward:/member/myInfo");
		}
		
		return mav;
	}
	
}
