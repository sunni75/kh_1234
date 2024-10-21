<%@ page language="java" 
	contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<!-- ModelAndView에서 저장한 데이터 호출 -->
<title>${title}</title>
</head>
<body>
	<form id="userForm" method="get" action="">
		<div>
			<label for="userID">아이디</label>
			<input type="text" id="userID" name="userID" placeholder="아이디를 입력하세요." value="" />
		</div>
		<div>
			<label for="email">이메일</label>
			<input type="text" id="email" name="email" placeholder="이메일을 입력하세요." value="" />
		</div>
		<button type="submit">확인</button>
	</form>
	<a href="/member/join">회원가입</a>
	<a href="/member/login">로그인</a>
	<a href="/member/findID">아이디찾기</a>
	<script>
		document.querySelector('#userForm').addEventListener('submit', function(e){
			e.preventDefault();
			const userID = document.querySelector('#userID').value?.trim();
			const email = document.querySelector('#email').value?.trim();
			
			const data = {
				method: 'post',
				headers: {
					'Content-Type': 'application/json' 
				},
				body: JSON.stringify({
					userID: userID,
					email: email,
				}),
			}
							
			fetch('/member/changePW', data)
				.then((response) => response.json())
				.then((data) => {
					console.log(data);
					const {result, message} = data;
					alert(message);
				});
			// 아이디, 이메일을 입력 받아서 비밀번호를 변경하고
			// 변경한 비밀번호를 alert창에 띄워준다.
			// end point는 /member/changePW 를 사용하고 
			// 1. 아이디, 이메일로 조회되는 행의 pk값이 있는지 체크
			// 2. 없으면 alert창에 메시지를 받을 수있게 구성 후 리턴
			// 3. 있으면 자바에서 랜덤 문자열을 만들고 DB에 기록한다.
			//    기록할때 생성된 문자열을 리턴해준다.
		});
	</script>
</body>
</html>









