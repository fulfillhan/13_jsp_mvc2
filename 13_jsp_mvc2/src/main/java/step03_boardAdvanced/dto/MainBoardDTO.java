package step03_boardAdvanced.dto;

import java.util.Date;

public class MainBoardDTO {
	
	private long boardId;
	private String writer;
	private String subject;
	private String content;
	private String passwd;
	private long readCnt;
	private Date enrollAt;
	
	public long getBoardId() {
		return boardId;
	}
	public void setBoardId(long boardId) {
		this.boardId = boardId;
	}
	public String getWriter() {
		return writer;
	}
	public void setWriter(String writer) {
		this.writer = writer;
	}
	public String getSubject() {
		return subject;
	}
	public void setSubject(String subject) {
		this.subject = subject;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getPasswd() {
		return passwd;
	}
	public void setPasswd(String passwd) {
		this.passwd = passwd;
	}
	public long getReadCnt() {
		return readCnt;
	}
	public void setReadCnt(long readCnt) {
		this.readCnt = readCnt;
	}
	public Date getEnrollAt() {
		return enrollAt;
	}
	public void setEnrollAt(Date enrollAt) {
		this.enrollAt = enrollAt;
	}
	
	@Override
	public String toString() {
		return "MainBoardDTO [boardId=" + boardId + ", writer=" + writer + ", subject=" + subject + ", content="
				+ content + ", passwd=" + passwd + ", readCnt=" + readCnt + ", enrollAt=" + enrollAt + "]";
	}
	
}
