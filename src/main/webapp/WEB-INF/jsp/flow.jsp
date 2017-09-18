<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"  isELIgnored="false"%>
<%@ include file="/WEB-INF/template/taglib.jsp" %>	
<!DOCTYPE html>
<html>
	<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath }/static/css/status.css"/>
	<script type="text/javascript" src="${pageContext.request.contextPath }/static/js/jquery/jquery-3.0.0.js"></script>
	<script src="${pageContext.request.contextPath }/static/js/layui/layui.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath }/static/js/resume/interview.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath }/static/js/resume/status.js"></script>
		<meta charset="UTF-8">
		<title>Status</title>
	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath }/static/js/layui/layui.css"/>
	<script type="text/javascript">
	
	var timer= null;
	$(function(){
		//获取上边进度条的高度	
/* 		if(true){
			var nowHeight= parseInt($('.one').height()+$('.two').height()+$('.three').height());
			$('.now').height(nowHeight);
		}
		
 */		
 
 
 		interview.baseUrl = "${pageContext.request.contextPath }";
 		var error = "${param.error}";
 		if(error && error != null){
 			alert(error);
 		}
		var stepHeightArray = [ parseInt($('.one').height()), parseInt($('.two').height()), parseInt($('.three').height()), parseInt($('.four').height()),
				 parseInt($('.five').height()), parseInt($('.six').height()),
						 parseInt($('.seven').height()), parseInt($('.eight').height()),
								 parseInt($('.nine').height()), parseInt($('.ten').height()), parseInt($('.eleven').height()),
										 parseInt($('.twelve').height()),parseInt($('.thirteen').height()),parseInt($('.fourteen').height())];
		var resumeId = "${resumeId}";
		interview.findByResumeId(resumeId, stepHeightArray);
		checkFile(resumeId);
		interview.getFiles(resumeId, 1, listVideo);
		interview.getFiles(resumeId, 4, listCertifications);
		interview.getFiles(resumeId, 3, listFlightTicket);
		//第12步、13步的日历
		layui.use('laydate', function(){
			var laydate = layui.laydate;
			var start = {
				min: laydate.now()
				,max: '2099-06-16 23:59:59'
				,istoday: false
				,choose: function(datas){
				end.min = datas; //开始日选好后，重置结束日的最小日期
				end.start = datas //将结束日的初始值设定为开始日
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

			
			$('#up12').on('click',function(){
				start.elem = this;
		 		laydate(start);
			})
			$('#up13').on('click',function(){
				start.elem = this;
		 		laydate(start);
			})
		})
		
		//第3步的上传文件
		$('#up1').change(function(){
			var filePath=$(this).val();
	        var arr=filePath.split('\\');
	        var fileName=arr[arr.length-1];
			$('#up1Label').html("&nbsp;&nbsp;&nbsp;"+fileName);
		})
		
		$('#up2').change(function(){
			var filePath=$(this).val();
	        var arr=filePath.split('\\');
	        var fileName=arr[arr.length-1];
			$('#up2Label').html("&nbsp;&nbsp;&nbsp;"+fileName);
		})
		
		$("#finishUpload").on("click",function(){
			interview.finishStep(resumeId, 3);
		});
		
		//第13步上传文件
		$('#up13Input').change(function(){
			var filePath=$(this).val();
	        var arr=filePath.split('\\');
	        var fileName=arr[arr.length-1];
			$('#up13label').html("&nbsp;&nbsp;&nbsp;"+fileName);
		})
		
		$('#uploadVideoBtn').click(function(){
			var video = $("#up1").val();
			if("" == video){
				alert("please choose a file");
				return ;
			}
			
			var files = document.getElementById("up1").files;
			if(files[0].size > 100*1024*1024){
				alert("Upload up to 100M at most");
				return ;
			}
			
			showProgress(files[0].name);
			$("#fileType3").val("video");
			uploadFile();
			
		});
		
		  
		
		$('#uploadCerBtn').click(function(){
			var video = $("#up2").val();
			if("" == video){
				alert("please choose a file");
				return;
			}
			$("#fileType3").val("certification");
			$("#threeForm").submit();
		});
		
		
	});
	
	function uploadFile() {  
        var fd = new FormData();  
        fd.append("video", document.getElementById('up1').files[0]);  
        fd.append("fileType", document.getElementById('fileType3').value);  
        fd.append("resumeId", document.getElementById('resumeId3').value);  
        var xhr = new XMLHttpRequest();  
        xhr.upload.addEventListener("progress", uploadProgress, false);  
        xhr.addEventListener("load", uploadComplete, false);  
        xhr.addEventListener("error", uploadFailed, false);  
        xhr.addEventListener("abort", uploadCanceled, false);  
        xhr.open("POST", "${pageContext.request.contextPath }/upload/intro/video");//修改成自己的接口  
        xhr.send(fd);  
  }  
  function uploadProgress(evt) {  
    if (evt.lengthComputable) {  
      var percentComplete = Math.round(evt.loaded * 100 / evt.total);  
      getProgress(percentComplete); 
    }  
    else {  
      console.log("else");  
    }  
  }  
  function uploadComplete(evt) {  
    /* 服务器端返回响应时候触发event事件*/  
   // alert(evt.target.responseText);
    var data = JSON.parse(evt.target.responseText);
    if(data.status == 1){
    	$("#up1").val("");
    	$('#up1Label').html("");
	    interview.getFiles("${resumeId}", 1, listVideo);
    }
  }  
  function uploadFailed(evt) {  
    alert("There was an error attempting to upload the file.");  
  }  
  function uploadCanceled(evt) {  
    alert("The upload has been canceled by the user or the browser dropped the connection.");  
  } 
  
  function cancel(xhr){
	  if(null != xhr){
		  xhr.abort();
	  }
	  hideProgress();
  }
	
	function showProgress(fileName){
		$(".uploadBox").css("display","block");
		$(".uploadBox .up span").html(fileName);
	}
	
	function hideProgress(){
		$(".uploadBox").css("display","none");
	}
	
	function getProgress(numb){
		var baifenbi=numb+"%";
		if(numb>=100){
			$('.baiNow').html("100%");
			$('.downCenter').width(304);
			clearInterval(timer);
			hideProgress();
		}else{
			$('.baiNow').html(Math.round(baifenbi));
			$('.downCenter').width(304*numb/100);
		}
				
	}
	
	function listVideo(data){
		$("#up1P").html("");
		for (var i = 0; i < data.length; i++) {
			var file = data[i];
			$("#up1P").append('<i><img onclick="deleteFile('+file.id+',\'video\');" src="${pageContext.request.contextPath }/static/img/del.png"/>'+file.fileName+'</i>');
		}
	}
	function listCertifications(data){
		$("#up2P").html("");
		for (var i = 0; i < data.length; i++) {
			var file = data[i];
			$("#up2P").append('<i><img onclick="deleteFile('+file.id+',\'certification\');" src="${pageContext.request.contextPath }/static/img/del.png"/>'+file.fileName+'</i>');
		}
		
	}
	
	function listFlightTicket(data){
		$("#up13P").html("");
		for (var i = 0; i < data.length; i++) {
			var file = data[i];
			$("#up13P").append('<i><img onclick="deleteFile('+file.id+',\'ticket\');" src="${pageContext.request.contextPath }/static/img/del.png"/>'+file.fileName+'</i>');
		}
		
	}
	
	function uploadFlightFile(){
		var flight = $("#up13Input").val();
		if("" == flight){
			alert("please choose a file");
			return ;
		}
		$("#thirteenForm").submit();
	}
	
	function toInfoPage(resumeId){
		window.location.href = "${pageContext.request.contextPath }/info/page/add?resumeId="+resumeId;
	}
	
	function toUploadPage(resumeId){
		window.location.href = "${pageContext.request.contextPath }/upload/"+resumeId+"/doc";
		
	}
	
	function deleteFile(id,type){
		$.ajax({
			url : "${pageContext.request.contextPath }/upload/"+id+"/delete",
			type : "POST",
			dataType : "json",
			success : function(data){
				if(data.status == 1){
					if("video" == type){
						
						interview.getFiles("${resumeId}", 1, listVideo);
					}else if("ticket" == type){
						interview.getFiles("${resumeId}", 3, listFlightTicket);
						
					}else{
						
						interview.getFiles("${resumeId}", 4, listCertifications);
						
					}
				}
			},
			error: function(XMLHttpRequest, textStatus, errorThrown) {
				 alert(XMLHttpRequest.status+"-"+XMLHttpRequest.readyState + "-" + textStatus);
		    }
		});
	}
	
	function checkFile(resumeId){
		$.ajax({
			url : "${pageContext.request.contextPath }/upload/files/list",
			type : "POST",
			dataType : "json",
			data:{resumeId:resumeId},
			success : function(data){
				if(data.status == 1){
					var hasResume = false;
					var hasPhoto = false;
					 if(data.data.length > 1){
						 for (var i = 0; i < data.data.length; i++) {
							if(0 == data.data[i].type){
								hasResume = true;
							}else if(2 == data.data[i].type){
								hasPhoto = true;
							}
						}
					 }
					if(!hasResume || !hasPhoto){
						$("#resumeFile").addClass("resumeFile");
					}
				}
			},
			error: function(XMLHttpRequest, textStatus, errorThrown) {
				 alert(XMLHttpRequest.status+"-"+XMLHttpRequest.readyState + "-" + textStatus);
		    }
		});
	}


	</script>
	</head>
	<body>
		<div class="box">
			<!--导航条-->
			<div class="nav">
				<a href="${pageContext.request.contextPath}/view/index.html" class="homeA">Home</a><a href="login.html" class="loginA">Status</a>
			</div>
			<!--右上角 信封-->
			<div class="messRight">
				<div class="up"></div>
				<p class="down">Message</p>
			</div>
			
			<!--添加禁止操作的蒙版-->
			<div id="mask"></div>
			
			<!--以中间棍为基础-->
			<div class="lineCenter">
				<!--最上边的进度条-->
				<div class="now"></div>
				<!--第1步-->
				<div class="step one">
					<div class="dot"></div>
					<div class="rightDetail">
						<div class="gou"></div><span class="num">01<i></i></span>
						<em>
							<i>Applications submitted</i>
							<i class="i2">Waiting for screening</i>
							<input type="button" name="" onclick="toInfoPage(${resumeId});" id="" value="Update Personal Information" />
							<input type="button" id="resumeFile" onclick="toUploadPage(${resumeId});" value="Update Resume and Photo"/>
						</em>
					</div>
				</div>
				
				<!--第2步-->
				<div class="step two">
					<div class="dot"></div>
					<div class="ban"></div>
					<div class="icon iconBan"></div>
					<div class="rightDetail">
						<div class="gou"></div><span class="num">02<i></i></span><em>Resume in screening process</em>
					</div>
				</div>
				
				<!--第3步-->
				<div class="step three">
					<div class="dot"></div>
					<div class="ban"></div>
					<div class="icon iconBan"></div>
					<div class="rightDetail">
						<div class="gou"></div><span class="num">03<i></i></span>
						<form id="threeForm" action="${pageContext.request.contextPath }/upload/video" method="post"  enctype="multipart/form-data">
						<div class="uploadBox">
							<p class="up">
								<span></span>
								<em class="baiNow"></em>
							</p>
							<p class="down">
								<span class="downCenter"></span>
							</p>
						</div>
						<em>
						
							<i>Submit introduction video</i>
								<i class="i2 i8"><i class="i21">Submit introduction video </i>
								<input type="hidden" name="resumeId" value="${resumeId }" id="resumeId3">
								<input type="hidden" name="fileType" value="video" id="fileType3">
								<span id="uploadVideoBtn">upload</span><input type="file" name="video" id="up1" value="upload" /><label id="up1Label" for="up1"></label></i>
								<p id="up1P"></p>
								
								<i class="i2"><i class="i22">Submit certificates</i> 
								<span  id="uploadCerBtn">upload</span><input type="file" name="certification" id="up2" value="upload" /><label  id="up2Label" for="up2"></label></i>
								<p id="up2P"></p>
								<input  type="button" name="" id="finishUpload" value="Finish upload"/>
						</em>
						</form>
					</div>
				</div>
				
				<!--第4步-->
				<div class="step four">
					<div class="dot"></div>
					<div class="ban"></div>
					<div class="icon iconBan"></div>
					<div class="rightDetail">
						<div class="gou"></div><span class="num">04<i></i></span><em>Video received</em>
					</div>
				</div>
				
				<!--第5步-->
				<div class="step five">
					<div class="dot"></div>
					<div class="ban"></div>
					<div class="icon iconBan"></div>
					<div class="rightDetail">
						<div class="gou"></div><span class="num">05<i></i></span><em>Waiting for 1st interview invitation</em>
					</div>
				</div>
				
				<!--第6步-->
				<div class="step six">
					<div class="dot"></div>
					<div class="ban"></div>
					<div class="icon iconBan"></div>
					<div class="rightDetail">
						<div class="gou"></div><span class="num">06<i></i></span><em>Waiting for 1st interview result</em>
					</div>
				</div>
				
				<!--第7步-->
				<div class="step seven">
					<div class="dot"></div>
					<div class="ban"></div>
					<div class="icon iconBan"></div>
					<div class="rightDetail">
						<div class="gou"></div><span class="num">07<i></i></span><em>Waiting for 2nd interview invitation</em>
					</div>
				</div>
				
				<!--第8步-->
				<div class="step eight">
					<div class="dot"></div>
					<div class="ban"></div>
					<div class="icon iconBan"></div>
					<div class="rightDetail">
						<div class="gou"></div><span class="num">08<i></i></span><em>Waiting for 2nd interview result</em>
					</div>
				</div>
				
				<!--第9步-->
				<div class="step nine">
					<div class="dot"></div>
					<div class="ban"></div>
					<div class="icon iconBan"></div>
					<div class="rightDetail">
						<div class="gou"></div><span class="num">09<i></i></span>
						<em>
							<i>Waiting for the job offer</i>
							<i class="i2"></i>
							<input type="button" name="" onclick="accepted(1,${resumeId});" id="nineAcceptBtn" value="Accept the job offer" /><a href="javascript:void(0);" id="nineDeclineBtn" onclick="accepted(0,${resumeId});">Decline</a>
						</em>
					</div>
				</div>
				
				<!--第10步-->
				<div class="step ten">
					<div class="dot"></div>
					<div class="ban"></div>
					<div class="icon iconBan"></div>
					<div class="rightDetail">
						<div class="gou"></div><span class="num">10<i></i></span>
						<em>
							<i>Offer accepted</i>
							<i id="countDown"></i>
							<i class="i2">Preparing visa material</i>
						</em>
					</div>
				</div>
				
				<!--第11步-->
				<div class="step eleven">
					<div class="dot"></div>
					<div class="ban"></div>
					<div class="icon iconBan"></div>
					<div class="rightDetail">
						<div class="gou"></div><span class="num">11<i></i></span>
						<em>
							<i>Visa materia submitted</i>
							<i class="i2">Visa is being processed</i>
						</em>
					</div>
				</div>
				
				<!--第12步-->
				<div class="step twelve">
					<div class="dot"></div>
					<div class="ban"></div>
					<div class="icon iconBan"></div>
					<div class="rightDetail">
						<div class="gou"></div><span class="num">12<i></i></span>
						<em>
							<i>Visa succeed</i>
							<i class="i2"><i class="i21">Visa received date:</i><input type="text" name="" id="up12" value="" /><span onclick="reveiveVisa(${resumeId})">Received visa</span></i>
						</em>
					</div>
				</div>
				
				<!--第13步-->
				<div class="step thirteen">
					<div class="dot"></div>
					<div class="ban"></div>
					<div class="icon iconBan"></div>
					<div class="rightDetail">
						<div class="gou"></div><span class="num">13<i></i></span>
						<form id="thirteenForm" action="${pageContext.request.contextPath }/upload/ticket" method="post"  enctype="multipart/form-data">
						<em>
							<i>Determine China arrival date</i>
							<input type="hidden" name="resumeId" value="${resumeId }"/>
							<i class="i2"><i class="i22">Fight ticket image upload</i><span class="inutspanBox"><input type="file" name="flightTicket" id="up13Input" value="upload" /><label id="up13label" for="up13Input"></label><span id="up13File" onclick="uploadFlightFile();">upload</span></span></i>
							<p id="up13P"></p>
							<i class="i2 i3"><i class="i21">Fight landing place</i><span class="inutspanBox"><input type="text" name="" id="up13Place" value="" /></span></i>
							<i class="i2 i3"><i class="i21">Fight landing date</i><span class="inutspanBox"><input type="text" name="" id="up13" value="" /></span></i>
							<input id="subt" type="button" onclick="uploadFlight(${resumeId});" value="submit"/>
						</em>
						</form>
					</div>
				</div>
				
				<!--第14步-->
				<div class="step fourteen">
					<div class="dot"></div>
					<div class="ban"></div>
					<div class="icon iconBan"></div>
					<div class="rightDetail">
						<div class="gou"></div><span class="num">14<i></i></span><em>Completion of settle down, congratulations!</em>
					</div>
				</div>
				
				<!--底部白条-->
				<div class="step last"></div>
			</div>
		</div>
	</body>
	
	</html>
	