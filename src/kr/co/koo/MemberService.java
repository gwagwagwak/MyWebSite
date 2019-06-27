package kr.co.koo;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import kr.co.koo.members.MemberDAO;
import kr.co.koo.members.MemberVO;

public class MemberService {
	
	public ArrayList<MemberVO> getMembersAll(HttpServletRequest request, HttpServletResponse response) {
		
		MemberDAO dao = MemberDAO.getInstance();
		return dao.membersAll();
	}
	
}





