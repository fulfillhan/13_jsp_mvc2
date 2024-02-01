package step03_boardAdvanced.controller;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import step03_boardAdvanced.dao.BoardAdvancedDAO;

@WebServlet("/boardSetDummy")
public class BoardSetDummy extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
       
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	
		BoardAdvancedDAO.getInstance().setDummy();
		
		String jsScript = "<script>";
			   jsScript += "alert('테스트 데이터가 생성되었습니다.');";
			   jsScript += "location.href='boardList';";
			   jsScript += "</script>";
		
		response.setContentType("text/html; charset=utf-8");
		PrintWriter out = response.getWriter();	
		out.print(jsScript);
		
	}


}
