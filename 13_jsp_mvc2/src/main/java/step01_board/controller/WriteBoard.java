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

@WebServlet("/bWrite")
public class WriteBoard extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
  
	//게시글 작성화면(유저입장에서 받는거다..?)
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		RequestDispatcher dis = request.getRequestDispatcher("step01_boardEx/bWrite.jsp");
		dis.forward(request, response);
	}

	//게시글 작성화면
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		
		// DTO형태 만들기
		 BoardDTO boardDTO= new BoardDTO();
		//boardDTO.writer = request.getParameter("writer");
		 
		boardDTO.setWriter(request.getParameter("writer"));
		boardDTO.setSubject(request.getParameter("subject"));
		boardDTO.setEmail(request.getParameter("email"));
		boardDTO.setPassword(request.getParameter("password"));
		boardDTO.setContent(request.getParameter("content"));
		
		
		// DAO클래스에 DTO를 전달
		BoardDAO.getInstance().insertBoard(boardDTO);
		
		response.setContentType("text/html; charset=UTF-8");
		
		PrintWriter out = response.getWriter();
		
		//location.href ='url' : 해당 url로 이동한다('jsp에서 이동할 때)
		String jsScript = """ 
				<script>
					alert = ("게시글이 등록되었습니다");
					location.href = 'bList'; 
				</script>
				""";
		
		out.print(jsScript);
		
//		request.getParameter("writer");
//		request.getParameter("subject");
//		request.getParameter("email");
//		request.getParameter("password");
//		request.getParameter("content");
		
	}

}
