package step03_boardAdvanced.controller;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import step03_boardAdvanced.dao.BoardAdvancedDAO;
import step03_boardAdvanced.dto.MainBoardDTO;

@WebServlet("/boardList")
public class BoardList extends HttpServlet {
	
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String searchKeyword = request.getParameter("searchKeyword");
		if (searchKeyword == null) searchKeyword = "total";
		
		String searchWord = request.getParameter("searchWord");
		if (searchWord == null) searchWord = "";
		
		
		// 화면에 보여질 게시글의 개수를 지정
		int onePageViewCnt = 10;
		
		if (request.getParameter("onePageViewCnt") != null) {
			onePageViewCnt = Integer.parseInt(request.getParameter("onePageViewCnt"));
		}
		
		// 현재 보여지고 있는 페이지
		String temp = request.getParameter("currentPageNumber");
		// null 처리
		if (temp == null) {
			temp = "1";
		}
		// 현재 보여지고 있는 페이지 문자를 숫자로 형변환
		int currentPageNumber = Integer.parseInt(temp);
		
		// 전체 게시글의 개수
		int allBoardCnt = BoardAdvancedDAO.getInstance().getAllBoardCnt(searchKeyword , searchWord);
		
		int allPageCnt = allBoardCnt / onePageViewCnt + 1;
		
		if (allBoardCnt % onePageViewCnt == 0) allPageCnt--;
		
		int startPage = (currentPageNumber - 1)  / 10  * 10 + 1;
		if (startPage == 0) {
			startPage = 1;
		}
		
		int endPage = startPage + 9;
		
		if (endPage > allPageCnt) endPage = allPageCnt;
		
		
		// 현재 보여질 페이지 시작 번호를 설정
		int startBoardIdx = (currentPageNumber - 1) * onePageViewCnt;
		
		ArrayList<MainBoardDTO> boardList = BoardAdvancedDAO.getInstance().getBoardList(searchKeyword, searchWord, startBoardIdx, onePageViewCnt); //getBoardList(startRow , onePageViewCnt , searchKeyword , searchWord);
		request.setAttribute("boardList"        , boardList); 
		request.setAttribute("onePageViewCnt" , onePageViewCnt);
		request.setAttribute("allBoardCnt"    , allBoardCnt);
		request.setAttribute("startBoardIdx"    , startBoardIdx);
		request.setAttribute("currentPageNumber"   , currentPageNumber);
		request.setAttribute("startPage"        , startPage);
		request.setAttribute("endPage"          , endPage);
		request.setAttribute("allPageCnt"    , allPageCnt);
		request.setAttribute("searchKeyword"    , searchKeyword);
		request.setAttribute("searchWord"       , searchWord);

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		request.setAttribute("today", sdf.format(new Date()));
		
		
		RequestDispatcher dis = request.getRequestDispatcher("step03_boardAdvancedEx/board/boardList.jsp");
		dis.forward(request, response);

	}

}
