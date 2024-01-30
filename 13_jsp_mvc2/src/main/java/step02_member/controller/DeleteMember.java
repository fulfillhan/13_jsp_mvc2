package step02_member.controller;

import java.io.File;
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

@WebServlet("/deleteMember")
public class DeleteMember extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
	
	private String profileRepositoryPath = FileConfig.PROFILE_REPOSITORY_PATH;
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		HttpSession session = request.getSession();
		request.setAttribute("memberId", (String)session.getAttribute("memberId"));
		
		RequestDispatcher dis = request.getRequestDispatcher("step02_memberEx/mDelete.jsp");
		dis.forward(request, response);
	
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		HttpSession session = request.getSession();
		String profileUID = MemberDAO.getInstance().getMemberDetail((String)session.getAttribute("memberId")).getProfileUUID();
		
		new File(profileRepositoryPath + profileUID).delete();
		
		MemberDAO.getInstance().deleteMember((String)session.getAttribute("memberId"));

		session.invalidate();
		
	    String jsScript = """
				<script>
					alert('회원탈퇴 되었습니다.');
				  location.href = 'mainMember';
			    </script>""";
	   
		response.setContentType("text/html; charset=utf-8");
		PrintWriter out = response.getWriter();	
		out.print(jsScript);

	}
	
}
