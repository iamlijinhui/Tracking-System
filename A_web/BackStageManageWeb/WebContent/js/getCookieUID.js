$(function getCookieUID(){
	var uid=$.cookie('UID');
	alert(uid);
	if(uid!=null)
	   return uid;
});
