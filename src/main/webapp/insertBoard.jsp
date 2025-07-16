<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" import="ch11.*"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
<%
	BoardDao bd=BoardDao.getInstance();
	for (int i=0; i<223; i++){
	    Board board=new Board();
		board.setSubject("테스트_"+i);
		board.setWriter("로제_"+i);
		board.setPassword("1");
		board.setContent("내용_"+i);
		bd.insert(board);
	}
%>
입력 완료
</body>
</html>