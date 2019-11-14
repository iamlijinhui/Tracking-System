  var sender=null;
  var receiver=null;
  $(function(){
	  $.cookie('mapID',null,{path:'/'});

	  $('#iframe').attr('src', $('#iframe').attr('src'));
	
	  
  });
function fnSearch(){
	$.cookie('mapID',null,{path:'/'});
            var $ID=$("#Express_id").val();
            $.cookie('mapID',$ID,{path:'/',expires:1});
            $('#iframe').attr('src', $('#iframe').attr('src'));
            $.ajax({
                type:"get",
                async:false,
                url:"http://localhost:8080/TestCxfHibernate/REST/Domain/getExpressSheet/"+$ID,
                beforeSend:function(request){  request.setRequestHeader("token",$.cookie('userToken'));   },
                dataType:"json",
                success:function(data){
                	if(data.code==102){
               		 alert("信息获取失败！");
               	 }
               	 else{

                	sender={
                   		 "ID":data.sender.ID,
                   		"name":data.sender.name,
                   			"telCode":data.sender.telCode,
                   		"postCode":data.sender.postCode,
                   		"regionCode":data.sender.regionCode,
                   			"regionString":data.sender.regionString,
                   		"address":data.sender.address,
                   			"department":data.sender.department
                   			
                    };
                	receiver={
                   		 "ID":data.recever.ID,
                   		"name":data.recever.name,
                   			"telCode":data.recever.telCode,
                   		"postCode":data.recever.postCode,
                   		"regionCode":data.recever.regionCode,
                   			"regionString":data.recever.regionString,
                   		"address":data.recever.address,
                   			"department":data.recever.department
                   			
                    };
                	var con="";                   	
 	                if(data=='')
 	                	alert("该运单不存在");
 	                else{
 	                	var accepter=data.accepter;
 	                	var deliver=data.deliver;
 	                	var accepteTime=data.accepteTime;
 	                	var deliveTime=data.deliveTime;
 	                	
 	                	  if(accepter==undefined||accepter==''||accepter==null)
 	 	                		accepter="尚未揽收";
 	 	                	if(deliver==undefined||deliver==''||deliver==null)
 	 	                		deliver="尚未派送";
 	 	                	if(accepteTime==undefined||accepteTime==''||accepteTime==null)
 	 	                		accepteTime="尚未揽收";
 	 	                	else
 	 	                		accepteTime=myTime(accepteTime);
 	 	                	if(deliveTime==undefined||deliveTime==''||deliveTime==null)
 	 	                		deliveTime="尚未派送";
 	 	                	else
 	 	                		deliveTime=myTime(deliveTime);
 	                	
 	                		
                    con += "<tr><td><a class='eID'>" + data.ID + "</a></td><td><a class='senderD'>" + data.sender.name +  "</a></td><td><a class='receiverD'>" + data.recever.name +"</a></td><td><a class='acc'>" + accepter+ "</a></td><td>" + accepteTime + "</td><td><a class='del'>" + deliver  + "</a></td><td>" + deliveTime +"</td></tr>";	                	
 	                }
                    $("#table1").html(con);
               	 }
                },
                error:function(){
                    alert("该运单不存在");
                }
            });
            $.ajax({
                type:"get",
                url:"http://localhost:8080/TestCxfHibernate/REST/Domain/getPath/"+$ID,
                beforeSend:function(request){  request.setRequestHeader("token",$.cookie('userToken'));   },
                dataType:"json",
                success:function(data){
                	if(data.code==102){
                  		 alert("信息获取失败！");
                  	 }
                  	 else{
                    alert("获取成功");
                    var con = "";
                    var next = "";
                    for (var i = 0; i < data.length; i++) {
                    	if(i==data.length-1)
                    	    data[i].start=data[i].end;
                    	if(data[i].status==1)
                    		{
                            con += "<tr><td>" + myTime(data[i].start) + "</td><td>"+data[i].nodeName +"</p>"+"<span>已揽收</span></p></td><td>" + data[i].userInfo+"</td></tr>"
                    		}
                    	if(data[i].status==2)
                		{
                            con += "<tr><td>" + myTime(data[i].start) + "</td><td>"+data[i].nodeName +"</p>"+"<span>已分拣</span></p></td><td>" + data[i].userInfo+"</td></tr>"
                		}
                    	if(data[i].status==3)
                		{
                		con += "<tr><td>" + myTime(data[i].start) + "</td><td>"+data[i].nodeName +"</p>"+"<span>已发出，将发往</span></p>"+data[i].nextNodeName+"</td><td>" + data[i].userInfo+"</td></tr>"
                         } 
                    	if(data[i].status==4)
                		{
                		    con += "<tr><td>" + myTime(data[i].start) + "</td><td>"+data[i].nodeName +"</p>"+"<span>正在派送</span></p></td><td>" + data[i].userInfo+"</td></tr>"
                         } 
                    	if(data[i].status==5)
                		{
                		    con += "<tr><td>" + myTime(data[i].start) + "</td><td>"+data[i].nodeName +"</p>"+"<span>已签收</span></p></td><td>" + data[i].userInfo+"</td></tr>"
                         } 
                    }
                    $("#table2").html(con);
                  	 }
                },
                error:function(){
                    alert("获取失败b");
                }
            });
        }
