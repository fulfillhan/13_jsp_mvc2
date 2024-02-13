package step03_boardAdvanced.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Random;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

import step03_boardAdvanced.dto.MainBoardDTO;
import step03_boardAdvanced.dto.ReplyDTO;

public class BoardAdvancedDAO {

	private BoardAdvancedDAO() {}
	private static BoardAdvancedDAO instance = new BoardAdvancedDAO();
	public static BoardAdvancedDAO getInstance() {
		return instance;
	}

	private Connection conn         = null;
	private PreparedStatement pstmt = null;
	private ResultSet rs            = null;

	
	private void getConnection() {
		
		try {
			
			/*
			
				이클립스에서 Servers폴더에 있는 Context.xml파일에 아래의 설정 추가 
			
				<Resource 
					auth="Container" 
					driverClassName="com.mysql.cj.jdbc.Driver"
					type="javax.sql.DataSource"
					url="jdbc:mysql://localhost:3306/MVC2_PRACTICE?serverTimezone=Asia/Seoul&amp;useSSL=false"
					name="jdbc/boardAdvanced" 
					username="root"
					password="1234" 
					loginTimeout="10" 
					maxWait="5000" 
				/> 
			
			 */
			
			Context initctx = new InitialContext();
			Context envctx = (Context)initctx.lookup("java:comp/env");
			DataSource ds = (DataSource)envctx.lookup("jdbc/boardAdvanced");
			conn = ds.getConnection();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

	
	private void getClose() {
		
    	if (rs != null)    {try {rs.close();}   catch (SQLException e) {}}
    	if (pstmt != null) {try {pstmt.close();} catch (SQLException e) {}}
        if (conn != null)  {try {conn.close();}  catch (SQLException e) {}}
        
    }

	
	public void insertBoard(MainBoardDTO mainBoardDTO) {

		try {
			
			getConnection();
			
			String sql = """
					INSERT INTO MAIN_BOARD (WRITER , SUBJECT , CONTENT, PASSWD) 
					VALUES (?, ?, ?, ?)
					""";
			
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, mainBoardDTO.getWriter());
			pstmt.setString(2, mainBoardDTO.getSubject());
			pstmt.setString(3, mainBoardDTO.getContent());
			pstmt.setString(4, mainBoardDTO.getPasswd());
			pstmt.executeUpdate();
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			getClose();
		}
		
	}
	
	
	public boolean checkAuthorizedUser(MainBoardDTO mainBoardDTO) {

		boolean isAuthorizedUser = false;
		
		try {
			
			getConnection();
			pstmt = conn.prepareStatement("SELECT * FROM MAIN_BOARD WHERE BOARD_ID = ? AND PASSWD = ?");
			pstmt.setLong(1, mainBoardDTO.getBoardId());
			pstmt.setString(2, mainBoardDTO.getPasswd());
			rs = pstmt.executeQuery();

			if (rs.next()) 	isAuthorizedUser = true;

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			getClose();
		}

		return isAuthorizedUser;
		
	}
	
	
	public void updateBoard(MainBoardDTO mainBoardDTO) {

		
		try {
				
			getConnection();
			
			String sql = """
					UPDATE MAIN_BOARD 
					SET    SUBJECT = ? , 
						   CONTENT = ? 
				    WHERE  BOARD_ID = ?
					""";
			
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, mainBoardDTO.getSubject());
			pstmt.setString(2, mainBoardDTO.getContent());
			pstmt.setLong(3, mainBoardDTO.getBoardId());
			pstmt.executeUpdate();
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			getClose();
		}
		
	}

	
	public void deleteBoard(long boardId) {
		
		try {
				
			getConnection();
			pstmt = conn.prepareStatement("DELETE FROM MAIN_BOARD WHERE BOARD_ID = ?");
			pstmt.setLong(1, boardId);
			pstmt.executeUpdate();
				
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			getClose();
		}
		
	}

	
	public int getAllBoardCnt(String searchKeyword , String searchWord) {
		
		int totalBoardCnt = 0;
		
		try {
			
			getConnection();
			
			String sql = "";
			if (searchKeyword.equals("total")) {
				if (searchWord.equals("")) {
					sql = "SELECT COUNT(*) FROM MAIN_BOARD";
					pstmt = conn.prepareStatement(sql);
				}
				else {
					sql = """
							SELECT COUNT(*) 
							FROM   MAIN_BOARD
							WHERE  SUBJECT LIKE CONCAT('%', ? , '%')
							OR     WRITER LIKE CONCAT('%', ? , '%') """; 
					pstmt = conn.prepareStatement(sql);
					pstmt.setString(1, searchWord);
					pstmt.setString(2, searchWord);
				}
				
			}
			else { 
				sql = "SELECT COUNT(*) " + 
					  "FROM   MAIN_BOARD " + 
					  "WHERE " + searchKeyword + " LIKE CONCAT('%', ? , '%')"; 
				pstmt = conn.prepareStatement(sql);
				pstmt.setString(1, searchWord);
			}
			
			rs = pstmt.executeQuery();
			
			if (rs.next()) {// 행이 하나 이상 존재한다면
				totalBoardCnt = rs.getInt(1);//첫번째 열을 가지고온다. 총 게시글 수
			} 
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			getClose();
		}
		
		return totalBoardCnt;
		
	}

	
	public ArrayList<MainBoardDTO> getBoardList(String searchKeyword, String searchWord,  int startBoardIdx, int onePageViewCnt) {

		ArrayList<MainBoardDTO> boardList = new ArrayList<MainBoardDTO>();
		
		try {
			
			getConnection();
			
			String sql = "";
			
			if (searchKeyword.equals("total")) { 
				if (searchWord.equals("")) {  
					sql = """
							SELECT    * 
							FROM     MAIN_BOARD 
							ORDER BY ENROLL_AT 
							DESC     LIMIT ? , ? """;
					pstmt = conn.prepareStatement(sql);
					pstmt.setInt(1, startBoardIdx);
					pstmt.setInt(2, onePageViewCnt);
				}
				else {	
					sql = """
							SELECT   * 
							FROM     MAIN_BOARD
							WHERE    SUBJECT LIKE CONCAT('%', ? ,'%')
							OR       WRITER LIKE CONCAT('%', ? ,'%')
							ORDER BY ENROLL_AT DESC
							LIMIT    ?,? """;
					pstmt = conn.prepareStatement(sql);
					pstmt.setString(1, searchWord);
					pstmt.setString(2, searchWord);
					pstmt.setInt(3, startBoardIdx);
					pstmt.setInt(4, onePageViewCnt);
					
				}
				
			}
			else {
				sql = "SELECT * FROM MAIN_BOARD " + 
					  "WHERE " + searchKeyword + " LIKE CONCAT('%', ? ,'%')" + 
					  "ORDER BY ENROLL_AT " +  
					  "DESC LIMIT ? , ? "; 
				
				
				pstmt = conn.prepareStatement(sql);
				pstmt.setString(1, searchWord);
				pstmt.setInt(2, startBoardIdx);
				pstmt.setInt(3, onePageViewCnt);
			}


			rs = pstmt.executeQuery();

			while (rs.next()) {
				
				MainBoardDTO mainBoardDTO = new MainBoardDTO();
				mainBoardDTO.setBoardId(rs.getLong("BOARD_ID"));
				mainBoardDTO.setWriter(rs.getString("WRITER"));
				mainBoardDTO.setSubject(rs.getString("SUBJECT"));
				mainBoardDTO.setEnrollAt(rs.getDate("ENROLL_AT"));
				mainBoardDTO.setReadCnt(rs.getLong("READ_CNT"));
				boardList.add(mainBoardDTO);
				
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			getClose();
		}
		
		return boardList;
		
	}
	
	
	public MainBoardDTO getBoardDetail(long boardId) {

		MainBoardDTO mainBoardDTO = new MainBoardDTO();

		try {
			
			getConnection();
			
			String sql = """
					UPDATE  MAIN_BOARD 
					SET 	READ_CNT = READ_CNT + 1 
					WHERE   BOARD_ID = ?
					""";
			
			pstmt = conn.prepareStatement(sql);
			pstmt.setLong(1, boardId);
			pstmt.executeUpdate();
			
			sql = """
					SELECT 	* 
					FROM 	MAIN_BOARD 
					WHERE 	BOARD_ID = ?
					""";
			
			pstmt = conn.prepareStatement(sql);
			pstmt.setLong(1, boardId);

			rs = pstmt.executeQuery();
			
			if (rs.next()) {
				mainBoardDTO.setBoardId(rs.getLong("BOARD_ID"));
				mainBoardDTO.setWriter(rs.getString("WRITER"));
				mainBoardDTO.setSubject(rs.getString("SUBJECT"));
				mainBoardDTO.setEnrollAt(rs.getDate("ENROLL_AT"));
				mainBoardDTO.setReadCnt(rs.getLong("READ_CNT"));
				mainBoardDTO.setContent(rs.getString("CONTENT"));
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			getClose();
		}
		
		return mainBoardDTO;
	
	}


	public void insertReply(ReplyDTO replyDTO) {
		 
		try {

			getConnection();
			String sql = """
					INSERT INTO REPLY_BOARD (WRITER, CONTENT, PASSWD , BOARD_ID) 
					VALUES 					(? , ? , ?  , ?)
					""";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, replyDTO.getWriter());
			pstmt.setString(2, replyDTO.getContent());
			pstmt.setString(3, replyDTO.getPasswd());
			pstmt.setLong(4, replyDTO.getBoardId());
			pstmt.executeUpdate();

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			getClose();
		}

	}
	
	
	public boolean checkValidMember(ReplyDTO replyDTO) {

		boolean isValidMember = false;
		
		try {
			
			getConnection();
			
			String sql = """
					SELECT 	* 
					FROM 	REPLY_BOARD 
					WHERE 	REPLY_ID = ? AND PASSWD = ?
					""";
			
			pstmt = conn.prepareStatement(sql);
			pstmt.setLong(1, replyDTO.getReplyId());
			pstmt.setString(2, replyDTO.getPasswd());
			rs = pstmt.executeQuery();

			if (rs.next()) 	isValidMember = true;

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			getClose();
		}

		return isValidMember;
		
	}
	
	
	public boolean updateReply(ReplyDTO replyDTO) {

		boolean isUpdate = false;
		
		try {
			
			if (checkValidMember(replyDTO)) {
				
				getConnection();
				
				String sql = """
						UPDATE REPLY_BOARD 
						SET    CONTENT =? , 
							   ENROLL_AT = NOW() 
						WHERE  REPLY_ID = ?
						""";
				
				pstmt = conn.prepareStatement(sql);
				pstmt.setString(1, replyDTO.getContent());
				pstmt.setLong(2, replyDTO.getReplyId());
				pstmt.executeUpdate();
				isUpdate = true;
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			getClose();
		}
		
		return isUpdate;
		
	}

	
	public boolean deleteReply(ReplyDTO replyDTO) {

		boolean isDelete = false;
		
		try {
			
			if (checkValidMember(replyDTO)) {
				
				getConnection();
				pstmt = conn.prepareStatement("DELETE FROM REPLY_BOARD WHERE REPLY_ID = ?");
				pstmt.setLong(1, replyDTO.getReplyId());
				pstmt.executeUpdate();
				isDelete = true;
				
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			getClose();
		}
		
		return isDelete;
		
	}
	
	
	public int getAllReplyCnt(long boardId) {
		
		int totalReplyCnt = 0;
		
		try {
			
			getConnection();
			String sql = """
					SELECT COUNT(*) 
					FROM   REPLY_BOARD 
					WHERE  BOARD_ID = ?
					""";
			
			pstmt = conn.prepareStatement(sql);
			pstmt.setLong(1, boardId);
			rs = pstmt.executeQuery();
			
			if (rs.next()) {
				totalReplyCnt = rs.getInt(1);
			} 
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			getClose();
		}
		
		return totalReplyCnt;
		
	}

	
	public ArrayList<ReplyDTO> getReplyList(long boardId) {
		 
		ArrayList<ReplyDTO> replyList = new ArrayList<ReplyDTO>();
		
		try {

			getConnection();

		    String sql = """
					SELECT 	 * 
					FROM   	 REPLY_BOARD 
					WHERE  	 BOARD_ID = ?
		   			ORDER BY ENROLL_AT DESC
					""";
					
			pstmt = conn.prepareStatement(sql);
			pstmt.setLong(1, boardId);
			rs = pstmt.executeQuery();
			
			while (rs.next()) {
				
				ReplyDTO replyDTO = new ReplyDTO();
				replyDTO.setReplyId(rs.getLong("REPLY_ID"));
				replyDTO.setWriter(rs.getString("WRITER"));
				replyDTO.setContent(rs.getString("CONTENT"));
				replyDTO.setPasswd(rs.getString("PASSWD"));
				replyDTO.setEnrollAt(rs.getDate("ENROLL_AT"));
				replyDTO.setBoardId(rs.getLong("BOARD_ID"));
				replyList.add(replyDTO);
				
			}
			
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			getClose();
		}
		
		return replyList;

	}
	
	
	public ReplyDTO getReplyDetail(long replyId) {

		ReplyDTO replyDTO = new ReplyDTO();

		try {
			
			getConnection();
			pstmt = conn.prepareStatement("SELECT * FROM REPLY_BOARD WHERE REPLY_ID = ?");
			pstmt.setLong(1, replyId);
			rs = pstmt.executeQuery();
			
			if (rs.next()) {
				replyDTO.setReplyId(rs.getLong("REPLY_ID"));
				replyDTO.setWriter(rs.getString("WRITER"));
				replyDTO.setPasswd(rs.getString("PASSWD"));
				replyDTO.setEnrollAt(rs.getDate("ENROLL_AT"));
				replyDTO.setContent(rs.getString("CONTENT"));
				replyDTO.setBoardId(rs.getLong("BOARD_ID"));
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			getClose();
		}
		
		return replyDTO;
	
	}
	
	
	public void setDummy() {
		
		Random ran = new Random();
		
		try {
			
			String[] word = {"가","나","다","라","마","바","사","아","자","차","카","타","파","하"};
			
			getConnection();
			for (int i = 1; i < 201; i++) {
				String writer  = "";
				String passwd  = "1111";
				String subject = "";
				String content = "";
				for (int j = 0; j < 7; j++) {
					writer  += word[ran.nextInt(word.length)];
					subject += word[ran.nextInt(word.length)];
					content += word[ran.nextInt(word.length)];
				}
				
				 String sql = """
							INSERT INTO  MAIN_BOARD (WRITER , SUBJECT , CONTENT, PASSWD) 
							VALUES					(?, ?, ?, ?)
							""";
				
				pstmt = conn.prepareStatement(sql);
				pstmt.setString(1, writer);
				pstmt.setString(2, subject);
				pstmt.setString(3, content);
				pstmt.setString(4, passwd);
				pstmt.executeUpdate();
			
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			getClose();
		}
		
	}
	
}
