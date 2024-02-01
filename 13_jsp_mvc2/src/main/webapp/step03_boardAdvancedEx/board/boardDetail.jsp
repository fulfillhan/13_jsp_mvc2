<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>boardDetail</title>
</head>
</head>
<body>

	<div align="center" style="padding-top: 100px">
		<h3>게시글 상세보기</h3>
		<table style="width: 700px; text-align: center" border="1">
			<colgroup>
				<col width="20%">
				<col width="80%">
			</colgroup>
			<tr>
				<td>제목</td>
				<td>${mainBoardDTO.subject }</td>
			</tr>
			<tr>
				<td>조회수</td>
				<td>${mainBoardDTO.readCnt }</td>
			</tr>
			<tr>
				<td>작성자</td>
				<td>${mainBoardDTO.writer }</td>
			</tr>
			<tr>
				<td>작성일</td>
				<td>${mainBoardDTO.enrollAt }</td>
			</tr>
			<tr>
				<td>글 내용</td>
				<td>${mainBoardDTO.content }</td>
			</tr>
			<tr align="right">
				<td colspan="2">
					<input type="button" value="수정" onclick="location.href='boardAuthentication?boardId=${mainBoardDTO.boardId}&menu=update'">
					<input type="button" value="삭제" onclick="location.href='boardAuthentication?boardId=${mainBoardDTO.boardId}&menu=delete'">
					<input type="button" value="목록보기"  onclick="location.href='boardList'">
				</td>
			</tr>
		</table>
		
		<br>
		<hr>
		<br>
		
		<h4>댓글 리스트 (${allReplyCnt })</h4>
		<table style="width: 700px;" border="1">
			<c:forEach var="replyDTO" items="${replyList }">
				<tr>
					<td>
						작성자 : ${replyDTO.writer } / 작성일 : ${replyDTO.enrollAt }<br>
						${replyDTO.content }
						<input type="button" value="수정" onclick="location.href='replyUpdate?replyId=${replyDTO.replyId}'">
						<input type="button" value="삭제" onclick="location.href='replyDelete?replyId=${replyDTO.replyId }'">
					</td>
				</tr>			
			</c:forEach>
			<tr>
				<td align="right">
					<input type="button" value="댓글작성" onclick="location.href='replyWrite?boardId=${mainBoardDTO.boardId }'">
				</td>
			</tr>
		</table>
		
	</div>
</body>
</html>