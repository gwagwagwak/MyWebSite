<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%
	String name = (String)session.getAttribute("name");
	String id = (String)session.getAttribute("id");
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

	<jsp:include page="include/welcome_header.jsp"/>
	
	<br><br>
	
	<div id="board_table" align="center">
	<form action="write.do" method="post">
		
		<input type="hidden" name="bName" value="${name}">
		
		<table width="500" cellpadding="0" cellspacing="0" border="1">
			<tr>
				<td>이름</td>
				<td>  
					${name}(${id})
				</td>
			</tr>
			<tr>
				<td>제목</td>
				<td>
					<input type="text" name="bTitle" size="50">
				</td>
			</tr>
			<tr>
				<td>내용</td>
				<td>
					<textarea rows="10" cols="20" name="bContent"></textarea>
				</td>
			</tr>
			
			<tr>
				<td colspan="2">
					<input type="submit" value="입력">&nbsp;&nbsp;
					<a href="list.do">목록 보기</a>
				</td>
			</tr>
		
		</table>	
	</form>
	</div>
	
	<jsp:include page="include/footer.jsp"/>

</body>
</html>


