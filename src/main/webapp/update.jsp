<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
<c:if test="${result > 0 }">
	<script type="text/javascript">
		alert("수정 완료! pageNum=${pageNum }");
		location.href="list.do?pageNum=${pageNum }";
	</script>
</c:if>
<c:if test="${result < 0 }">
	<script type="text/javascript">
		alert("암호가 틀려서 수정할 수 없습니다.");
		history.back();
	</script>
</c:if>
<c:if test="${result == 0 }">
	<script type="text/javascript">
		alert("수정 실패! 관리자 문의요망!");
		history.back();
	</script>
</c:if>

</body>
</html>