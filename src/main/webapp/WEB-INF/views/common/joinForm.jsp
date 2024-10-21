<%@ page language="java" 
	contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="ko">
<head>
<meta charset="UTF-8">
<title><c:out value="${title}" /></title>
</head>
<body>
	<form method="post" action="/member/joinProc">
		<div>
			<label for="userID">아이디</label>
			<input type="text" id="userID" name="userID" value="" />
			<button type="button" id="btnIdCheck">아이디 중복확인</button>
		</div>
		<div>
			<label for="email">이메일</label>
			<input type="text" id="email" name="email" value="" />
			<button type="button" id="btnEmailCheck">이메일 중복확인</button>
		</div>
		<div>
			<label for="username">이름</label>
			<input type="text" id="username" name="username" value="" />
		</div>
		<div>
			<label for="password">비밀번호</label>
			<input type="password" id="password" name="password" value="" />
		</div>
		<div>
			<label for="password2">비밀번호 확인</label>
			<input type="password" id="password2" name="password2" value="" />
		</div>
		<button type="button" id="btnJoin">가입</button>
	</form>
	<script>
		// 아이디 중복 체크 여부 변수
		let idCheck = false;
		let idDup = false;
		
		// 이메일 중복 체크 여부 변수
		let emailCheck = false;
		let emailDup = false;
		
		const btnIdCheck = document.querySelector('#btnIdCheck');
		// 아이디 중복 체크
		btnIdCheck.addEventListener('click', function(e){
			idCheck = false;
			idDup = false;
			const userID = document.querySelector('#userID').value?.trim();
			
			fetch('/member/checkUserID/' + userID)
				.then((response) => response.json())
				.then((data) => {
					console.log(data);
					const isExist = data.isExist;
					idCheck = true;
					idDup = isExist;
					if (isExist) {
						alert('이미 사용중인 아이디 입니다.');
						document.querySelector('#userID').focus();
					} else {
						alert('사용 가능한 아이디입니다.');
					}
				});
		});
		
		const btnEmailCheck = document.querySelector('#btnEmailCheck');
		
		btnEmailCheck.addEventListener('click', function(e){
			emailCheck = false;
			emailDup = false;
			const email = document.querySelector('#email').value?.trim();
			
			fetch('/member/checkEmail/' + email)
				.then((response) => response.json())
				.then((data) => {
					console.log(data)
					const isExist = data.isExist;
					emailCheck = true;
					emailDup = isExist;
					if (isExist) {
						alert('이미 사용중인 이메일 입니다.');
						document.querySelector('#email').focus();
					} else {
						alert('사용 가능한 이메일입니다.');
					}
				});
		});
		
		
		const btnJoin = document.querySelector('#btnJoin');
		btnJoin.addEventListener('click', function(e){
			const username = document.querySelector('#username').value?.trim();
			const password = document.querySelector('#password').value?.trim();
			const password2 = document.querySelector('#password2').value?.trim();
			if (username.length < 2) {
				alert('이름은 두글자 이상 입력하세요.');
				document.querySelector('#username').focus();
				return;
			}
			if (password.length < 4) {
				alert('비밀번호는 4글자 이상 입력하세요.');
				document.querySelector('#password').focus();
				return;
			}
			if (password !== password2) {
				alert('비밀번호가 일치하지 않습니다.');
				document.querySelector('#password').focus();
				return;
			}
			if (
				password.length >= 4 && password2.length >= 4 
				&& password === password2 
				&& idCheck && !idDup 
				&& emailCheck && !emailDup
				) {
				console.log('가입처리');
				// 도메인/member/joinProc2
				// get : url?key=value&key=value
				// json post : 
				// {
					// key : value
					// key : value
				// }
				
				const data = {
					method: 'post',
					headers: {
						'Content-Type': 'application/json' 
					},
					body: JSON.stringify({
						userID: document.querySelector('#userID').value.trim(),
						password: document.querySelector('#password').value.trim(),
						password2: document.querySelector('#password2').value.trim(),
						email: document.querySelector('#email').value.trim(),
						username: document.querySelector('#username').value.trim()
					}),
				}
				
				console.log(data)
				
				fetch('/member/joinProc2', data)
					.then((response) => response.json())
					.then((data) => {
						console.log(data);
						const {result, message} = data;
						alert(message);
						if (result) {
							window.location.href = '/member/login';
						}
					});
			} else {
				alert('회원가입 양식을 확인하세요.');
				return;
			}
		});
	</script>
</body>
</html>