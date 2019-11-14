$(function() {
		// 初始化省市区
		initAddress();
 

		// 更改省份后的操作
		$("select[name='province']").change(function() {
 
			var provCode = $("select[name='province']").val();
 
			getCity(provCode);
 
		});
 
		// 更改城市后的操作
		$("select[name='city']").change(function() {
			var cityCode = $("select[name='city']").val();
			getArea(cityCode);
		});
 
	});
 
	function initAddress() {
 
		var firstProvCode;
		// ajax请求所有省份
		$.ajax({
	         type:"get",
	         url:"http://localhost:8080/TestCxfHibernate/REST/Misc/getProvinceList",
              beforeSend:function(request){  request.setRequestHeader("token",$.cookie('userToken'));   },
	         data: {
	 			method : "initProvince"
	 		},
	         dataType:"json",	    
	         success:function(data){
	        
	        	 if(data.code==102){
	        		 alert("信息获取失败！");
	        	 }
	        	 else{
	        		 $.each(data, function(i, d) {
	     				$("select[name='province']").append(
	     						"<option value='"+d.Code+"'>" + d.Name
	     								+ "</option>");
	     			});
	      
	     			// 获取第一个省份的code
	     			firstProvCode = data[0].Code;
	     			// 根据第一个省份code获取对应城市列表
	     			getCity(firstProvCode);
	        	 }
	         }
		 });
		
 
	}
 
	//获取对应城市列表（里面包括获取第一个城市的区县列表）
	function getCity(provCode) {
 
		var firstCityCode;
 
		// ajax请求所有市级单位
		$.ajax({
	         type:"get",
	         url:"http://localhost:8080/TestCxfHibernate/REST/Misc/getCityList/"+provCode,
              beforeSend:function(request){  request.setRequestHeader("token",$.cookie('userToken'));   },
	         data:{
	 			method : "getCity",
				provCode : provCode
			},
	         dataType:"json",	    
	         success:function(data){
	        
	        	 if(data.code==102){
	        		 alert("信息获取失败！");
	        	 }
	        	 else{
	        		// 先清空城市下拉框
	     			$("select[name='city']").empty();
	      
	     			$.each(data, function(i, d) {
	     				$("select[name='city']").append(
	     						"<option value='"+d.Code+"'>" + d.Name
	     								+ "</option>");
	     			});
	     			
	     			// 获取第一个城市的code
	     			firstCityCode = data[0].Code;
	     			// 根据第一个城市code获取对应区县列表
	     			getArea(firstCityCode);
	        	 }
	         }
		 });
		
	}
 
	function getArea(cityCode) {
 
		// ajax请求所有区县单位
		$.ajax({
	         type:"get",
	         url:"http://localhost:8080/TestCxfHibernate/REST/Misc/getTownList/"+cityCode,
              beforeSend:function(request){  request.setRequestHeader("token",$.cookie('userToken'));   },
	         data: {
	 			method : "getArea",
				cityCode : cityCode
			},
	         dataType:"json",	    
	         success:function(data){
	        
	        	 if(data.code==102){
	        		 alert("信息获取失败！");
	        	 }
	        	 else{
	        		// 先清空区县下拉框
	     			$("select[name='area']").empty();
	     			$.each(data, function(i, d) {
	     				$("select[name='area']").append(
	     						"<option value='"+d.Code+"'>" + d.Name
	     								+ "</option>");
	     			});
	        	 }
	         }
		 });
		
	}
