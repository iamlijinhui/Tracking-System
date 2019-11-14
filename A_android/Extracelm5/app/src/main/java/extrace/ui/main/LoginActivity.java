package extrace.ui.main;

import android.content.SharedPreferences;
import android.support.v7.app.ActionBarActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import extrace.loader.UserInfoLoader;
import extrace.misc.model.UserInfo;
import extrace.net.IDataAdapter;

public class LoginActivity extends ActionBarActivity implements
IDataAdapter<UserInfo> {
	int i = 0;
    CheckBox cbPwd;
    CheckBox cbLogin;
	EditText etUserId;
	EditText etUserPwd;
	Button tvUserLogin;
	TextView tvSet;
	TextView tvUser;
	TextView tvShift;
	String userId;
	String userPwd;
	String[] user = {"快递员登录","司机登录","经理登录"};
	private UserInfo mItem;
	private UserInfoLoader mLoader;
    public static final String RPWD="rpwd";
	public static final String EID="eid";
	public static final String EPWD="epwd";
	public static final String RLOGIN="rlogin";

	SharedPreferences sp;
    SharedPreferences.Editor et;
	private ExTraceApplication app;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
		etUserId = (EditText) findViewById(R.id.login_et_userid);
		etUserPwd = (EditText) findViewById(R.id.login_et_userpwd);
		tvUserLogin = (Button) findViewById(R.id.login_tv_login);
		cbLogin = (CheckBox) findViewById(R.id.checkBox_login);
		cbPwd = (CheckBox) findViewById(R.id.checkBox_password);
		tvSet = (TextView) findViewById(R.id.login_tv_set);
		tvUser = (TextView) findViewById(R.id.login_tv_user);
		tvShift = (TextView) findViewById(R.id.login_tv_shift);
		sp = getPreferences(MODE_PRIVATE);//模式:其他app是否能看到
		et = sp.edit();//修改器
		String rpwd = new String();	//记住密码
		String rlogin = new String();	//自动登录
		rpwd = sp.getString(RPWD,"F");
		rlogin = sp.getString(RLOGIN,"F");



		app = (ExTraceApplication) getApplication();
		String PREFS_NAME = "ExTrace.cfg";
		SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
		SharedPreferences.Editor editor = settings.edit();
		String URL = settings.getString("ServerUrl","noData");
		if(!"noData".equals(URL))	{
			app.setServerUrl(URL);
		}								//更改网络配置

		if (rpwd.equals("T")) {
			cbPwd.setChecked(true);
			etUserId.setText(sp.getString(EID,""));
			etUserPwd.setText(sp.getString(EPWD,""));
		}

		//Toast.makeText(LoginActivity.this, URL+rlogin+"存储"+rpwd, Toast.LENGTH_SHORT).show();

        tvUserLogin.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {		//登录监听
	
				userId = etUserId.getText().toString();
				userPwd = etUserPwd.getText().toString();

				if ("".equals(userId) ||  "".equals(userPwd)){
					Toast.makeText(LoginActivity.this, "请输入账号密码!", Toast.LENGTH_SHORT).show();

				}
				else{
					mLoader = new UserInfoLoader(LoginActivity.this, LoginActivity.this);
					//每次点击新建Loader,以刷新数据
					mLoader.LoadUserInfoByID(userId,userPwd);

					et.putString(EID,etUserId.getText().toString());
					et.putString(EPWD,etUserPwd.getText().toString());
					et.commit();			//每次登陆重新在内存中写入密码
				}



			}
		});

		if (rlogin.equals("T")) {
			cbLogin.setChecked(true);
			tvUserLogin.performClick();	//模拟点击事件 且位置一定要放到注册监听事件之后,否则无法调用
		}

		cbPwd.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {		//记住密码监听
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if(isChecked){
					et.putString(RPWD,"T");//存入键值对
					et.putString(EID,etUserId.getText().toString());
					et.putString(EPWD,etUserPwd.getText().toString());
					et.commit();//将缓存区里面的操作写入文件


                }else{
					et.putString(RPWD,"F");//存入键值对
					et.commit();//将缓存区里面的操作写入文件

           		 }
            }
        });

		cbLogin.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {		//自动登录监听
			@Override
			public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
				if(isChecked){
					et.putString(RLOGIN,"T");//存入键值对
					et.commit();//将缓存区里面的操作写入文件

				}else{
					et.putString(RLOGIN,"F");//存入键值对
					et.commit();//将缓存区里面的操作写入文件

				}
			}
		});

		tvSet.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				Intent nextIntent = new Intent(LoginActivity.this,SetActivity.class);
				startActivity(nextIntent);
			}
		});


		tvShift.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				i++;
				if(i > user.length - 1){
					i  = 0;
				}
				tvUser.setText(user[i]);
			}
		});

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.login, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	public UserInfo getData() {
		return mItem;
	}

	@Override
	public void setData(UserInfo data) {
		//mItem = new UserInfo();
		mItem = data;
	}

	@Override
	public void notifyDataSetChanged() {		
		//一定要在此方法中进行数据处理
		String pwd = mItem.getPWD();//不为空的情况
		
		if(userPwd.equals(pwd)) {
			Intent intent = new Intent(LoginActivity.this, MainActivity.class);
        	startActivity(intent);
        	LoginActivity.this.finish();	//让用户不能通过返回键回到原页面
		}
		else {
			Toast.makeText(LoginActivity.this, "密码错误!", Toast.LENGTH_SHORT).show();
		}
	}

//	@Override
//	public List<CustomerInfo> getData() {
//		// TODO Auto-generated method stub
//		return mItem;
//	}
//
//	@Override
//	public void setData(List<CustomerInfo> data) {
//		// TODO Auto-generated method stub
//		mItem = new ArrayList<CustomerInfo>();
//		mItem = data;
//	}
//
//	@Override
//	public void notifyDataSetChanged() {
//		//一定要在此方法中进行数据处理
//		int id = mItem.get(0).getID();//不为空的情况
//		
//		if(Integer.parseInt(userPwd) == id ) {
//			Intent intent = new Intent(LoginActivity.this, MainActivity.class);
//        	startActivity(intent);
//        	LoginActivity.this.finish();	//让用户不能通过返回键回到原页面
//		}
//		else {
//			Toast.makeText(LoginActivity.this, "账号或密码错误!", Toast.LENGTH_SHORT).show();
//			//Toast.makeText(LoginActivity.this, id+"测试二!"+mItem, Toast.LENGTH_SHORT).show();
//			
//			//mLoader.SaveCustomer(mItem);
//		}
//
//	}

}
