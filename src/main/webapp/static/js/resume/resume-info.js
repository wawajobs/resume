

var resumeInfo = {
	baseUrl : "",	
	URL : {
		save : "/info/save",
		update : "/info/update",
		
	},
	findById : function(resumeId){
		$.ajax({
			url:resumeInfo.baseUrl + "/info/"+resumeId +"/info",
			type:"get",
			dataType:"json",
			data:{},
			success : function(data){
				if(data.status == 1){
					var resume = data.resumeInfo;
					$("#id").val(resumeInfo.checkNull(resume.id));
					$("#name").val(resumeInfo.checkNull(resume.name));
					$("#position").val(resumeInfo.checkNull(resume.position));
					$("#birth").val(resumeInfo.checkNull(resume.birthDate));
					$("#tel").val(resumeInfo.checkNull(resume.phone));
					$("#citizenship").val(resumeInfo.checkNull(resume.citizenship));
					$("#education").val(resumeInfo.checkNull(resume.education));
					$("#major").val(resumeInfo.checkNull(resume.major));
					$("#obtain").val(resumeInfo.checkNull(resume.country));
					$("#degree").val(resumeInfo.checkNull(resume.certification));
					$("#specialized").val(resumeInfo.checkNull(resume.specialized));
					$("#experienceLength").val(resumeInfo.checkNull(resume.experienceLength));
					$("#salary").val(resumeInfo.checkNull(resume.salary));
					$("#location").val(resumeInfo.checkNull(resume.location));
					$("#otherPositions").val(resumeInfo.checkNull(resume.otherPositions));
					$("#recommended").val(resumeInfo.checkNull(resume.recommended));
					if("0-7" == resumeInfo.checkNull(resume.age)){
						$("#0-7").attr("checked","checked");
						$('.age label').find('em').css("display",'none');
						$("#0-7-lable").find('em').css("display","block");
					}else if("7-12" == resumeInfo.checkNull(resume.age)){
						$("#7-12").attr("checked","checked");
						$('.age label').find('em').css("display",'none');
						$("#7-12-lable").find('em').css("display","block");
						
					}else if("12-18" == resumeInfo.checkNull(resume.age)){
						$("#12-18").attr("checked","checked");
						$('.age label').find('em').css("display",'none');
						$("#12-18-lable").find('em').css("display","block");
						
					}else  if("adults" == resumeInfo.checkNull(resume.age)){
						$("#adults").attr("checked","checked");
						$('.age label').find('em').css("display",'none');
						$("#adults-lable").find('em').css("display","block");
						
					}
					
					if("m" == resumeInfo.checkNull(resume.gender)){
						$("#sexman").attr("checked","checked");
						$('.sex label').css({
							"color":"#e3e7ed",
							"border":"1px solid #e3e7ed"
						});
						$("#sexman-label").css({
							"color":"#61c0e8",
							"border":"1px solid #61c0e8"
						});
					}else if("f" == resumeInfo.checkNull(resume.gender)){
						$("#sexwoman").attr("checked","checked");
						$('.sex label').css({
							"color":"#e3e7ed",
							"border":"1px solid #e3e7ed"
						});
						$("#sexwoman-label").css({
							"color":"#61c0e8",
							"border":"1px solid #61c0e8"
						});
					}if("" == resumeInfo.checkNull(resume.gender)){
						$("#sexoths").attr("checked","checked");
						$('.sex label').css({
							"color":"#e3e7ed",
							"border":"1px solid #e3e7ed"
						});
						$("#sexoths-label").css({
							"color":"#61c0e8",
							"border":"1px solid #61c0e8"
						});
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
	save : function(type){
		$.ajax({
			url:resumeInfo.baseUrl + resumeInfo.URL.save,
			type:"post",
			dataType:"json",
			data:$("#infoForm").serialize(),
			success : function(data){
				if(data.status == 1){
					if(0 == type){
						$("#id").val(data.resumeInfo.id);
						alert("successed");
					}else{
						window.location.href="";
					}
				}else{
					alert("error");
				}
			},
			error: function(XMLHttpRequest, textStatus, errorThrown) {
				 alert(XMLHttpRequest.status+"-"+XMLHttpRequest.readyState + "-" + textStatus);
		    }
		});
	},
	
	update : function(){
		$.ajax({
			url:resumeInfo.baseUrl + resumeInfo.URL.update,
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
	
	checkNull : function(val){
		if(null == val){
			return "";
		}
		return val;
	}
	
};