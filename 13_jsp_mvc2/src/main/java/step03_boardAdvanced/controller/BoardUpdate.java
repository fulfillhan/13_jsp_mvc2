package step03_boardAdvanced.controller;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import step03_boardAdvanced.dao.BoardAdvancedDAO;
import step03_boardAdvanced.dto.MainBoardDTO;


@WebServlet("/boardUpdate")
public class BoardUpdate extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		request.setAttribute("mainBoardDTO" , BoardAdvancedDAO.getInstance().getBoardDetail(Long.parseLong(request.getParameter("boardId"))));
		
		RequestDispatcher dis = request.getRequestDispatcher("step03_boardAdvancedEx/board/boardUpdate.jsp");
		dis.forward(request, response);
		
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	
		request.setCharacterEncoding("UTF-8");
		
		MainBoardDTO mainBoardDTO = new MainBoardDTO();
		mainBoardDTO.setBoardId(Long.parseLong(request.getParameter("boardId")));
		mainBoardDTO.setWriter(request.getParameter("writer"));// 왜 포함되어있는거지...
		mainBoardDTO.setSubject(request.getParameter("subject"));
		mainBoardDTO.setContent(request.getParameter("content"));
		
		BoardAdvancedDAO.getInstance().updateBoard(mainBoardDTO);
		
		String jsScript = "<script>";
			   jsScript += "alert('수정 되었습니다.');";
			   jsScript += "location.href='boardList';";
			   jsScript += "</script>";
	
		response.setContentType("text/html; charset=utf-8");
		PrintWriter out = response.getWriter();	
		out.print(jsScript);
		
	}
	

}
