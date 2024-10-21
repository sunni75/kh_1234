package com.example.demo.vo;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

// 멤버변수의 getter 자동 생성
@Getter
// 멤버변수의 setter 자동 생성
@Setter
// 객체의 toString 메서드 자동 생성
@ToString
// 파라미터 없는 기본 컨스트럭터 자동 생성
@NoArgsConstructor
// 모든 맴버 변수를 파라미터로하는 컨스트럭터 생성
// @AllArgsConstructor
public class MemberVO {
	private Long idx;
	private int isAdmin;
	private String userID;
	private String password;
	private String username;
	private String email;
	private LocalDateTime regDate;
	private int isUse;
	private LocalDateTime dropDate;
	
	@Builder
	public MemberVO(Long idx, int isAdmin, String userID, String password, String username, String email,
			LocalDateTime regDate, int isUse, LocalDateTime dropDate) {
		this.idx = idx;
		this.isAdmin = isAdmin;
		this.userID = userID;
		this.password = password;
		this.username = username;
		this.email = email;
		this.regDate = regDate;
		this.isUse = isUse;
		this.dropDate = dropDate;
	}
	
	
	
	
	
	
	
	
	
}
