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
			<label for="email">이메일</label>
			<input type="text" id="email" name="email" placeholder="이메일을 입력하세요." value="" />
		</div>
		<button type="submit">확인</button>
	</form>
	<a href="/member/join">회원가입</a>
	<a href="/member/login">로그인</a>
	<a href="/member/findPW">비밀번호찾기</a>
	<script>
		document.querySelector('#userForm').addEventListener('submit', function(e){
			e.preventDefault();
			const email = document.querySelector('#email').value?.trim();
			fetch('/member/findID/' + email)
				.then((res) => res.json())
				.then((data) => {
					console.log(data);
					const {result, message} = data;
					alert(message);
				});
		});
	</script>
</body>
</html>









