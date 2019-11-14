
	   function fnSearch1(){		   
	        var $username=$("#name");   
	        $.ajax({
	            type:"get",
	            url:"http://localhost:8080/TestCxfHibernate/REST/Misc/getCustomerListByName/"+$username.val(),
                beforeSend:function(request){  request.setRequestHeader("token",$.cookie('userToken'));   },
	            dataType:"json",	    
	            success:function(data){
	            	if(data.code==102){
	           		 alert("信息获取失败！");
	           	 }
	           	 else{

	                var con="";   
	                if(data=='')
	                	alert("该客户不存在");
	                else{
	                $.each(data,function(i,item){
	             
	                    con+="<tr><td>"+item.ID+"</td><td>"+item.name+"</td><td>"+item.telCode+"</td><td>"+item.department+"</td><td>"+item.address+"</td><td>"+item.regionCode+"</td><td>"+item.postCode+"</td></tr>"
	                });
	                }
	                $("#table_search").html(con);
	           	 }
	            },
	            error:function(){
	                alert("没有该用户");
	            }
	        })
	    	
	    }
	   function fnSearch2(){		   
		   var $userTelCode=$("#telcode"); 
	        $.ajax({
	            type:"get",
	            url:"http://localhost:8080/TestCxfHibernate/REST/Misc/getCustomerListByTelCode/"+$userTelCode.val(),
              beforeSend:function(request){  request.setRequestHeader("token",$.cookie('userToken'));   },
	            dataType:"json",	    
	            success:function(data){
	            	if(data.code==102){
	           		 alert("信息获取失败！");
	           	 }
	           	 else{

	                var con="";   
	                if(data=='')
	                	alert("该客户不存在");
	                else{
	                $.each(data,function(i,item){
	             
	                    con+="<tr><td>"+item.ID+"</td><td>"+item.name+"</td><td>"+item.telCode+"</td><td>"+item.department+"</td><td>"+item.address+"</td><td>"+item.regionCode+"</td><td>"+item.postCode+"</td></tr>"
	                });
	                }
	                $("#table_search").html(con);
	           	 }
	            },
	            error:function(){
	                alert("没有该用户");
	            }
	        })
	    	
	    }