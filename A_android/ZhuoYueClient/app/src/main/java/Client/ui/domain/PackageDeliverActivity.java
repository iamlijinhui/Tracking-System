package Client.ui.domain;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.baidu.location.BDAbstractLocationListener;
import com.baidu.location.BDLocation;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.InfoWindow;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.map.Overlay;
import com.baidu.mapapi.map.OverlayOptions;
import com.baidu.mapapi.map.PolylineOptions;
import com.baidu.mapapi.model.LatLng;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import Client.loader.PathLoader;
import Client.misc.model.CustomerInfo;
import Client.misc.model.ExpressSheet;
import Client.misc.model.Location;
import Client.misc.model.Path;
import Client.net.MyDataAdapter;
import Client.ui.main.R;
import zxing.util.CaptureActivity;

public class PackageDeliverActivity extends AppCompatActivity implements MyDataAdapter<List<Path>> {
    RecyclerView recyclerView;
    List<Path> pathList;
    PathLoader pathLoader;
    ExpressSheet expressSheet;
    TextView tvReceiveSite;
    TextView tvEsId;
    CustomerInfo customerInfo;
    ViewGroup  gvLookMap;
    String id;

    private MapView mMapView = null;
    private BaiduMap baiduMap;
    LocationClient locationClient;
    List<Location> locationsList;

    public static final int REQUEST_HISTORY = 106;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_package_deliver);
        Intent intent = getIntent();
        //expressSheet = (ExpressSheet) intent.getSerializableExtra("expressSheet");
        //Toast.makeText(this,expressSheet+"",Toast.LENGTH_SHORT);
        recyclerView = (RecyclerView)findViewById(R.id.package_deliver_rec);
        tvReceiveSite = (TextView)findViewById(R.id.package_deliver_receivesite);
        tvEsId = (TextView) findViewById(R.id.package_deliver_esid);
        gvLookMap = (ViewGroup)findViewById(R.id.package_deliver_lookmap);


        setListener();
        Intent mIntent = getIntent();
        if (mIntent.hasExtra("Action")) {
           id = mIntent.getStringExtra("Action");
            try {
                pathLoader = new PathLoader(PackageDeliverActivity.this, PackageDeliverActivity.this);
                String esId = new String("");
                pathLoader.getPath(id);
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
        else{
            this.setResult(RESULT_CANCELED, mIntent);
            this.finish();
        }

        //地图相关
        mMapView = (MapView) findViewById(R.id.deliver_mapView);

        final ScrollView scrollView = (ScrollView)findViewById(R.id.scroll_view);
        mMapView.getChildAt(0).setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if(event.getAction() == MotionEvent.ACTION_UP){
                    //允许ScrollView截断点击事件，ScrollView可滑动
                    scrollView.requestDisallowInterceptTouchEvent(false);
                }else{
                    //不允许ScrollView截断点击事件，点击事件由子View处理
                    scrollView.requestDisallowInterceptTouchEvent(true);
                }
                return false;
            }
        });

