<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"  isELIgnored="false"%>
<%@ include file="/WEB-INF/template/taglib.jsp" %>	
<!DOCTYPE html>
<html>
<head>
	<%@include file="/WEB-INF/jsp/common/head.jsp"%>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath }/static/css/userlist.css"/>
	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath }/static/css/switch.css"/>
	<link href="${pageContext.request.contextPath }/static/js/table/tableViewer.css" rel="stylesheet" type="text/css">
	<script type="text/javascript" src="${pageContext.request.contextPath }/static/js/jquery/jquery-3.0.0.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath }/static/js/table/tableViewer.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath }/static/js/resume/user.js"></script>
	<title>admin</title>
	<script>
var list=new QueryList("line_list","");
	
	$(function(){
		user.baseUrl = "${pageContext.request.contextPath }/";
		list.header=new Array("Name","email","enable","delete");
		
		list.fill=false;
	    list.getData = function (){
	    	user.list(fillTable);
		
		}; 
		list.createTable();
		list.getData();
		
		$("#subUser").on('click',function(){
			setEmp();
		});
		
	});
	
	function fillTable(datas){
		list.callback(datas);
	}
		
	
	</script>
		
	</head>
	<body>
		<div class="box">
			<!--导航条-->
			<div class="nav">
				<a href="${pageContext.request.contextPath}/view/index.html" class="homeA">Home</a>
				
				<a href="javascript;">-</a>
				<a href="interview/page" >manage</a>
				<a href="javascript;">-</a>
				<a href="#" class="loginA">admin</a>
			</div>
			<p class="tit">Administration page</p>
			<div class="addBox">
				<input class="specialInput" type="email" name="email" id="empEmail" value="" />
				<span id="subUser"></span>
			</div>
			<div class="clr">
		
		<div class="right" id="line_list"></div>
		</div>
		</div>
	</body>
</html>

