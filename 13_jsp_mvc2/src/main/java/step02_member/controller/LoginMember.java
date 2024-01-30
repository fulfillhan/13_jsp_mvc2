package step02_member.controller;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import step02_member.dao.MemberDAO;
import step02_member.dto.MemberDTO;

@WebServlet("/loginMember")
public class LoginMember extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		RequestDispatcher dis = request.getRequestDispatcher("step02_memberEx/mLogin.jsp");
		dis.forward(request, response);
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		MemberDTO memberDTO = new MemberDTO();
		memberDTO.setMemberId(request.getParameter("memberId"));
		memberDTO.setPasswd(request.getParameter("passwd"));
		
		String isAuthorized = "false";
		if (MemberDAO.getInstance().loginMember(memberDTO)) {
			
			HttpSession session = request.getSession();
			session.setAttribute("memberId", request.getParameter("memberId"));			
			isAuthorized = "true";
			
		}
		
		PrintWriter out = response.getWriter();	
		out.print(isAuthorized);

	}

}
