/*page3  3块*/
      /*$(document).ready(function() {
        $("#carousel").featureCarousel({
			autoPlay:7000,
			trackerIndividual:false,
			trackerSummation:false,
			bottomPadding:20,
			smallFeatureWidth:.7,
			smallFeatureHeight:.7,
			sidePadding:0,
			smallFeatureOffset:0
		});
      });*/
	  
/*page4飘*/

$(window).load(function(){
	function moveRocket(){
		/*$(".qtleft").animate({'left':'+=30'},5000).delay(1000)
					.animate({'left':'-=30'},5000,function(){
				setTimeout(moveRocket,1000);
		});
		$(".qtright").animate({'right':'+=30'},5000).delay(1000)
					.animate({'right':'-=30'},5000,function(){
				setTimeout(moveRocket,1000);
		});
		$(".qtbottom").animate({'bottom':'+=30'},5000).delay(1000)
					.animate({'bottom':'-=30'},5000,function(){
				setTimeout(moveRocket,1000);
		});
		$(".qttop").animate({'top':'+=30'},5000).delay(1000)
					.animate({'top':'-=30'},5000,function(){
				setTimeout(moveRocket,1000);
		});
		*/
		$("#cloud1").animate({'left':'+=50'},5000).delay(1000)
					.animate({'left':'-=50'},5000,function(){
				setTimeout(moveRocket,1000);
		});
	};
	moveRocket();
});
/*page4*/
 $(function(){
		  $('.row1,.row2,.row3').adipoli({
                    'startEffect' : 'transparent',
                    'hoverEffect' : 'boxRandom'
                });
		 
	  });
/*page5*/
$(document).ready(function () {
	$(".page5 li .pag5").each(function(index, element){
		
		
		
		 var me = $(this);
		 var $img = $(this).find("img");
		 $img.load(function(){
			 var imgw = $img.width(); 
			 var imgh = $img.height(); 
			 var imgw1 = imgw + imgw * 0.2;
			 var imgh1 = imgh + imgh * 0.2;
			
			 
			 me.hover(function(){
				 me.parent().addClass("curr");
				 me.find("img").animate({width:imgw1, height:imgh1},"normal");
				 },function(){
					 me.parent().removeClass("curr");
					 me.find("img").animate({width:imgw, height:imgh},"normal");
					 
					 })
			})
			 
		 });
		 

	})

/*page title动画*/
$(document).load(function () {
	

	})
