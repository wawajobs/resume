var swiper = new Swiper('.swiper-container', {		//最外层的盒子
    pagination: '.swiper-pagination',
    paginationClickable: true,
   	loop:true,
// 	autoplay:5000,
   	autoplayDisableOnInteraction :false
});
	  

$(function(){
	var windowWidth=$(window).width();
	if(windowWidth>768){
		/*how Do 四个圈布局*/	  
		var circleBoxLength=$('.circleBox').width();
		var cirCK=Math.round(circleBoxLength*0.17);
		$('.circleBoxAlone').width(cirCK+"px");
		var cirBian=Math.round(cirCK*0.91);
		var marginTop=Math.round(cirBian/2);
		
		$('.circleBoxAlone img').width(cirBian+"px");
		$('.circleBoxAlone img').height(cirBian+"px");
		$('.circleBox').find('.right').css('margin-top',marginTop-15);
		
		
		/*why 布局*/
		$('.boxAlone').find('p').on('mousemove',function(){
			var nowDetail=$(this).html();
			$(this).attr('title',nowDetail);
		})
		
		//========================================第二版改进====整屏滚动=========================================
		/*第一屏*/
		/*var AllHeight=$(document).height();
		var bannerHeight=AllHeight-122;
		$('.banner').height(bannerHeight);*/
		
		/*第二屏  lun2 who*/
		var lun2DownWidth=$('.lun2Box').find('.down').width();
		var lun2DownHeight=Math.round(lun2DownWidth*0.45);
		$('.lun2Box').find('.down').height(lun2DownHeight+'px');
		
		
	}
})

/*lun2 轮播图*/
window.onload=function(){
	var odown=document.getElementById('down');
	var aImg=odown.children;
	var nowIndex=0;
	
	//书朋版淡入淡出
	function jianbian(){
		if(nowIndex==aImg.length-1){
			nowIndex=0;
			move(aImg[aImg.length-1],'opacity',0);
			move(aImg[nowIndex],'opacity',100)
					
		}else{
			nowIndex++;
			move(aImg[nowIndex-1],'opacity',0);
			move(aImg[nowIndex],'opacity',100);
		}
	}
//	var timer=setInterval(jianbian,2000);
}

/*lun3 teaching*/
/*var lun3Width=$('.lun3Box').width();
var lun3Height=Math.round(lun3Width*0.43);
$('.lun3Box').height(lun3Height+'px');*/


















