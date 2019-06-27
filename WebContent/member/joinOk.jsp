<%@page import="kr.co.koo.members.MemberDAO"%>
<%@page import="java.sql.Timestamp"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<% request.setCharacterEncoding("utf-8"); %>
<jsp:useBean id="vo" class="kr.co.koo.members.MemberVO" />
<jsp:setProperty name="vo" property="*" />

<%
	vo.setrDate(new Timestamp(System.currentTimeMillis()));
	
	MemberDAO dao = MemberDAO.getInstance();
	
	if(dao.confirmId(vo.getId()) == MemberDAO.MEMBER_EXISTENT) {
%>
		<script type="text/javascript">
			alert("아이디가 이미 존재합니다.");
			history.back();
		</script>
<%
	}else {
		int regNum = dao.insertMember(vo);
		if(regNum == MemberDAO.JOIN_SUCCESS) {
			session.setAttribute("id", vo.getId());		
%>
		<script type="text/javascript">
			alert("회원가입을 축하합니다.");
			document.location.href="../index.jsp";
		</script>
<%
		}else {
%>		
		<script type="text/javascript">
			alert("회원가입에 실패했습니다.");
			document.location.href="../index.jsp";
		</script>
<%
		}
	}	
%>		

    
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>

</body>
</html>



