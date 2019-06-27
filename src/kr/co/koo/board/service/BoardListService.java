package kr.co.koo.board.service;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import kr.co.koo.board.model.BoardDAO;
import kr.co.koo.board.model.BoardVO;

public class BoardListService implements IBoardService {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) {
		
		BoardDAO dao = BoardDAO.getInstance();
		ArrayList<BoardVO> lists = dao.list();
		request.setAttribute("list", lists);
	}

}
