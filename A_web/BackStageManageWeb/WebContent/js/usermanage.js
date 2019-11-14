$(function(){ 
	$("tbody").html("");
     $.ajax({
         type:"get",
         url:"http://localhost:8080/TestCxfHibernate/REST/Misc/getAllUsers",
         beforeSend:function(request){
        	
        	
        	 request.setRequestHeader("token",$.cookie('userToken'));
         },
         dataType:"json",	    
         success:function(data){
        
        	 if(data.code==102){
        		 alert("信息获取失败！");
        	 }
        	 else{
        		
             var con="";   
             if(data=='')
             	$("#tablearea").html("<h3>尚未有用户</h3>");
             else{
             $.each(data,function(i,item){
            	 var type;
            	 var status;
            	 if(item.URull==0)
            		 type="配送员/揽收员/分拣员";
            	 else if(item.URull==1)
            		 type="经理/管理员";
            	 else if(item.URull==2)
            		 type="汽车司机";
            	 if(item.status==0)
            		 status="在职";
            	 else if(item.status==-1)
            		 status="离职";
            	
                 con+="<tr><td>"+item.UID+"</td><td>"+item.name+"</td><td>"+type+"</td><td>"+item.dptID+"</td><td>"+item.telCode+"</td><td>"+status+"</td><td>"+item.PWD+"</td><td>"+item.receivePackageID+"</td><td>"+item.transPackageID+"</td><td>"+item.delivePackageID+"</td><td>"+$("#barDemo").html()+"</td></tr>";
             });
             }
             $("tbody").append(con);
        	 }
         },
         error:function(){
             alert("没有用户");
         }
     })
     
});


$("#searchUserByID").click(function(){
	$("tbody").html("");
	var uid=$("#uid").val();
	 $.ajax({
         type:"get",
         url:"http://localhost:8080/TestCxfHibernate/REST/Misc/getUserInfo/"+uid,
         beforeSend:function(request){  request.setRequestHeader("token",$.cookie('userToken'));   },
         dataType:"json",	    
         success:function(data){

        	 if(data.code==102){
        		 alert("信息获取失败！");
        	 }
        	 else{
             var con="";   
             if(data==''){
             	$("#tablearea").html("<h3>没有该用户</h3>");
             }
             else{
            	 var type;
            	 var status;
            	 if(data.URull==0)
            		 type="配送员/揽收员/分拣员";
            	 else if(data.URull==1)
            		 type="经理/管理员";
            	 else if(data.URull==2)
            		 type="汽车司机";
            	 if(data.status==0)
            		 status="在职";
            	 else if(data.status==-1)
            		 status="离职";
            	
                 con+="<tr><td>"+data.UID+"</td><td>"+data.name+"</td><td>"+type+"</td><td>"+data.dptID+"</td><td>"+data.telCode+"</td><td>"+status+"</td><td>"+data.PWD+"</td><td>"+data.receivePackageID+"</td><td>"+data.transPackageID+"</td><td>"+data.delivePackageID+"</td><td>"+$("#barDemo").html()+"</td></tr>";
             }
             $("tbody").html(con);
        	 }
         },
         error:function(){
             alert("没有该用户");
         }
     });
});


$("#showAll").click(function(){
	window.location.reload();
});



$("tbody").on("click","#modify",function(){
	var uid=$(this).parents("tr").find("td").eq(0).text();
	var uname=$(this).parents("tr").find("td").eq(1).text();
	var utype=$(this).parents("tr").find("td").eq(2).text();
	var udpt=$(this).parents("tr").find("td").eq(3).text();
	var utel=$(this).parents("tr").find("td").eq(4).text();
	var ustatus=$(this).parents("tr").find("td").eq(5).text();
	var upwd=$(this).parents("tr").find("td").eq(6).text();
	var urecPac=$(this).parents("tr").find("td").eq(7).text();
	var utraPac=$(this).parents("tr").find("td").eq(8).text();
	var udelPac=$(this).parents("tr").find("td").eq(9).text();
	var urull=0;
	var status=0;
	if(utype=="配送员/揽件员/分拣员")
		urull=0;
	else if(utype=="经理/管理员")
		urull=1;
	else if(utype=="汽车司机")
		urull=2;
	
	if(ustatus=="在职")
		status=0;
	else if(ustatus=="离职")
		status=-1;
	$("#muid").removeAttr("readonly");
	$("#muid").val(uid);
	$("#muid").attr("readonly","readonly");
	$("#muid").val(uid);
	$("#uname").val(uname);
	$("#utype").val(urull);
	$("#udpt").val(udpt);
	$("#utel").val(utel);
	$("#ustatus").val(status);
	$("#upwd").val(upwd);
	$("#urecPac").val(urecPac);
	$("#utraPac").val(utraPac);
	$("#udelPac").val(udelPac);
	
	$("#myModal").modal('show');
	
	return false;
});

