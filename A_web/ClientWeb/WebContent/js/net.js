$("#nodeName").on("click",function(){
                   var nodeName = $("#town").val();
                   var node=nodeName+"00";
                  
                   $.ajax({
                       type:"get",
                       url:"http://localhost:8080/TestCxfHibernate/REST/Misc/getNode/"+node,
                       dataType:"json",                     
                       success:function(data){
                       	var con="";                   	
        	                if(data=='')
        	                	alert("该网点不存在");
        	                else{
                           con += "<tr><td>" + data.ID + "</td><td>" + data.nodeName +  "</td><td>" + data.telCode +"</td></tr>"	                	
        	                }
                           $("#table").html(con);
                       },
                       error:function(){
                           alert("该网点不存在");
                       }
                   })
               })


 