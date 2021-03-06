<%@ page language="java" pageEncoding="UTF-8" isELIgnored="false"%>
<%@ page import="org.springframework.security.core.context.SecurityContextHolder"%>
<%@ page import="org.springframework.security.core.Authentication"%>
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
	<title>Access Denied</title>
</head>

<body>
	<h1>Sorry, access is denied</h1>

	<p><%=request.getAttribute("SPRING_SECURITY_403_EXCEPTION")%></p>
	<p>
		<%
			Authentication auth = SecurityContextHolder.getContext().getAuthentication();
			if (auth != null) {
		%>
			Authentication object as a String:
			<%=auth.toString()%><BR>
			<BR>
		<%
			}
		%>
	</p>
</body>
</html>