$("tbody").on("click","#delete",function(){
	var uid=$(this).parents("tr").find("td").eq(0).text();
	var uname=$(this).parents("tr").find("td").eq(1).text();
	var utype=$(this).parents("tr").find("td").eq(2).text();
	var udpt=$(this).parents("tr").find("td").eq(3).text();
	var utel=$(this).parents("tr").find("td").eq(4).text();
	var ustatus=$(this).parents("tr").find("td").eq(5).text();
	var upwd=$(this).parents("tr").find("td").eq(6).text();
	var urecPac=$(this).parents("tr").find("td").eq(7).text();
	var utraPac=$(this).parents("tr").find("td").eq(8).text();
	var udelPac=$(this).parents("tr").find("td").eq(9).text();
	var urull=0;
	var status=0;
	if(utype=="配送员/揽件员/分拣员")
		urull=0;
	else if(utype=="经理/管理员")
		urull=1;
	else if(utype=="汽车司机")
		urull=2;
	

	
	var delUser = {
  		  "UID":uid,
  			"name":uname,
  			"URull":urull,
  			"dptID":udpt,
  			"telCode":utel,
  			"status":-1,
  			"PWD":upwd,
  			"receivePackageID":urecPac,
    "transPackageID":utraPac,
    "delivePackageID":udelPac
    };
	 $.ajax({
         type: "POST",
         url: "http://localhost:8080/TestCxfHibernate/REST/Misc/saveUserInfo?_type=json",
        beforeSend:function(request){  request.setRequestHeader("token",$.cookie('userToken'));   },
         contentType: "application/json",
         data:JSON.stringify(delUser),
         dataType: "json",
         success: function (message) {
        	 if(message.code==102){
        		 alert("信息获取失败！");
        	 }
        	 else{
        	 alert("员工已离职！");
        	 window.location.reload();
             console.log(message);
        	 }
      },
         error: function (message) {
        	 console.log(message);
        	 alert("离职操作失败！");

         }
     });
	
	return false;
});

$("#modify_btn").click(function(){
	var modUser=GetModalData();
	 if(modUser==false){
		 alert("信息不完善！请重新填写");
	 
	 }
	 else{
     $.ajax({
         type: "POST",
         url: "http://localhost:8080/TestCxfHibernate/REST/Misc/saveUserInfo?_type=json",
         beforeSend:function(request){  request.setRequestHeader("token",$.cookie('userToken'));   },
         contentType: "application/json",
         data:JSON.stringify(modUser),
         dataType: "json",
         success: function (message) {
        	 if(message.code==102){
        		 alert("信息获取失败！");
        	 }
        	 else{
        	 alert("修改成功！");
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

function GetModalData() {
      var json = {
    		  "UID":$("#muid").val(),
    			"name":$("#uname").val(),
    			"URull":$("#utype").val(),
    			"dptID":$("#udpt").val(),
    			"telCode":$("#utel").val(),
    			"status":$("#ustatus").val(),
    			"PWD":$("#upwd").val(),
    			"receivePackageID":$("#urecPac").val(),
      "transPackageID":$("#utraPac").val(),
      "delivePackageID":$("#udelPac").val()
      };
      if(json.name==''||json.telCode==''||json.PWD==''||json.dptID=='')
  		return false;
      return json;
  }


