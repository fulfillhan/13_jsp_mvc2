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
import step03_boardAdvanced.dto.ReplyDTO;

@WebServlet("/replyUpdate")
public class ReplyUpdate extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
       
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		request.setAttribute("replyDTO", BoardAdvancedDAO.getInstance().getReplyDetail(Long.parseLong(request.getParameter("replyId"))));
		
		RequestDispatcher dis = request.getRequestDispatcher("step03_boardAdvancedEx/reply/replyUpdate.jsp");
		dis.forward(request, response);
		
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		request.setCharacterEncoding("utf-8");
		
		ReplyDTO replyDTO = new ReplyDTO();
		replyDTO.setReplyId(Long.parseLong(request.getParameter("replyId")));
		replyDTO.setBoardId(Long.parseLong(request.getParameter("boardId")));
		replyDTO.setContent(request.getParameter("content"));
		replyDTO.setPasswd(request.getParameter("passwd"));

		String jsScript = "";
		if (BoardAdvancedDAO.getInstance().updateReply(replyDTO)) {
			jsScript = "<script>";
			jsScript += "alert('수정 되었습니다.');";
			jsScript += "location.href='boardDetail?boardId=" + replyDTO.getBoardId()+"'";
			jsScript += "</script>";
			
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
