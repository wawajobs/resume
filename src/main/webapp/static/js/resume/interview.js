
Date.prototype.Format = function (fmt) { //author: lishuai 
	    var o = {
	        "M+": this.getMonth() + 1, //月份 
	        "d+": this.getDate(), //日 
	        "h+": this.getHours(), //小时 
	        "m+": this.getMinutes(), //分 
	        "s+": this.getSeconds(), //秒 
	        "q+": Math.floor((this.getMonth() + 3) / 3), //季度 
	        "S": this.getMilliseconds() //毫秒 
	    };
	    if (/(y+)/.test(fmt)) fmt = fmt.replace(RegExp.$1, (this.getFullYear() + "").substr(4 - RegExp.$1.length));
	    for (var k in o)
	    if (new RegExp("(" + k + ")").test(fmt)) fmt = fmt.replace(RegExp.$1, (RegExp.$1.length == 1) ? (o[k]) : (("00" + o[k]).substr(("" + o[k]).length)));
	    return fmt;
	};

var interview = {
	baseUrl : "",
	currentStep : 1,
	URL : {
		save : "",
		update : "",
		list : "/interview/flow/list",
		
	},
	save : function(){
		$.ajax({
			url:interview.URL.save,
			type:"post",
			dataType:"json",
			data:{},
			success : function(data){
				
			},
			error: function(XMLHttpRequest, textStatus, errorThrown) {
				 alert(XMLHttpRequest.status+"-"+XMLHttpRequest.readyState + "-" + textStatus);
		    }
		});
	},
	
	update : function(){
		$.ajax({
			url:interview.URL.update,
			type:"post",
			dataType:"json",
			data:{},
			success : function(data){
				
			},
			error: function(XMLHttpRequest, textStatus, errorThrown) {
				 alert(XMLHttpRequest.status+"-"+XMLHttpRequest.readyState + "-" + textStatus);
		    }
		});
	},
	
	findByResumeId : function(resumeId,stepHeightArray){
		$.ajax({
			url:interview.baseUrl + "/interview/"+ resumeId +"/status",
			type:"get",
			dataType:"json",
			data:{},
			success : function(data){
				if(data.status == 1){
					var flow = data.interviewFlow;
					var step = flow.step;
					var nowHeight= stepHeightArray[0];
					for (var i = 0; i < step && step < stepHeightArray.length; i++) {
						var height = stepHeightArray[i+1];
						nowHeight += height;
					}
					$('.now').height(nowHeight);
					var classAry = ["one","two","three","four","five","six","seven","eight","nine","ten","eleven","twelve","thirteen","fourteen"];
					for (var i = 0; i < step; i++) {
						$('.'+classAry[i]+' .num i').css("display",'block');
					}
					if(step < 14){
						$('.'+classAry[step]+' .num').addClass('bigNow');
						$('.'+classAry[step]+' .num').next('em').addClass('bigNowEm');
					}
					if(step >=3 ){
						$('#finishUpload').remove();
					}
					if(step >= 9){
						$(".nine em input").remove();
						$(".nine em a").remove();
						
					}
					if(step >= 13){
						$("#subt").remove();
					}
					if(null != flow.arriedDate){
						$('.nine .i2').html('Last expected date of arrival to China ' + new Date().Format("yyyy-MM-dd"));
						var countDown = lastDate - new Date().getTime();
						if(countDown < 0){
							$('.ten #countDown').html('Count down 0 days');
						}else{
							$('.ten #countDown').html("Count down " + Math.ceil(countDown/24/60/60/1000) + " days");
						}
					}
					
					
					if(null != flow.visaDate){
						$("#up12").val(new Date(flow.visaDate).Format("yyyy-MM-dd"));
					}
					
					if(null != flow.place){
						$("#up13Place").val(flow.place);
					}
					
					if(null != flow.flightDate){
						$("#up13").val(new Date(flow.flightDate).Format("yyyy-MM-dd"));
						
					}
					
				}else{
					alert(data.message);
				}
			},
			error: function(XMLHttpRequest, textStatus, errorThrown) {
				 alert(XMLHttpRequest.status+"-"+XMLHttpRequest.readyState + "-" + textStatus);
		    }
		});
	},
	list : function(fillTable){
		$.ajax({
			url: interview.baseUrl+interview.URL.list,
			type:"post",
			dataType:"json",
			data:{step:interview.currentStep},
			success : function(data){
				if(data.status == 1){
					var fileMap = new Map();
					var videoMap = new Map();
					var datas = new Array();
					for (var i = 0; i < data.data.length; i++) {
						var interview = data.data[i];
						list.header=new Array("Name","<span >Age</span>","Gender","Skype","Email address",
								"Citizenship","Education","Education country",
								"Resume","Video","Message","Backward","Forward","");
						
						var resumeDown = "-";
						if(null != interview.resumeInfo.resumeFiles){
							var downloadHtml = "";
							var hasNewFile = false;
							for (var j = 0; j < interview.resumeInfo.resumeFiles.length; j++) {
								var file = interview.resumeInfo.resumeFiles[j];
								downloadHtml += ("<p><a href='javascript:void(0);' onclick='interview.download(null,"+file.id+")'>"+file.fileName+"</a></p>");
								if(file.downloaded == 0){
									hasNewFile = true;
								}
							}
							var style = hasNewFile ? "style='color:blue;'" : "style='color:black;'";
							var con = hasNewFile ? "download new" : "download";
							if(interview.resumeInfo.resumeFiles.length > 1){
								fileMap.put(interview.resumeId,downloadHtml);
								resumeDown = "<a href='javascript:void(0);' data-reveal-id='myDownload' " + style +" resumeId="+interview.resumeId+">"+con+"</a>";
							}else{
								resumeDown = "<a href='javascript:void(0);'  " + style +"   onclick='interview.download(this,"+interview.resumeInfo.resumeFiles[0].id+")'>"+con+"</a>";
							}
						}
						
						var videoDown = "-";
						if(null != interview.resumeInfo.videos && interview.resumeInfo.videos.length != 0){
							var downloadHtml = "";
							var hasNewFile = false;
							for (var j = 0; j < interview.resumeInfo.videos.length; j++) {
								var file = interview.resumeInfo.videos[j];
								downloadHtml += ("<p><a href='javascript:void(0);' onclick='interview.download(null,"+file.id+")'>"+file.fileName+"</a></p>");
								if(file.downloaded == 0){
									hasNewFile = true;
								}
							}
							var style = hasNewFile ? "style='color:blue;'" : "style='color:black;'";
							var con = hasNewFile ? "download new" : "download";
							if(interview.resumeInfo.videos.length > 1){
								videoMap.put(interview.resumeId,downloadHtml);
								videoDown = "<a href='javascript:void(0);' data-reveal-id='myVideoDownload'  " + style +" resumeId="+interview.resumeId+">"+con+"</a>";
							}else{
								videoDown = "<a href='javascript:void(0);'  " + style +"   onclick='interview.download(this,"+interview.resumeInfo.videos[0].id+")'>"+con+"</a>";
							}
//							if("0" == interview.resumeInfo.videos.downloaded){
//								
//								videoDown = "<a href='javascript:void(0);' style='color:blue;' onclick='download(\'video_"+interview.resumeId+"\',\'"+
//										interview.video.fileAddress+"\');' id='video_"+interview.resumeId+"'>download new</a>";
//							}else{
//								videoDown = "<a href='javascript:void(0);' onclick='download(\'video_"+interview.resumeId+"\',\'"+interview.video.fileAddress+"\');' id='video_"+interview.resumeId+"'>download</a>";
//							}
							
						}
						
						
						
						var message = "<a href='#'>message</a>";
						datas[i] = new Array(interview.resumeId,checkNull(interview.resumeInfo.name),
								checkNull(interview.resumeInfo.age),
								checkNull(interview.resumeInfo.gender),
								checkNull(interview.resumeInfo.phone),
								checkNull(interview.resumeInfo.userInfo.email),
								checkNull(interview.resumeInfo.citizenship),
								checkNull(interview.resumeInfo.education),
								checkNull(interview.resumeInfo.country),
								
								resumeDown,videoDown,message,
//								"<input type='checkbox' name='backword' value='"+interview.resumeId+"'/>",
//								"<input type='checkbox' name='forword' value='"+interview.resumeId+"'/>",
								"<a class='more' href='javascript:void(0);' onclick='backward("+interview.resumeId+","+interview.step+")' >backward</a>",
								"<a class='more' href='javascript:void(0);' onclick='forward("+interview.resumeId+","+interview.step+")' >forward</a>",
								"<a class='more' href='javascript:void(0);' onclick='showMoreInfo("+interview.resumeId+","+interview.step+")' >more</a>");
						
					}
					fillTable(datas);
					
					$('a[data-reveal-id]').on('click', function(e) {
						e.preventDefault();
						var modalLocation = $(this).attr('data-reveal-id');
						if("myVideoDownload" == modalLocation){
							
							$('#'+modalLocation + " .content").html(videoMap.get($(this).attr('resumeId')));
						}else{
							
							$('#'+modalLocation + " .content").html(fileMap.get($(this).attr('resumeId')));
						}
						$('#'+modalLocation).reveal($(this).data());
					});

				}else{
					alert(data.message);
				}
			},
			error: function(XMLHttpRequest, textStatus, errorThrown) {
				 alert(XMLHttpRequest.status+"-"+XMLHttpRequest.readyState + "-" + textStatus);
		    }
		});
	},
	
	updateStatus : function(resumeId,step){
		$.ajax({
			url:interview.baseUrl + "/interview/"+ resumeId +"/"+step+"/update",
			type:"PUT",
			dataType:"json",
			data:{},
			success : function(data){
				if(data.status == 1){
					var flow = data.interviewFlow;
					var step = flow.step;
					var nowHeight= 0;
					for (var i = 0; i < step; i++) {
						var height = stepHeightArray[i];
						nowHeight += height;
					}
					$('.now').height(nowHeight);
				}else{
					alert(data.message);
				}
			},
			error: function(XMLHttpRequest, textStatus, errorThrown) {
				 alert(XMLHttpRequest.status+"-"+XMLHttpRequest.readyState + "-" + textStatus);
		    }
		});
	},
	forward : function(resumeId,step,arrivedDate,accepted){
		var data = {};
		if(arrivedDate){
			data["arrivedDate"] = arrivedDate;
		}
		if(accepted){
			data["accepted"] = accepted;
		}
		$.ajax({
			url:interview.baseUrl + "/interview/"+ resumeId +"/"+step+"/update",
			type:"POST",
			dataType:"json",
			data:data,
			success : function(data){
				if(data.status == 1){
					interview.list(fillTable);
				}else{
					alert(data.message);
				}
			},
			error: function(XMLHttpRequest, textStatus, errorThrown) {
				 alert(XMLHttpRequest.status+"-"+XMLHttpRequest.readyState + "-" + textStatus);
		    }
		});
	},
	finishStep : function(resumeId,step,accepted,visaDate,place,flightDate){
		var data = {};
		if(accepted != undefined && accepted != null){
			data["accepted"] = accepted;
		}
		if(visaDate != undefined && visaDate != null){
			data["visaDate"] = visaDate;
		}
		if(place != undefined && place != null){
			data["place"] = place;
		}
		if(flightDate != undefined && flightDate != null){
			data["flightDate"] = flightDate;
		}
		$.ajax({
			url:interview.baseUrl + "/interview/"+ resumeId +"/"+step+"/update",
			type:"POST",
			dataType:"json",
			data:data,
			success : function(data){
				if(data.status == 1){
					window.location.href = interview.baseUrl + "/interview/page";
				}else{
					alert(data.message);
				}
			},
			error: function(XMLHttpRequest, textStatus, errorThrown) {
				 alert(XMLHttpRequest.status+"-"+XMLHttpRequest.readyState + "-" + textStatus);
		    }
		});
	},
	getFiles : function(resumeId,type,callback){
		
		$.ajax({
			url:interview.baseUrl + "/upload/files/"+ resumeId +"/"+type,
			type:"POST",
			dataType:"json",
			success : function(data){
				if(data.status == 1){
					callback(data.data);
				}else{
					alert(data.message);
				}
			},
			error: function(XMLHttpRequest, textStatus, errorThrown) {
				 alert(XMLHttpRequest.status+"-"+XMLHttpRequest.readyState + "-" + textStatus);
		    }
		});
	},
	download : function(element,id){
		if(null != element){
			element.innerHTML = "download";
			element.style.color = "black";
		}
		window.open(interview.baseUrl + "/upload/" + id +"/download" );
	}
	
	
	
};

