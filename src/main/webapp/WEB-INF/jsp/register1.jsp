<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"  isELIgnored="false"%>
<%@ include file="/WEB-INF/template/taglib.jsp" %>	
<!DOCTYPE html>
<html>
<head>
	<%@include file="/WEB-INF/jsp/common/head.jsp"%>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<link href="${pageContext.request.contextPath }/static/js/table/tableViewer.css" rel="stylesheet" type="text/css">
	<script type="text/javascript" src="${pageContext.request.contextPath }/static/js/jquery/jquery-3.0.0.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath }/static/js/table/tableViewer.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath }/static/js/resume/user.js"></script>
	<title>sign in</title>
	<script>
	
	
	var list=new QueryList("line_list","checkbox");
	
	$(function(){
		user.baseUrl = "${pageContext.request.contextPath }/";
		list.header=new Array("nickname","email","password","locked");
		
		list.fill=false;
	    list.getData = function (){
	    	user.list(fillTable);
		
		}; 
		list.createTable();
		list.getData();
		
		
	});
	
	function fillTable(datas){
		list.callback(datas);
	}
	
	
	
	function fun_submit(){
		
		var email = $("#email").val() ;
		var pwd = $("#pwd").val() ;
		var repwd = $("#repwd").val() ;
		if(pwd != repwd){
			return;
		}
		
		user.register(email, pwd);
	}
	function fun_reset(){
		document.f.reset();
	}
	
	document.onkeypress=function(e){
		var code;
		if(!e){
			var e=window.event;
		}
		if(e.keyCode){
			code=e.keyCode;
		}
		else if(e.which){
			code=e.which;
		}
		if(code==13){
			fun_submit();
		}
	}
	
	</script>
</head>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<title>Sign Up</title>
		<link rel="stylesheet" type="text/css" href="signUp.css"/>
	</head>
	<body>
		<div class="box">
			<!--导航条-->
			<div class="nav">
				<a href="../index.html" class="homeA">Home</a><a href="javascript;">-</a><a href="login.html" class="loginA">Sign up</a>
			</div>
			
			<!--登录表单-->
			<div class="loginBox">
				<p class="title">Sign up</p>
				<form action="" method="post">
					<input type="email" name="" id="email" value="" placeholder="Email address" required="required"/>
					<input type="password" name="" id="password" value="" placeholder="Enter your Password" required="required"/>
					<input type="password" name="" id="passwordAgain" value="" placeholder="Enter your Password again" required="required"/>
					<input type="submit" name="" id="sub" value="OK" />
				</form>
			</div>
			
		</div>
	</body>
	<script src="../jquery-3.2.1.min.js"></script>
	<script src="signUp.js"></script>
</html>
