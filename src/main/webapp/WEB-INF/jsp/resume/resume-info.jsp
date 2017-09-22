<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"  isELIgnored="false"%>
<%@ include file="/WEB-INF/template/taglib.jsp" %>	
<!DOCTYPE html>
<html>
	<head>
	<%@include file="/WEB-INF/jsp/common/head.jsp"%>
	<title>Personal information</title>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<meta name="viewport" content="width=device-width,initial-scale=1,minimum-scale=1,maximum-scale=1,user-scalable=no"/>
	<script type="text/javascript" src="${pageContext.request.contextPath }/static/js/jquery/jquery-3.0.0.js"></script>
	<meta charset="UTF-8">


	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath }/static/js/layui/layui.css"/>
	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath }/static/css/personalInformation.css"/>
	<script src="../../../static/js/base.js"></script>
	<script async src="https://www.googletagmanager.com/gtag/js?id=UA-106861645-1"></script>
	<script>
	  	window.dataLayer = window.dataLayer || [];
	  	function gtag(){dataLayer.push(arguments)};
	  	gtag('js', new Date());
	  	gtag('config', 'UA-106861645-1');
	</script>
	</head>
	<body>
		<div class="box">
			<!--导航条-->
			<div class="nav">
				<a href="${pageContext.request.contextPath}/view/index.html" class="homeA">
					<span>Home</span>
					<img class="rightImg"  src="../../../static/img/jianMiddleRight.png"/>
				</a>
				<a href="#" class="loginA">
					<img class="leftImg" src="../../../static/img/jianMiddleLeft.png"/>
					<span>Personal information</span>
					<img class="rightImg"  src="../../../static/img/jianMiddleRight.png"/>
				</a>
			</div>
			<p class="tit">Personal information</p>
			<!--表单详情-->
			<div class="formBox">
				<form id="infoForm" action="${pageContext.request.contextPath }/info/save" method="post" enctype="application/x-www-form-urlencoded">
				<input type="hidden" name="id" id="id" value="0" />
					<!--==第一行==-->
					<div class="box1">
						<!--姓名-->
						<div class="name">
							<p>Name</p>
							<input type="text" name="name" id="name" required="required"/>
						</div>
						<!--位置-->
						<div class="position">
							<p>Position applied for</p>
							<input type="text" name="position" id="position" required="required" value="" />
						</div>
					</div>
					<div class="box5">
						<div class="obtain">
								<p>Recommended by</p>
								<input type="text" name="recommended" id="recommended" value="" />
						</div>
					</div>
					<!--==第二行==-->
					<div class="box2">
						<!--生日-->
						<div class="date">
							<p>Date of Birth</p>
							<input type="text" name="birthDate" id="birth" required="required" value="" />
						</div>
						<!--电话-->
						<div class="contact">
							<p>Contact number (Skype is preferred)</p>
							<input type="tel" name="phone" id="tel" required="required" value="" />
						</div>
					</div>
					
					<!--==第3行==-->
					<div class="box3">
						<!--性别-->
						<div class="sex">
							<p>Gender</p>
							<input type="radio" name="gender" id="sexman" value="m" required="required" checked="checked"/>
							<label id="sexman-label" class="mel active" for="sexman">Male</label>
							<input type="radio" name="gender" id="sexwoman" value="f" required="required" />
							<label id="sexwoman-label" class="fmel" for="sexwoman">Female</label>
							<input type="radio" name="gender" id="sexoths" value="" required="required"/>
							<label id="sexoths-label" class="otherMan" for="sexoths">Others</label>
						</div>
						<!--国籍-->
						<div class="citizen">
							<p>Citizenship</p>
							<input type="text" name="citizenship" id="citizenship" required="required" value="" />
						</div>
					</div>
					
					<!--==第4行==-->
					<div class="box4">
						<!--教育背景-->
						<div class="edu">
							<p>Education background (degree)</p>
							<input type="text" name="education" id="education" required="required" value="" />
						</div>
						<!--主修-->
						<div class="major">
							<p>Major</p>
							<input type="text" name="major" id="major" required="required" value="" />
						</div>
					</div>
					
					<!--==第5行==-->
					<div class="box5">
						<!--xxxx-->
						<div class="obtain">
							<p>Country in which education has been obtained</p>
							<input type="text" name="country" id="obtain" required="required" value="" />
						</div>
						<!--学位-->
						<div class="degree">
							<p>Degree / Certifications</p>
							<input type="text" name="certification" id="degree" value="" />
						</div>
					</div>
					
					<!--==第6行==-->
					<div class="box6">
						
						<!--工作经验-->
						<div class="years">
							<i class="xia"></i>
							<p>Length of working experience</p>
							<input class="yearsNumber" type="text" name="experienceLength" id="experienceLength" value="" readonly="readonly"/>
							<span class="numberSpan">
								<em>0</em>
								<em>1</em>
								<em>2</em>
								<em>3</em>
								<em>4</em>
								<em>5</em>
								<em>6</em>
								<em>7</em>
								<em>8</em>
								<em>9</em>
								<em>10</em>
								<em>10+</em>
							</span>
						</div>
						<!--专业技能-->
						<div class="specical">
							<p>Specialized skills (if possible)</p>
							<div class="addBox">
								<input class="specialInput" type="text" name="specialized" id="specialized" value="" />
								<span></span>
							</div>
						</div>
					</div>
					<div class="moreSpecical">
						<input type="text" name="" id="" value="" />
						<input type="text" name="" id="" value="" />
					</div>
					<!--==第7行==-->
					<div class="box7">
						<!--工资-->
						<div class="money">
							<p>Expected monthly salary in U.S dollar</p>
							<input type="text" name="salary" id="salary" value="" />
						</div>
						<!--期望在中国的工作地点-->
						<div class="china">
							<p>Expected working location in China</p>
							<input type="text" name="location" id="location" value="" />
						</div>
					</div>
					<!-- 第8行  年龄段-->
					<div class="age">
						<p>Expected teaching age group of students.(Can be multiple choice)</p>
						
						<input type="radio" name="age" value="0-7" id="0-7"/>
						<label id="0-7-lable" class="0-7" for="0-7"><em></em></label><span>0-7</span>
						
						<input type="radio" name="age" value="7-12" id="7-12" />
						<label id="7-12-lable" class="7-12" for="7-12"><em></em></label><span>7-12</span>
						
						<input type="radio" name="age" value="12-18" id="12-18"/>
						<label id="12-18-lable" class="12-18" for="12-18"><em></em></label><span>12-18</span>
						
						<input type="radio" name="age" value="adults" id="adults" checked="checked"/>
						<label id="adults-lable" class="adults" for="adults"><em></em></label><span>adults</span>
					</div>
					
					<!--第9行-->
					<!--建议-->
					<p class="others">Other positions of consideration</p>
					<textarea class="messageIt" name="otherPositions" id="otherPositions" rows="" cols=""></textarea>
					<input type="hidden" id="clickType" value="0" name="clickType" />
					<input type="submit" style="display:none" id="submit"/>
					<div class="btb">
						<div class="Next">Save&Next</div>
						<div class="save">Save</div>
					</div>
				</form>
			</div>
		</div>
	</body>
	<script src="${pageContext.request.contextPath }/static/js/layui/layui.js"></script>
	<script src="${pageContext.request.contextPath }/static/js/resume/personalInformation.js"></script>
	<script src="${pageContext.request.contextPath }/static/js/resume/resume-info.js"></script>
	
	<script type="text/javascript">
		resumeInfo.baseUrl = "${pageContext.request.contextPath }";
		
		var resumeId = "${resumeId}"
		if(resumeId && "" != resumeId){
			//TODO set resumeInfo to form
			resumeInfo.findById(resumeId);
			
		}
		
		$(".save").on("click",function(){
			$("#submit").click();
		});
		
		$(".Next").on("click",function(){
			//resumeInfo.save(1);
			$("#clickType").val("1");
			$("#submit").click();
			/* if(resumeId && "" != resumeId){
				window.location.href = "${pageContext.request.contextPath }/upload/"+resumeId+"/doc";
			}else{
				alert("please save your information");
			} */
			
		});
		
		
	</script>
</html>

