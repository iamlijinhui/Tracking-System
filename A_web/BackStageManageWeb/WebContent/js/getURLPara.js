		function getURLPara(urlPara){
		    	 var reg = new RegExp("(^|&)" +urlPara + "=([^&]*)(&|$)"); //构造一个含有目标参数的正则表达式对象
		         var r = window.location.search.substr(1).match(reg);  //匹配目标参数
		         if (r != null) {
		          	var uid=unescape(r[2]);
		         	return uid;
		         }
		    	return null
		    };