package kr.co.koo.board.service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import kr.co.koo.board.model.BoardDAO;
import kr.co.koo.board.model.BoardVO;

public class BoardReplyViewService implements IBoardService {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) {
		
		String bId = request.getParameter("bId");
		
		BoardDAO dao = BoardDAO.getInstance();
		BoardVO vo = dao.replyView(bId);
		
		request.setAttribute("reply", vo);
	}

}