function backward(resumeId,step){
	interview.forward(resumeId, step-1);
}

function forward(resumeId,step,arriveDate){
	interview.forward(resumeId, step+1,arriveDate);
	
}

function accepted(accepted,resumeId){
	interview.finishStep(resumeId,9,accepted);
}

function reveiveVisa(resumeId){
	var visaDate = $("#up12").val();
	interview.finishStep(resumeId,12,null,visaDate);
}

function uploadFlight(resumeId){
	var place = $("#up13Place").val();
	var flightDate = $("#up13").val();
	interview.finishStep(resumeId,13,null,null,place,flightDate);
}

function checkNull(val){
	if(null == val){
		return "";
	}
	return val;
}

function showMoreInfo(resumeId,step){
	//TODO show info
	
	$.ajax({
		url:interview.baseUrl + "/interview/"+resumeId +"/detail",
		type:"get",
		dataType:"json",
		data:{},
		success : function(data){
			if(data.status == 1){
				var flow = data.interviewFlow;
				var resume = flow.resumeInfo;
				var user = resume.userInfo;
				if(null != user){
					$("#d_email").html(checkNull(user.email));
				}
				$("#d_name").html(checkNull(resume.name));
				$("#d_position").html(checkNull(resume.position));
				$("#d_birthDate").html(checkNull(resume.birthDate));
				$("#d_phone").html(checkNull(resume.phone));
				$("#d_citizenship").html(checkNull(resume.citizenship));
				$("#d_education").html(checkNull(resume.education));
				$("#d_major").html(checkNull(resume.major));
				$("#d_county").html(checkNull(resume.country));
				$("#d_certification").html(checkNull(resume.certification));
				$("#d_specialized").html(checkNull(resume.specialized));
				$("#d_experienceLength").html(checkNull(resume.experienceLength));
				$("#d_salary").html(checkNull(resume.salary));
				$("#d_location").html(checkNull(resume.location));
				$("#d_otherPositions").html(checkNull(resume.otherPositions));
				$("#d_age").html(checkNull(resume.age));
				if("m" == checkNull(resume.gender)){
					$("#d_gender").html("Male");
				}else if("f" == checkNull(resume.gender)){
					$("#d_gender").html("Female");
				}if("" == checkNull(resume.gender)){
					$("#d_gender").html("Other");
				} 
				var lastDate = checkNull(flow.arrivedDate);
				if("" != lastDate){
					$("#d_lastDate").val(new Date(lastDate).Format("yyyy-MM-dd"));
				}
				
				var countDown = lastDate - new Date().getTime();
				if(countDown < 0){
					$("#d_count_down").html(0 + "days");
				}else{
					$("#d_count_down").html(Math.ceil(countDown/24/60/60/1000) + " days");
				}
				if(null != flow.visaDate){
					$("#d_visa_date").html(new Date(flow.visaDate).Format("yyyy-MM-dd"));
				}
//				$("#d_vis_date").html(checkNull(flow.visaDate));
				var flightDate = "";
				if(null != flow.flightDate){
					flightDate = new Date(flow.flightDate).Format("yyyy-MM-dd");
				}
				$("#d_flight").html(checkNull(flow.place) + " " + flightDate);
				
				var flightFileDown = "-";
				if(null != flow.flightTicket){
					var hasNewFile = flow.flightTicket == 0;
					var style = hasNewFile ? "style='color:blue;'" : "style='color:black;'";
					var con = hasNewFile ? "download new" : "download";
					flightFileDown = "<a href='javascript:void(0);'  " + style +"   onclick='interview.download(this,"+flow.flightTicket.id+")'>"+con+"</a>";
				}
				
				$("#d_ticket").html(flightFileDown);
				
				$("#pre").on('click',function(){
					backward(resumeId, step);
					$(".close-reveal").click();
				});
				$("#clo").on('click',function(){
					forward(resumeId,step,$("#d_lastDate").val());
					$(".close-reveal").click();
				});
			}else{
				alert(data.message);
			}
		},
		error: function(XMLHttpRequest, textStatus, errorThrown) {
			 alert(XMLHttpRequest.status+"-"+XMLHttpRequest.readyState + "-" + textStatus);
	    }
	});
	
	$('#myInfo').reveal();
}


function proccessStatus(step){
	switch (step) {
	case 1:
		
		;
	case 2:
		
		;
	case 3:
		
		;
	case 4:
		
		;
	case 5:
		
		;
	case 6:
		
		;
	case 7:
		
		;
	case 8:
		
		;
	case 9:
		
		;
	case 10:
		
		;
	case 11:
		
		;
	case 12:
		
		;
	case 13:
		
		;
	case 14:
		
		;

	default:
		break;
	}
}






