   
//  //添加客户

        	 $("#add_btn").click(function () {
        		 var info=getJsonData();
                 $.ajax({
                     type: "POST",
                     url: "http://localhost:8080/TestCxfHibernate/REST/Misc/saveCustomerInfo?_type=json",
                   beforeSend:function(request){  request.setRequestHeader("token",$.cookie('userToken'));   },
                     contentType: "application/json",
                     data:JSON.stringify(info),
                     dataType: "json",
                     success: function (message) {
                    	 if(data.code==102){
                    		 alert("信息获取失败！");
                    	 }
                    	 else{

                    	 alert("保存成功！");
                         console.log(message);
                    	 }
                  },
                     error: function (message) {
                    	 console.log(message);
                    	 alert("保存失败！");
                         $("#request-process-patent").html("提交数据失败！");
                     }
                 });
             });

       

    function getJsonData() {
        var json = {
        	"address": $("#address").val(),
        	"department": $("#adddep").val(),
        	"name": $("#addname").val(),
        	"postCode":$("#addpos").val(),
        	"regionCode":$("#area").val(),
        	"telCode": $("#addtel").val()     
        };
        return json;
    }
    
 