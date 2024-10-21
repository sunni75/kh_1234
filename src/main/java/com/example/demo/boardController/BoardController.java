package com.example.demo.boardController;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.exception.BadRequestException;
import com.example.demo.service.FileService;
import com.example.demo.service.impl.BoardServiceImpl;
import com.example.demo.vo.BoardVO;
import com.example.demo.vo.FileMasterVO;
import com.example.demo.vo.MemberVO;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Controller
@RequestMapping("/board")
@Slf4j
@AllArgsConstructor
public class BoardController {
	
	private BoardServiceImpl boardService;
	private FileService fileService;
	
	/**
	 * 게시물 목록
	 * @param model
	 * @return
	 */
	@GetMapping("/list")
	public String boardList(Model model) {
		return "board/list2";
	}
	
	/**
	 * 게시물 내용
	 * @param idx
	 * @param model
	 * @return
	 */
	@GetMapping("/view/{idx}")
	public String boardView(@PathVariable Long idx, Model model) {
		model.addAttribute("idx", idx);
		return "board/view";
	}
	
	/**
	 * 게시물 작성 페이지
	 * @param model
	 * @return
	 */
	@GetMapping("/write")
	public String boardWrite(Model model) {
		
		FileMasterVO fileMasterVO = new FileMasterVO();
		MemberVO memberVO = (MemberVO) model.getAttribute("userInfo");
		fileMasterVO.setRegID(memberVO.getUserID());
		
		Long fileMasterId = fileService.insertFileMaster(fileMasterVO);
		model.addAttribute("fileMasterId", fileMasterId);
		
		return "board/write";
	}
	
	/**
	 * 게시물 수정 페이지
	 * @param model
	 * @param idx
	 * @return
	 */
	@GetMapping("/modify")
	public String boardModify(Model model, @RequestParam(value = "idx", required = true, defaultValue = "") Long idx) {
		BoardVO boardVO = boardService.selectBoard(idx);
		model.addAttribute("boardVO", boardVO);
		return "board/modify";
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	@GetMapping("/list2")
	public String boardList2(Model model) {
		model.addAttribute("title", "게시물 목록");
		model.addAttribute("htmlTag", "<p>나는 P태그다</p>");
		model.addAttribute("age", 30);
		
		List<MemberVO> list = new ArrayList<>();
		
		MemberVO memberVO = MemberVO.builder()
				.userID("abcd")
				.username("홍길동")
				.build();
		
		list.add(memberVO);
		
		MemberVO memberVO2 = MemberVO.builder()
				.userID("efghj")
				.username("홍길순")
				.build();
		
		list.add(memberVO2);
		
		MemberVO memberVO3 = MemberVO.builder()
				.userID("zzzz")
				.username("이순신")
				.build();
		
		list.add(memberVO3);
		
		model.addAttribute("memberVO", memberVO);
		
		model.addAttribute("list", list);
		
		return "board/list";
	}
	
	
	
	
	
}
