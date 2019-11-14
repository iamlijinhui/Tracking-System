  
//  //添加客户

        	 $("#add_user").click(function () {
        		 var info=getJsonData();
        		 if(info==false){
        			 alert("信息不完善！请重新填写");
        		 
        		 }
        		 else{
                 $.ajax({
                     type: "POST",
                     url: "http://localhost:8080/TestCxfHibernate/REST/Misc/register?_type=json",
                     beforeSend:function(request){  request.setRequestHeader("token",$.cookie('userToken'));   },
                     contentType: "application/json",
                     data:JSON.stringify(info),
                     dataType: "json",
                     success: function (message) {
                    	 if(message.code==102){
                    		 alert("添加失败！");
                    	 }
                    	 else{
                    	 alert("添加成功！");
                    	 window.location.reload();
                         console.log(message);
                    	 }
                  },
                     error: function (message) {
                    	 console.log(message);
                    	 alert("添加失败！");

                     }
                 });
        		 }
             });

       

    function getJsonData() {
    	var upwd=$("#upwd").val();
    	var uname=$("#uname").val();
    	var urull=$("#utype").val();
    	var utel=$("#utel").val();
    	var udpt=$("#udpt").val();
    	if(upwd==''||uname==''||urull==''||utel==''||udpt=='')
    		return false;
        var json = {
        	"PWD": upwd,
        	"name": uname,
        	"URull": urull,
        	"telCode":utel,
        	"status":0,
        	"dptID": udpt
        };
        return json;
    }
    
 