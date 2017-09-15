<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"  isELIgnored="false"%>
<%@ include file="/WEB-INF/template/taglib.jsp" %>	

<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<meta name="viewport" content="width=device-width,initial-scale=1,minimum-scale=1,maximum-scale=1,user-scalable=no"/>
		<title>sign in</title>
		<link rel="stylesheet" type="text/css" href="static/css/login.css"/>
		<script src="static/js/base.js"></script>
		<script type="text/javascript" src="static/js/jquery/jquery-3.0.0.js"></script>
		<script src="static/js/jquery-cookie/jquery.cookie-1.3.1.js" type="text/javascript"></script>
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
	
	$(document).ready(function(){
		
	});
	
	if(self!=top){
		window.location='login.jsp';
	}
	
	function fun_submit(){
		if($("#email").val() == ""){
			$("#errorSpan").html("Please fill in the email");
			return false;
		}else if($("#password").val() == ""){
			$("#errorSpan").html("Please fill in the password");
			return false;
		}
		document.f.submit();
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
				<a href="${pageContext.request.contextPath}/view/index.html" class="homeA">Home</a><a href="login.jsp" class="loginA">Sign in</a>
			</div>
			
			<!--登录表单-->
			<div class="loginBox">
				<p class="title">Sign in</p>
				<form  id="login_id" name='f' action='login' method='POST'>
					<input type="email" name="username" id="email" value="${sessionScope.SPRING_SECURITY_LAST_USERNAME}" placeholder="Email address" required="required"/>
					<input type="password" name="password" id="password" value="" placeholder="Password" required="required"/>
					<p class="forget"><a href="${pageContext.request.contextPath}/public/user/forgot/page">Forget password</a></p>
					<div class="sure">
						<input type="text" name="verifyCode" id="sureMa" value="" placeholder="Verification code"/>
						<span id="sureMaImg"> <img id="imgObj" alt="验证码" src="<%= request.getContextPath()%>/public/verify/code" onclick="changeImg();"/></span>
					</div>
					<p class="wrong"><span>&nbsp;</span><span id="errorSpan">
					<c:if test="${not empty param.login_error}">
						${sessionScope.SPRING_SECURITY_LAST_EXCEPTION.message}
					</c:if>
					</span></p>
					<input type="button" name="" id="sub" value="Sign in" onclick="fun_submit();" />
					<p class="more"><a class="leftA" href="#">Don't have an account?</a><a class="rightA" href="${pageContext.request.contextPath}/public/user/reg/page">Sign up</a></p>
				</form>
			</div>
			
		</div>
	</body>
</html>

