package step01_board.controller;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import step01_board.dao.BoardDAO;
import step01_board.dto.BoardDTO;


@WebServlet("/bAuthentication")
public class AuthenticationBoard extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
   
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		BoardDTO boardDTO = BoardDAO.getInstance().getBoardDetail(Long.parseLong(request.getParameter("boardId")));
		request.setAttribute("boardDTO", boardDTO);
		
		RequestDispatcher dis = request.getRequestDispatcher("step01_boardEx/bAuthentication.jsp");
		dis.forward(request, response); //화면 이동
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// 아이디랑 비번이 맞는지 확인
		
		// 아이디 비번 받기
		//1.dto로 묶기
		BoardDTO boardDTO = new BoardDTO();
		boardDTO.setBoardId(Long.parseLong(request.getParameter("boardId")));
		boardDTO.setPassword(request.getParameter("password"));
		
		// dao로 보내기
		//boolean = BoardDAO.getInstance().checkAuthorizedUser(boardDTO);// 이아이디 인증해주기!
        
		String jsScript="";
		if (BoardDAO.getInstance().checkAuthorizedUser(boardDTO)) {
			// 리액션 
			jsScript = "<script>";
			//jsScript += "location.href='bDelete?boardId=";		
			jsScript += "alert('ok');";		
			jsScript += "</script>";		
		}
		else {
			// 리액션
			jsScript = "<script>";
			//jsScript += "location.href='bDelete?boardId=";		
			jsScript += "alert('nok');";		
			jsScript += "</script>";	
		}
		
		PrintWriter out = response.getWriter();
		out.print(jsScript);
	
	}

}
