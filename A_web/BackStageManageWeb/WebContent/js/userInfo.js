$(function(){
	var uid=getUID();
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
        		
        		 
        	 }
        },
        error:function(){
            alert("没有该用户");
        }
    })
})