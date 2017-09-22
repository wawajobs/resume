<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/template/taglib.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<script async src="https://www.googletagmanager.com/gtag/js?id=UA-106861645-1"></script>
		<script>
		  	window.dataLayer = window.dataLayer || [];
		  	function gtag(){dataLayer.push(arguments)};
		  	gtag('js', new Date());
		  	gtag('config', 'UA-106861645-1');
		</script>
<title>error</title>
</head>
<body>
<c:if test="${not empty param.error }">

	<h3>${param.error }</h3>
</c:if>
</body>
</html>