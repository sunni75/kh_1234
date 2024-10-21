package com.example.demo.mapper;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.example.demo.vo.MemberVO;

// DAO, MAPPER
@Repository
public class MemberMapper {
	
	@Autowired
	private SqlSession session;

	//public MemberMapper(SqlSession session) {
	//	this.session = session;
	//}
	
	/**
	 * 회원 목록 
	 * @param memberVO
	 * @return
	 */
	public List<MemberVO> selectList(MemberVO memberVO) {
		return session.selectList("member.selectList", memberVO);
	}
	
	/**
	 * 회원 정보
	 * @param memberVO
	 * @return
	 */
	public MemberVO selectOne(MemberVO memberVO) {
		return session.selectOne("member.selectOne", memberVO);
	}
	
	/**
	 * 회원 저장
	 * @param memberVO
	 */
	public void insert(MemberVO memberVO) {
		session.insert("member.insert", memberVO);
	}
	
	/**
	 * 정보 수정
	 * @param memberVO
	 */
	public void update(MemberVO memberVO) {
		session.update("member.update", memberVO);
	}
	
	/**
	 * 삭제
	 * @param memberVO
	 */
	public void delete(MemberVO memberVO) {
		session.delete("member.delete", memberVO);
	}
	
	/**
	 * 회원 탈퇴 처리
	 * @param idx
	 */
	public void memberDrop(Long idx) {
		session.update("member.memberDrop", idx);
	}
	
	/**
	 * 아이디 중복 체크
	 * @param userID
	 * @return
	 */
	public int checkUserID(String userID) {
		return session.selectOne("member.checkUserID", userID);
	}
	
	/**
	 * 이메일 중복 체크
	 * @param email
	 * @return
	 */
	public int checkEmail(String email) {
		return session.selectOne("member.checkEmail", email);
	}
	
	/**
	 * 이메일로 아이디 찾기
	 * @param email
	 * @return
	 */
	public String findID(String email) {
		return session.selectOne("member.findID", email);
	}
	
	/**
	 * 아이디와 이메일로 사용자의 PK값 얻어 내기
	 * @param memberVO
	 * @return
	 */
	public Long findPW(MemberVO memberVO) {
		return session.selectOne("member.findPW", memberVO);
	}
	
	/**
	 * pk값으로 비밀번호 변경
	 * @param memberVO
	 */
	public void updatePW(MemberVO memberVO) {
		session.update("member.updatePW", memberVO);
	}
	
	/**
	 * 회원정보 변경
	 * @param memberVO
	 */
	public void updateInfo(MemberVO memberVO) {
		session.update("member.updateInfo", memberVO);
	}
	
}
