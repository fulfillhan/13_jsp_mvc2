package step03_boardAdvanced.controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import step03_boardAdvanced.dao.BoardAdvancedDAO;


@WebServlet("/boardDetail")
public class BoardDetail extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		long boardId = Long.parseLong(request.getParameter("boardId"));
		request.setAttribute("mainBoardDTO", BoardAdvancedDAO.getInstance().getBoardDetail(boardId));
		request.setAttribute("allReplyCnt", BoardAdvancedDAO.getInstance().getAllReplyCnt(boardId));
		request.setAttribute("replyList", BoardAdvancedDAO.getInstance().getReplyList(boardId));
		
		RequestDispatcher dis = request.getRequestDispatcher("step03_boardAdvancedEx/board/boardDetail.jsp");
		dis.forward(request, response);
	
	}

}
