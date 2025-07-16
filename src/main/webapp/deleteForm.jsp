<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html><html><head><meta charset="UTF-8">
<title>Insert title here</title>
<style type="text/css">@import url("common.css");</style>
</head><body>
<form action="delete.do" method="post" name="frm" onsubmit="return chk()">
	<input type="hidden" name="pageNum" value="${pageNum }">
	<input type="hidden" name="num" value="${num }">
<table><caption>게시글 삭제</caption>
	<tr><th>암호</th><td><input type="password" name="password" 
		required="required" autofocus="autofocus" ></td></tr>
	<tr><th colspan="2"><input type="submit" value="글삭제"></th></tr>
</table>
</form>
</body>
</html>