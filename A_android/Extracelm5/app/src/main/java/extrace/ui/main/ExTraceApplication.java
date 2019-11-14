package extrace.ui.main;

import extrace.misc.model.UserInfo;
import android.app.Application;
import android.content.SharedPreferences;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.TextView;

import com.baidu.mapapi.CoordType;
import com.baidu.mapapi.SDKInitializer;

import java.util.Timer;
import java.util.TimerTask;

public class ExTraceApplication extends Application{
    public static String nowURL;

    public static String TOKEN;
	private static final String PREFS_NAME = "ExTrace.cfg";
	String mServerUrl;
	String mMiscService,mDomainService;
    UserInfo userInfo;

    public String getTOKEN() {
        return TOKEN;
    }

    public void setTOKEN(String TOKEN) {
        this.TOKEN = TOKEN;
    }

    //定时相关
    private int spinnerOption;
    private String pkgID  = "暂无";
    private boolean status = false;
    private String pkgStatus = "暂无";
    private static String  TAG = "TimerDemo";
    private TextView mTextView = null;
    private Timer mTimer = null;
    private TimerTask mTimerTask = null;
    private static int count = 0;
    private boolean isPause = false;
    private boolean isStop = true;
    private  int delay = 3000;  //1s
    private  int period = 3000;  //1s
    private static final int UPDATE_TEXTVIEW = 0;

    //下拉框状态
    public void setSpinnerOption(int spinnerOption){
        this.spinnerOption = spinnerOption;
    }
    public int  getSpinnerOption(){
        return this.spinnerOption;
    }

//包裹ID
    public void setPkgID(String pkgID){
        this.pkgID = pkgID;
    }
    public String  getPkgID(){
        return this.pkgID;
    }

    //上传状态
    public void setStatusOpen(){
        this.status = true;
    }
    public void setStatusClose(){
        this.status = false;
    }

    public boolean getStatus(){
        return this.status;
    }
//包裹状态
    public void setPkgStatus(String status){
        this.pkgStatus = status;
    }

    public String getPkgStatus(){
        return this.pkgStatus;
    }
//上传时间间隔
    public void setPeriod(int period){
        this.period = period;
        this.delay =  period;
    }
//开关上传进程
    public void startTimer(final Handler handler){
        if (mTimer == null) {
            mTimer = new Timer();
        }

        if (mTimerTask == null) {
            mTimerTask = new TimerTask() {
                @Override
                public void run() {
                    Log.i(TAG, "count: "+String.valueOf(count));
                    sendMessage(UPDATE_TEXTVIEW,handler);

                    do {
                        try {
                            Log.i(TAG, "sleep(1000)...");
                            Thread.sleep(1000);
                        } catch (InterruptedException e) {
                        }
                    } while (isPause);

                    count ++;
                }
            };
        }

        if(mTimer != null && mTimerTask != null )
            mTimer.schedule(mTimerTask, delay, period);
    }

    public void stopTimer(){
        if (mTimer != null) {
            mTimer.cancel();
            mTimer = null;
        }
        if (mTimerTask != null) {
            mTimerTask.cancel();
            mTimerTask = null;
        }
        count = 0;
    }

    public void sendMessage(int id, Handler mHandler ){
        if (mHandler != null) {
            Message message = Message.obtain(mHandler, id);
            mHandler.sendMessage(message);
        }
    }





    public void setUserInfo(UserInfo user) {
    	userInfo = user;
    }
    
    public String getServerUrl() {  
        return mServerUrl;  
    }  
    public String getMiscServiceUrl() {  
        return mServerUrl+mMiscService;  
    }  
    public String getDomainServiceUrl() {  
        return mServerUrl+mDomainService;  
    }  
  
    public UserInfo getLoginUser(){
    	return userInfo;
    }
    
    public void setServerUrl(String url) {  
        mServerUrl = url;
        SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
		SharedPreferences.Editor editor = settings.edit();
		editor.putString("ServerUrl", mServerUrl);
		editor.commit();

        nowURL = mServerUrl + mDomainService;

    }  

    @Override  
    public void onCreate() {  
        super.onCreate();
        //在使用SDK各组件之前初始化context信息，传入ApplicationContext
        SDKInitializer.initialize(this);
        //自4.3.0起，百度地图SDK所有接口均支持百度坐标和国测局坐标，用此方法设置您使用的坐标类型.
        //包括BD09LL和GCJ02两种坐标，默认是BD09LL坐标。
        SDKInitializer.setCoordType(CoordType.BD09LL);
        SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
        mServerUrl = settings.getString("ServerUrl", ""); 
        mMiscService = settings.getString("MiscService", "/REST/Misc/"); 
        mDomainService = settings.getString("DomainService", "/REST/Domain/"); 
		if(mServerUrl == null || mServerUrl.length() == 0)
		{
			mServerUrl = "http://192.168.43.55:8080/TestCxfHibernate";
            //mServerUrl = "http://192.168.1.105:8080/TestCxfHibernate";

            mMiscService = "/REST/Misc/";
			mDomainService = "/REST/Domain/";

            nowURL = mServerUrl + mDomainService;

			SharedPreferences.Editor editor = settings.edit();  
			editor.putString("ServerUrl", mServerUrl);
			editor.putString("MiscService", mMiscService);
			editor.putString("DomainService", mDomainService);
			editor.commit();
		}
		//临时造一个用户
//		userInfo = new UserInfo();
//		userInfo.setID(12);
//		userInfo.setReceivePackageID("1111112222");
//		userInfo.setTransPackageID("1111113333");
//		userInfo.setDelivePackageID("1111115555");
    }  
      
    public void onTerminate() {  
        super.onTerminate();  
          
        //save data of the map  
    }  
}
