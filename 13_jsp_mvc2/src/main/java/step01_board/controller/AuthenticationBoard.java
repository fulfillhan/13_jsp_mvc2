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
		
		request.setAttribute("menu", request.getParameter("menu"));
		
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
		
		 String menu = request.getParameter("menu");
		
		// dao로 보내기
		//boolean = BoardDAO.getInstance().checkAuthorizedUser(boardDTO);// 이아이디 인증해주기!
        
		String jsScript="";
		if (BoardDAO.getInstance().checkAuthorizedUser(boardDTO)) {//아이디 true라면
			if(menu.equals("delete")) {
				
				jsScript = "<script>";
				jsScript += "location.href='bDelete?boardId=" + boardDTO.getBoardId() + "';";	//""이거 잘 보기	
				jsScript += "alert('ok');";		
				jsScript += "</script>";		
			}
			else if (menu.equals("update")) {
				jsScript = "<script>";
				jsScript += "location.href='bUpdate?boardId=" + boardDTO.getBoardId() + "';";	
				jsScript += "alert('ok');";		
				jsScript += "</script>";		
			}
		}
		else {
			/* 
			
			# 한페이지 이전으로 이동하는 자바스크립트 함수
			history.back();
			history.go(-1);
		
			+@)
			history.forward(); 한 페이지 앞으로 이동
			history.go(-2);	   두 페이지 이전으로 이동
			history.go(-3);    세 페이지 이전으로 이동
		*/ 
		
			jsScript = "<script>";
			jsScript += "alert('패스워드를 확인하세요.');";//; 을 잘써야한다.
			jsScript += "history.go(-1);";
			//jsScript += "location.href='bDelete?boardId=";		
			//jsScript += "alert('nok');";		
			jsScript += "</script>";	
		}
		response.setContentType("text/html; charset=UTF-8");
		PrintWriter out = response.getWriter();
		out.print(jsScript);
	
	}

}
