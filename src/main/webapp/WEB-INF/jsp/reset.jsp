<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"  isELIgnored="false"%>
<%@ include file="/WEB-INF/template/taglib.jsp" %>	
<!DOCTYPE html>
<html>
<head>
	<%@include file="/WEB-INF/jsp/common/head.jsp"%>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath }/static/css/reset.css"/>
	<script type="text/javascript" src="${pageContext.request.contextPath }/static/js/jquery/jquery-3.0.0.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath }/static/js/resume/user.js"></script>
	<title>sign in</title>
	<script>
	
	// 刷新图片  
    function changeImg() {  
        var imgSrc = $("#imgObj");  
        var src = imgSrc.attr("src");  
        imgSrc.attr("src", changeUrl(src));  
    }  
    //为了使每次生成图片不一致，即不让浏览器读缓存，所以需要加上时间戳  
    function changeUrl(url) {  
        var timestamp = (new Date()).valueOf();  
      	var url = "${pageContext.request.contextPath}/public/verify/code?t="+timestamp;
        return url;  
    } 
	
	
	function fun_submit(){
		
		var email = "${email}";
		var verifyCode = "${verifyCode}";
		var emailVerifyCode = "${emailVerifyCode}";
		var pwd = $("#pass").val() ;
		var repwd = $("#passSure").val() ;
		if(pwd == ""){
			$("#errorSpan").html("");
			return ;
		}else if(repwd == ""){
			$("#errorSpan").html("");
			return ;
		}
		
		if(pwd != repwd){
			$("#errorSpan").html("");
			return;
		}
		
		user.resetPwd(email, pwd,verifyCode,emailVerifyCode);
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
	<body>
		<div class="box">
			<!--导航条-->
			<div class="nav">
				<a href="${pageContext.request.contextPath}/view/index.html" class="homeA">Home</a><a href="javascript;">-</a><a href="login.html" class="loginA">Reset password</a>
			</div>
			
			<!--表单前边的导航-->
			<div class="headBox">
				<div class="headBoxCenter">
					<p class="centerUp">
						<span class="upL">01</span>
						<em></em>
						<span class="upR">02</span>
					</p>
					<p class="centerDown">
						<span class="downL">Verify email</span>
						<span class="downR">Reset password</span>
					</p>
				</div>
			</div>
			<!--登录表单-->
			<div class="loginBox">
				<p class="title">Reset password</p>
				<form action="#" method="post">
					<input type="password" name="" id="pass" value="" placeholder="New password" required="required"/>
					<input type="password" name="" id="passSure" value="" placeholder="Confirm New Password" required="required"/>
					<p class="wrong"><span>&nbsp;</span><span id="errorSpan"></span></p>
					<input type="button" onclick="fun_submit();" name="" id="sub" value="Done" />
					<p class="more"><a class="leftA" href="#">Don't have an account?</a><a class="rightA" href="#">Sign up</a></p>
				</form>
			</div>
		</div>
	</body>
</html>