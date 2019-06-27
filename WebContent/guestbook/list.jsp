<%@page import="kr.co.koo.guestbook.service.MessageListView"%>
<%@page import="kr.co.koo.guestbook.service.GetMessageListService"%>
<%@ page contentType="text/html; charset=utf-8" %>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
	//요청 파라미터 이름이 page인 값을 가져와 pageNumberStr에 저장.
	String pageNumberStr = request.getParameter("page");
	//기본 페이지 번호는 1번으로 지정.
	int pageNumber = 1;
	/*
		만약 요청 파라미터에서 가져온 값이 존재할 경우
		그 값을 정수형으로 변환후 pageNumber 변수에 저장.
	*/
	if (pageNumberStr != null) {
		pageNumber = Integer.parseInt(pageNumberStr);
	}
	/*
		요청에 관한 세부 처리는 Service에게 위임하여
		그 처리 결과를 받아 viewData 객체에 저장.
	*/
	GetMessageListService messageListService = 
			GetMessageListService.getInstance();
	MessageListView viewData = 
			messageListService.getMessageList(pageNumber);	
%>

<jsp:include page="../include/header.jsp" />
<%-- viewData 객체를 EL 변수 viewData로 지정 --%>
<!-- Bootstrap core CSS -->
<link href="../vendor/bootstrap/css/bootstrap.css" rel="stylesheet">

<!-- Custom fonts for this template -->
<link
	href="https://fonts.googleapis.com/css?family=Raleway:100,100i,200,200i,300,300i,400,400i,500,500i,600,600i,700,700i,800,800i,900,900i"
	rel="stylesheet">
<link
	href="https://fonts.googleapis.com/css?family=Lora:400,400i,700,700i"
	rel="stylesheet">

<!-- Custom styles for this template -->
<link href="../css/business-casual.css" rel="stylesheet">
<c:set var="viewData" value="<%= viewData %>"/>


<div align="center">
	<form action="writeMessage.jsp" method="post" >
		이름: <input type="text" name="guestName"> <br>
		암호: <input type="password" name="password"> <br>
		메시지<br> <textarea name="message" cols="30" rows="3"></textarea> <br>
		<input type="submit" value="메시지 남기기" />
	</form>
</div>
<hr>

<%-- MessageTotalCount가 0인 경우(글이 하나도 없는 경우) --%>
<c:if test="${viewData.isEmpty()}">
등록된 메시지가 없습니다.
</c:if>

<%-- MessageTotalCount가 1이상인 경우(글이 하나 이상 존재하는 경우) --%>
<c:if test="${!viewData.isEmpty()}">
<div align="center">
<table border="1">
	
	<%-- 
		방명록 글 목록을 브라우저에 출력하기 위한 JSTL 코드
		messageList로부터 정보를 받아와 meesage라는 
		MessageVO타입의 EL 변수에 반복하여 저장.
	 --%>
	<c:forEach var="message" items="${viewData.messageList}">
	<tr>
		<td>
		메시지 번호: ${message.id} <br/>
		손님 이름: ${message.guestName} <br/>
		메시지: ${message.message} <br/>
		
		<%-- 삭제할 글 번호값을 url 파라미터로 전달 --%>
		<a href="confirmDeletion.jsp?messageId=${message.id}">[삭제하기]</a>
		</td>
	</tr>
	</c:forEach>
</table>
</div>
<%--
	페이지 번호를 브라우저에 출력하기 위한 JSTL 코드
	페이지 번호는 1번부터 시작하며 총 페이지 수까지 반복해서 출력해야 한다.
	그리고 사용자가 페이지 번호를 클릭했을 시 url 파라미터를 통해
	페이지 수의 요청정보를 전달해야 한다.
 --%>
<div align="center">
	<c:forEach var="pageNum" begin="1" end="${viewData.pageTotalCount}">
	<a href="list.jsp?page=${pageNum}">[${pageNum}]</a> 
	</c:forEach>
</div>
</c:if>
</body>
</html>