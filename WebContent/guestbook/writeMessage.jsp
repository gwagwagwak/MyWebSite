<%@page import="kr.co.koo.guestbook.service.WriteMessageService"%>
<%@ page contentType="text/html; charset=utf-8" %>
<%@ page errorPage="errorView.jsp" %>
<%
	request.setCharacterEncoding("utf-8");
%>
<jsp:useBean id="message" class="kr.co.koo.guestbook.model.MessageVO">
	<jsp:setProperty name="message" property="*" />
</jsp:useBean>
<%
	WriteMessageService writeService = WriteMessageService.getInstance();
	writeService.write(message);
	response.sendRedirect("list.jsp");
%>
<html>
<head>
	<title>방명록 메시지 남김</title>
</head>
<body>
방명록에 메시지를 남겼습니다.
<br/>
<a href="list.jsp">[목록 보기]</a>
</body>
</html>