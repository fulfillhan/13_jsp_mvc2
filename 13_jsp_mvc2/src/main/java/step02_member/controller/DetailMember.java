package step02_member.controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import step02_member.dao.MemberDAO;

@WebServlet("/detailMember")
public class DetailMember extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
       
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	
		HttpSession session = request.getSession();
		request.setAttribute("memberDTO", MemberDAO.getInstance().getMemberDetail((String)session.getAttribute("memberId")));
		
		RequestDispatcher dis = request.getRequestDispatcher("step02_memberEx/mDetail.jsp");
		dis.forward(request, response);
		
	}


}
