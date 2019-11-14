function fnSearch1(){		   
	 
       var ID=$("#barcodeValue").val();
       var UID=$("#UID").val();
       if(ID!=''){
    	 
       $.ajax({
    	   
           type:"get",
           url:"http://localhost:8080/TestCxfHibernate/REST/Domain/newExpressSheet/id/"+ ID +"/uid/"+ UID,
           beforeSend:function(request){  request.setRequestHeader("token",$.cookie('userToken'));   },
           contentType: "application/json; charset=utf-8",
           dataType:"json",
           
           success:function(data){
        	   if(data.code==102){
          		 alert("信息获取失败！");
          	 }
          	 else{

               alert("创建成功");
          	 }
           },
           error:function(data){
               alert("创建失败");
           }
           
       });
       }
      
	    }


function fnSearch2(){		   
    var ID=$("#barcodeValue").val();
    var UID=$("#UID").val();
    if(ID!=''&&UID!=''){
    	$.cookie('expSheetID',ID,{path:'/'});
    	$.cookie('expSheetUID',UID,{path:'/'});
    	$.ajax({
     	   
            type:"get",
            url:"http://localhost:8080/TestCxfHibernate/REST/Domain/newExpressSheet/id/"+ ID +"/uid/"+ UID,
//          beforeSend:function(request){  request.setRequestHeader("token",$.cookie('userToken'));   },
            contentType: "application/json; charset=utf-8",
            dataType:"json",
            
            success:function(data){
            	if(data.code==102){
             		 alert("信息获取失败！");
             	 }
             	 else{
                alert("创建成功");
                window.location.href="new_ExpressSheetwanshan.html";
             	 }
            },
            error:function(data){
            	
                alert("创建失败");
            }
            
        });
    	 
    }
    else{
    	 
    	alert("不可为空！");
    }

    
  
	    }