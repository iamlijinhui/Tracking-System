 function fnSearch(){
            var id=$("#package_id").val();
            $.ajax({
                type:"get",
                async:false,
                url:"http://localhost:8080/TestCxfHibernate/REST/Domain/getTransPackage/"+id,
                beforeSend:function(request){  request.setRequestHeader("token",$.cookie('userToken'));   },
                dataType:"json",
                success:function(data){
                	if(data.code==102){
               		 alert("信息获取失败！");
               	 }
               	 else{
                    var con1 = "";
                    var m = 0;
                    var createTime=myTime(data.createTime);
                    var sourceNode=data.sourceNode;
                    if(sourceNode==undefined||sourceNode==''||sourceNode==null)
                    	sourceNode="未确定";
                    var targetNode=data.targetNode;
                    if(targetNode==undefined||targetNode==''||targetNode==null)
                    	targetNode="未确定";
                    var status=data.status;
                	if(status==0)
                		status="新建包裹";
                	else if(status==1)
                		status="揽收包裹";
                	else if(status==2)
                		status="转运包裹";
                	else if(status==3)
                		status="派送包裹";
                	else if(status==4)
                		status="已完成包裹";
                	else if(status==5)
                		status="打包中的包裹";
                        con1 += "<tr><td><a>"  + data.ID + "</a></td><td>" + sourceNode + "</td><td>"  + targetNode + "</td><td>" +createTime + "</td><td>"+status + "</td><td>";
               
                    $("#tablePac").html(con1);
               	 }
                },
                error:function(){
                    alert("获取失败");
                }
            });
            $.ajax({
                type:"get",
                async:false,
                url:"http://localhost:8080/TestCxfHibernate/REST/Domain/getExpressListInPackage/PackageId/"+id,
                beforeSend:function(request){  request.setRequestHeader("token",$.cookie('userToken'));   },
                dataType:"json",
                success:function(data){
                	if(data.code==102){
               		 alert("信息获取失败！");
               	 }
               	 else{
                    var con = "";
                    var m = 0;
                    $.each(data, function(i, item) {
                        m+=1;
                        var accepter=item.accepter;
                        var accepteTime=item.accepteTime;
                        var deliver=item.deliver;
                        var deliveTime=item.deliveTime;
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
 	                	
                
                        con += "<tr><td><a class='eID'>"  + item.ID + "</a></td><td><a class='acc'>" + accepter + "</a></td><td>"  + accepteTime + "</td><td><a class='del'>" + deliver + "</a></td><td>" + deliveTime + "</td></tr>"
                    });
                    $("#table").html(con);
                    $("#m").html(m);
               	 }
                },
                error:function(){
                    alert("获取失败");
                }
            });
 }
 
 var sender=null;
 var receiver=null;
 var express=null;
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
                	var status=data.status;
                	if(status==0)
                		status="刚创建";
                	else if(status==1)
                		status="刚揽收";
                	else if(status==2)
                		status="分拣中";
                	else if(status==3)
                		status="转运中";
                	else if(status==4)
                		status="派送中";
                	else if(status==5)
                		status="已签收";
                	
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
               			"status":status,
               			"weight":data.weight,
               		
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
	 var aID=$(this).parents("tr").find("td").eq(1).text();
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
	 var dID=$(this).parents("tr").find("td").eq(3).text();
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
 function myTime(time){
     
     var dateee = new Date(time).toJSON();

//  var dateee = new Date("2017-07-09T09:46:49.667").toJSON();
  
  var date = new Date(+new Date(dateee)+8*3600*1000).toISOString().replace(/T/g,' ').replace(/\.[\d]{3}Z/,'')  
  
     return date;

}

