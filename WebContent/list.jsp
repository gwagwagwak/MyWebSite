<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%
	if(session.getAttribute("ValidMem") == null) {
%>
<jsp:forward page="index.jsp" />
<%
	}
%>
    
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>

<style>
#board_table {
	margin-bottom: 20px;
	padding: 30px 15px;
	background: #fff;
	background: rgba(255,255,255,0.9);
}
</style>

<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>

	<jsp:include page="include/welcome_header.jsp" />
	
	<br><br>
	
	
	<div id="board_table" align="center">
	<table width="700" cellpadding="0" cellspacing="0" border="1">
		<tr>
			<td>글번호</td>
			<td>작성자</td>
			<td>제목</td>
			<td>날짜</td>
			<td>조회수</td>
		</tr>
		
		<c:forEach var="vo" items="${list}">
		<tr>
			<td>${vo.bId}</td>
			<td>${vo.bName}</td>
			
			<td>
			<c:forEach begin="1" end="${vo.bIndent}">ㄴre:</c:forEach>
			<a href="content_view.do?bId=${vo.bId}">
				${vo.bTitle}
			</a>
			</td>
			
			<td>${vo.bDate}</td>
			<td>${vo.bHit}</td>
		</tr>
		</c:forEach>
		
		<tr>
			<td colspan="5">
				<a href="write_view.do">글 작성</a>
			</td>
		</tr>
	
	</table>
	</div>
	
	<jsp:include page="include/footer.jsp"/>
	
</body>
</html>



