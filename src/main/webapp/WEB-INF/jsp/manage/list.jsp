<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"  isELIgnored="false"%>
<%@ include file="/WEB-INF/template/taglib.jsp" %>	
<!DOCTYPE html>
<html>
<head>
	<%@include file="/WEB-INF/jsp/common/head.jsp"%>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath }/static/css/manage.css"/>
	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath }/static/css/reveal.css"/>
	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath }/static/css/messageAlert.css"/>
	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath }/static/js/layui/layui.css"/>
	<link href="${pageContext.request.contextPath }/static/js/table/tableViewer.css" rel="stylesheet" type="text/css">
	<script type="text/javascript" src="${pageContext.request.contextPath }/static/js/jquery/jquery-3.0.0.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath }/static/js/table/tableViewer.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath }/static/js/resume/interview.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath }/static/js/resume/map.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath }/static/js/jquery-reveal/jquery.reveal.js"></script>
	<script src="${pageContext.request.contextPath }/static/js/layui/layui.js"></script>
	<title>sign in</title>
	<script>
var list=new QueryList("line_list","");
	
	$(function(){
		interview.baseUrl = "${pageContext.request.contextPath }/";
		list.header=new Array("Name","<span id='thAge' class='thorder'>Age</span>",
				"<span id='thgender' class='thorder'>Gender</span>","Skype","Email address",
				"<span id='thcitizenship' class='thorder'>Citizenship</span>",
				"<span id='theducation' class='thorder'>Education</span>","Education country",
				"Resume","Video","Message","Backward","Forward","");
		
		list.fill=false;
	    list.getData = function (){
	    	interview.list(fillTable);
		
		}; 
		list.createTable();
		list.getData();
		
		$(".left").on('click','li',function(){
			$(".left li").removeClass("sel");
			$(this).addClass("sel");
			interview.currentStep = $(this).attr("value");
			interview.list(fillTable);
		});
		
		//生日
		layui.use('laydate', function(){
			var laydate = layui.laydate;
			var start = {
				min: laydate.now()
				,max: '2099-06-16 23:59:59'
				,istoday: false
				,choose: function(datas){
				end.min = datas; //开始日选好后，重置结束日的最小日期
				end.start = datas; //将结束日的初始值设定为开始日
				}
			};
			var end = {
				 min: laydate.now()
					 
				 ,max: '2099-06-16 23:59:59'
				 ,istoday: false
				 ,choose: function(datas){
				  start.max = datas; //结束日选好后，重置开始日的最大日期
				 }
			};

			
			$('#d_lastDate').on('focus',function(){
				start.elem = this;
		 		laydate(start);
			});
			$('#d_lastDate').on('click',function(){
				start.elem = this;
		 		laydate(start);
			});
		});
		
	});
	
	function fillTable(datas){
		list.callback(datas);
		$(".thorder").addClass("ordercol");
	}
	

	
	</script>
	
	<style type="text/css">
		.kele:before {
			position: absolute;
			content: " ";
			border: transparent 14px solid;
			border-width: 13px 8px;
			border-right-color: #CCC;
			top: 1px;
			left: -16px;
			height: 0;
			width: 0;
		}
		
		.kele {
			position: relative;
			width: 40px;
			height: 28px;
			background: #EEE;
			border-radius: 5px;
			margin: 30px;
			text-align: center;
			line-height: 28px;
			color: #999;
			font-size: 14px;
			border: 1px solid #CCC;
		}
		
		.kele:after {
			position: absolute;
			content: " ";
			border: transparent 13px solid;
			border-width: 12px 8px;
			border-right-color: #EEE;
			top: 2px;
			left: -15px;
			height: 0;
			width: 0;
		}
</style>
		
	</head>
	<body>
		<div class="box">
			<!--导航条-->
			<div class="nav">
				<a href="${pageContext.request.contextPath}/view/index.html" class="homeA">Home</a><a href="javascript;">-</a><a href="#" class="loginA">resume list</a>
			</div>
			<p class="tit"></p>
			<c:if test="${not empty adminUrl }">
				<p class="adminUrl"><a href="${pageContext.request.contextPath }${adminUrl }">admin</a></p>
			</c:if>
			<div class="clr">
		<div class="left">
			<ul>
				<li class="sel" value="1">1.Applications submitted<li>
				<li value="2">2. Resume screened<li>
				<li value="3">3. Submit introduction video and certifications<li>
				<li value="4">4. Video & certificates submitted<li>
				<li value="5">5. Wait for 1st interview<li>
				<li value="6">6. 1st interview passed, result emailed.<li>
				<li value="7">7. Wait for 2nd interview<li>
				<li value="8">8. 2nd interview passed, result emailed.<li>
				<li value="9">9. Wait for job offer<li>
				<li value="10">10. Offer accepted<li>
				<li value="11">11. Visa materia submitted<li>
				<li value="12">12. Visa succeed<li>
				<li value="13">13. Determine China arrival date<li>
				<li value="14">14. Completion of settle down<li>
			</ul>
		</div>
		<div class="right" id="line_list"></div>
		</div>
		</div>
		
		<div id="myDownload" class="reveal-modal">
			
			<div id="downloadContent" class="content">
				
			</div>
			
			<a class="close-reveal-modal close-reveal">&#215;</a>

		</div>
		<div id="myVideoDownload" class="reveal-modal">
			
			<div id="downloadContent" class="content">
				
			</div>
			
			<a class="close-reveal-modal close-reveal">&#215;</a>

		</div>
		
		<div  id="myInfo" class="info-reveal-modal">
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
					<span>Last date to China</span><em><input type="text" id="d_lastDate" placeholder=""></em>
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
					<input type="button" name="" onclick="" class="close-reveal" id="nex" value="Close"/>
				</div>
			</div>
			<a class="close-reveal-modal close-reveal">&#215;</a>
		</div>
	</body>
</html>

