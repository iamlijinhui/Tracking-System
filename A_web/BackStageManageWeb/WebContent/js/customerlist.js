
layui.use('table', function(){
	  var table = layui.table;
	  
	  table.render({
	    elem: '#customerlist'
	    ,url:'http://localhost:8080/TestCxfHibernate/REST/Misc/getAllCustomers'
	    	 
	    ,toolbar: '#toolbarDemo'
	    ,title: '客户数据表'
	    	,parseData:function(res){
	        	  console.log(res);//解决code为0的问题
	        	  return{
	        		  "code":0
	        		  ,"msg":""
	        	      ,"count":1000
	        	      ,"data":res
	        	  }
	          }
	    ,height: 400
	    ,cols: [[
	      {type: 'checkbox', fixed: 'left'}
	      ,{field:'ID', width:80, title: 'ID', fixed: 'left', unresize: true, sort: true}
	      ,{field:'name', width:80, title: '用户名', edit: 'text'}
	      ,{field:'telCode', width:150, title: '手机号'}
	       ,{field:'department', width:200, title: '部门'}                    
	       ,{field:'address', width:280, title: '地址'}  
	       ,{field:'regionCode', width:120, title: '区域编号'}
	       ,{field:'postCode', width:120, title: '邮政编码'}
	      ,{fixed: 'right', title:'操作', toolbar: '#barDemo', width:150}
	    ]]
	    ,page: true
	  });
	  
	  //头工具栏事件
	  table.on('toolbar(customerlist)', function(obj){
	    var checkStatus = table.checkStatus(obj.config.id);
	    switch(obj.event){
	      case 'getCheckData':
	        var data = checkStatus.data;
	        layer.alert(JSON.stringify(data));
	      break;
	      case 'getCheckLength':
	        var data = checkStatus.data;
	        layer.msg('选中了：'+ data.length + ' 个');
	      break;
	      case 'isAll':
	        layer.msg(checkStatus.isAll ? '全选': '未全选');
	      break;
	    };
	  });
	  
	  //监听行工具事件
	  table.on('tool(customerlist)', function(obj){
	    var data = obj.data;
	    //console.log(obj)
	    if(obj.event === 'del'){
	      layer.confirm('真的删除行么', function(index){       	            
	            	  var data = obj.data; //获得当前行数据
	            	  var $UID=data['ID'];  //获取属性uid的值	            	  
	            $.ajax({
	                    type:"get",
	                    url:"http://localhost:8080/TestCxfHibernate/REST/Misc/deleteCustomerInfo/"+$UID,
	                    
	                    dataType:"json",
	                    
	                    success:function(){
	                    	if(data=='')
	    	                	alert("该客户不存在");
	                    	else
	                            alert("删除成功");
	                    }
	            })
	        
	        layer.close(index);
	           
	      });
	    }
	    else if(obj.event === 'edit'){
	    	var data = obj.data; //获得当前行数据
      	    var $UID=data['ID'];  //获取属性uid的值	
      	    var $regioncode=data['regionCode'];
      	    var prv;
      	    var cty;
      	    var twn;
      	    
      	    //根据结点号获取省市县
      	  $.ajax({
	            type:"get",
	            async: false,
	            url:"http://localhost:8080/TestCxfHibernate/REST/Misc/getRegion/"+$regioncode,
//              beforeSend:function(request){  request.setRequestHeader("token",$.cookie('userToken'));   },
	            dataType:"json",	    
	            success:function(data){
	            	if(data.code==102){
	           		 alert("信息获取失败！");
	           	 }
	           	 else{

	            	prv=data.prv;
	            	cty=data.cty;
	            	twn=data.twn;
	           	 }
	            },
	            error:function(){
	                alert("没有该用户");
	            }
	        })
      	   
      	    //查询
      	  $.ajax({
	            type:"get",
	            url:"http://localhost:8080/TestCxfHibernate/REST/Misc/getCustomerInfo/"+$UID,
//              beforeSend:function(request){  request.setRequestHeader("token",$.cookie('userToken'));   },
	            dataType:"json",	    
	            success:function(data){
	            	if(data.code==102){
	           		 alert("信息获取失败！");
	           	 }
	           	 else{

	                if(data=='')
	                	alert("该客户不存在");
	                else{
	                	$('#addname').val(data.name);
	                	$('#addtel').val(data.telCode);
	                	$('#adddep').val(data.department);
	                	$('#addpos').val(data.postCode);
//	                	$('#province').val(prv);
//	                	$('#city').val(cty);
//	                	$('#area').val(twn);
	                	function1(prv,cty,twn);
	                	$('#address').val(data.address);
	                	
	                }
	            	$('#myModal').modal('show');
	           	 }
	            },
	            error:function(){
	                alert("没有该用户");
	            }
	        });
	        function function1(p,q,r){
	        	
	        var select1=document.getElementById("province").options;
	        var select2=document.getElementById("city").options;
	        var select3=document.getElementById("area").options;
      	      for(i=0;i<select1.length;i++){
      	    	 if(select1[i].text==p){    	    		 
      	    		 select1[i].selected=true;
      	    	 }   	    	
      	       }
      	      
      	    for(i=0;i<select2.length;i++){
     	    	 if(select2[i].text==q){    	    		 
     	    		 select2[i].selected=true;
     	    	 }   	    	
     	       }
      	     for(i=0;i<select3.length;i++){
   	    	    if(select3[i].text==r){    	    		 
   	    		 select3[i].selected=true;
   	    	 }   	    	
   	       }
      	  }
      	    //修改信息
        
      	        $("#add_btn").click(function () {
      	                $.ajax({
      	                    type: "POST",
      	                    url: "http://localhost:8080/TestCxfHibernate/REST/Misc/saveCustomerInfo?_type=json",
//                          beforeSend:function(request){  request.setRequestHeader("token",$.cookie('userToken'));   },
      	                    contentType: "application/json; charset=utf-8",
      	                    data: JSON.stringify(GetJsonData1()),
      	                    dataType: "json",
      	                    success: function (message) {
      	                    	if(data.code==102){
      	                 		 alert("信息获取失败！");
      	                 	 }
      	                 	 else{

      	                        alert("修改成功");
      	                 	 }
      	                    },
      	                    error: function (message) {
      	                        $("#request-process-patent").html("提交数据失败！");
      	                    }
      	                });
      	        });

      	
      	    function GetJsonData1() {
      	        var json = {
      	            "ID":$UID,
      	            "address": $("#address").val(),
              	    "department": $("#adddep").val(),
              	    "name": $("#addname").val(),
              	    "postCode":$("#addpos").val(),
              	    "regionCode":$("#area").val(),
              	    "telCode": $("#addtel").val()    
      	        };
      	        return json;
      	    }

	    }
	  });
	});



