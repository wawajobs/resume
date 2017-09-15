<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"  isELIgnored="false"%>
<%@ include file="/WEB-INF/template/taglib.jsp" %>	
<!DOCTYPE html>
<html>
	<head>
	<%@include file="/WEB-INF/jsp/common/head.jsp"%>
	<title>Personal information</title>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<script type="text/javascript" src="${pageContext.request.contextPath }/static/js/jquery/jquery-3.0.0.js"></script>
	<meta charset="UTF-8">


	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath }/static/css/messageAlert.css"/>
	</head>
	<body>
		<div class="box">
			<div class="boxCenter">
				<p>
					<span>Name</span><em id="d_name"></em>
				</p>
				
				<p>
					<span>Email:</span><em id="d_email"></em>
				</p>
				<p>
					<span>D.O.B</span><em id="d_birthDate"></em>
				</p>
				<p>
					<span>Gender:</span><em id="d_gender"></em>
				</p>
				<p>
					<span>Position applied for</span><em id="d_position"></em>
				</p>
				<p>
					<span>Skype number</span><em id="d_phone"></em>
				</p>
				<p>
					<span>Citizenship</span><em id="d_citizenship"></em>
				</p>
				<p>
					<span>Education background/degree</span><em id="d_education"></em>
				</p>
				<p>
					<span>Major</span><em id="d_major"></em>
				</p>
				<p>
					<span>Country in which education has been obtained</span><em id="d_county"></em>
				</p>
				<p>
					<span>Certification</span><em id="d_certification"></em>
				</p>
				<p>
					<span>Specialized skill</span><em id="d_specialized"></em>
				</p>
				<p>
					<span>Length of working experience</span><em id="d_experienceLength"></em>
				</p>
				<p>
					<span>Expected monthly salary in U.S. dollors</span><em id="d_salary"></em>
				</p>
				<p>
					<span>Expected working location in China</span><em id="d_location"></em>
				</p>
				<p>
					<span>Expected teaching age group of students</span><em id="d_age"></em>
				</p>
				<p>
					<span>Other positions of consideration</span><em id="d_otherPositions"></em>
				</p>
				<p>
					<span>Last date to China</span><em id="d_lastDate"></em>
				</p>
				<p>
					<span>Count down</span><em id="d_count_down"></em>
				</p>
				<p>
					<span>Visa received date</span><em id="d_visa_date"></em>
				</p>
				<p>
					<span>Flight ticket image</span><em id="d_ticket"></em>
				</p>
				<p>
					<span>Flight landing place and date</span><em id="d_flight"></em>
				</p>
				
				<!--下边的三个按钮-->
				<div class="btbBox">
					<input type="button" name="" onclick="" id="pre" value="Backward"/>
					<input type="button" name="" onclick="" id="clo" value="Forward"/>
					<input type="button" name="" onclick="" id="nex" value="Close"/>
				</div>
			</div>
		</div>
	</body>
	<script src="${pageContext.request.contextPath }/static/js/layui/layui.js"></script>
	<script src="${pageContext.request.contextPath }/static/js/resume/personalInformation.js"></script>
	<script src="${pageContext.request.contextPath }/static/js/resume/resume-info.js"></script>
	
	<script type="text/javascript">
		resumeInfo.baseUrl = "${pageContext.request.contextPath }";
		
		var resumeId = "${resumeId}";
		if(resumeId && "" != resumeId){
			$.ajax({
				url:"${pageContext.request.contextPath }/info/"+resumeId +"/info",
				type:"get",
				dataType:"json",
				data:{},
				success : function(data){
					if(data.status == 1){
						var resume = data.resumeInfo;
						$("#d_name").html(resumeInfo.checkNull(resume.name));
						$("#d_position").html(resumeInfo.checkNull(resume.position));
						$("#d_birthDate").html(resumeInfo.checkNull(resume.birthDate));
						$("#d_phone").html(resumeInfo.checkNull(resume.phone));
						$("#d_citizenship").html(resumeInfo.checkNull(resume.citizenship));
						$("#d_education").html(resumeInfo.checkNull(resume.education));
						$("#d_major").html(resumeInfo.checkNull(resume.major));
						$("#d_county").html(resumeInfo.checkNull(resume.country));
						$("#d_certification").html(resumeInfo.checkNull(resume.certification));
						$("#d_specialized").html(resumeInfo.checkNull(resume.specialized));
						$("#d_experienceLength").html(resumeInfo.checkNull(resume.experienceLength));
						$("#d_salary").html(resumeInfo.checkNull(resume.salary));
						$("#d_location").html(resumeInfo.checkNull(resume.location));
						$("#d_otherPositions").html(resumeInfo.checkNull(resume.otherPositions));
						$("#d_age").html(resumeInfo.checkNull(resume.age));
						if("m" == resumeInfo.checkNull(resume.gender)){
							$("#d_gender").html("Male");
						}else if("f" == resumeInfo.checkNull(resume.gender)){
							$("#d_gender").html("Fmale");
						}if("" == resumeInfo.checkNull(resume.gender)){
							$("#d_gender").html("Other");
						} 
					}else{
						alert(data.message);
					}
				},
				error: function(XMLHttpRequest, textStatus, errorThrown) {
					 alert(XMLHttpRequest.status+"-"+XMLHttpRequest.readyState + "-" + textStatus);
			    }
			});
			
		}
		
		$(".save").on("click",function(){
			$("#submit").click();
		});
		
		$(".Next").on("click",function(){
			//resumeInfo.save(1);
			if(resumeId && "" != resumeId){
				window.location.href = "${pageContext.request.contextPath }/upload/"+resumeId+"/doc";
			}else{
				alert("please save your information");
			}
			
		});
		
		
	</script>
</html>

