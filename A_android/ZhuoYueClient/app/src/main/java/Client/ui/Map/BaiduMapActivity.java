package Client.ui.Map;

import android.os.Bundle;

import Client.ui.main.R;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
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
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.overlayutil.BikingRouteOverlay;
import com.baidu.mapapi.overlayutil.DrivingRouteOverlay;
import com.baidu.mapapi.overlayutil.WalkingRouteOverlay;
import com.baidu.mapapi.search.core.SearchResult;
import com.baidu.mapapi.search.route.BikingRouteResult;
import com.baidu.mapapi.search.route.DrivingRoutePlanOption;
import com.baidu.mapapi.search.route.DrivingRouteResult;
import com.baidu.mapapi.search.route.IndoorRouteResult;
import com.baidu.mapapi.search.route.MassTransitRouteResult;
import com.baidu.mapapi.search.route.OnGetRoutePlanResultListener;
import com.baidu.mapapi.search.route.PlanNode;
import com.baidu.mapapi.search.route.RoutePlanSearch;
import com.baidu.mapapi.search.route.TransitRouteResult;
import com.baidu.mapapi.search.route.WalkingRouteResult;

import java.util.ArrayList;

public class BaiduMapActivity extends AppCompatActivity {
    private static final String TAG ="BaiduMapActivity" ;

    private MapView mMapView = null;
    private BaiduMap baiduMap;
    LocationClient  locationClient;
    private double latitude;
    private double longitude;
    private boolean firstLocation;
    public RoutePlanSearch mySearch;
    public PlanNode stNode;
    public PlanNode endNode;
    ArrayList<PlanNode> arg0 =new ArrayList<>();


