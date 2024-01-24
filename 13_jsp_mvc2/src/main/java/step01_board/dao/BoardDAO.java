package step01_board.dao;

import java.sql.Connection;



import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

import step01_board.dto.BoardDTO;


/*

# JSP MVC2 데이터베이스 연동메뉴얼

	1) 데이터베이스 연결 풀링(연결을 미리 만들어 놓고 재사용하여 데이터베이스 연결 및 해제에 따른 오버헤드를 줄임)과 
	   관련된 기능을 사용하고 데이터베이스 애플리케이션의 성능을 향상시키기 위하여 아래의 라이브러리를 WEB-INF/lib 경로에 추가한다.
	
		commons-dbcp2-2.2.0.jar 
		commons-pool2-2.5.0.jar
		mysql-connector-j-8.0.32.jar



	2) 이클립스에서 Servers폴더에 있는 Context.xml (경로설정) 파일에 아래의 설정을 추가한다. 
	
		[ 확인사항 ] 
		- url , name , username , password
	
		<Resource 
			auth="Container" 
			driverClassName="com.mysql.cj.jdbc.Driver"
			type="javax.sql.DataSource"
			url="jdbc:mysql://localhost:3306/MVC2_PRACTICE?serverTimezone=Asia/Seoul&amp;useSSL=false"
			name="jdbc/board" 
			username="root"
			password="1234" 
			loginTimeout="10" 
			maxWait="5000" 
		/> 
		 * 데이터를 더 추가한다면 MVC2_PRACTICE? 변경/name="jdbc/board" 변경


	3) 데이터베이스와 연동하는 메서드를 생성하여 데이터베이스 연결객체를 생성 및 사용한다. 
	
		(패키지)
		import javax.naming.Context;
		import javax.naming.InitialContext;
		import javax.sql.DataSource;
		
		(연결코드)
		Context initctx = new InitialContext();						 // 데이터베이스와 연결하기 위한 init객체 생성
		Context envctx = (Context) initctx.lookup("java:comp/env");  // lookup 메서드를 통해 context.xml 파일에 접근하여 자바환경 코드를 검색    
		DataSource ds = (DataSource) envctx.lookup("jdbc/board"); 	 // <Context>태그안의 <Resource> 환경설정의 name이 jdbc/board인 것을 검색(SQL하고 다른거 연결할 때 바꿔줘야함)	  
		conn = ds.getConnection();

*/


//DAO (Data Access Object) : 데이터베이스와의 통신 및 데이터베이스 관련 작업을 처리하는 데 사용하는 객체
public class BoardDAO {
	
	/*
 	
 	# 싱글턴 디자인 패턴
  
	단일 인스턴스 유지, 상태 공유, 전역 접근 지점, 어플리케이션의 상태 관리 및 메모리 최적화와 같은 이점을 활용하여
	어플리케이션의 구조를 더 효율적으로 관리하고 유지보수하기 쉽게 개발할 수 있다.

*/

	  private BoardDAO() {} 
	  private static BoardDAO instance = new BoardDAO();
	  public static BoardDAO getInstance() {
		  return instance; 
		  }
	 
	
	//데이터베이스 연동 객체 생성
	private Connection conn = null;
	private PreparedStatement pstmt = null;
	private ResultSet rs = null;

