<%@ page language="java" 
	contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>${title}</title>
</head>
<body>
	
	<form id="infoForm" method="post" action="/member/updateInfo">
		<input type="hidden" id="idx" name="idx" value='<c:out value="${userInfo.getIdx()}" />' readonly />
		<div>
			<label for="userID">아이디<label>
			<input type="text" id="userID" name="userID" value='<c:out value="${userInfo.getUserID()}" />' readonly />
		</div>
		<div>
			<label for="password">비밀번호<label>
			<input type="password" id="password" name="password" value='' />
		</div>
		<div>
			<label for="password2">비밀번호 확인<label>
			<input type="password" id="password2" name="password2" value='' />
		</div>
		<div>
			<label for="username">이름<label>
			<input type="text" id="username" name="username" value='<c:out value="${userInfo.getUsername()}" />' />
		</div>
		<div>
			<label for="email">이메일<label>
			<input type="text" id="email" name="email" value='<c:out value="${userInfo.getEmail()}" />' />
			<button type="button">중복확인</button>
		</div>
		<button type="button" id="btnConfirm">확인</button>
	</form>
<!--
1. myInfo에 session에 있는 값을 MemberVO로 캐스팅해서 form에 양식을 만든다.
2. form에 들어갈 필드 : userID(읽기전용), password (공란), username, email (변경시 중복체크)
3. 폼을 작성해서 post로 전송
4. DB에 저장하기전에 session에 있는 idx값, userID값 2개를 읽어서 form에 있는 userID와 일치하는지 확인
5. DB에 session에서 확인한 idx값, userID값으로 조회되는 행 존재 여부 확인
6. 행이 존재하면 update, 없으면 오류 메시지 출력
7. update가 되면 수정완료 메시지 후 랜딩페이지로 이동
8. 만약 오류가 나면 forward로 정보 수정페이지로 다시 이동
-->
<script>
	// 폼 제출 시 사고를 방지하는 차원에서 비밀번호를 입력 받는다.
	// 만약 비밀번호도 변경해야하면 비밀번호 필드 2개 다 입력 받는다.
	// 비밀번호를 2개 입력 받은 경우 비교 처리 필요
	// 비동기 통신으로 처리해도 상관 없으나 실습을 위해서 post로 제출 처리한다.
	document.querySelector('#btnConfirm').addEventListener('click', function(e){
		e.preventDefault();
		
		const userID = document.querySelector('#userID').value?.trim();
		const email = document.querySelector('#email').value?.trim();
		const username = document.querySelector('#username').value?.trim();
		const password = document.querySelector('#password').value?.trim();
		const password2 = document.querySelector('#password2').value?.trim();
		
		const email_regex = /^[a-zA-Z0-9._-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,4}$/i;
		if(!email_regex.test(email)){ 
			alert('이메일 형식을 확인하세요.');
			return false; 
		}
		if (userID === '') {
			alert('아이디가 없습니다.');
			window.location.href = '/';
			return false;
		}
		if (username.length < 2) {
			alert('이름은 2글자 이상 입력하세요.');
			return false;
		}
		if (password === '' && password2 !== '') {
			alert('비밀번호 변경을 원할 경우 비밀번호와 비밀번호 확인을 모두 입력하세요.');
			return false;
		}
		if (password !== '' && password2 !== '' && password !== password2) {
			alert('비밀번호와 비밀번호 확인 란이 다릅니다.');
			return false;
		}
		
		document.querySelector('#infoForm').submit();
	});
</script>
	
	
	
	
</body>
</html>