    OnGetRoutePlanResultListener listener = new OnGetRoutePlanResultListener() {
        //获取步行线路规划结果
        @Override
        public void onGetWalkingRouteResult(WalkingRouteResult walkingRouteResult) {
            //创建WalkingRouteOverlay实例
            WalkingRouteOverlay overlay = new WalkingRouteOverlay(baiduMap);
            if (walkingRouteResult.getRouteLines().size() > 0) {
                //获取路径规划数据,(以返回的第一条数据为例)


                //为WalkingRouteOverlay实例设置路径数据
                baiduMap.setOnMarkerClickListener(overlay);
                overlay.setData(walkingRouteResult.getRouteLines().get(0));
//                //在地图上绘制WalkingRouteOverlay
                overlay.addToMap();
                overlay.zoomToSpan();
                Toast.makeText(getBaseContext(), "只能够绘图" + walkingRouteResult.getRouteLines().get(0).toString(), Toast.LENGTH_SHORT).show();

            }
        }

        //获取综合公共交通线路规划结果
        @Override
        public void onGetTransitRouteResult(TransitRouteResult transitRouteResult) {
        }

        //获取**跨城**综合公共交通线路规划结果
        @Override
        public void onGetMassTransitRouteResult(MassTransitRouteResult massTransitRouteResult) {
        }

        //获取驾车线路规划结果
        @Override
        public void onGetDrivingRouteResult(DrivingRouteResult drivingRouteResult) {
            if (drivingRouteResult == null || drivingRouteResult.error != SearchResult.ERRORNO.NO_ERROR) {
                Toast.makeText(BaiduMapActivity.this, "1抱歉，未找到结果" + drivingRouteResult.error, Toast.LENGTH_SHORT).show();
                Log.e("error", "" + drivingRouteResult.error.toString());
            }
            if (drivingRouteResult.error == SearchResult.ERRORNO.AMBIGUOUS_ROURE_ADDR) {
                // 起终点或途经点地址有岐义，通过以下接口获取建议查询信息
                drivingRouteResult.getSuggestAddrInfo();
                Toast.makeText(BaiduMapActivity.this, "建议地址信息" + drivingRouteResult.getSuggestAddrInfo(), Toast.LENGTH_SHORT).show();

                return;
            }
            if (drivingRouteResult.error == SearchResult.ERRORNO.NO_ERROR) {
                if (drivingRouteResult.getRouteLines().size() >= 1) {
                    DrivingRouteOverlay overlay = new DrivingRouteOverlay(baiduMap);
                    baiduMap.setOnMarkerClickListener(overlay);
                    overlay.setData(drivingRouteResult.getRouteLines().get(0));
                    overlay.addToMap();
                    overlay.zoomToSpan();
                } else {
                    Log.d("route result", "结果数<0");
                    return;
                }
            }

        }

        //室内路线规划结果
        @Override
        public void onGetIndoorRouteResult(IndoorRouteResult indoorRouteResult) {
        }

        //获取普通骑行路规划结果
        @Override
        public void onGetBikingRouteResult(BikingRouteResult bikingRouteResult) {
            if (bikingRouteResult == null || bikingRouteResult.error != SearchResult.ERRORNO.NO_ERROR) {
                Toast.makeText(BaiduMapActivity.this, "抱歉，未找到结果", Toast.LENGTH_SHORT).show();
            }
            if (bikingRouteResult.error == SearchResult.ERRORNO.AMBIGUOUS_ROURE_ADDR) {
                // 起终点或途经点地址有岐义，通过以下接口获取建议查询信息
                bikingRouteResult.getSuggestAddrInfo();
                return;
            }
            if (bikingRouteResult.error == SearchResult.ERRORNO.NO_ERROR) {
                Toast.makeText(BaiduMapActivity.this, "找到结果", Toast.LENGTH_SHORT).show();

                if (bikingRouteResult.getRouteLines().size() >= 1) {
                    BikingRouteOverlay overlay = new BikingRouteOverlay(baiduMap);
                    baiduMap.setOnMarkerClickListener(overlay);
                    overlay.setData(bikingRouteResult.getRouteLines().get(0));//设置路线数据
                    overlay.addToMap();
                    overlay.zoomToSpan();
                } else {
                    Log.d("route result", "结果数<0");
                    return;
                }
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_baidu_map);

        mMapView = (MapView) findViewById(R.id.bmapView);
        baiduMap = mMapView.getMap();

        initLocationOption();
        mySearch = RoutePlanSearch.newInstance();

        mySearch.setOnGetRoutePlanResultListener(listener);
        LatLng xy = new LatLng(34.822796,113.543162);
        LatLng arg = new LatLng(34.778894,113.701189);
        LatLng end = new LatLng(34.778894,113.701189);
        arg0.add(PlanNode.withLocation(arg));
        //  stNode = PlanNode.withLocation(xy);
        stNode = PlanNode.withCityNameAndPlaceName("郑州市", "高新区");
        endNode = PlanNode.withCityNameAndPlaceName("北京", "百度科技园");
        mySearch.drivingSearch((new DrivingRoutePlanOption())
                .from(stNode)
                .passBy(arg0)
                .to(endNode));
//        Btn1 = (Button) findViewById(R.id.pre);
//        Btn2 = (Button) findViewById(R.id.next);
//        Btn2.setOnClickListener(new View.OnClickListener() {
//
//            @Override
//            public void onClick(View v) {
//
//                Toast.makeText(getBaseContext(), "开始", Toast.LENGTH_SHORT).show();
//
//                mySearch.drivingSearch((new DrivingRoutePlanOption())
//                        .from(stNode)
//                .passBy(arg0)
//                        .to(endNode));
//
//            }
//        });
    }
    @Override
    protected void onStart()
    {
        // 如果要显示位置图标,必须先开启图层定位
        baiduMap.setMyLocationEnabled(true);
        super.onStart();
    }

    @Override
    protected void onStop()
    {
        // 关闭图层定位
        baiduMap.setMyLocationEnabled(false);
//        locationClient.stop();
        super.onStop();
    }
    @Override
    protected void onResume() {
        super.onResume();
        //在activity执行onResume时执行mMapView. onResume ()，实现地图生命周期管理
        mMapView.onResume();
    }
    @Override
    protected void onPause() {
        super.onPause();
        //在activity执行onPause时执行mMapView. onPause ()，实现地图生命周期管理
        mMapView.onPause();
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        //在activity执行onDestroy时执行mMapView.onDestroy()，实现地图生命周期管理
        mMapView.onDestroy();
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
            baiduMap.animateMapStatus(update);
            update = MapStatusUpdateFactory.zoomBy(3f);
            // 放大
            baiduMap.animateMapStatus(update);



            MyLocationData locData = new MyLocationData.Builder()
                    .accuracy(location.getRadius())
                    // 此处设置开发者获取到的方向信息，顺时针0-360
                    .direction(location.getDirection()).latitude(location.getLatitude())
                    .longitude(location.getLongitude()).build();
            baiduMap.setMyLocationData(locData);
            latitude = location.getLatitude();
            //获取经度信息
            longitude = location.getLongitude();
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
        MyLocationListener myLocationListener = new MyLocationListener();
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
