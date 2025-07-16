<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<style type="text/css">@import url("common.css");</style>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.7.1/jquery.min.js"></script>
</head>
<body>
<table><caption>게시글 상세보기</caption>
	<tr><th>제목</th><td>${board.subject }</td></tr>
	<tr><th>작성자</th><td>${board.writer }</td></tr>
	<tr><th>조회수</th><td>${board.readcount }</td></tr>
	<tr><th>작성일</th><td>${board.reg_date }</td></tr>
	<tr><th>내용</th><td><pre>${board.content }</pre></td></tr>
	<tr><th colspan="2">
		<button onclick="location.href='updateForm.do?num=${board.num}&pageNum=${pageNum}'">수정</button>
		<button onclick="location.href='deleteForm.do?num=${board.num}&pageNum=${pageNum}'">삭제</button>
		<button onclick="location.href='writeForm.do?num=${board.num}&pageNum=${pageNum}'">답변</button>
		<button onclick="location.href='list.do?pageNum=${pageNum}">목록</button>
	</th></tr>
</table>
<div id="disp">
</div>
<script type="text/javascript">
	// id가 disp인 곳에 list.jsp를 실행하여 보여줘  (여기서는 게시글 리스트임)
	// list.jsp table에서 table은 list.jsp에서 table만 보이라는 뜻.
	$('#disp').load('list.do?pageNum=${pageNum} table');
</script>

</body>
</html>