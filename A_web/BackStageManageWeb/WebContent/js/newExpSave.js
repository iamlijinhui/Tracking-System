$(function(){
	var expSheetID=$.cookie('expSheetID');
	var expSheetUID=$.cookie('expSheetUID');
	if(expSheetID!=null&&expSheetID!=undefined&&expSheetID!='null'&&expSheetUID!=null&&expSheetUID!=undefined&&expSheetUID!='null'){
		$("#ydh").val(expSheetID);
		$("#lsy").val(expSheetUID);
		$.cookie('expSheetID',null,{path:'/'});
		$.cookie('expSheetUID',null,{path:'/'});
	}
	//保存寄件人、收件人信息
        var senderJson=null;
    	var senderID=$("select[name='Sender']").val();
    	var receiverJson=null;
     	var receiverID=$("select[name='Receiver']").val();


     	//如果Sender改变
    	$("select[name='Sender']").change(function() {
    		senderID=$("select[name='Sender']").val();
    		$.ajax({
                type:"get",
                async:false,
                url:"http://localhost:8080/TestCxfHibernate/REST/Misc/getCustomerInfo/"+senderID,
              beforeSend:function(request){  request.setRequestHeader("token",$.cookie('userToken'));   },
                dataType:"json",
                success:function(data){
                	if(data.code==102){
               		 alert("信息获取失败！");
               	 }
               	 else{
                   senderJson={
                		'ID':data.ID,
                		'name':data.name,
                		'telCode':data.telCode,
                		'department':data.department,
                		'regionCode':data.regionCode,
                		'address':data.address,
                		'postCode':data.postCode
                   };
                
               	 }
                },
                error:function(){
                    alert("没有该寄件用户");
                    return;
                }
            });
 
		});
    	
    	//如果Receiver改变
    	$("select[name='Receiver']").change(function() {
    		 receiverID=$("select[name='Receiver']").val();
    		$.ajax({
                type:"get",
                async:false,
                url:"http://localhost:8080/TestCxfHibernate/REST/Misc/getCustomerInfo/"+ receiverID,
              beforeSend:function(request){  request.setRequestHeader("token",$.cookie('userToken'));   },
                dataType:"json",
                success:function(data){
                	if(data.code==102){
               		 alert("信息获取失败！");
               	 }
               	 else{
                   receiverJson={
                		'ID':data.ID,
                		'name':data.name,
                		'telCode':data.telCode,
                		'department':data.department,
                		'regionCode':data.regionCode,
                		'address':data.address,
                		'postCode':data.postCode
                   };
               	 }

                },
                error:function(){
                    alert("没有该收件用户");
                    return;
                }
            });
 
		});
    	
    	
    	var newSender=null;
    	//填写寄件人信息模态框
    	$("#senderInfo").click(function(){
    		$("#myModalSender").modal('show');
    		
    		return false;
    	});
    	
    	//保存寄件人信息
    	$("#s_btn").click(function(){
    		newSender=getSJsonData();

    		if(newSender==false){
    			alert("寄件人信息填写不完整，请重新填写！");
    			
    		}
    	
    		
    		else{
    			var sId=null;
    		 $.ajax({
                 type: "POST",
                 async:false,
                 url: "http://localhost:8080/TestCxfHibernate/REST/Misc/saveCustomerInfo?_type=json",
               beforeSend:function(request){  request.setRequestHeader("token",$.cookie('userToken'));   },
                 contentType: "application/json",
                 data:JSON.stringify(newSender),
                 dataType: "json",
                 success: function (message) {
                	 if(message.code==102){
                		 alert("信息获取失败！");
                	 }
                	 else{
                	 alert("保存成功！");
                	 sId=message.ID;
                	newSender.ID=message.ID;
                	 senderJson=newSender;
                	 }
                    
              },
                 error: function (message) {
                	 console.log(message);
                	 alert("保存失败！");
                     $("#request-process-patent").html("提交数据失败！");
                 }
             });
    		
    		//得到原值
    		 var s=$("#Sender").val();
    		 var r=$("#Receiver").val();
             //刷新下拉框
    		 $("select[name='Sender']").html("");
             $("select[name='Receiver']").html("");
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
                         con += "<option value='"+item.ID+"'>" + item.name + "</option>";
                     });
                     $("select[name='Sender']").append(con);
                     $("select[name='Receiver']").append(con);
                     $("#Sender").val(sId);
                 
                     $("#Receiver").val(r);
                	 }
                 }
             });
    		
            
    		}
    	});
    	


    	//填写收件人信息
    	$("#receiverInfo").click(function(){
    		$("#myModalReceiver").modal('show');
    		return false;
    	});
    	
    	var newReceiver=null;
    	//保存收件人信息
    	$("#r_btn").click(function(){
    		newReceiver=getRJsonData();

    		if(newReceiver==false){
    			alert("收件人信息填写不完整，请重新填写！");
    			
    		}
    	
    		
    		else{
    			var rId=null;
    		 $.ajax({
                 type: "POST",
                 async:false,
                 url: "http://localhost:8080/TestCxfHibernate/REST/Misc/saveCustomerInfo?_type=json",
               beforeSend:function(request){  request.setRequestHeader("token",$.cookie('userToken'));   },
                 contentType: "application/json",
                 data:JSON.stringify(newReceiver),
                 dataType: "json",
                 success: function (message) {
                	 if(message.code==102){
                		 alert("信息获取失败！");
                	 }
                	 else{
                	 rId=message.ID;
                	 alert("保存成功！");
                	 newReceiver.ID=message.ID;
                	 receiverJson=newReceiver;
                	 }
                    
              },
                 error: function (message) {
                	 console.log(message);
                	 alert("保存失败！");
                     $("#request-process-patent").html("提交数据失败！");
                 }
             });
    		
    		//得到原值
    		 var s=$("#Sender").val();
    		 var r=$("#Receiver").val();
             //刷新下拉框
    		 $("select[name='Sender']").html("");
             $("select[name='Receiver']").html("");
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
                         con += "<option value='"+item.ID+"'>" + item.name + "</option>";
                     });
                     $("select[name='Sender']").append(con);
                     $("select[name='Receiver']").append(con);
                     $("#Sender").val(s);
                     
                     $("#Receiver").val(rId);		
                 }
                 }
             });
    		}
    	});
    	
    	//保存订单信息
    	$("#savebtn").click(function(){
    		
    		//如果寄件人为空，即未使用下拉框
    		if(senderJson==null||senderJson=='null'){
    			senderID=$("select[name='Sender']").val();
        		$.ajax({
                    type:"get",
                    async:false,
                    url:"http://localhost:8080/TestCxfHibernate/REST/Misc/getCustomerInfo/"+senderID,
                  beforeSend:function(request){  request.setRequestHeader("token",$.cookie('userToken'));   },
                    dataType:"json",
                    success:function(data){
                    	 if(data.code==102){
                    		 alert("信息获取失败！");
                    	 }
                    	 else{
                       senderJson={
                    		'ID':data.ID,
                    		'name':data.name,
                    		'telCode':data.telCode,
                    		'department':data.department,
                    		'regionCode':data.regionCode,
                    		'address':data.address,
                    		'postCode':data.postCode
                       };
                    	 }

                    },
                    error:function(){
                        alert("没有该寄件用户");
                        return;
                    }
                });
    		}
    		//如果收件人为空，即未使用下拉框
    		if(receiverJson==null||receiverJson=='null'){
    			 receiverID=$("select[name='Receiver']").val();
    	    		$.ajax({
    	                type:"get",
    	                async:false,
    	                url:"http://localhost:8080/TestCxfHibernate/REST/Misc/getCustomerInfo/"+ receiverID,
                      beforeSend:function(request){  request.setRequestHeader("token",$.cookie('userToken'));   },
    	                dataType:"json",
    	                success:function(data){
    	                	 if(data.code==102){
    	                		 alert("信息获取失败！");
    	                	 }
    	                	 else{
    	                   receiverJson={
    	                		'ID':data.ID,
    	                		'name':data.name,
    	                		'telCode':data.telCode,
    	                		'department':data.department,
    	                		'regionCode':data.regionCode,
    	                		'address':data.address,
    	                		'postCode':data.postCode
    	                   };
    	                	 }

    	                },
    	                error:function(){
    	                    alert("没有该收件用户");
    	                    return;
    	                }
    	            });
    			
    		}
    		
    		
    		 var expressSheet = {
                     "ID":$("#ydh").val(),
                     "type":"0",
                     "sender": senderJson,
                     "recever":receiverJson,
                     "weight":$("#WF").val(),
                     "tranFee":$("#TF").val(),
                     "packageFee":$("#PF").val(),
                     "insuFee":$("#IF").val(),
                     "accepter":$("#lsy").val(),
                     "deliver":"",
          
                     "deliveTime":"",
                     "acc1":"",
                     "acc2":"",
                     "status":"0"
                     
                   
                 };
    		 //获取时间
    		 $.ajax({
	                type:"get",
	                async:false,
	                url:"http://localhost:8080/TestCxfHibernate/REST/Domain/getExpressSheet/"+expressSheet.ID,
                  beforeSend:function(request){  request.setRequestHeader("token",$.cookie('userToken'));   },
	                dataType:"json",
	                success:function(data){
	                	 if(data.code==102){
	                		 alert("信息获取失败！");
	                	 }
	                	 else{
	                	expressSheet.accepteTime=data.accepteTime;
	                	 }
	                },
	                error:function(){
	                    alert("没有该订单");
	                    return;
	                }
	            });
    		 if(expressSheet.ID==''||expressSheet.sender==''||expressSheet.sender==null||expressSheet.recever==''||expressSheet.recever==null){
   
    			 alert("运单信息不完善，请继续完善！")
    			 
    		 }
    		 else{
    			 //alert(time);
    			 //alert(expressSheet.accepteTime);
    		 $.ajax({
                 type: "POST",
                 url: "http://localhost:8080/TestCxfHibernate/REST/Domain/saveExpressSheet?_type=json",
               beforeSend:function(request){  request.setRequestHeader("token",$.cookie('userToken'));   },
                 contentType: "application/json",
                 data:JSON.stringify(expressSheet),
                 dataType: "json",
                 success: function (message) {
                	 if(message.code==102){
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
    		 }
    	});
    	
    	
    	
    	
    	
});


function getSJsonData() {
    var json = {
    	"address": $("#saddress").val(),
    	"department": $("#sdep").val(),
    	"name": $("#sname").val(),
    	"postCode":$("#spos").val(),
    	"regionCode":$("#sarea").val(),
    	"telCode": $("#stel").val()     
    };
    if(json.name==''||json.address==''||json.department==''||json.postCode==''||json.regionCode==''||json.telCode==''){
    	return false;
    }
    return json;
}


function getRJsonData() {
    var json = {
    	"address": $("#raddress").val(),
    	"department": $("#rdep").val(),
    	"name": $("#rname").val(),
    	"postCode":$("#rpos").val(),
    	"regionCode":$("#rarea").val(),
    	"telCode": $("#rtel").val()     
    };
    if(json.name==''||json.address==''||json.department==''||json.postCode==''||json.regionCode==''||json.telCode==''){
    	return false;
    }
    return json;
}