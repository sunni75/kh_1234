package com.example.demo.boardController;

import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.mapper.BoardMapper;
import com.example.demo.payload.response.ApiResponse;
import com.example.demo.service.impl.BoardServiceImpl;
import com.example.demo.vo.BoardVO;
import com.example.demo.vo.MemberVO;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

// RESTful API
// rest : 편안하다 x
// Representational State Transfer
// 자원(Resource) : REST에서 모든 것은 자원으로 표현된다.
//		자원은 URI(Uniform Resource Identifier)를 통해 고유하게 식별된다. (예 : 사용자 정보를 열람 /member/user?idx=1 
//		http://도메인/member/user/1
// URI ,URL
// URL : Uniform Resource Locator (예 : 특정한 자원의 위치를 나타낸다.) http://도메인/member/user/info.html
// 상태 전이(Stateless) : RESTful API는 상태가 없다. 각 요청은 독립적이고, 클라이언트의 상태를 서버가 저장하지 않는다. 클라이언트는 필요한 정보를 요청에 포함해야한다.
// 표현(Representation) : 클라이언트가 자원의 표현을 요청하고, 서버는 JSON, XML 형식으로 반환한다.
// HTTP 메서드 : GET, POST (일반적인 request형식)
//		GET : 자원 요청
//		POST : 새로운 자원 생성
// 		PUT : 자원 수정 (전체)
//		PATCH : 자원 수정 (부분)
//		DELETE : 자원 삭제


@RestController
@RequestMapping("/api/board")
@Slf4j
@AllArgsConstructor
public class RestBoardController {
	
	private BoardServiceImpl boardService;
	
	/**
	 * 게시물 저장
	 * @param boardVO
	 * @return
	 */
	@PostMapping("/insert")
	public ResponseEntity<?> insertBoard(
			@RequestBody BoardVO boardVO, HttpServletRequest request, Model model
			){
		MemberVO memberVO = (MemberVO) model.getAttribute("userInfo");
		boardVO.setRegID(memberVO.getUserID());
		return ResponseEntity.ok(boardService.insertBoard(boardVO, request));
	}
	
	/**
	 * 게시물 목록
	 * @param boardVO
	 * @return
	 */
	@GetMapping("/list")
	public ResponseEntity<?> boardList(@ModelAttribute BoardVO boardVO) {
		return ResponseEntity.ok(boardService.selectList(boardVO));
	}
	
	/**
	 * 게시물 열람
	 * @param idx
	 * @return
	 */
	@GetMapping("/view/{idx}")
	public ResponseEntity<?> boardView(@PathVariable Long idx) {
		return ResponseEntity.ok(boardService.selectBoard(idx));
	}
	
	/**
	 * 게시물 수정
	 * @param boardVO
	 * @return
	 */
	@PutMapping("/update")
	public ResponseEntity<?> boardUpdate(@RequestBody BoardVO boardVO, HttpServletRequest request) {
		HttpSession session = request.getSession();
		MemberVO memberVO  = (MemberVO) session.getAttribute("userInfo");
		boardVO.setRegID(memberVO.getUserID());
		return ResponseEntity.ok(boardService.updateBoard(boardVO));
	}
	
	/**
	 * 게시물 삭제
	 * @param idx
	 * @return
	 */
	@DeleteMapping("/delete/{idx}")
	public ResponseEntity<?> boardDelete(@PathVariable Long idx) {
		boardService.delete(idx);
		return ResponseEntity.ok(new ApiResponse(true, "삭제되었습니다."));
	}
	

}
