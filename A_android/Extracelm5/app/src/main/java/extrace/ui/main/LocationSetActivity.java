package extrace.ui.main;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
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

import extrace.loader.ExpressListLoader;
import extrace.loader.UploadLocation;
import extrace.misc.model.Location;
import extrace.misc.model.UserInfo;
import extrace.ui.domain.PackageInfoActivity;
import extrace.ui.domain.PackagePaisongActivity;

public class LocationSetActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener{
    Switch stUpload;
    TextView tvPkgId;
    TextView tvPkgStatus;
    ViewGroup vgPkgInfo;
    private UserInfo user;
    private ExTraceApplication app;
    String pid;
    private Spinner spinner ;
    private ArrayAdapter<CharSequence> adapter ;    //下拉框
    int[] TIME = {3,5,10,20,40,60};

    //地图相关
    private MapView mMapView = null;
    private BaiduMap baiduMap;
    LocationClient locationClient;
    private double latitude;
    private double longitude;
    private boolean firstLocation;

    private Handler mHandler = null;
    private static final int UPDATE_TEXTVIEW = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location_set);
        app = (ExTraceApplication) getApplication();
        user = app.getLoginUser();

        tvPkgId = (TextView)findViewById(R.id.location_set_pkgid);
        tvPkgStatus = (TextView)findViewById(R.id.location_set_pkgstatus);
        vgPkgInfo = (ViewGroup)findViewById(R.id.location_set_pkginfo);
        ImageView ivBack = (ImageView)findViewById(R.id.back);
        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });


        stUpload = (Switch) findViewById(R.id.location_set_st_upload);
        stUpload.setChecked(app.getStatus());

        pid = app.getPkgID();
        tvPkgId.setText(pid);
        tvPkgStatus.setText(app.getPkgStatus()+"");

        //地图
        mMapView = (MapView) findViewById(R.id.location_set_mapView);
        baiduMap = mMapView.getMap();


        //定时
        mHandler = new Handler(){
            @Override
            public void handleMessage(Message msg) {
                switch (msg.what) {
                    case UPDATE_TEXTVIEW:

                        Location location = initLocationOption();
                        location.setPackageId(pid);
                        Toast.makeText(LocationSetActivity.this,""+location,Toast.LENGTH_SHORT).show();

                        UploadLocation uploadLocation = new UploadLocation();
                        uploadLocation.uploadLoc(location);
                        //刷新
                        // mLoader = new ExpressListLoader(PackagePaisongActivity.this, PackagePaisongActivity.this);
                        // mLoader.LoadExpressListInPackage(user.getDelivePackageID()+"");
                        break;
                    default:
                        break;
                }
            }
        };


        /***
         * 下拉框
         */
        spinner = (Spinner) findViewById(R.id.location_set_spinner);
        //两种方式给下拉列表注册适配器
        //2 :.createFromResource(content对象,xml中数组id,样式ID)
        adapter = ArrayAdapter.createFromResource(LocationSetActivity.this,
                R.array.time,android.R.layout.simple_spinner_dropdown_item );
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        spinner.setSelection(0, true);         //Spinner在初始化时会自动调用一次OnItemSelectedListener事件   提供的解决办法：在事件注册之前调用
        spinner.setSelection(app.getSpinnerOption());// 设置下拉框默认选中项

        //注册监听器
        spinner.setOnItemSelectedListener(this);

        //开关
        stUpload.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    if ("暂无".equals(pid)) {
                        stUpload.setChecked(false);
                        Toast.makeText(LocationSetActivity.this,"未绑定包裹信息,上传失败!",Toast.LENGTH_SHORT).show();

                    }else{
                        app.startTimer(mHandler);       //开始上传位置
                        app.setStatusOpen();             //存储开关状态
                        Toast.makeText(LocationSetActivity.this,"开始上传位置信息!",Toast.LENGTH_SHORT).show();
                    }

                }else {
                    //stopTimer();       //结束上传位置
                    app.stopTimer();
                    app.setStatusClose();             //存储开关状态

                    Toast.makeText(LocationSetActivity.this,"停止上传位置信息!",Toast.LENGTH_SHORT).show();
                }
            }
        });

        //包裹详情
        vgPkgInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(!"暂无".equals(pid)){
                    Intent intent = new Intent(LocationSetActivity.this,PackageInfoActivity.class);
                    intent.putExtra("packageId",pid);
                    startActivity(intent);
                }
                else{
                    Toast.makeText(LocationSetActivity.this,"暂无包裹信息!",Toast.LENGTH_SHORT).show();

                }

            }
        });


    }


    //下拉框

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
        String item = (String) spinner.getItemAtPosition(position);
        //Toast.makeText(LocationSetActivity.this, "您选中了"+TIME[position]+"选项"+position, Toast.LENGTH_SHORT).show();
        Toast.makeText(LocationSetActivity.this, "已修改上传频率，需重新开启上传位置！", Toast.LENGTH_SHORT).show();
        stUpload.setChecked(false);
        app.stopTimer();
        app.setStatusClose();             //存储开关状态
        app.setPeriod(TIME[position]*1000);
        app.setSpinnerOption(position);
        TextView tv = (TextView)view;
        tv.setTextColor(getResources().getColor(R.color.black));    //设置颜色
        tv.setTextSize(15.0f);    //设置大小


//        stUpload.setChecked(false);
//        app.stopTimer();

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }


    //地图
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

    private Location initLocationOption() {
//定位服务的客户端。宿主程序在客户端声明此类，并调用，目前只支持在主线程中启动
        locationClient = new LocationClient(getApplicationContext());
//声明LocationClient类实例并配置定位参数
        LocationClientOption locationOption = new LocationClientOption();
        LocationSetActivity.MyLocationListener myLocationListener = new LocationSetActivity.MyLocationListener();
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
        Location l = new Location();
        l.setX((float) latitude);
        l.setY((float) longitude);
        return l;
    }


}
