 $(function(){
//获取运单信息
        $("#btn").click(function(){
            var $ID=$("#UID").val();
            
            //查询快递员
       	 $.ajax({
	         type:"get",
	         url:"http://localhost:8080/TestCxfHibernate/REST/Misc/getUserInfo/"+$ID,
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
	             if(data==''){
	             	$("#table1").html("<h3>没有该用户</h3>");
	             }
	             else{
	            	 var type;
	            	
	            	 if(data.URull==0)
	            		 type="配送员/揽收员/分拣员";
	            	 else if(data.URull==1)
	            		 type="经理/管理员";
	            	 else if(data.URull==2)
	            		 type="汽车司机";
	            	 
	            	
	                 con+="<tr><td>"+data.UID+"</td><td>"+data.name+"</td><td>"+type+"</td><td>"+data.dptID+"</td><td>"+data.telCode+"</td></tr>";
	             }
	             $("tbody").html(con);
	        	 }
	         },
	         error:function(){
	             alert("没有该用户");
	         }
	     });
            //查询处理包裹
            $.ajax({
                type:"get",
//            
                url:"http://localhost:8080/TestCxfHibernate/REST/Misc/getUserPackages/"+$ID,
                beforeSend:function(request){  request.setRequestHeader("token",$.cookie('userToken'));   },
                dataType:"json",
                success:function(data){
                	if(data.code==102){
               		 alert("信息获取失败！");
               	 }
               	 else{
                    alert("获取成功");
                    var con = "";
                    for (var i = 0; i < data.length; i++) {
                    	var status=data[i].pkg.status;
                    	if(status==0)
                    		status="负责新建包裹";
                    	else if(status==1)
                    		status="负责揽收包裹";
                    	else if(status==2)
                    		status="负责转运包裹";
                    	else if(status==3)
                    		status="负责派送包裹";
                    	else if(status==4)
                    		status="已完成包裹";
                    	else if(status==5)
                    		status="负责打包包裹";
                    con += "<tr><td>"  + data[i].SN  +"</td><td>"+ data[i].pkg.ID +"</td><td>"+ status +"</td><td>"+ myTime(data[i].pkg.createTime) +"</td></tr>";
                    }
                    $("#table").html(con);
               	 }
                },
                error:function(){
                    alert("获取失败le");
                }
            })
        })
     
        function myTime(time){
            
            var dateee = new Date(time).toJSON();

        //  var dateee = new Date("2017-07-09T09:46:49.667").toJSON();
         
         var date = new Date(+new Date(dateee)+8*3600*1000).toISOString().replace(/T/g,' ').replace(/\.[\d]{3}Z/,'')  
         
            return date;

        }

    })