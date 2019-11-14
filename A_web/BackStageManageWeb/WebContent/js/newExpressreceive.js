function fnSearch1(){	
	var ID=$("#ID").val();
    var UID=$("#UID").val();
	 
	$.ajax({
        type:"get",
        url:"http://localhost:8080/TestCxfHibernate/REST/Domain/receiveExpressSheetId/id/"+ ID +"/uid/"+ UID,
      beforeSend:function(request){  request.setRequestHeader("token",$.cookie('userToken'));   },
        contentType: "application/json; charset=utf-8",
        dataType:"json",
        success:function(data){
        	 if(data.code==102){
        		 alert("信息获取失败！");
        	 }
        	 else{
            alert("揽收成功");
        	 }
        },
        error:function(){
            alert("揽收失败")
        }
    })
	    }