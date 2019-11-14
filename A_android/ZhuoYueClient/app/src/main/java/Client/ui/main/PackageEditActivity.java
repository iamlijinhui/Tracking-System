package Client.ui.main;

import android.support.v7.app.ActionBarActivity;

import java.util.Iterator;
import java.util.List;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import Client.loader.ExpressListLoader;
import Client.loader.ExpressLoader;
import Client.misc.model.ExpressSheet;
import Client.misc.model.UserInfo;
import Client.net.MyDataAdapter;
import Client.ui.domain.PackageInfoActivity;
import zxing.util.CaptureActivity;

public class PackageEditActivity extends ActionBarActivity implements MyDataAdapter<List<ExpressSheet>>{
	
	
	public static final int REQUEST_PACK = 103; 
	public static final int REQUEST_UNPACK = 104;
	public static final int REQUEST_FENJIAN = 105;
	public static final int REQUEST_ZHUANGBAO = 106; //打包内部
	public static int SCAN_STATUS = 0; //区分拆包装包

	private List<ExpressSheet> ExpressList;
	private Intent mIntent;
	private ExpressListLoader mLoader;
	private ExpressLoader expressLoader;
	TextView tvPakageID;
	TextView tvPakageText;

	TextView tvPackWord;
	TextView tvUnPackWord;
	TextView tvPackWord2;
	TextView tvUnPackWord2;
    ListView lvExpress;
    ImageView ivScan;
    Button btFinishPack;
	UserInfo user;
	private ExTraceApplication app;
	String pid;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_package_edit);
		tvPakageID = (TextView) findViewById(R.id.package_ID);
		tvPakageText = (TextView) findViewById(R.id.package_edit_text);
		tvPackWord2 = (TextView) findViewById(R.id.package_tv_pack2);
		tvUnPackWord2 = (TextView) findViewById(R.id.package_tv_unpack2);

		lvExpress = (ListView) findViewById(R.id.lv_icons);
		ivScan = (ImageView) findViewById(R.id.package_edit_scan);
		tvPackWord = (TextView) findViewById(R.id.package_tv_pack);
		tvUnPackWord = (TextView) findViewById(R.id.package_tv_unpack);
		btFinishPack = (Button) findViewById(R.id.package_edit_bt_finishpack);

		app = (ExTraceApplication) getApplication();
		user = app.getLoginUser();

		setListener();
		mIntent = getIntent();
		if (mIntent.hasExtra("Action")) {
			if(mIntent.getStringExtra("Action").equals("pack")){	//打包
				Pack();
			}
			
			else if(mIntent.getStringExtra("Action").equals("unPack")){	//拆包
				UnPack();
			}
			else{
				this.setResult(RESULT_CANCELED, mIntent);
				this.finish();
			}
		}
	}


	private void setListener(){

		ivScan.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				if(SCAN_STATUS == 1) {
					Toast.makeText(PackageEditActivity.this, "装包", Toast.LENGTH_SHORT).show();
					zhaungbao();
				}else if(SCAN_STATUS == 2){
					fenJian();
				}
			}
		});

		btFinishPack.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {

				expressLoader = new ExpressLoader(PackageEditActivity.this);
				expressLoader.finishPack(user.getUID()+"",pid);
				mLoader = new ExpressListLoader(PackageEditActivity.this, PackageEditActivity.this);
				mLoader.LoadExpressListInPackage(pid);
				Toast.makeText(PackageEditActivity.this, "完成打包", Toast.LENGTH_SHORT).show();

			}
		});

		tvPakageID.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				//Toast.makeText(PackageEditActivity.this, pid+"查询的包裹", Toast.LENGTH_SHORT).show();
				Intent intent = new Intent(PackageEditActivity.this,PackageInfoActivity.class);
				intent.putExtra("packageId",pid);
				startActivity(intent);

			}
		});
		tvPakageText.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				//Toast.makeText(PackageEditActivity.this, pid+"查询的包裹", Toast.LENGTH_SHORT).show();
				Intent intent = new Intent(PackageEditActivity.this,PackageInfoActivity.class);
				intent.putExtra("packageId",pid);
				startActivity(intent);
			}
		});

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.package_edit, menu);
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
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

		switch (resultCode) {
		case RESULT_OK:
			switch (requestCode){
			/*
			 * 新增打包拆包
			 */
			case REQUEST_PACK:
				tvPackWord.setVisibility(View.VISIBLE);
				tvPackWord2.setVisibility(View.VISIBLE);
				btFinishPack.setVisibility(View.VISIBLE);
				SCAN_STATUS = 1;		//打包
				if (data.hasExtra("BarCode")) {			//打包
					String id = data.getStringExtra("BarCode");

                    pid = id;
					tvPakageID.setText(id);
					try {
						mLoader = new ExpressListLoader(this, this);
						mLoader.LoadExpressListInPackage(id);
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
				break;
				
			case REQUEST_UNPACK:
				tvUnPackWord.setVisibility(View.VISIBLE);
				tvUnPackWord2.setVisibility(View.VISIBLE);
				SCAN_STATUS = 2;		//拆包
				if (data.hasExtra("BarCode")) {			//拆包
					String id = data.getStringExtra("BarCode");
					//int newid = Integer.parseInt(id);
					pid = id;
					tvPakageID.setText(id);
					try {
						mLoader = new ExpressListLoader(this, this);
						mLoader.LoadExpressListInPackage(id);
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
				break;

				case REQUEST_FENJIAN:
					if (data.hasExtra("BarCode")) {			//分捡快件 拆包内部
						String id = data.getStringExtra("BarCode");
						//Toast.makeText(PackageEditActivity.this, "查询"+id, Toast.LENGTH_SHORT).show();
						//找到包裹中id等于所扫描id的快件并将其状态置为1

						expressLoader = new ExpressLoader(this);
						expressLoader.unPack(pid,id,user.getUID()+"");
						mLoader = new ExpressListLoader(this, this);
						mLoader.LoadExpressListInPackage(pid);



//						Iterator<ExpressSheet> it = ExpressList.iterator();
//						while(it.hasNext()) {
//							ExpressSheet temp = it.next();
//							if (temp.getID().equals(id)){
//								temp.setStatus(1);
//								expressLoader = new ExpressLoader(this);
//								expressLoader.Save(temp);
//								mLoader = new ExpressListLoader(this, this);
//								mLoader.LoadExpressListInPackage("1111115555");
//								break;
//							}
//						}
//



//						tvPakageID.setText(id);
//						try {
//							mLoader = new ExpressListLoader(this, this);
//							mLoader.LoadExpressListInPackage("1111115555");
//						} catch (Exception e) {
//							e.printStackTrace();
//						}
					}
					break;


				case REQUEST_ZHUANGBAO:
					if (data.hasExtra("BarCode")) {			//打包内部
						String id = data.getStringExtra("BarCode");
						//Toast.makeText(PackageEditActivity.this, "查询"+id, Toast.LENGTH_SHORT).show();
						//找到包裹中id等于所扫描id的快件并将其状态置为1

						expressLoader = new ExpressLoader(this);
						expressLoader.Pack(user.getUID()+"",pid,id);
						mLoader = new ExpressListLoader(this, this);
						mLoader.LoadExpressListInPackage(pid);
					}
					break;

					}
			break;
		default:
			break;
		}
	}

	private void Pack(){					//打包 外部查询
		Intent intent = new Intent();
		intent.setClass(this, CaptureActivity.class);
		startActivityForResult(intent, REQUEST_PACK);
	}


	private void UnPack(){					//拆包 外部查询
		Intent intent = new Intent();
		intent.setClass(this, CaptureActivity.class);
		startActivityForResult(intent, REQUEST_UNPACK);  	
	}

	private void fenJian(){				//分拣快递
		Intent intent = new Intent();
		intent.setClass(this, CaptureActivity.class);
		startActivityForResult(intent, REQUEST_FENJIAN);
	}

	private void zhaungbao(){				//装包
		Intent intent = new Intent();
		intent.setClass(this, CaptureActivity.class);
		startActivityForResult(intent, REQUEST_ZHUANGBAO);
	}


	@Override
	public List<ExpressSheet> getData() {
		// TODO Auto-generated method stub
		return ExpressList;
	}

	@Override
	public void setData(List<ExpressSheet> data) {
		// TODO Auto-generated method stub
		ExpressList = data;
	}

	@Override
	public void notifyDataSetChanged() {
		// TODO Auto-generated method stub
		//Toast.makeText(PackageEditActivity.this, "查询的包裹"+ExpressList, Toast.LENGTH_SHORT).show();

		//查询包裹状态,去掉不满足状态的包裹
		Iterator<ExpressSheet> it = ExpressList.iterator();
		while(it.hasNext()) {
			ExpressSheet temp = it.next();
			if (temp.getStatus() == 2){
				it.remove();
			}
		}

        //为listView添加一个适配器Adapter
		lvExpress.setAdapter(new PackageEditAdapter( PackageEditActivity.this,ExpressList,pid,SCAN_STATUS)); //icon spair数组
		lvExpress.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                Intent intent = new Intent();
//                intent.putExtra("chosenIconId",ExpressList.get(position)); //放入一个对应的数据
//                setResult(RESULT_OK,intent);   //返回结果码,及Intent
//                finish();
            	
        		Toast.makeText(PackageEditActivity.this, "查询"+ExpressList.get(position), Toast.LENGTH_SHORT).show();

            }
        });
		
		
	}

	@Override
	public void packDataSetChanged() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void unPackDataSetChanged() {
		// TODO Auto-generated method stub
		
	}			
	
}
