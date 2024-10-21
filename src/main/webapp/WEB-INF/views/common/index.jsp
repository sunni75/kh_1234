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
	<c:choose>
		<c:when test="${memberInfo != null}">
			<a href="/member/logout">로그아웃</a>
		</c:when>
		<c:otherwise>
			<a href="/member/login">로그인</a>
		</c:otherwise>
	</c:choose>
	
</body>
</html>