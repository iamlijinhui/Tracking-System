$(function(){
	var ifLogin=$.cookie("UID");
	if(ifLogin==null||ifLogin=='null'||ifLogin==''||ifLogin==undefined){
		alert("尚未登录，请先登录");
	var preUrl=window.location.href;
    $.cookie("preUrl",preUrl,{path:'/'});
	window.location.href="index.html?preUrl";
	}
});