$(function(){
	//选性别
	$('.sex label').on("click",function(){
		$('.sex label').css({
			"color":"#e3e7ed",
			"border":"1px solid #e3e7ed"
		});
		$(this).css({
			"color":"#61c0e8",
			"border":"1px solid #61c0e8"
		});
	});
	
	//选年龄段
	$('.age label').on("click",function(){
		$('.age label').find('em').css("display",'none');
		$(this).find('em').css("display","block");
	});
	
	//增加专业技能
	$('.addBox').find('span').on('click',function(){
		var newSpecialInput='<input type="text" name="" id="" value="" />';
		$('.moreSpecical').append(newSpecialInput);
	});
	
	//选工作经验
	$('.xia').on('click',function(){
		$('.numberSpan').toggleClass('none');
	});
	
	$('.numberSpan').find('em').on('click',function(){
		var yearHtml=$(this).html();
		$('.yearsNumber').val(yearHtml);
		$('.numberSpan').toggleClass('none');
	});
	

	
	//生日
	layui.use('laydate', function(){
		var laydate = layui.laydate;
		var start = {
			min: '1900-01-01 00:00:00'
			,max: '2099-06-16 23:59:59'
			,istoday: false
			,choose: function(datas){
			end.min = datas; //开始日选好后，重置结束日的最小日期
			end.start = datas; //将结束日的初始值设定为开始日
			}
		};
		var end = {
			 min: '1900-01-01 00:00:00'
				 
			 ,max: '2099-06-16 23:59:59'
			 ,istoday: false
			 ,choose: function(datas){
			  start.max = datas; //结束日选好后，重置开始日的最大日期
			 }
		};

		
		$('#birth').on('focus',function(){
			start.elem = this;
	 		laydate(start);
		});
		$('#birth').on('click',function(){
			start.elem = this;
	 		laydate(start);
		});
	});
});