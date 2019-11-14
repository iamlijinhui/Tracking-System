function newPackage(){		   
	 
       var ID=$("#barcodeValue").val();
       var UID=$("#UID").val();
      
 
       if(ID==null||UID==null||ID==''||UID==''){
    	   alert("信息不够完善，请继续填写！");
       }
       else{
    	  
    	   var pac={
    	    		  "id":ID,
    	    		  "uid":UID
    	   
    	       };
    	   $.ajax({
        	   
               type:"get",
               url:"http://localhost:8080/TestCxfHibernate/REST/Domain/newTransPackage/id/"+ ID +"/uid/"+ UID,
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
       }//else
      
}