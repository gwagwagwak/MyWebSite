package kr.co.koo.board.controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import kr.co.koo.board.service.BoardContentService;
import kr.co.koo.board.service.BoardDeleteService;
import kr.co.koo.board.service.BoardListService;
import kr.co.koo.board.service.BoardReplyService;
import kr.co.koo.board.service.BoardReplyViewService;
import kr.co.koo.board.service.BoardUpdateService;
import kr.co.koo.board.service.BoardWriteService;
import kr.co.koo.board.service.IBoardService;

/**
 * Servlet implementation class BoardController
 */
@WebServlet("*.do")
public class BoardController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public BoardController() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		actionDo(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		actionDo(request, response);
	}

	private void actionDo(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		request.setCharacterEncoding("utf-8");
		
		String uri = request.getRequestURI();
		String conPath = request.getContextPath();
		String cmd = uri.substring(conPath.length());
		
		IBoardService service = null;
		String viewPage = null;
		
		if(cmd.equals("/write_view.do")) {
			viewPage = "write_view.jsp";
			
		}else if(cmd.equals("/list.do")) {
			service = new BoardListService();
			service.execute(request, response);
			viewPage = "list.jsp";
		}else if(cmd.equals("/write.do")) {
			service = new BoardWriteService();
			service.execute(request, response);
			viewPage = "list.do";
		}else if(cmd.equals("/content_view.do")) {
			service = new BoardContentService();
			service.execute(request, response);
			viewPage = "content_view.jsp";
		}else if(cmd.equals("/delete.do")) {
			service = new BoardDeleteService();
			service.execute(request, response);
			viewPage = "list.do";
			
		}else if(cmd.equals("/update.do")) {
			service = new BoardUpdateService();
			service.execute(request, response);
			viewPage = "list.do";
			
		}else if(cmd.equals("/reply_view.do")) {
			service = new BoardReplyViewService();
			service.execute(request, response);
			viewPage = "reply_view.jsp";
			
		}else if(cmd.equals("/reply.do")) {
			service = new BoardReplyService();
			service.execute(request, response);
			viewPage = "list.do";
		}
		
		/*
		 * 자바코드로 포워딩 처리
		 * 1. RequestDispatcher라는 클래스 사용
		 * 2. request.getRequestDispatcher() 메서드로
		 *    객체 생성
		 * 3. 이 객체를 통해 forward()를 호출. 
		 */
		RequestDispatcher dp = 
				request.getRequestDispatcher(viewPage);
		dp.forward(request, response);
	}

}
