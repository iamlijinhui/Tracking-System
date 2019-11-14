
$("#printBarcode").click(function(){
	//直接调用浏览器打印功能 
	bdhtml=window.document.body.innerHTML; 
	//定义打印区域起始字符，根据这个截取网页局部内容     
	sprnstr="<!--startprint-->"; 
	//打印区域开始的标记 
	eprnstr="<!--endprint-->"; 
	//打印区域结束的标记  
	prnhtml=bdhtml.substr(bdhtml.indexOf(sprnstr)+17); 
	prnhtml=prnhtml.substring(0,prnhtml.indexOf(eprnstr));
	window.document.body.innerHTML=prnhtml; 
	//开始打印 
	window.print(); 
	//还原网页内容     
	window.document.body.innerHTML=bdhtml;
});

$("#generateBarcode").click(function(){
	$("#image").html("<img id='bcode'/>");
	var barvalue=$("#barcodeValue").val();
	if(barvalue.length<12){
		alert("快递单号长度不够，请重新输入！");
	}else{
	if(barvalue==""){
		alert("请输入条形码字符串！");
	}else{
		$("#bcode").JsBarcode(barvalue);
	}
	}
});
$("#randomGenerateBarcode").click(function(){
	$("#image").html("<img id='bcode'/>");
	var barvalue=geneUniqNum(12,10);
	$("#bcode").JsBarcode(barvalue);
	$("#barcodeValue").val(barvalue);
	
});

$("#getPacID").click(function(){
	$("#image").html("<img id='bcode'/>");
	var barvalue=$("#barcodeValue").val();
	
	if(barvalue.length<8)
		alert("包裹单号长度不够，请重新输入！");
	else{
	if(barvalue==""){
		alert("请输入条形码字符串！");
	}else{
		$("#bcode").JsBarcode(barvalue);
	}
	}
});
$("#getRandPacID").click(function(){
	$("#image").html("<img id='bcode'/>");
	var barvalue=geneUniqNum(8,10);
	$("#bcode").JsBarcode(barvalue);
	$("#barcodeValue").val(barvalue);
	
});

