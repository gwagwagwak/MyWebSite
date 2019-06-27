/**
 * 
 */

function infoConfirm() {
	if(document.reg_form.id.value.length == 0) {
		alert("아이디는 필수사항입니다.");
		reg_form.id.focus();
		return;
	}
	
	if(document.reg_form.id.value.length < 4) {
		alert("아이디는 4글자 이상이어야합니다.");
		reg_form.id.focus();
		return;
	}
	
	if(document.reg_form.pw.value.length == 0) {
		alert("비밀번호는 필수사항입니다.");
		reg_form.pw.focus();
		return;
	}
	
	//비밀번호 확인 체크
	if(document.reg_form.pw.value != document.reg_form.pw_check.value) {
		alert("비밀번호가 일치하지 않습니다.");
		reg_form.pw.focus();
		return;
	}
	
	if(document.reg_form.email.value.length == 0) {
		alert("이메일은 필수사항입니다.");
		reg_form.email.focus();
		return;
	}
	
	if(document.reg_form.name.value.length == 0) {
		alert("이름은 필수사항입니다.");
		reg_form.name.focus();
		return;
	}
	
	document.reg_form.submit();
}

function updateInfoConfirm() {
	if(document.reg_form.pw.value.length == 0) {
		alert("비밀번호는 필수사항입니다.");
		reg_form.pw.focus();
		return;
	}
	
	//비밀번호 확인 체크
	if(document.reg_form.pw.value != document.reg_form.pw_check.value) {
		alert("비밀번호가 일치하지 않습니다.");
		reg_form.pw.focus();
		return;
	}
	
	if(document.reg_form.email.value.length == 0) {
		alert("이메일은 필수사항입니다.");
		reg_form.email.focus();
		return;
	}
	
	document.reg_form.submit();	
}



