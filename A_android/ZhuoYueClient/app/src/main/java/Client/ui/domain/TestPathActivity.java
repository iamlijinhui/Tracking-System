package Client.ui.domain;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.baidu.location.BDAbstractLocationListener;
import com.baidu.location.BDLocation;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.map.Overlay;
import com.baidu.mapapi.map.OverlayOptions;
import com.baidu.mapapi.map.PolylineOptions;
import com.baidu.mapapi.model.LatLng;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import Client.loader.LocationLoader;
import Client.misc.model.Location;
import Client.net.IDataAdapter;
import Client.ui.main.R;

public class TestPathActivity extends AppCompatActivity implements IDataAdapter<List<Location>> {

    private MapView mMapView = null;
    private BaiduMap baiduMap;
    List<Location> locationsList;
    LocationLoader locationLoader;
    LocationClient locationClient;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_path);

        mMapView = (MapView) findViewById(R.id.test_mapView);
        baiduMap = mMapView.getMap();
        initLocationOption();
        LatLng l1 = new LatLng(34.8224106,113.5417633);
        MapStatusUpdate update = MapStatusUpdateFactory.newLatLng(l1);
        // 移动到某经纬度
        baiduMap.animateMapStatus(update);
        update = MapStatusUpdateFactory.zoomBy(5f);
        // 放大
        baiduMap.animateMapStatus(update);
        locationLoader = new LocationLoader(TestPathActivity.this,TestPathActivity.this);
        locationLoader.getPosition(2+"");



    }

    @Override
    public List<Location> getData() {
        return locationsList;
    }

    @Override
    public void setData(List<Location> data) {
        locationsList = data;
    }

    @Override
    public void notifyDataSetChanged() {

//        //构建折线点坐标
//        LatLng p1 = new LatLng(34.8163948, 113.5357819);
//        LatLng p2 = new LatLng(34.8263948, 113.5457819);
//        LatLng p3 = new LatLng(34.8363948, 113.5557819);
        List<LatLng> points = new ArrayList<LatLng>();
//        points.add(p1);
//        points.add(p2);
//        points.add(p3);



        if(null == locationsList || locationsList.size() ==0 ){

            Toast.makeText(TestPathActivity.this,"暂时没有物流信息!",Toast.LENGTH_SHORT).show();
        }else{
//不为空时绘图
            Iterator<Location> iter = locationsList.iterator();
            while(iter.hasNext()){  //执行过程中会执行数据锁定，性能稍差，若在循环过程中要去掉某个元素只能调用iter.remove()方法。
                Location location = iter.next();
                LatLng p = new LatLng(location.getX(), location.getY());
                points.add(p);
            }

            //设置折线的属性
            OverlayOptions mOverlayOptions = new PolylineOptions()
                    .width(5)
                    .color(0xff0080ff)
                    .points(points);
            //在地图上绘制折线
            //mPloyline 折线对象
            Overlay mPolyline = baiduMap.addOverlay(mOverlayOptions);

        }


    }
    public class MyLocationListener extends BDAbstractLocationListener {
        @Override
        public void onReceiveLocation(BDLocation location) {
            //mapView 销毁后不在处理新接收的位置
            if (location == null || mMapView == null){
                return;
            }



            LatLng ll = new LatLng(location.getLatitude(),
                    location.getLongitude());
            MapStatusUpdate update = MapStatusUpdateFactory.newLatLng(ll);
            // 移动到某经纬度
//            baiduMap.animateMapStatus(update);
//            update = MapStatusUpdateFactory.zoomBy(5f);
//            // 放大
//            baiduMap.animateMapStatus(update);



            MyLocationData locData = new MyLocationData.Builder()
                    .accuracy(location.getRadius())
                    // 此处设置开发者获取到的方向信息，顺时针0-360
                    .direction(location.getDirection()).latitude(location.getLatitude())
                    .longitude(location.getLongitude()).build();
            baiduMap.setMyLocationData(locData);
//            latitude = location.getLatitude();
//            //获取经度信息
//            longitude = location.getLongitude();
            //获取定位精度，默认值为0.0f
//            float radius = location.getRadius();
//            //获取经纬度坐标类型，以LocationClientOption中设置过的坐标类型为准
//            String coorType = location.getCoorType();
//            //获取定位类型、定位错误返回码，具体信息可参照类参考中BDLocation类中的说明
//            int errorCode = location.getLocType();
        }
    }

    private void initLocationOption() {
//定位服务的客户端。宿主程序在客户端声明此类，并调用，目前只支持在主线程中启动
        locationClient = new LocationClient(getApplicationContext());
//声明LocationClient类实例并配置定位参数
        LocationClientOption locationOption = new LocationClientOption();
        TestPathActivity.MyLocationListener myLocationListener = new TestPathActivity.MyLocationListener();
//注册监听函数
        locationClient.registerLocationListener(myLocationListener);
//可选，默认高精度，设置定位模式，高精度，低功耗，仅设备
        locationOption.setLocationMode(LocationClientOption.LocationMode.Hight_Accuracy);
//可选，默认gcj02，设置返回的定位结果坐标系，如果配合百度地图使用，建议设置为bd09ll;
        locationOption.setCoorType("bd09ll");
//可选，默认0，即仅定位一次，设置发起连续定位请求的间隔需要大于等于1000ms才是有效的
        locationOption.setScanSpan(1000);
//可选，设置是否需要地址信息，默认不需要
        locationOption.setIsNeedAddress(true);
//可选，设置是否需要地址描述
        locationOption.setIsNeedLocationDescribe(true);
//可选，设置是否需要设备方向结果
        locationOption.setNeedDeviceDirect(false);
//可选，默认false，设置是否当gps有效时按照1S1次频率输出GPS结果
        locationOption.setLocationNotify(true);
//可选，默认true，定位SDK内部是一个SERVICE，并放到了独立进程，设置是否在stop的时候杀死这个进程，默认不杀死
        locationOption.setIgnoreKillProcess(true);
//可选，默认false，设置是否需要位置语义化结果，可以在BDLocation.getLocationDescribe里得到，结果类似于“在北京天安门附近”
        locationOption.setIsNeedLocationDescribe(true);
//可选，默认false，设置是否需要POI结果，可以在BDLocation.getPoiList里得到
        locationOption.setIsNeedLocationPoiList(true);
//可选，默认false，设置是否收集CRASH信息，默认收集
        locationOption.SetIgnoreCacheException(false);
//可选，默认false，设置是否开启Gps定位
        locationOption.setOpenGps(true);
//可选，默认false，设置定位时是否需要海拔信息，默认不需要，除基础定位版本都可用
        locationOption.setIsNeedAltitude(false);
//设置打开自动回调位置模式，该开关打开后，期间只要定位SDK检测到位置变化就会主动回调给开发者，该模式下开发者无需再关心定位间隔是多少，定位SDK本身发现位置变化就会及时回调给开发者
        locationOption.setOpenAutoNotifyMode();
//设置打开自动回调位置模式，该开关打开后，期间只要定位SDK检测到位置变化就会主动回调给开发者
        locationOption.setOpenAutoNotifyMode(3000,1, LocationClientOption.LOC_SENSITIVITY_HIGHT);
//需将配置好的LocationClientOption对象，通过setLocOption方法传递给LocationClient对象使用
        locationClient.setLocOption(locationOption);
//开始定位
        locationClient.start();
    }

}
