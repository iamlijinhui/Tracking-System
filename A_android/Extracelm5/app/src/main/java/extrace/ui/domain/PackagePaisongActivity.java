package extrace.ui.domain;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
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

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import extrace.loader.DeliverLoader;
import extrace.loader.ExpressListLoader;
import extrace.loader.UploadLocation;
import extrace.misc.model.ExpressSheet;
import extrace.misc.model.Location;
import extrace.misc.model.UserInfo;
import extrace.net.MyDataAdapter;
import extrace.ui.Map.BaiduMapActivity;
import extrace.ui.main.ExTraceApplication;
import extrace.ui.main.LocationSetActivity;
import extrace.ui.main.PackageEditActivity;
import extrace.ui.main.PackageEditAdapter;
import extrace.ui.main.R;
import zxing.util.CaptureActivity;

public class PackagePaisongActivity extends AppCompatActivity implements MyDataAdapter<List<ExpressSheet>> {

    private UserInfo user;
    private ExTraceApplication app;
    ListView listView;
    ImageView ivScan;
    TextView tvStarPaiSong;
    TextView tvEndPaiSong;
    Message message;
    Handler handler;
    String pid;

    //地图相关
    private MapView mMapView = null;
    private BaiduMap baiduMap;
    LocationClient  locationClient;
    private double latitude;
    private double longitude;
    private boolean firstLocation;


    private List<ExpressSheet> ExpressList;
    private ExpressListLoader mLoader;
    DeliverLoader deliverLoader;
    public static final int REQUEST_PAISONG = 106;


    private Handler mHandler = null;
    private static final int UPDATE_TEXTVIEW = 0;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_package_paisong);
        app = (ExTraceApplication) getApplication();
        user = app.getLoginUser();
        listView = (ListView) findViewById(R.id.package_paisong_lves);
        ivScan = (ImageView)findViewById(R.id.package_paisong_scan);
        tvStarPaiSong = (TextView)findViewById(R.id.package_paisong_start);
        tvEndPaiSong = (TextView)findViewById(R.id.package_paisong_end);
        ImageView ivBack = (ImageView)findViewById(R.id.back);
        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        mLoader = new ExpressListLoader(PackagePaisongActivity.this, PackagePaisongActivity.this);
        mLoader.LoadExpressListInPackage(user.getDelivePackageID()+"");
        setListener();

        pid = user.getDelivePackageID();

        //地图
        mMapView = (MapView) findViewById(R.id.paisong_mapView);
        baiduMap = mMapView.getMap();

//       handler = new Handler() {
//            @Override
//            public void handleMessage(Message msg) {
//                super.handleMessage(msg);
//                if (msg.what == 0) {
//                    /**
//                     * 写执行的代码
//                     */
//                    location location = initLocationOption();
//                   //Toast.makeText(PackagePaisongActivity.this,"hahahahah"+initLocationOption(),Toast.LENGTH_SHORT).show();
//                }
//            }
//        };

        /**
         * timer.schedule()方法的三个参数解释 :

         ① : 一个TimerTask对象

         ② : 0 的意思 : 当你调用了timer.schedule()方法之后,这个方法就肯定会调用TimerTask()方法中的run()方法,这个参数指的是这两者之间的差值,也就是说用户在调用了schedule()方法之后,会等待0时间,才会第一次执行run()方法,0也就是代表无延迟了,如果传入其他的,就代表要延迟执行了

         ③ : 第一次调用了run()方法之后,从第二次开始每隔多长时间调用一次run()方法.
         */
//        new Timer().schedule(new TimerTask() {
//            @Override
//            public void run() {
//                // (1) 使用handler发送消息
//                message=new Message();
//                message.what=0;

//                handler.sendMessage(message);
//            }
//        },0,1000);

        mHandler = new Handler(){
            @Override
            public void handleMessage(Message msg) {
                switch (msg.what) {
                    case UPDATE_TEXTVIEW:

                        Location location = initLocationOption();
                        location.setPackageId(pid);
                        Toast.makeText(PackagePaisongActivity.this,""+location,Toast.LENGTH_SHORT).show();
                        mLoader = new ExpressListLoader(PackagePaisongActivity.this, PackagePaisongActivity.this);
                        //mLoader.uploadLoc(location);
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

    }


    //弹出框
    private void setListener(){
        ivScan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                paiSong();
            }
        });
        tvStarPaiSong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                app.startTimer(mHandler);       //开始上传位置
