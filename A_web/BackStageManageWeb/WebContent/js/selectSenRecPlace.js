$(function() {
		// 初始化省市区
		initSAddress();
		initRAddress();

		// 更改省份后的操作
		$("select[name='sprovince']").change(function() {
 
			var provCode = $("select[name='sprovince']").val();
 
			getSCity(provCode);
 
		});
		$("select[name='rprovince']").change(function() {
			 
			var provCode = $("select[name='rprovince']").val();
 
			getRCity(provCode);
 
		});
 
		// 更改城市后的操作
		$("select[name='scity']").change(function() {
			var cityCode = $("select[name='scity']").val();
			getSArea(cityCode);
		});
		$("select[name='rcity']").change(function() {
			var cityCode = $("select[name='rcity']").val();
			getRArea(cityCode);
		});
 
	});
 
	function initSAddress() {
 
		var firstProvCode;
		// ajax请求所有省份
		 $.ajax({
	         type:"get",
	         url:"http://localhost:8080/TestCxfHibernate/REST/Misc/getProvinceList",
           beforeSend:function(request){  request.setRequestHeader("token",$.cookie('userToken'));   },
	         data: {
	 			method : "initSProvince"
	 		},
	         dataType:"json",	    
	         success:function(data){
	        
	        	 if(data.code==102){
	        		 alert("信息获取失败！");
	        	 }
	        	 else{
	        		 $.each(data, function(i, d) {
	     				$("select[name='sprovince']").append(
	     						"<option value='"+d.Code+"'>" + d.Name
	     								+ "</option>");
	     			});
	      
	     			// 获取第一个省份的code
	     			firstProvCode = data[0].Code;
	     			// 根据第一个省份code获取对应城市列表
	     			getSCity(firstProvCode);
	        	 }
	         }
		 });
		
 
	}
	function initRAddress() {
		 
		var firstProvCode;
		// ajax请求所有省份
		
		 $.ajax({
	         type:"get",
	         url:"http://localhost:8080/TestCxfHibernate/REST/Misc/getProvinceList",
              beforeSend:function(request){  request.setRequestHeader("token",$.cookie('userToken'));   },
	         data:  {
	 			method : "initRProvince"
	 		},
	         dataType:"json",	    
	         success:function(data){
	        
	        	 if(data.code==102){
	        		 alert("信息获取失败！");
	        	 }
	        	 else{
	        		 $.each(data, function(i, d) {
	     				$("select[name='rprovince']").append(
	     						"<option value='"+d.Code+"'>" + d.Name
	     								+ "</option>");
	     			});
	      
	     			// 获取第一个省份的code
	     			firstProvCode = data[0].Code;
	     			// 根据第一个省份code获取对应城市列表
	     			getRCity(firstProvCode);
	        	 }
	         }
		 });
		
 
	}
 
	//获取对应城市列表（里面包括获取第一个城市的区县列表）
	function getSCity(provCode) {
 
		var firstCityCode;
 
		// ajax请求所有市级单位
		 $.ajax({
	         type:"get",
	         url:"http://localhost:8080/TestCxfHibernate/REST/Misc/getCityList/"+provCode,
              beforeSend:function(request){  request.setRequestHeader("token",$.cookie('userToken'));   },
	         data:  {
	 			method : "getSCity",
				provCode : provCode
			},
	         dataType:"json",	    
	         success:function(data){
	        
	        	 if(data.code==102){
	        		 alert("信息获取失败！");
	        	 }
	        	 else{
	        		// 先清空城市下拉框
	     			$("select[name='scity']").empty();
	      
	     			$.each(data, function(i, d) {
	     				$("select[name='scity']").append(
	     						"<option value='"+d.Code+"'>" + d.Name
	     								+ "</option>");
	     			});
	     			
	     			// 获取第一个城市的code
	     			firstCityCode = data[0].Code;
	     			// 根据第一个城市code获取对应区县列表
	     			getSArea(firstCityCode);
	        	 }
	         }
		 });
		
	}
	
	function getRCity(provCode) {
		 
		var firstCityCode;
 
		// ajax请求所有市级单位
		$.ajax({
	         type:"get",
	         url:"http://localhost:8080/TestCxfHibernate/REST/Misc/getCityList/"+provCode,
              beforeSend:function(request){  request.setRequestHeader("token",$.cookie('userToken'));   },
	         data: {
	 			method : "getRCity",
				provCode : provCode
			},
	         dataType:"json",	    
	         success:function(data){
	        
	        	 if(data.code==102){
	        		 alert("信息获取失败！");
	        	 }
	        	 else{
	        		// 先清空城市下拉框
	     			$("select[name='rcity']").empty();
	      
	     			$.each(data, function(i, d) {
	     				$("select[name='rcity']").append(
	     						"<option value='"+d.Code+"'>" + d.Name
	     								+ "</option>");
	     			});
	     			
	     			// 获取第一个城市的code
	     			firstCityCode = data[0].Code;
	     			// 根据第一个城市code获取对应区县列表
	     			getRArea(firstCityCode);
	        	 }
	         }
		 });
	
	}
 
	function getSArea(cityCode) {
 
		// ajax请求所有区县单位
		$.ajax({
	         type:"get",
	         url:"http://localhost:8080/TestCxfHibernate/REST/Misc/getTownList/"+cityCode,
              beforeSend:function(request){  request.setRequestHeader("token",$.cookie('userToken'));   },
	         data: {
	 			method : "getSArea",
				cityCode : cityCode
			},
	         dataType:"json",	    
	         success:function(data){
	        
	        	 if(data.code==102){
	        		 alert("信息获取失败！");
	        	 }
	        	 else{
	        		// 先清空区县下拉框
	     			$("select[name='sarea']").empty();
	     			$.each(data, function(i, d) {
	     				$("select[name='sarea']").append(
	     						"<option value='"+d.Code+"'>" + d.Name
	     								+ "</option>");
	     			});
	        	 }
	         }
		 });
		
	}
	function getRArea(cityCode) {
		 
		// ajax请求所有区县单位
		
		$.ajax({
	         type:"get",
	         url:"http://localhost:8080/TestCxfHibernate/REST/Misc/getTownList/"+cityCode,
              beforeSend:function(request){  request.setRequestHeader("token",$.cookie('userToken'));   },
	         data: {
	 			method : "getRArea",
				cityCode : cityCode
			},
	         dataType:"json",	    
	         success:function(data){
	        
	        	 if(data.code==102){
	        		 alert("信息获取失败！");
	        	 }
	        	 else{
	        		// 先清空区县下拉框
	     			$("select[name='rarea']").empty();
	     			$.each(data, function(i, d) {
	     				$("select[name='rarea']").append(
	     						"<option value='"+d.Code+"'>" + d.Name
	     								+ "</option>");
	     			});
	        	 }
	         }
		 });
		
	}