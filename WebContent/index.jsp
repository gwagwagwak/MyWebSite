<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%
	if(session.getAttribute("ValidMem") != null) {
%>
<jsp:forward page="welcome.jsp" />
<%
	}
%>


<jsp:include page="include/header.jsp" />

<br>
<br>

<div class="container">
	<div class="row">
		<div class="box">
			<div class="col-lg-12 text-center">
				<iframe width="1060px" height="700px"
					src="https://www.youtube.com/embed/40Z9-kr504w" frameborder="0"
					allowfullscreen>
				</iframe>
			</div>
		</div>
	</div>
</div>

<section class="page-section cta">
	<div class="container">
		<div class="row">
			<div class="col-xl-9 mx-auto">
				<div class="cta-inner text-center rounded">
					<h2 class="section-heading mb-4">
						<span class="section-heading-upper">Our Promise</span> <span
							class="section-heading-lower">To You</span>
					</h2>
					<p class="mb-0">When you walk into our shop to start your day,
						we are dedicated to providing you with friendly service, a
						welcoming atmosphere, and above all else, excellent products made
						with the highest quality ingredients. If you are not satisfied,
						please let us know and we will do whatever we can to make things
						right!</p>
				</div>
			</div>
		</div>
	</div>
</section>

<jsp:include page="include/footer.jsp" />