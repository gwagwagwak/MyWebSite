<%@page import="kr.co.koo.members.MemberVO"%>
<%@page import="kr.co.koo.members.MemberDAO"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%
	request.setCharacterEncoding("utf-8");

	String id = request.getParameter("id");
	String pw = request.getParameter("pw");
	
	MemberDAO dao = MemberDAO.getInstance();
	
	int chkNum = dao.userCheck(id, pw);
	
	if(chkNum == MemberDAO.LOGIN_ID_NO) {	
%>
	<script type="text/javascript">
		alert("회원가입된 아이디가 아닙니다.");
		history.back();
	</script>
<%
	} else if(chkNum == MemberDAO.LOGIN_PW_NO) {		
%>	
  	<script type="text/javascript">
		alert("비밀번호가 틀렸습니다.");
		history.back();
	</script>  
<%
	}else {
		MemberVO vo = dao.getMember(id);
		
		String name = vo.getName();
		session.setAttribute("id", id);
		session.setAttribute("name", name);
		session.setAttribute("ValidMem", "yes");
		
		response.sendRedirect("../welcome.jsp");
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