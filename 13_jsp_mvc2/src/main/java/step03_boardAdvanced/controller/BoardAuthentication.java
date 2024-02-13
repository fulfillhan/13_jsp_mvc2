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

@WebServlet("/boardAuthentication")
public class BoardAuthentication extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		request.setAttribute("menu", request.getParameter("menu"));   
		
		long boardId = Long.parseLong(request.getParameter("boardId"));
		request.setAttribute("mainBoardDTO" , BoardAdvancedDAO.getInstance().getBoardDetail(boardId));
		
		RequestDispatcher dis = request.getRequestDispatcher("step03_boardAdvancedEx/board/boardAuthentication.jsp");
		dis.forward(request, response);
		
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	
		request.setCharacterEncoding("utf-8");
		
		//String boardId = request.getParameter("boardId");
		long boardId= Long.parseLong(request.getParameter("boardId"));
		
		MainBoardDTO mainBoardDTO = new MainBoardDTO();
		mainBoardDTO.setBoardId(boardId);
		mainBoardDTO.setPasswd(request.getParameter("passwd"));
	
		String menu = request.getParameter("menu");
		
		
		String jsScript = "";
		if (BoardAdvancedDAO.getInstance().checkAuthorizedUser(mainBoardDTO)) {
			
			if (menu.equals("update")) {
				jsScript = "<script>";
				jsScript += "location.href='boardUpdate?boardId=" + boardId + "';";
				jsScript += "</script>";
			}
			else if (menu.equals("delete")) {
				jsScript = "<script>";
				jsScript += "location.href='boardDelete?boardId=" + boardId + "';";
				jsScript += "</script>";
			}
			
		}
		else {
			 jsScript = "<script>";
			 jsScript += "alert('패스워드를 확인하세요.');";
			 jsScript += "history.go(-1);";
			 jsScript += "</script>";
		}
		
		response.setContentType("text/html; charset=utf-8");
		PrintWriter out = response.getWriter();	
		out.print(jsScript);
	}

}
