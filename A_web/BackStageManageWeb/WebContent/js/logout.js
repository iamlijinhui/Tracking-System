function logout(){
	$.cookie('UID',null,{path:'/'});
	$.cookie('userToken',null,{path:'/'});

	window.location.href="index.html";
	return false;
}