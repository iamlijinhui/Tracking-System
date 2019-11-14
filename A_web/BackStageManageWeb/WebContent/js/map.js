/*
* @Author: Administrator
* @Date:   2018-05-07 23:44:18
* @Last Modified by:   Administrator
* @Last Modified time: 2018-05-08 00:15:48
*/

	// 百度地图API功能
	var map = new BMap.Map("allmap");
	var point = new BMap.Point(116.331398,39.897445);
	map.centerAndZoom(point,11);

	function theLocation(){
		var city = document.getElementById("cityName").value;
		if(city != ""){
			map.centerAndZoom(city,13);      // 用城市名设置地图中心点
		}
	}

    var map = new BMap.Map("allmap");  
	map.centerAndZoom(new BMap.Point(116.4035,39.915),11); 
	setTimeout(function(){
		map.setZoom(11);   
	}, 4000);  //4秒后放大到10级
	

    var map = new BMap.Map("allmap");
     map.centerAndZoom(new BMap.Point(116.404, 39.915), 11);
    // 添加带有定位的导航控件
    var navigationControl = new BMap.NavigationControl({
    // 靠左上角位置
    anchor: BMAP_ANCHOR_TOP_LEFT,
    // LARGE类型
    type: BMAP_NAVIGATION_CONTROL_LARGE,
    // 启用显示定位
    enableGeolocation: true
    });
    map.addControl(navigationControl);
    // 添加定位控件
    var geolocationControl = new BMap.GeolocationControl();
    geolocationControl.addEventListener("locationSuccess", function(e){
    // 定位成功事件
    var address = '';
    address += e.addressComponent.province;
    address += e.addressComponent.city;
    address += e.addressComponent.district;
    address += e.addressComponent.street;
    address += e.addressComponent.streetNumber;
    alert("当前定位地址为：" + address);
    });
    geolocationControl.addEventListener("locationError",function(e){
    // 定位失败事件
    alert(e.message);
    });
    // map.addControl(geolocationControl);
    // map.enableScrollWheelZoom(true);
    // map.centerAndZoom(point,11);
    // map.centerAndZoom(new BMap.Point(116.404, 39.915), 11);

    var geolocation = new BMap.Geolocation();
    geolocation.getCurrentPosition(function(r){console.log(r.point)
        if(this.getStatus() == BMAP_STATUS_SUCCESS){
            var mk = new BMap.Marker(r.point);
            map.addOverlay(mk);//标出所在地
            map.panTo(r.point);//地图中心移动
            //alert('您的位置：'+r.point.lng+','+r.point.lat);
            var point = new BMap.Point(r.point.lng,r.point.lat);//用所定位的经纬度查找所在地省市街道等信息
            var gc = new BMap.Geocoder();
            gc.getLocation(point, function(rs){
               var addComp = rs.addressComponents; console.log(rs.address);//地址信息
               //alert(rs.address);//弹出所在地址

            });
        }else {
            alert('failed'+this.getStatus());
        }        
    },{enableHighAccuracy: true})
    map.addControl(geolocationControl);
    map.enableScrollWheelZoom(true);