//                Toast.makeText(PackagePaisongActivity.this,"开始上传位置信息!",Toast.LENGTH_SHORT).show();

                AlertDialog.Builder builder = new AlertDialog.Builder(PackagePaisongActivity.this);
                builder.setTitle("是否授权上传位置信息？");
                builder.setPositiveButton("是", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        //设置状态信息
                        app.setStatusClose();
                        app.setPkgID(pid);
                        app.setPkgStatus("派送中");

                        //延时跳转
                        Timer mTimre = new Timer();
                        mTimre.schedule(new TimerTask() {
                            @Override
                            public void run() {
                                Intent intent4 = new Intent(PackagePaisongActivity.this, LocationSetActivity.class);
                                startActivity(intent4);
                            }
                        }, 1000);

                    }
                });
                builder.setNegativeButton("否", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Toast.makeText(PackagePaisongActivity.this, "未授权位置信息，派送失败！",Toast.LENGTH_SHORT).show();
                    }
                });
                builder.show();

            }

        });

        tvEndPaiSong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                AlertDialog.Builder builder = new AlertDialog.Builder(PackagePaisongActivity.this);
                builder.setTitle("提示");
                builder.setMessage("请确认包裹中快件是否全部签收？" ) ;
                builder.setPositiveButton("是", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        //stopTimer();       //结束上传位置
                        app.stopTimer();
                        app.setStatusClose();             //存储开关状态
                        mLoader = new ExpressListLoader(PackagePaisongActivity.this, PackagePaisongActivity.this);
                        mLoader.finishDelivery(pid,user.getUID()+"");
                        Toast.makeText(PackagePaisongActivity.this,"派送完成!",Toast.LENGTH_SHORT).show();

                    }
                });
                builder.setNegativeButton("否", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Toast.makeText(PackagePaisongActivity.this, "取消操作！",Toast.LENGTH_SHORT).show();
                    }
                });
                builder.show();

            }
        });
    }

    private void paiSong(){
        Intent intent = new Intent();
        intent.setClass(this, CaptureActivity.class);
        startActivityForResult(intent, REQUEST_PAISONG);
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        switch (resultCode) {
            case RESULT_OK:
                switch (requestCode){
                    /*
                     * 查包
                     */
                    case REQUEST_PAISONG:
                        if (data.hasExtra("BarCode")) {			//打包
                            String id = data.getStringExtra("BarCode");
                            try {
                                deliverLoader = new DeliverLoader(PackagePaisongActivity.this);
                                deliverLoader.DispatchExpressSheet(id,user.getUID()+"");
                                mLoader = new ExpressListLoader(PackagePaisongActivity.this, PackagePaisongActivity.this);
                                mLoader.LoadExpressListInPackage(user.getDelivePackageID()+"");  //刷新
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
    @Override
    public void packDataSetChanged() {

    }

    @Override
    public void unPackDataSetChanged() {

    }

    @Override
    public List<ExpressSheet> getData() {
        return ExpressList;
    }

    @Override
    public void setData(List<ExpressSheet> data) {
        ExpressList = data;
    }

    @Override
    public void notifyDataSetChanged() {

        //Toast.makeText(PackagePaisongActivity.this, "查询"+ExpressList, Toast.LENGTH_SHORT).show();
        deliverLoader = new DeliverLoader(PackagePaisongActivity.this);
        listView.setAdapter(new PackagePaisongAdapter( PackagePaisongActivity.this,ExpressList,deliverLoader,user.getUID()+"")); //icon spair数组
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(PackagePaisongActivity.this, "查询"+ExpressList.get(position), Toast.LENGTH_SHORT).show();
            }
        });
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
        PackagePaisongActivity.MyLocationListener myLocationListener = new PackagePaisongActivity.MyLocationListener();
//注册监听函数
        locationClient.registerLocationListener(myLocationListener);
//可选，默认高精度，设置定位模式，高精度，低功耗，仅设备
        locationOption.setLocationMode(LocationClientOption.LocationMode.Hight_Accuracy);
//可选，默认gcj02，设置返回的定位结果坐标系，如果配合百度地图使用，建议设置为bd09ll;
        locationOption.setCoorType("gcj02");
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
