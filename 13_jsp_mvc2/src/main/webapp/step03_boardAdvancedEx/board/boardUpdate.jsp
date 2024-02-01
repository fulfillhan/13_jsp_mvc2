<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>boardUpdate</title>
<script src="ckeditor/ckeditor.js"></script>
</head>
<body>

	<div align="center" style="padding-top: 100px">
		<h3>게시글 수정</h3>
		<br>
		<form action="boardUpdate" method="post">
			<table style="width: 700px;" border="1">
				<colgroup>
					<col width="20%">
					<col width="80%">
				</colgroup>
				<tr>
					<td>작성자</td>
					<td>${mainBoardDTO.writer }</td>
				</tr>
				<tr>
					<td>작성일</td>
					<td>${mainBoardDTO.enrollAt }</td>
				</tr>
				<tr>
					<td>제목</td>
					<td><input type="text" name="subject" value="${mainBoardDTO.subject }" size="70"/></td>
				</tr>
				<tr>
					<td>글내용</td>
					<td>
						<textarea rows="10" cols="60" name="content">${mainBoardDTO.content }</textarea>
						<script>CKEDITOR.replace("content")</script>
					</td>
				</tr>
				<tr>
					<td colspan="2" align="right">
						<input type="hidden" name="boardId" value="${mainBoardDTO.boardId }" /> 
						<input type="submit" value="수정하기" />
						<input type="button" onclick="location.href='boardList'" value="목록보기" />
					</td>
				</tr>
			</table>
		</form>
	</div>
</body>
</html>