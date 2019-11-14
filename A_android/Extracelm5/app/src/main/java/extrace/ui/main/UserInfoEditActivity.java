package extrace.ui.main;

import android.support.v7.app.ActionBarActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import extrace.loader.UserInfoLoader;
import extrace.misc.model.UserInfo;
import extrace.net.IDataAdapter;
import extrace.ui.domain.ExpressEditActivity;
import zxing.util.CaptureActivity;

public class UserInfoEditActivity extends ActionBarActivity implements
IDataAdapter<UserInfo> {
	public static final int REQUEST_CAPTURE = 100; 

	private ExTraceApplication app;
	private UserInfo user;
	private UserInfoLoader mLoader;
	
	TextView tvSave;
	TextView tvID;
	EditText etPwd;
	EditText etName;
	EditText etTelPhone;
	TextView tvDept;
	TextView tvReceive;
	TextView tvDelive;
	TextView tvTrans;
    TextView tvBack;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_userinfo_edit);
		app = (ExTraceApplication) getApplication(); 
		user = app.getLoginUser();
		findIdAndSetText();
		//Toast.makeText(this, user.geTransPackageID()+"结果 ", Toast.LENGTH_SHORT).show();	
		
		tvSave.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				mLoader = new UserInfoLoader(UserInfoEditActivity.this, UserInfoEditActivity.this);
				//每次点击新建Loader,以刷新数据
				user.setPWD(etPwd.getText().toString());
				user.setName(etName.getText().toString());
				user.setTelCode(etTelPhone.getText().toString());
				//Toast.makeText(UserInfoEditActivity.this, user+"结果 ", Toast.LENGTH_SHORT).show();	

				mLoader.SaveUser(user);
				
				//StartCapture(); //测试扫码功能
			}
		});
		
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.user_info_edit, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
	
	private void findIdAndSetText() {
		tvSave = (TextView) findViewById(R.id.user_edit_tv_save);
		tvBack = (TextView) findViewById(R.id.user_edit_tv_back);

		tvID = (TextView) findViewById(R.id.user_edit_tv_id);
		tvID.setText(user.getUID()+"");
		tvDept = (TextView) findViewById(R.id.user_edit_tv_dept);
		tvDept.setText(user.getDptID());
		tvReceive = (TextView) findViewById(R.id.user_edit_tv_receive);
		tvReceive.setText(user.getReceivePackageID());
		tvDelive = (TextView) findViewById(R.id.user_edit_tv_delive);
		tvDelive.setText(user.getDelivePackageID());
		tvTrans = (TextView) findViewById(R.id.user_edit_tv_trans);
		tvTrans.setText(user.geTransPackageID());
		etPwd = (EditText) findViewById(R.id.user_edit_tv_pwd);
		etPwd.setText(user.getPWD());
		etName = (EditText) findViewById(R.id.user_edit_tv_name);
		etName.setText(user.getName());
		etTelPhone = (EditText) findViewById(R.id.user_edit_tv_telphone);
		etTelPhone.setText(user.getTelCode());


		tvBack.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				onBackPressed();
			}
		});

	}

	@Override
	public UserInfo getData() {
		
		return user;
	}

	@Override
	public void setData(UserInfo data) {
		
		user = data; 
		
	}

	@Override
	public void notifyDataSetChanged() {
		
	}
	
	private void StartCapture(){
		Intent intent = new Intent();
		//intent.putExtra("Action","Captrue");
		intent.setClass(UserInfoEditActivity.this, CaptureActivity.class);
		startActivityForResult(intent, REQUEST_CAPTURE);  	
	}
}
