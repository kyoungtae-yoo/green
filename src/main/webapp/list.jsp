
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html><html><head><meta charset="UTF-8">
<title>Insert title here</title>
<style type="text/css">@import url("common.css");</style>
</head><body>

<h2>게시글 목록</h2>
<table>
	<tr><th>번호</th><th>제목</th><th>작성자</th><th>작성일</th><th>조회수</th></tr>
<c:if test="${empty list}">
	<tr><th colspan="5">게시글이 없습니다</th></tr>
</c:if>
<c:if test="${not empty list}">
	<c:forEach var="board" items="${list }">
		<tr><td>${number} <c:set var="number" value="${number-1}"></c:set></td>
			<c:if test="${board.del == 'y' }">
				<th colspan="4">삭제된 글입니다</th>
			</c:if>
			<c:if test="${board.del != 'y' }">
				<td title="${board.content }">
				<c:if test="${board.re_level > 0 }">
					<img alt="" src="images/level.gif" width="${board.re_level*10 }" height="5">
					<img alt="" src="images/re.gif">
				</c:if>
					<a href="content.do?num=${board.num}&pageNum=${currentPage}">${board.subject }</a>
				<c:if test="${board.readcount > 50 }">
					<img alt="" src="images/hot.gif">
				</c:if></td>
				<td>${board.writer }</td>
				<td>${board.reg_date }</td>
				<td>${board.readcount }</td>
			</c:if>
		</tr>
	</c:forEach>
</c:if>
</table>
<div align="center">
<c:if test="${startPage > PAGE_PER_BLOCK }">
    <button onclick="location.href='list.do?pageNum=${startPage-1 }'">◀이전</button>
</c:if>
	<c:forEach var="i" begin="${startPage }" end="${endPage }">
		<button onclick="location.href='list.do?pageNum=${i }'">${i }</button>
	</c:forEach>
<c:if test="${endPage < totalPage }">
    <button onclick="location.href='list.do?pageNum=${endPage+1 }'">다음▶</button>
</c:if>
<p><button onclick="location.href='writeForm.do?num=0&pageNum=1'">글쓰기</button>
</div>
</body>
</html>