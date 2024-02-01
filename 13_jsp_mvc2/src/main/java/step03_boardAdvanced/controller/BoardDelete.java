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

@WebServlet("/boardDelete")
public class BoardDelete extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		request.setAttribute("boardId" , request.getParameter("boardId"));
		
		RequestDispatcher dis = request.getRequestDispatcher("step03_boardAdvancedEx/board/boardDelete.jsp");
		dis.forward(request, response);
		
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		BoardAdvancedDAO.getInstance().deleteBoard(Long.parseLong(request.getParameter("boardId")));
		
		String jsScript = "<script>";
			   jsScript += "alert('삭제 되었습니다.');";
			   jsScript += "location.href='boardList';";
			   jsScript += "</script>";
	
		response.setContentType("text/html; charset=utf-8");
		PrintWriter out = response.getWriter();	
		out.print(jsScript);
		
	}
	
	
}
