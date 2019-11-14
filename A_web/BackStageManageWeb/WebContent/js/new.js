    $(function() {
        //        获取当前时间
            var date= new Date();
            var year=date.getFullYear();
            var month=date.getMonth();
            var day=date.getDate();
            var hours=date.getHours();
            var minutes=date.getMinutes();
            var time=year+'-'+month+'-'+date+ '&nbsp;'+hours+':'+minutes;
           
//填写寄件人
        $("#J_person").one('mouseover',function () {
            $.ajax({
                type: "get",
                url: "http://localhost:8080/TestCxfHibernate/REST/Misc/getAllCustomers",
                beforeSend:function(request){  request.setRequestHeader("token",$.cookie('userToken'));   },
                dataType: "json",
                success: function (data) {
                	if(data.code==102){
               		 alert("信息获取失败！");
               	 }
               	 else{

                    var con = "";
                    var con1="";
                    $.each(data, function (i, item) {
                        con += "<option>" +  item.name + "</option>";                       
                    });
                    $("#J_person").html(con);
               	 }
                }
            })
        });
        //      获取完整的json数据     
        
        
        var count=0;
        $("#J_person").click(function () {
            if(count==0){
                count=1;
            }else if(count==1){
                var $username=$("#J_person");
                
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
//                        定义json数据
                            function GetJsonData1() {
                                var json = {
                                    "ID":data.ID,
                                };
                                return json;
                            }
                            window.json1=JSON.stringify(GetJsonData1());                         
                            $("#JJ").val(json1);
                   	 }
                    },
                    error:function(){
                        alert("没有该用户");
                    }
                })
                count=0;
            }
            
        });
//填写收件人
        $("#S_person").one('mouseover',function () {
            $.ajax({
                type: "get",
                url: "http://localhost:8080/TestCxfHibernate/REST/Misc/getAllCustomers",
                beforeSend:function(request){  request.setRequestHeader("token",$.cookie('userToken'));   },
                dataType: "json",
                success: function (data) {
                	if(data.code==102){
               		 alert("信息获取失败！");
               	 }
               	 else{

                    var con = "";
                    $.each(data, function (i, item) {
                        con += "<option>" + item.name + "</option>";
                    });
                    $("#S_person").html(con);
               	 }
                }
            })
        });
        //      获取完整的json数据
        
        var count=0;
        var SID;//定义寄件人ID
        
        $("#S_person").click(function () {
            if(count==0){
                count=1;
            }else if(count==1){
                var $username=$("#S_person");
                alert($username.val());
                $.ajax({
    	            type:"get",
    	            async: false,
    	            url:"http://localhost:8080/TestCxfHibernate/REST/Misc/getCustomerListByName/"+$username.val(),
                    beforeSend:function(request){  request.setRequestHeader("token",$.cookie('userToken'));   },
    	            dataType:"json",	    
    	            success:function(data){
    	            	if(data.code==102){
    	           		 alert("信息获取失败！");
    	           	 }
    	           	 else{

    	            	$.each(data,function(i,item){
    	   	             
    	                    SID=item.ID;
    	                });
    	           	 }
    	            },
    	            error:function(){
    	                alert("没有该用户");
    	            }
    	        })
                count=0;
                alert(SID);
            }
            return SID;
        });
        
	        //保存
        $("#savebtn").click(function(){
        	
               
            var info=getJsonData();
            $.ajax({           	
                type: "POST",
                url: "http://localhost:8080/TestCxfHibernate/REST/Domain/saveExpressSheet?_type=json",
                beforeSend:function(request){  request.setRequestHeader("token",$.cookie('userToken'));   },
                contentType: "application/json",
                data:JSON.stringify(info),
                dataType: "json",
                success: function () {
                	if(data.code==102){
               		 alert("信息获取失败！");
               	 }
               	 else{

               	 alert("保存成功！");
               	 }
             },
                error: function () {               	 
               	 alert("保存失败！");                    
                }
            });
        });
        
  
        function getJsonData() {   
        	
            var json = {
            		"ID":$("#ydh").val(),
                    "type":"",
                    "recever": $("#SJ").val(),
                    "sender": SID,
                    "weight":$("#WF").val(),
                    "tranFee":$("#TF").val(),
                    "packageFee":$("#PF").val(),
                    "insuFee":$("#IF").val(),
                    "accepter":$("#lsy").val(),
                    "deliver":"",
                    "accepteTime":time,
                    "deliveTime":"",
                    "acc1":"",
                    "acc2":"",
                    "status":"0"
            };
            return json;
        }

    })