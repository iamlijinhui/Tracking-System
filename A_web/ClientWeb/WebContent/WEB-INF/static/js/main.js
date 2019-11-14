$(function(){
	$('.carousel').carousel({
		interval: 5000
	})
	$(".newsArt li").mouseenter(function(){
		var i = $(this).index();
		//alert(i);
		$(".newsArt li").eq(i).siblings().removeClass("active");
		$(".newsArt li").eq(i).addClass("active");
		$(".newsImg li").eq(i).siblings().removeClass("active");
		$(".newsImg li").eq(i).addClass("active");
	});
	
	
	$(".map iframe").load(function() {
		$(".map .loader").css("display", "none")
	})
	$(".map iframe").load(function() {
		$(".map .loader").css("display", "none")
	})
})
