<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>bWrite</title>
<!-- webapp 하위경로 부터 작성한다. -->
<script src="ckeditor/ckeditor.js"></script>
</head>
<body>

	<div align="center">
		<h3>커뮤니티 게시글 작성</h3>
		<form action="bWrite" method="post">
			<table border="1">
				<tr>
					<td>작성자</td>
					<td><input type="text" name = "writer"/></td>
				</tr>
				<tr >
					<td>제목</td>
					<td><input type="text" name="subject"/></td>
				</tr>
				<tr>
					<td>이메일</td>
					<td><input type="text" name="email"/></td>
				</tr>
				<tr>
					<td>비밀번호</td>
					<td><input type="password" name="password"/></td>
				</tr>
				<tr>
					<td>글내용</td>
					<td>
						<textarea rows="10" cols="50" name="content" ></textarea>
						<script>CKEDITOR.replace("content");</script>
					</td>
				</tr>
				<tr align="center">
					<td colspan="2">
						<input type="submit" value="글쓰기" />
						<input type="button" value="목록보기">
					</td>
				</tr>
			</table>
		</form>
	</div>
	
</body>
</html>