$(document).on('click','.eID',function(){
	
	 var eID=$(this).parents("tr").find("td").eq(0).text();
	 $.ajax({
        type:"get",
        async:false,
        url:"http://localhost:8080/TestCxfHibernate/REST/Domain/getExpressSheet/"+eID,
        beforeSend:function(request){  request.setRequestHeader("token",$.cookie('userToken'));   },
        dataType:"json",
        success:function(data){
        	if(data.code==102){
       		 alert("信息获取失败！");
       	 }
       	 else{

        	                  	
             if(data=='')
             	alert("该运单不存在");
             else{
           	  var accepter=data.accepter;
               	var deliver=data.deliver;
               	var accepteTime=data.accepteTime;
               	var deliveTime=data.deliveTime;
               	
               	  if(accepter==undefined||accepter==''||accepter==null)
                    		accepter="尚未揽收";
                    	if(deliver==undefined||deliver==''||deliver==null)
                    		deliver="尚未派送";
                    	if(accepteTime==undefined||accepteTime==''||accepteTime==null)
                    		accepteTime="尚未揽收";
                    	else
                    		accepteTime=myTime(accepteTime);
                    	if(deliveTime==undefined||deliveTime==''||deliveTime==null)
                    		deliveTime="尚未派送";
                    	else
                    		deliveTime=myTime(deliveTime);
              	sender={
                 		 "ID":data.sender.ID,
                 		"name":data.sender.name,
                 			"telCode":data.sender.telCode,
                 		"postCode":data.sender.postCode,
                 		"regionCode":data.sender.regionCode,
                 			"regionString":data.sender.regionString,
                 		"address":data.sender.address,
                 			"department":data.sender.department
                 			
                  };
              	receiver={
                 		 "ID":data.recever.ID,
                 		"name":data.recever.name,
                 			"telCode":data.recever.telCode,
                 		"postCode":data.recever.postCode,
                 		"regionCode":data.recever.regionCode,
                 			"regionString":data.recever.regionString,
                 		"address":data.recever.address,
                 			"department":data.recever.department
                 			
                  };
              	express={
              			"ID":data.ID,
              			"sender":sender,
              			"receiver":receiver,
              			"tranFee":data.tranFee,
              			"insuFee":data.insuFee,
              			"packageFee":data.packageFee,
              			"status":data.status,
              			"weight":data.weight,
              			"type":data.type,
              			"accepter":accepter,
              			"deliver":deliver,
              			"accepteTime":accepteTime,
              			"deliveTime":deliveTime
              			
              	};
              
               $("#eid").text(express.ID);
     		 //寄件人详细信息
               $("#esid").text(express.sender.ID);
               $("#esname").text(express.sender.name);
               $("#estel").text(express.sender.telCode);
               $("#espos").text(express.sender.postCode);
               $("#esreg").text(express.sender.regionCode);
               $("#esregS").text(express.sender.regionString);
               $("#esadd").text(express.sender.address);
               $("#esdpt").text(express.sender.department);
               //收件人详细信息
               $("#erid").text(express.receiver.ID);
               $("#ername").text(express.receiver.name);
               $("#ertel").text(express.receiver.telCode);
               $("#erpos").text(express.receiver.postCode);
               $("#erreg").text(express.receiver.regionCode);
               $("#erregS").text(express.receiver.regionString);
               $("#eradd").text(express.receiver.address);
               $("#erdpt").text(express.receiver.department);
               
     		  $("#ewei").text(express.weight);
     		  $("#etrf").text(express.tranFee);
     		  $("#epaf").text(express.packageFee);
     		  $("#einf").text(express.insuFee);
     		  $("#eacc").text(express.accepter);
     		  $("#eact").text(express.accepteTime);
     		 $("#edel").text(express.deliver);
    		  $("#edet").text(express.deliveTime);
    		  $("#etyp").text(express.type);
    		  $("#esta").text(express.status);
    		  
     		  $("#expModal").modal('show');
             		
             }
       	 }
        },
        error:function(){
            alert("该运单不存在");
        }
    });
	  return false;
});
$(document).on('click','.acc',function(){
	 var rec=null;
	 var aID=$(this).parents("tr").find("td").eq(3).text();
	 if(aID!="尚未揽收"){
		 $.ajax({
	         type:"get",
	         url:"http://localhost:8080/TestCxfHibernate/REST/Misc/getUserInfo/"+aID,
	         beforeSend:function(request){  request.setRequestHeader("token",$.cookie('userToken'));   },
	         dataType:"json",	    
	         success:function(data){
	        	 
	        	 if(data.code==102){
	        		 alert("信息获取失败！");
	        	 }
	        	 else{

	             if(data==''){
	             	alert("没有该用户");
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
	            	rec = {
	            			"UID":data.UID,
	            	        	"PWD": data.PWD,
	            	        	"name": data.name,
	            	        	"URull": type,
	            	        	"telCode":data.telCode,
	            	        	"status":status,
	            	        	"dptID": data.dptID,
	            	        	"receivePackageID":data.receivePackageID,
	            	        	"transPackageID":data.transPackageID,
	            	        	"delivePackageID":data.delivePackageID
	            	};
	            	 $("#uid").text(rec.UID);
	        		 $("#uname").text(rec.name);
	        		 $("#upwd").text(rec.PWD);
	        		 $("#usta").text(rec.status);
	        		 $("#urull").text(rec.URull);
	        		 $("#utel").text(rec.telCode);
	        		 $("#udpt").text(rec.dptID);
	        		 $("#urec").text(rec.receivePackageID);
	        		 $("#utrans").text(rec.transPackageID);
	        		 $("#udel").text(rec.delivePackageID);
	        		 $("#userModal").modal('show');
	             }
	        	 }
	         },
	         error:function(){
	             alert("没有该用户");
	         }
	     });
		
	 }
	 
	 
});

