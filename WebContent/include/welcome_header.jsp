<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
	if (session.getAttribute("ValidMem") == null) {
%>
<jsp:forward page="index.jsp" />
<%
	}
	String name = (String) session.getAttribute("name");
	String id = (String) session.getAttribute("id");
%>
<!DOCTYPE html>
<html>

<head>

<meta charset="utf-8">
<meta name="viewport"
	content="width=device-width, initial-scale=1, shrink-to-fit=no">
<meta name="description" content="">
<meta name="author" content="">

<title>Business Casual - Start Bootstrap Theme</title>

<!-- Bootstrap core CSS -->
<link href="vendor/bootstrap/css/bootstrap.css" rel="stylesheet">

<!-- Custom fonts for this template -->
<link
	href="https://fonts.googleapis.com/css?family=Raleway:100,100i,200,200i,300,300i,400,400i,500,500i,600,600i,700,700i,800,800i,900,900i"
	rel="stylesheet">
<link
	href="https://fonts.googleapis.com/css?family=Lora:400,400i,700,700i"
	rel="stylesheet">

<!-- Custom styles for this template -->
<link href="css/business-casual.css" rel="stylesheet">

</head>

<body>
	<!-- Modal -->
	<div class="modal fade" id="myModal" role="dialog">
		<div class="modal-dialog">

			<!-- Modal content-->
			<div class="modal-content">
				<div class="modal-header">
					<h4 class="modal-title">My World 회원 가입</h4>
					<button type="button" class="close" data-dismiss="modal">×</button>

				</div>

				<div class="modal-body">

					<form action="member/joinOk.jsp" method="post"
						class="form-horizontal">

						<div class="form-group">
							<label for="inputId3" class="col-sm-3 control-label">* ID</label>
							<div class="col-sm-6">
								<input type="text" class="form-control" id="inputId3" name="id">
							</div>

						</div>

						<div class="form-group">
							<label for="inputPassword3" class="col-sm-3 control-label">*
								비밀번호</label>
							<div class="col-sm-6">
								<input type="password" class="form-control" id="inputPassword3"
									name="pw" placeholder="8자 이상으로 입력하세요.">
							</div>
						</div>

						<div class="form-group">
							<label for="inputPassword3" class="col-sm-3 control-label">*
								비밀번호 확인</label>
							<div class="col-sm-6">
								<input type="password" class="form-control"
									id="inputPassword3-confirm" placeholder="위와 동일한 비밀번호를 입력하세요.">
							</div>
						</div>

						<div class="form-group">
							<label for="inputName3" class="col-sm-3 control-label">*
								이름</label>
							<div class="col-sm-6">
								<input type="text" class="form-control" id="inputName3"
									name="name">
							</div>
						</div>

						<div class="form-group">
							<label for="inputaddr3" class="col-sm-3 control-label">*
								주소</label>
							<div class="col-sm-6">
								<input type="text" class="form-control" id="inputaddr3"
									name="address">
							</div>
						</div>



						<div class="form-group">
							<label for="inputEmail3" class="col-sm-3 control-label">*
								이메일</label>
							<div class="col-sm-6">
								<input type="text" class="form-control" id="inputEmail3"
									name="email" placeholder="example@myworld.com">
							</div>
						</div>



						<div class="modal-footer">
							<input type="submit" class="btn btn-default" value="완료"
								onclick="return confirm('회원 가입을 완료하시겠습니까?')">
							<button type="button" class="btn btn-default"
								data-dismiss="modal">닫기</button>
						</div>
					</form>
				</div>
			</div>
		</div>
	</div>



	<div style="float: right">
		


		<form action="member/logout.jsp" method="post">
			<div class="form-group">
			<p style="color: white; float: right; font-size: 10">${name }(${id}) 님 안녕하세요~
			</p>
			</div>
			<div class="form-group" style="float: right">
				<input type="submit" value="로그아웃" class="btn btn-default">&nbsp;
			</div>
			<div class="form-group" style="float: right">
				<input type="button" value="정보수정" class="btn btn-default"
					onclick="javascript:window.location='update.jsp'">&nbsp;
			</div>
			<div class="form-group" style="float: right">
				<input type="button" value="회원탈퇴" class="btn btn-default"
					onclick="javascript:window.location='delete.jsp'">
			</div>
		</form>
	</div>
	
	<br>


	<h1 class="site-heading text-center text-white d-none d-lg-block">
		<span class="site-heading-upper text-primary mb-3">안녕?</span> <span
			class="site-heading-lower">My World</span>
	</h1>

	<!-- Navigation -->
	<nav class="navbar navbar-expand-lg navbar-dark py-lg-4" id="mainNav">
		<div class="container">
			<a
				class="navbar-brand text-uppercase text-expanded font-weight-bold d-lg-none"
				href="#">Start Bootstrap</a>
			<button class="navbar-toggler" type="button" data-toggle="collapse"
				data-target="#navbarResponsive" aria-controls="navbarResponsive"
				aria-expanded="false" aria-label="Toggle navigation">
				<span class="navbar-toggler-icon"></span>
			</button>
			<div class="collapse navbar-collapse" id="navbarResponsive">
				<ul class="navbar-nav mx-auto">
					<li class="nav-item active px-lg-4"><a
						class="nav-link text-uppercase text-expanded" href="index.html">Home
							<span class="sr-only">(current)</span>
					</a></li>
					<li class="nav-item px-lg-4"><a
						class="nav-link text-uppercase text-expanded" href="about.html">About</a>
					</li>
					<li class="nav-item px-lg-4"><a
						class="nav-link text-uppercase text-expanded" href="guestbook/list.jsp">GuestBook</a>
					</li>
					<li class="nav-item px-lg-4"><a
						class="nav-link text-uppercase text-expanded" href="list.do">Board</a>
					</li>
					<li class="nav-item px-lg-4"><a
						class="nav-link text-uppercase text-expanded" data-toggle="modal"
						href="#myModal">JOIN</a></li>

				</ul>
			</div>
		</div>
	</nav>
	<!-- 여기까지가 헤더 부분입니다. -->