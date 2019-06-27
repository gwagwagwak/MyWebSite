<%@ page contentType="text/html; charset=utf-8" %>
<html>
<head>
    <title>방명록 메시지 삭제 확인</title>
</head>
<body>

<form action="deleteMessage.jsp" method="post">
<%--
	글을 삭제하려면 글번호 정보가 필요하므로 글번호에 대한 정보를 post 요청시
	전달해줘야 한다. 그러나 사용자가 글 번호를 직접 입력할 필요성은 없기 때문에
	type="hidden" 속성을 사용하여 사용자 눈에는 보이지 않게 데이터를 전달한다. 
 --%>
<input type="hidden" name="messageId" value="${param.messageId}">
메시지를 삭제하시려면 암호를 입력하세요:<br>
암호: <input type="password" name="password"> <br>
<input type="submit" value="메시지 삭제하기">
</form>
</body>
</html>