//        mMapView.setOnTouchListener(new View.OnTouchListener() {
//            @Override
//            public boolean onTouch(View view, MotionEvent event) {
//                Log.i("touch" , "--tv_touch--");
//                mMapView.getParent().requestDisallowInterceptTouchEvent(true);
//                if (event.getAction() == MotionEvent.ACTION_UP) {
//                    Log.i("touch" , "--ACTION_UP-");
//                }
//                if (event.getAction() == MotionEvent.ACTION_DOWN) {
//                    Log.i("touch" , "--ACTION_DOWN-");
//                }
//                if (event.getAction() == MotionEvent.ACTION_MOVE) {
//                    Log.i("touch" , "--ACTION_MOVE-");
//                }
//                return true;
//            }
//        });
        baiduMap = mMapView.getMap();
        initLocationOption();
        LatLng l1 = new LatLng(34.8224106,113.5417633);
        MapStatusUpdate update = MapStatusUpdateFactory.newLatLng(l1);
        // 移动到某经纬度
        baiduMap.animateMapStatus(update);
        update = MapStatusUpdateFactory.zoomBy(5f);
        // 放大
        baiduMap.animateMapStatus(update);

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        switch (resultCode) {
            case RESULT_OK:
                switch (requestCode){
                    /*
                     * 扫码并查询历史信息
                     */
                    case REQUEST_HISTORY:

                        if (data.hasExtra("BarCode")) {
                            id = data.getStringExtra("BarCode");
                            tvEsId.setText("卓越快递\n快递单号：" + id);
                            try {
                                pathLoader = new PathLoader(PackageDeliverActivity.this, PackageDeliverActivity.this);
                                String esId = new String("");
                                pathLoader.getPath(id);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                        break;
                }
                break;
            default:
                break;
        }
    }

    private void setListener() {
        gvLookMap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mMapView.setVisibility(View.VISIBLE);
                gvLookMap.setVisibility(View.INVISIBLE);
            }
        });

        tvEsId.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            }
        });
    }


    @Override
    public void packDataSetChanged() {

    }

    @Override
    public void unPackDataSetChanged() {

    }

    @Override
    public List<Path> getData() {
        return pathList;
    }

    @Override
    public void setData(List<Path> data) {
        pathList = data;
    }

    @Override
    public void notifyDataSetChanged() {

        //Toast.makeText(PackageDeliverActivity.this,pathList.get(0).getLocations()+"",Toast.LENGTH_SHORT).show();
        locationsList = pathList.get(0).getLocations(); //获取坐标信息
        customerInfo = pathList.get(0).getReceiver();
        tvReceiveSite.setText("[收货地址]"+customerInfo.getRegionString()+customerInfo.getAddress()+customerInfo.getDepartment());
        Collections.reverse(pathList);          //将List逆序
        PackageDeliverAdapter logisticsAdapter = new PackageDeliverAdapter(this, pathList);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(logisticsAdapter);



        //Toast.makeText(PackageDeliverActivity.this,locationsList+"",Toast.LENGTH_SHORT).show();

        List<LatLng> points = new ArrayList<LatLng>();

        if(null == locationsList || locationsList.size() ==0 ){

           Toast.makeText(PackageDeliverActivity.this,"暂时没有物流信息!",Toast.LENGTH_SHORT).show();
        }else{

            //定义Maker坐标点
            LatLng point = new LatLng(locationsList.get(0).getX(),locationsList.get(0).getY());
            LatLng point_now = new LatLng(locationsList.get(locationsList.size()-1).getX(),locationsList.get(locationsList.size()-1).getY());



//不为空时绘图
            Iterator<Location> iter = locationsList.iterator();
            while(iter.hasNext()){  //执行过程中会执行数据锁定，性能稍差，若在循环过程中要去掉某个元素只能调用iter.remove()方法。
                Location location = iter.next();
                LatLng p = new LatLng(location.getX(), location.getY());
                points.add(p);
            }

//构建Marker图标
            BitmapDescriptor bitmap = BitmapDescriptorFactory
                    .fromAssetWithDpi("Icon_start.png");
                  //  .fromResource(R.drawable.icon_start);
//构建MarkerOption，用于在地图上添加Marker
            OverlayOptions option = new MarkerOptions()
                    .position(point)
                    .icon(bitmap);
//在地图上添加Marker，并显示
            baiduMap.addOverlay(option);
            //////////////////////////////////////////////////////////////////
            BitmapDescriptor bitmap1 = BitmapDescriptorFactory
                    .fromAssetWithDpi("Icon_bus_station.png");
            BitmapDescriptor bitmap2 = BitmapDescriptorFactory
                    .fromAssetWithDpi("Icon_shou.png");
            //  .fromResource(R.drawable.icon_start);
//构建MarkerOption，用于在地图上添加Marker
            if(pathList.get(0).getStatus()!=5)
            {
               // Toast.makeText(this,"status"+pathList.get(0).getStatus(),Toast.LENGTH_SHORT).show();
                OverlayOptions option1 = new MarkerOptions()
                        .position(point_now)
                        .icon(bitmap1);
//在地图上添加Marker，并显示
                baiduMap.addOverlay(option1);
            }
            if(pathList.get(0).getStatus()==5)
            {
                OverlayOptions option2 = new MarkerOptions()
                        .position(point_now)
                        .icon(bitmap2);
//在地图上添加Marker，并显示
                baiduMap.addOverlay(option2);
                Button button = new Button(this);
//                button.setBackgroundResource(R.drawable.popup);
                button.setText("已签收");
                InfoWindow mInfoWindow = new InfoWindow(button, point_now, -100);

//使InfoWindow生效
                baiduMap.showInfoWindow(mInfoWindow);
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

    private void esHistory(){
        Intent intent = new Intent();
        intent.setClass(this, CaptureActivity.class);
        startActivityForResult(intent, REQUEST_HISTORY);
    }




    //地图相关
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
        PackageDeliverActivity.MyLocationListener myLocationListener = new PackageDeliverActivity.MyLocationListener();
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
