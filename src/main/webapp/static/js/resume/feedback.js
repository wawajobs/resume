

	var feedback = {
			baseUrl : "",
			URL : {
				feedback : "/feedback/add"
			},
			add : function(name,email,title,message){
				$.ajax({
					url: feedback.baseUrl+feedback.URL.feedback,
					type:"post",
					dataType:"json",
					data : {name:name,email:email,title:title,message:message},
					success : function(data){
						if(data.status == 1){
							alert("thank you for your message");
						}else{
							alert(data.message);
						}
					},
					error: function(XMLHttpRequest, textStatus, errorThrown) {
						 alert(XMLHttpRequest.status+"-"+XMLHttpRequest.readyState + "-" + textStatus);
				    }
				});
			}
	}