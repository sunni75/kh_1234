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
<style>
	table {
		width: 100%;
		border-collapse: collapse;
	}
</style>
</head>
<body>
	<table>
		<thead>
			<tr>
				<th>번호</th>
				<th>권한</th>
				<th>아이디</th>
				<th>비번</th>
				<th>이름</th>
				<th>이메일</th>
				<th>가입일</th>
				<th>계정사용여부</th>
				<th>탈퇴일</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${list}" var="vo">
				<tr>
					<td><c:out value="${vo.getIdx()}" /></td>
					<td>
						<c:choose>
							<c:when test="${vo.getIsAdmin() == 1}">
								관리자
							</c:when>
							<c:otherwise>
								일반사용자
							</c:otherwise>
						</c:choose>
					</td>
					<td><c:out value="${vo.getUserID()}" /></td>
					<td><c:out value="${vo.getPassword()}" /></td>
					<td><c:out value="${vo.getUsername()}" /></td>
					<td><c:out value="${vo.getEmail()}" /></td>
					<td><c:out value="${vo.getRegDate()}" /></td>
					<td><c:out value="${vo.getIsUse()}" /></td>
					<td><c:out value="${vo.getDropDate()}" /></td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
</body>
</html>