$(document).on('click','.del',function(){
	 var del=null;
	 var dID=$(this).parents("tr").find("td").eq(5).text();
   if(dID!="尚未派送"){
   	 $.ajax({
	         type:"get",
	         url:"http://localhost:8080/TestCxfHibernate/REST/Misc/getUserInfo/"+dID,
	         beforeSend:function(request){  request.setRequestHeader("token",$.cookie('userToken'));   },
	         dataType:"json",	    
	         success:function(data){
	        	 if(data.code==102){
	        		 alert("信息获取失败！");
	        	 }
	        	 else{

	           
	             if(data==''){
	             	alert("没有该用户");
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
	            	del = {
	            			"UID":data.UID,
	            	        	"PWD": data.PWD,
	            	        	"name": data.name,
	            	        	"URull": type,
	            	        	"telCode":data.telCode,
	            	        	"status":status,
	            	        	"dptID": data.dptID,
	            	        	"receivePackageID":data.receivePackageID,
	            	        	"transPackageID":data.transPackageID,
	            	        	"delivePackageID":data.delivePackageID
	            	};
	            	 $("#uid").text(del.UID);
	        		 $("#uname").text(del.name);
	        		 $("#upwd").text(del.PWD);
	        		 $("#usta").text(del.status);
	        		 $("#urull").text(del.URull);
	        		 $("#utel").text(del.telCode);
	        		 $("#udpt").text(del.dptID);
	        		 $("#urec").text(del.receivePackageID);
	        		 $("#utrans").text(del.transPackageID);
	        		 $("#udel").text(del.delivePackageID);
	        		 $("#userModal").modal('show');
	             }
	        	 }
	         },
	         error:function(){
	             alert("没有该用户");
	         }
	     });
	 }
	 
});
  $(document).on('click','.senderD',function(){
	
	  if(sender!=null){
		  $("#cid").text(sender.ID);
		  $("#cname").text(sender.name);
		  $("#ctel").text(sender.telCode);
		  $("#cpos").text(sender.postCode);
		  $("#creg").text(sender.regionCode);
		  $("#cregS").text(sender.regionString);
		  $("#cdep").text(sender.department);
		  $("#cadd").text(sender.address);
		  $("#detailModal").modal('show');
		  
	  }
	  else{
		  alert("用户信息获取出错！");
	  }
	  return false;
  });
  
  $(document).on('click','.receiverD',function(){
	  if(receiver!=null){
		  $("#cid").text(receiver.ID);
		  $("#cname").text(receiver.name);
		  $("#ctel").text(receiver.telCode);
		  $("#cpos").text(receiver.postCode);
		  $("#creg").text(receiver.regionCode);
		  $("#cregS").text(receiver.regionString);
		  $("#cdep").text(receiver.department);
		  $("#cadd").text(receiver.address);
		  $("#detailModal").modal('show');
		  
	  }
	  else{
		  alert("用户信息获取出错！");
	  }
	  return false;
  });
  
  function myTime(time){
	     
	     var dateee = new Date(time).toJSON();

	//  var dateee = new Date("2017-07-09T09:46:49.667").toJSON();
	  
	  var date = new Date(+new Date(dateee)+8*3600*1000).toISOString().replace(/T/g,' ').replace(/\.[\d]{3}Z/,'')  
	  
	     return date;

	}
