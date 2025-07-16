<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<style type="text/css">@import url("common.css");</style>
</head>
<body>
<form action="update.do" method="post" name="frm" onsubmit="return chk()">
	<input type="hidden" name="num" value="${board.num }">
	<input type="text" name="pageNum" value="${pageNum }">
	<!-- 답변글에서 사용 -->
<table><caption>게시글 작성</caption>
	<tr><th>제목</th><td><input type="text" name="subject" required="required" value="${board.subject }"></td></tr>
	<tr><th>암호</th><td><input type="password" name="password" required="required" ></td></tr>
	<tr><th>내용</th><td><textarea rows="5" cols="40" name="content" >${board.content }</textarea> </td></tr>
	<tr><th colspan="2"><input type="submit" value="수정"></th></tr>
</table>
</form>
</body>
</html>