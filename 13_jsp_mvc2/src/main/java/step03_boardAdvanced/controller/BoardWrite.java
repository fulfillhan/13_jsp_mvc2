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

@WebServlet("/boardWrite")
public class BoardWrite extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		RequestDispatcher dis = request.getRequestDispatcher("step03_boardAdvancedEx/board/boardWrite.jsp");
		dis.forward(request, response);
		
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		request.setCharacterEncoding("utf-8");
		
		MainBoardDTO mainBoardDTO = new MainBoardDTO();
		mainBoardDTO.setWriter(request.getParameter("writer"));
		mainBoardDTO.setSubject(request.getParameter("subject"));
		mainBoardDTO.setContent(request.getParameter("content"));
		mainBoardDTO.setPasswd(request.getParameter("passwd"));

		BoardAdvancedDAO.getInstance().insertBoard(mainBoardDTO);
		
		String jsScript = "<script>";
		   jsScript += "alert('게시글이 등록되었습니다.');";
		   jsScript += "location.href='boardList';";
		   jsScript += "</script>";
	
		   response.setContentType("text/html; charset=utf-8");
		   PrintWriter out = response.getWriter();	
		   out.print(jsScript);
		
	}
	
}
