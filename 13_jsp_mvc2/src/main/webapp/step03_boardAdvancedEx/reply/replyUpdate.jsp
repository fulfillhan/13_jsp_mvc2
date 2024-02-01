<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>replyUpdate</title>
<script src="ckeditor/ckeditor.js"></script>
</head>
<body>

	<div align="center" style="padding-top: 100px">
		<h3>댓글 수정</h3>
		<br>
		<form action="replyUpdate" method="post">
			<table style="width: 700px;" border="1">
				<colgroup>
					<col width="20%">
					<col width="80%">
				</colgroup>
				<tr>
					<td>작성자</td>
					<td>${replyDTO.writer }</td>
				</tr>
				<tr>
					<td>작성일</td>
					<td>${replyDTO.enrollAt }</td>
				</tr>
				<tr>
					<td>패스워드</td>
					<td><input type="password" name="passwd" size="70"/></td>
				</tr>
				<tr>
					<td>댓글</td>
					<td>
						<textarea rows="10" cols="60" name="content">${replyDTO.content }</textarea>
					</td>
				</tr>
				<tr>
					<td colspan="2" align="right">
						<input type="hidden" name="boardId" value="${replyDTO.boardId }" /> 
						<input type="hidden" name="replyId" value="${replyDTO.replyId }" /> 
						<input type="submit" value="댓글수정" />
					</td>
				</tr>
			</table>
		</form>
	</div>
</body>
</html>