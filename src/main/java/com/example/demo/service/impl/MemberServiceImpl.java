package com.example.demo.service.impl;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.mapper.MemberMapper;
import com.example.demo.payload.request.ChangePwRequest;
import com.example.demo.payload.request.JoinRequest;
import com.example.demo.service.CrudService;
import com.example.demo.util.StringUtil;
import com.example.demo.vo.MemberVO;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@Service
public class MemberServiceImpl implements CrudService<MemberVO> {
	@Autowired
	private MemberMapper mapper;

	@Override
	public List<MemberVO> selectList(MemberVO e) {
		return mapper.selectList(e);
	}

	@Override
	public MemberVO selectOne(MemberVO e) {
		return mapper.selectOne(e);
	}
	
	public MemberVO selectOne(String userID, String password) {
		MemberVO vo = new MemberVO();
		vo.setUserID(userID);
		vo.setPassword(password);
		return mapper.selectOne(vo);
	}

	@Override
	public void insert(MemberVO e) {
		mapper.insert(e);
	}

	@Override
	public void update(MemberVO e) {
		mapper.update(e);
	}

	@Override
	public void delete(MemberVO e) {
		mapper.delete(e);
	}

	public void memberDrop(Long idx) {
		mapper.memberDrop(idx);
	}
	
	public HashMap<String, Object> checkUserID(String userID) {
		int cnt = mapper.checkUserID(userID);
		// 리턴해야 하는 객체가 단순한 숫자 연산의 결과타입이면 번거롭게 VO를 생성하지 말고 hashMap을 쓰면 편리하다.
		HashMap<String, Object> map = new HashMap<>();
		map.put("isExist", cnt == 0 ? false : true);
		return map;
	}
	
	public HashMap<String, Object> checkEmail(String email) {
		int cnt = mapper.checkEmail(email);
		// 리턴해야 하는 객체가 단순한 숫자 연산의 결과타입이면 번거롭게 VO를 생성하지 말고 hashMap을 쓰면 편리하다.
		HashMap<String, Object> map = new HashMap<>();
		map.put("isExist", cnt == 0 ? false : true);
		return map;
	}
	
	public HashMap<String, Object> memberJoin(JoinRequest joinRequest) {
		// 리턴 (받는것 포함) 할 객체가 특정하기 어려울 경우 hashmap을 사용한다.
		HashMap<String, Object> map = new HashMap<>();
		
		// hashMap 리턴 시 아래의 내용으로 구성
		// 가입 성공 여부 true, false
		// 메시지 "성공" "실패" "무언가 사유로 인한 실패"
		
		// 1. 아이디 중복 체크
		HashMap<String, Object> idMap = this.checkUserID(joinRequest.getUserID());
		boolean idExist = (boolean) idMap.get("isExist");
		if (idExist) {
			map.put("result", false);
			map.put("message", "아이디가 사용중입니다.");
			return map;
		}
		
		// 2. 이메일 중복 체크
		HashMap<String, Object> emailMap = this.checkEmail(joinRequest.getEmail());
		boolean emailExist = (boolean) emailMap.get("isExist");
		if (emailExist) {
			map.put("result", false);
			map.put("message", "이메일이 사용중입니다.");
			return map;
		}
		
		// 3. 비번 확인 (비번2개가 동일한지, 비번 길이 4자 이상)
		String pw1 = joinRequest.getPassword();
		String pw2 = joinRequest.getPassword2();
		if (!pw1.equals(pw2)) {
			map.put("result", false);
			map.put("message", "비밀번호가 일치하지 않습니다.");
			return map;
		}
		if (pw1.length() < 4 || pw2.length() < 4) {
			map.put("result", false);
			map.put("message", "비밀번호는 4글자 이상 입력하세요.");
			return map;
		}
		
		// 4. 사용자 이름 있는지 체크 (4자 이상)
		String username = joinRequest.getUsername();
		if (username.length() < 2) {
			map.put("result", false);
			map.put("message", "이름은 2글자 이상 입력하세요.");
			return map;
		}
		
		MemberVO memberVO = MemberVO.builder()
				.email(joinRequest.getEmail())
				.userID(joinRequest.getUserID())
				.password(pw1)
				.username(username)
				.build();
		
		this.insert(memberVO);
		
		map.put("result", true);
		map.put("message", "가입이 완료되었습니다.");
		
		return map;
	}
	
	public HashMap<String, Object> findID(String email) {
		HashMap<String, Object> map = new HashMap<>();
		String userID = mapper.findID(email);
		
		map.put("result", userID == null ? false : true);
		map.put("message", userID == null ? "찾으시는 아이디가 없습니다." : "찾으시는 아이디는 " + userID + "입니다.");
		return map;
	}

	public Object changePW(ChangePwRequest changePwRequest) {
		// 이메일, 아이디로 해당 row 존재 여부 확인 (row가 있으면 idx값이 리턴된다.)
		MemberVO memberVO = MemberVO.builder()
				.email(changePwRequest.getEmail())
				.userID(changePwRequest.getUserID())
				.build();
		Long idx = mapper.findPW(memberVO);
		
		HashMap<String, Object> map = new HashMap<>();
		
		// row가 없으면 계정 못찾는 메시지 리턴
		if (idx == null) {
			map.put("result", false);
			map.put("message", "계정을 찾을 수 없습니다.");
			return map;
		}
		
		// 계정이 있으면 랜덤하게 문자열을 생성해서 
		// idx값에 해당하는 비밀번호 변경
		String randomPw = StringUtil.generateRandomString(10);
		
		// 기존에 memberVO가 있기때문에 별도로 생성 하지 않고 기존 변수명 활용
		memberVO = MemberVO.builder()
				.password(randomPw)
				.idx(idx).build();
		mapper.updatePW(memberVO);
		
		// 완료 메시지에 비밀번호를 넣어서 리턴
		map.put("result", true);
		map.put("message", "임시 비밀번호는 " + randomPw + "입니다.");
		
		return map;
	}

	public boolean updateInfo(HttpServletRequest request, MemberVO memberVO) {
		HttpSession session = request.getSession();
		MemberVO userInfo = (MemberVO) session.getAttribute("userInfo");
		
		if (userInfo == null) {
			return false;
		}
		
		// pk값이 세션에 있는거랑 다르면 로직을 추가할 수도 있다.
		if (!memberVO.getIdx().equals(userInfo.getIdx()) ) {
			return false;
		}
		
		// 세션에 있는 userID와 파라미터로 넘어온 userID가 다르면 이 역시 로직을 추가하는 것도 방법이다.
		if (!memberVO.getUserID().equals(userInfo.getUserID())) {
			return false;
		}
		
		if (!memberVO.getPassword().equals("")) {
			memberVO.setPassword("");
		}
		
		mapper.updateInfo(memberVO);
		
		return true;
	}
	
	
	
}
