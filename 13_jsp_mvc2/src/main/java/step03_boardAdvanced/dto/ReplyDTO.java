package step03_boardAdvanced.dto;

import java.util.Date;

public class ReplyDTO {

	private long replyId;
	private String writer;
	private String content;
	private String passwd;
	private Date enrollAt;
	private long boardId;
	
	public long getReplyId() {
		return replyId;
	}
	public void setReplyId(long replyId) {
		this.replyId = replyId;
	}
	public String getWriter() {
		return writer;
	}
	public void setWriter(String writer) {
		this.writer = writer;
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
	public Date getEnrollAt() {
		return enrollAt;
	}
	public void setEnrollAt(Date enrollAt) {
		this.enrollAt = enrollAt;
	}
	public long getBoardId() {
		return boardId;
	}
	public void setBoardId(long boardId) {
		this.boardId = boardId;
	}
	
	@Override
	public String toString() {
		return "ReplyDTO [replyId=" + replyId + ", writer=" + writer + ", content=" + content + ", passwd=" + passwd
				+ ", enrollAt=" + enrollAt + ", boardId=" + boardId + "]";
	}
	
}
