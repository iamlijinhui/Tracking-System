package Client.ui.main;

import android.support.v7.app.ActionBarActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import Client.misc.model.UserInfo;
import Client.ui.domain.ErrorExpressActivity;
import Client.ui.domain.MyPackageActivity;

public class UserInfoActivity extends ActionBarActivity {

	TextView txUserName;
	TextView tvBack;
	ImageView ivUserHead;
	private ExTraceApplication app;
	UserInfo user;
	ViewGroup vgMyPackage;
	ViewGroup user_info_errorpackage;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_user_info);
		
		TextView tvLogout  = (TextView) findViewById(R.id.user_tv_logout);
		txUserName = (TextView) findViewById(R.id.user_tv_name);
		ivUserHead = (ImageView) findViewById(R.id.user_iv_head);
		tvBack = (TextView) findViewById(R.id.userinfo_tv_back);
		vgMyPackage = (ViewGroup) findViewById(R.id.user_info_mypackage);
		user_info_errorpackage = (ViewGroup) findViewById(R.id.user_info_errorpackage);
		//user = ((ExTraceApplication)context.getApplication()).getLoginUser(); 不能在activity中用该方法
		app = (ExTraceApplication) getApplication(); 
		user = app.getLoginUser();
		
		//Toast.makeText(UserInfoActivity.this, user+"结果 ", Toast.LENGTH_SHORT).show();
		txUserName.setText(user.getName());
		
		
		//退出
		tvLogout.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				 Intent intent = new Intent(UserInfoActivity.this,LoginActivity.class);
				 startActivity(intent);
			}
		});
		
		//详细信息
		ivUserHead.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(UserInfoActivity.this,UserInfoEditActivity.class);
				startActivity(intent);	
			}
		});

		tvBack.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				onBackPressed();
			}
		});

		//我的包裹
		vgMyPackage.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				Intent intent = new Intent(UserInfoActivity.this,MyPackageActivity.class);
				startActivity(intent);
			}
		});

		//错误快件
		user_info_errorpackage.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				Intent intent = new Intent(UserInfoActivity.this,ErrorExpressActivity.class);
				startActivity(intent);
			}
		});
	}
	
	
	

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		
		txUserName.setText(user.getName());		//返回到当前activity执行
		
	}




	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.user_info, menu);
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
	
	
}