	//데이터베이스 연동메서드 생성
	private void getConnection() {
		
		try {
			
		Context initctx = new InitialContext();						
		Context envctx = (Context) initctx.lookup("java:comp/env");      
		DataSource ds = (DataSource) envctx.lookup("jdbc/board"); 	 //  envctx.lookup("이건연습임"); DB연결할때 SERVER에서 context.xml확장에서 변경해줘야함 
		conn = ds.getConnection();
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}
	//데이터베이스 해지메서드 생성
private void getClose() {
		
		if(rs != null)    try {rs.close();} catch (SQLException e) {e.printStackTrace();}
		if(pstmt != null) try {pstmt.close();} catch (SQLException e) {e.printStackTrace();}
		if(conn != null)  try {conn.close();} catch (SQLException e) {e.printStackTrace();}}

  public void insertBoard(BoardDTO boardDTO) {
		
		try {
			
			getConnection();	// DB 연결
			
			String sql = """ 
				INSERT INTO BOARD (WRITER , PASSWORD , EMAIL , SUBJECT , CONTENT , READ_CNT , ENROLL_DT ) 
				VALUES (?,?,?,?,?,0,NOW()) """;
			
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, boardDTO.getWriter());
			pstmt.setString(2, boardDTO.getPassword());
			pstmt.setString(3, boardDTO.getEmail());
			pstmt.setString(4, boardDTO.getSubject());
			pstmt.setString(5, boardDTO.getContent());
			
			pstmt.executeUpdate();
			
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			getClose();			// DB 연결 해제
		}
		
	}
	
			
		//**이부분 중요 흐름 알기!!
  public ArrayList<BoardDTO> getBoardList() {
		
		ArrayList<BoardDTO> boardList = new ArrayList<BoardDTO>();
		
		try {
			
			getConnection();
			
			pstmt = conn.prepareStatement("SELECT * FROM BOARD");
			rs = pstmt.executeQuery();
			
			while (rs.next()) {
				BoardDTO boardDTO = new BoardDTO();
				boardDTO.setBoardId(rs.getLong("BOARD_ID"));
				boardDTO.setWriter(rs.getString("WRITER"));
				boardDTO.setSubject(rs.getString("SUBJECT"));
				boardDTO.setEnrollDt(rs.getDate("ENROLL_DT"));
				boardDTO.setReadCnt(rs.getLong("READ_CNT"));
				boardList.add(boardDTO);
			}
			
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			getClose();
		}
		//단위 테스트(중간테스트해보기)
		//System.out.println(boardList);
		
		return boardList;
		
	}
  
  	public BoardDTO getBoardDetail(long boardId){
  		
  		BoardDTO boardDTO = new BoardDTO();// 담을 수 있는 객체 생성
  		
          try {
			
			getConnection();	// DB 연결
			
			 pstmt = conn.prepareStatement("UPDATE BOARD SET READ_CNT = READ_CNT +1 WHERE BOARD_ID=?");
			 pstmt.setLong(1, boardId);
			 pstmt.executeUpdate();
			 
			pstmt = conn.prepareStatement("SELECT * FROM BOARD WHERE BOARD_ID = ?");
			pstmt.setLong(1, boardId);
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				boardDTO.setBoardId(boardId);
				boardDTO.setWriter(rs.getString("WRITER"));
				boardDTO.setEmail(rs.getString("EMAIL"));
				boardDTO.setSubject(rs.getString("SUBJECT"));
				boardDTO.setContent(rs.getString("CONTENT"));
				boardDTO.setReadCnt(rs.getLong("READ_CNT"));
				boardDTO.setEnrollDt(rs.getDate("ENROLL_DT"));
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			getClose();			// DB 연결 해제
		}
  		
  		//System.out.println(boardDTO);
  		
  		return boardDTO;
  	}
  	//인증제 맞다 아니다?
  	public boolean checkAuthorizedUser(BoardDTO boardDTO) {
		
  		boolean isAuthorizedUser = false;
  		
  		try {
			getConnection();
			String sql = """
					SELECT * FROM BOARD WHERE BOARD_ID=? AND PASSWORD = ?
					""";
			pstmt = conn.prepareStatement(sql);
			pstmt.setLong(1, boardDTO.getBoardId());// boardDTO에서 넘어온 boardId
			pstmt.setString(2, boardDTO.getPassword());
			
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				isAuthorizedUser = true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			
		}
  		
  		// 중간테스트
  		System.out.println(isAuthorizedUser);
  		return isAuthorizedUser;
		
		
	}
  	
	
}
