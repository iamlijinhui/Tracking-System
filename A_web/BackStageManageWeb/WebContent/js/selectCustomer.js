$(function(){
	
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
       	 }		
        }
    });
});