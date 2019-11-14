 $(function(){
//获取运单信息
        $("#btn").click(function(){
            var $ID=$("#Express_id").val();
            $.ajax({
                type:"get",
//                url:"test2.json",
                url:"http://localhost:8080/TestCxfHibernate/REST/Domain/getExpressSheet/"+$ID,
              beforeSend:function(request){  request.setRequestHeader("token",$.cookie('userToken'));   },
                dataType:"json",
                success:function(data){
                	if(data.code==102){
               		 alert("信息获取失败！");
               	 }
               	 else{
                    alert("获取成功");
                    var con = "";
                    con += "<tr><td>"  + data.ID + "</td><td>"  + data.recever.name + "</td><td>"  + data.recever.regionString + data.recever.address + "</td><td>" + data.sender.name + "</td><td>"  + data.sender.regionString + data.sender.address + "</td></tr>";
                    $("#table").html(con);
               	 }
                },
                error:function(){
                    alert("获取失败");
                }
            })
        })
        $("#DL").click(function(){
            $.ajax({
                type:"get",
                url:"http://localhost:8080/TestCxfHibernate/REST/Domain/dispatchExpressSheetId/id/"+$("#Express_id").val()+"/uid/"+$("#UID").val(),
              beforeSend:function(request){  request.setRequestHeader("token",$.cookie('userToken'));   },
                dataType:"json",
                success:function(data){
                	if(data.code==102){
               		 alert("信息获取失败！");
               	 }
               	 else{
                    alert("已开始配送");
               	 }
                },
                error:function(){
                    alert("获取任务失败");
                }
            })
        });
        $("#QS").click(function(){
            $.ajax({
                type:"get",
                url:"http://localhost:8080/TestCxfHibernate/REST/Domain/deliveryExpressSheetId/id/"+$("#Express_id").val()+"/uid/"+$("#UID").val(),
              beforeSend:function(request){  request.setRequestHeader("token",$.cookie('userToken'));   },
                dataType:"json",
                success:function(data){
                	if(data.code==102){
               		 alert("信息获取失败！");
               	 }
               	 else{
                    alert("快递已签收");
               	 }
                },
                error:function(){
                    alert("快递签收失败");
                }
            })
        })

    })