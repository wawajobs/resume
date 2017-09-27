<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"  isELIgnored="false"%>
<%@ include file="/WEB-INF/template/taglib.jsp" %>	
<!DOCTYPE html>
<html>
<head>
	<script async src="https://www.googletagmanager.com/gtag/js?id=UA-106861645-1"></script>
	<script>
	  	window.dataLayer = window.dataLayer || [];
	  	function gtag(){dataLayer.push(arguments)};
	  	gtag('js', new Date());
	  	gtag('config', 'UA-106861645-1');
	</script>
	<%@include file="/WEB-INF/jsp/common/head.jsp"%>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<meta name="viewport" content="width=device-width,initial-scale=1,minimum-scale=1,maximum-scale=1,user-scalable=no"/>
	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath }/static/css/forgot.css"/>
	<script src="${pageContext.request.contextPath }/static/js/resume/base.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath }/static/js/jquery/jquery-3.0.0.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath }/static/js/resume/user.js"></script>
	<title>sign in</title>
	<script>
	
	$(function(){
		user.baseUrl = "${pageContext.request.contextPath }";
	});
	
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
		
		var email = $("#email").val() ;
		var verifyCode = $("#verifyCode").val() ;
		var emailCode = $("#emailCode").val() ;
		if(email == ""){
			$("#errorSpan").html("Please fill in the email");
			return ;
			
		}else if(verifyCode == ""){
			$("#errorSpan").html("Please fill in the verification code");
			return ;
		}else if(emailCode == ""){
			$("#errorSpan").html("Please fill in the email verification Code");
			return ;
		}
		
		user.checkVerifyCode(email, verifyCode, emailCode);
	}
	
	function sendCode(){
		var email = $("#email").val();
		if(email == ""){
			$("#errorSpan").html("Please fill in the email");
			return ;
		}
		user.sendVerifyCode(email);
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
				<a href="${pageContext.request.contextPath}/view/index.html" class="homeA">
					<span>Home</span>
					<img class="rightImg"  src="${pageContext.request.contextPath }/static/img/jianMiddleRight.png"/>
				</a>
				<a href="login.jsp" class="loginA">
					<img class="leftImg" src="${pageContext.request.contextPath }/static/img/jianMiddleLeft.png"/>
					<span>Forgot password</span>
					<img class="rightImg"  src="${pageContext.request.contextPath }/static/img/jianMiddleRight.png"/>
				</a>
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
				<p class="title">Verify Email</p>
				<form action="#" method="post">
					<input type="email" name="email" id="email" value="" placeholder="Email address" required="required"/>
					<div class="sure">
						<input type="text" name="verifyCode" id="verifyCode" value="" placeholder="Verify not a robot" required="required"/>
						<span id="sureMaImg"> <img id="imgObj" alt="验证码" src="<%= request.getContextPath()%>/public/verify/code" onclick="changeImg();"/></span>
					</div> 
					<div class="sure sure2">
						<input type="text" name="emailCode" id="emailCode" value="" placeholder="Verification code" required="required"/>
						<span id="sureMaImg" onclick="sendCode();">send</span>
					</div>
					<p class="wrong"><span>&nbsp;</span><span id="errorSpan"></span></p>
					<input type="button" onclick="fun_submit();" name="" id="sub" value="Reset Password"  required="required"/>
					<p class="more"><a class="leftA" href="${pageContext.request.contextPath }/public/user/reg/page">Don't have an account?</a><a class="rightA" href="${pageContext.request.contextPath }/public/user/reg/page">Sign up</a></p>
				</form>
			</div>
		</div>
	</body>
</html>

