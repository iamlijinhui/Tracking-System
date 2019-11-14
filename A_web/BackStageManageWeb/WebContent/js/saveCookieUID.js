$(function(){
	var uid=$.cookie('UID');
	if(uid==null||uid==undefined||uid=='null'||uid=='undefined'){
	   var t=getURLPara('UID');
	   $.cookie('UID',t,{path:'/',expires:1});
	}
	else{
		$.cookie('UID',uid,{path:'/'});
	}
});