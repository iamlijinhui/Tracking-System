package Client.ui.domain;

import java.util.Locale;

import zxing.util.CaptureActivity;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import Client.loader.ExpressLoader;
import Client.misc.model.CustomerInfo;
import Client.misc.model.ExpressSheet;
import Client.net.MyDataAdapter;
import Client.ui.main.R;
import Client.ui.misc.CustomerListActivity;

public class ExpressEditActivity extends ActionBarActivity implements ActionBar.TabListener,MyDataAdapter<ExpressSheet> {

//	public static final int INTENT_NEW = 1; 
//	public static final int INTENT_EDIT = 2; 
	
	public static final int REQUEST_CAPTURE = 100; 
	public static final int REQUEST_RCV = 101; 
	public static final int REQUEST_SND = 102; 
	public static final int REQUEST_PACK = 103; 
	public static final int REQUEST_UNPACK = 104;

	SectionsPagerAdapter mSectionsPagerAdapter;

	/**
	 * The {@link ViewPager} that will host the section contents.
	 */
	ViewPager mViewPager;

	private ExpressSheet mItem;

	private ExpressLoader mLoader;
	private Intent mIntent;
	private ExpressEditFragment1 baseFragment;
	private ExpressEditFragment2 externFragment;
	private boolean new_es = false;	//新建
//	private TextView mIDView;
//	private TextView mRcvNameView;
//	private TextView mRcvDptView;
//	private TextView mRcvAddrView;
//	private TextView mRcvRegionView;
//	private ImageView mbtnRcv;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_express_edit);

		// Set up the action bar.
		final ActionBar actionBar = getSupportActionBar();
		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);//m 即可另Actionbra使用tab作为导航模式


		Resources r = getResources();                     //背景
		Drawable myDrawable = r.getDrawable(R.drawable.bg);
		Drawable myDrawable2 = r.getDrawable(R.drawable.bg);
		actionBar.setBackgroundDrawable(myDrawable2);      //actonBar背景
		actionBar.setStackedBackgroundDrawable(myDrawable);//tab栏背景


		// Create the adapter that will return a fragment for each of the three
		// primary sections of the activity.
		mSectionsPagerAdapter = new SectionsPagerAdapter(
				getSupportFragmentManager());			//fragment 可认为是一个轻量级的Activity，但不同与Activity，
														//它是要嵌到Activity中来使用的，它用来解决设备屏幕大小的不同

		// Set up the ViewPager with the sections adapter.
		mViewPager = (ViewPager) findViewById(R.id.pager);//Viewpager使用起来就是我们通过创建adapter给它填充多个view，左右滑动时，
														  //切换不同的view。Google官方是建议我们使用Fragment来填充ViewPager的
		mViewPager.setAdapter(mSectionsPagerAdapter);

		// When swiping between different sections, select the corresponding
		// tab. We can also use ActionBar.Tab#select() to do this if we have
		// a reference to the Tab.
		mViewPager
				.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
					@Override							//当新页面将被选中时调用，动画不是必需完成的。
					public void onPageSelected(int position) {
						actionBar.setSelectedNavigationItem(position);
					}
				});

		// For each of the sections in the app, add a tab to the action bar.
		for (int i = 0; i < mSectionsPagerAdapter.getCount(); i++) {		//m 给tab栏设置标题
			// Create a tab with text corresponding to the page title defined by
			// the adapter. Also specify this Activity object, which implements
			// the TabListener interface, as the callback (listener) for when
			// this tab is selected.
			actionBar.addTab(actionBar.newTab()
					.setText(mSectionsPagerAdapter.getPageTitle(i))
					.setTabListener(this));
		}
				
		mIntent = getIntent();
		if (mIntent.hasExtra("Action")) {
			if(mIntent.getStringExtra("Action").equals("New")){		//揽收
				new_es = true;
				StartCapture();
			}
			else if(mIntent.getStringExtra("Action").equals("Query")){	//查询快件
				StartCapture();
			}
			else if(mIntent.getStringExtra("Action").equals("Edit")){
				ExpressSheet es;
				if (mIntent.hasExtra("ExpressSheet")) {
					es = (ExpressSheet) mIntent.getSerializableExtra("ExpressSheet");
					Refresh(es.getID());
				} else {
					this.setResult(RESULT_CANCELED, mIntent);
					this.finish();
				}
			}

			/*
			 * 新增打包,拆包
			 */
			else if(mIntent.getStringExtra("Action").equals("pack")){	//打包
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
		else{
			this.setResult(RESULT_CANCELED, mIntent);
			this.finish();
		}

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.express_edit, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		int id = item.getItemId();
		switch (id) {
		case R.id.action_ok:
			Save();
			return true;
		case R.id.action_refresh:
			if (mItem != null) {
				Refresh(mItem.getID());
			}
			return true;
		case (android.R.id.home):
	        mIntent.putExtra("ExpressSheet",mItem);  
			this.setResult(RESULT_OK, mIntent);
			this.finish();
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	public void onTabSelected(ActionBar.Tab tab,
			FragmentTransaction fragmentTransaction) {
		// When the given tab is selected, switch to the corresponding page in
		// the ViewPager.
		mViewPager.setCurrentItem(tab.getPosition());
	}

	@Override
	public void onTabUnselected(ActionBar.Tab tab,
			FragmentTransaction fragmentTransaction) {
	}

	@Override
	public void onTabReselected(ActionBar.Tab tab,
			FragmentTransaction fragmentTransaction) {
	}
	
	@Override
	public ExpressSheet getData() {
		return mItem;
	}

	@Override
	public void setData(ExpressSheet data) {
		mItem = data;
	}

	@Override
	public void notifyDataSetChanged() {
		if(baseFragment != null){
			baseFragment.RefreshUI(mItem);

		}
		if(externFragment != null ){
			externFragment.RefreshUI(mItem);
		}
	}

	/**
	 * 当子模块的事情做完之后就回到主界面，回调函数onActivityResult()。
     * requestCode和startActivityForResult中的requestCode相对应
     * resultCode和Intent是由子Activity通过其setResult()方法返回
     */
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
		
    	CustomerInfo customer;

		switch (resultCode) {
		case RESULT_OK:
			switch (requestCode){
			case REQUEST_CAPTURE:
				if (data.hasExtra("BarCode")) {
					String id = data.getStringExtra("BarCode");
					try {
						mLoader = new ExpressLoader(this, this);
						if(new_es){
							new_es = false;
							mLoader.New(id);
						}
						else{
							mLoader.Load(id);
						}
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
				break;
			/*
			 * 新增打包拆包
			 */
			case REQUEST_PACK:
				if (data.hasExtra("BarCode")) {			//打包
					String id = data.getStringExtra("BarCode");
					try {
						mLoader = new ExpressLoader(this, this);
						mLoader.Load(id);
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
				break;
				
			case REQUEST_UNPACK:
				if (data.hasExtra("BarCode")) {			//拆包
					String id = data.getStringExtra("BarCode");
					try {
						mLoader = new ExpressLoader(this, this);
						mLoader.Load(id);
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
				break;

			case REQUEST_RCV:
				if (data.hasExtra("CustomerInfo")) {
					customer = (CustomerInfo) data.getSerializableExtra("CustomerInfo");
					mItem.setRecever(customer);
					baseFragment.displayRcv(mItem);
				}
				break;
			case REQUEST_SND:
				if (data.hasExtra("CustomerInfo")) {
					customer = (CustomerInfo) data.getSerializableExtra("CustomerInfo");
					mItem.setSender(customer);
					baseFragment.displaySnd(mItem);
				}
				break;

			}
			break;
		default:
			break;
		}
	}

	void Refresh(String id){
		try {
			mLoader = new ExpressLoader(this, this);
			mLoader.Load(id);
		} catch (Exception e) {
			e.printStackTrace();
		}		
	}
	
	void Save(){						//所有存用户信息的调用
		mLoader = new ExpressLoader(this, this);
		if(externFragment != null ){			//存入拓展信息
			externFragment.setExpand(mItem);
		}
		//Toast.makeText(ExpressEditActivity.this, "请填写发信人,收信人!", Toast.LENGTH_SHORT).show();

		if(mItem.getStatus() == 0 ){			//存入拓展信息

			mItem.setStatus(1);
        }
		//Toast.makeText(ExpressEditActivity.this, mItem+ "状态", Toast.LENGTH_SHORT).show();


		if(mItem.getSender() == null|| mItem.getRecever() == null){
			Toast.makeText(ExpressEditActivity.this, "请填写发信人,收信人!", Toast.LENGTH_SHORT).show();
		}else{
			mLoader.Save(mItem);
		}


        //mLoader.Save(mItem);


    }
	
	private void StartCapture(){
		Intent intent = new Intent();
		intent.putExtra("Action","Captrue");
		intent.setClass(this, CaptureActivity.class);
		startActivityForResult(intent, REQUEST_CAPTURE);  	
	}

	/*
	 * 新增打包拆包
	 */
	private void Pack(){					//打包
		Intent intent = new Intent();
		intent.setClass(this, CaptureActivity.class);
		startActivityForResult(intent, REQUEST_PACK);  	
	}
	
	private void UnPack(){					//拆包
		Intent intent = new Intent();
		intent.setClass(this, CaptureActivity.class);
		startActivityForResult(intent, REQUEST_UNPACK);  	
	}			
	
	private void GetCustomer(int intent_code) {
		Intent intent = new Intent();
		intent.setClass(this, CustomerListActivity.class);
		if(intent_code == REQUEST_RCV){
			if(baseFragment.mRcvNameView.getTag() == null){
				intent.putExtra("Action","New");
			}
			else{
				intent.putExtra("Action","New");
				intent.putExtra("CustomerInfo",(CustomerInfo)baseFragment.mRcvNameView.getTag());
			}
		}
		else if(intent_code == REQUEST_SND){
			if(baseFragment.mSndNameView.getTag() == null){
				intent.putExtra("Action","New");
			}
			else{
				intent.putExtra("Action","New");
				intent.putExtra("CustomerInfo",(CustomerInfo)baseFragment.mSndNameView.getTag());
			}
		}
		startActivityForResult(intent, intent_code);
	}

	/**
	 * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
	 * one of the sections/tabs/pages.
	 */
	public class SectionsPagerAdapter extends FragmentPagerAdapter {

		public SectionsPagerAdapter(FragmentManager fm) {
			super(fm);
		}

		@Override
		public Fragment getItem(int position) {
			switch(position){
			case 0:
				baseFragment = ExpressEditFragment1.newInstance();
				return baseFragment;
			case 1:
				externFragment = ExpressEditFragment2.newInstance();
				return externFragment;
			}
			return ExpressEditFragment1.newInstance();
		}

		@Override
		public int getCount() {
			return 2;
		}

		@Override
		public CharSequence getPageTitle(int position) {
			Locale l = Locale.getDefault();
			switch (position) {
			case 0:
				return getString(R.string.title_ex_edit1).toUpperCase(l);
			case 1:
				return getString(R.string.title_ex_edit2).toUpperCase(l);
			}
			return null;
		}
	}

	/**
	 * A placeholder fragment containing a simple view.
	 */
	public static class ExpressEditFragment1 extends Fragment {
		
		private TextView mIDView;
		private TextView mRcvNameView;
		private TextView mRcvTelCodeView;
		private TextView mRcvDptView;
		private TextView mRcvAddrView;
		private TextView mRcvRegionView;

		private TextView mSndNameView;
		private TextView mSndTelCodeView;
		private TextView mSndDptView;
		private TextView mSndAddrView;
		private TextView mSndRegionView;

		private TextView mRcverView;
		private TextView mRcvTimeView;
		
		private TextView mSnderView;
		private TextView mSndTimeView;

		private ImageView mbtnCapture;
		private ImageView mbtnRcv;
		private ImageView mbtnSnd;

		public static ExpressEditFragment1 newInstance() {
			ExpressEditFragment1 fragment = new ExpressEditFragment1();
			return fragment;
		}

		public ExpressEditFragment1() {
		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			View rootView = inflater.inflate(R.layout.fragment_express_edit1,
					container, false);
			mIDView = (TextView) rootView.findViewById(R.id.expressId);
			mRcvNameView = (TextView) rootView.findViewById(R.id.expressRcvName);
			mRcvTelCodeView = (TextView) rootView.findViewById(R.id.expressRcvTel);
			mRcvAddrView = (TextView) rootView.findViewById(R.id.expressRcvAddr);
			mRcvDptView = (TextView) rootView.findViewById(R.id.expressRcvDpt);
			mRcvRegionView = (TextView) rootView.findViewById(R.id.expressRcvRegion);	

			mSndNameView = (TextView) rootView.findViewById(R.id.expressSndName);
			mSndTelCodeView = (TextView) rootView.findViewById(R.id.expressSndTel);
			mSndAddrView = (TextView) rootView.findViewById(R.id.expressSndAddr);
			mSndDptView = (TextView) rootView.findViewById(R.id.expressSndDpt);
			mSndRegionView = (TextView) rootView.findViewById(R.id.expressSndRegion);	

			mRcvTimeView = (TextView) rootView.findViewById(R.id.expressAccTime);
			mSndTimeView = (TextView) rootView.findViewById(R.id.expressDlvTime);

			mbtnCapture = (ImageView) rootView.findViewById(R.id.action_ex_capture_icon);
			mbtnCapture.setOnClickListener(
					new View.OnClickListener() {
						@Override
						public void onClick(View view) {
							((ExpressEditActivity) getActivity()).StartCapture();
						}
					});
			mbtnRcv = (ImageView) rootView.findViewById(R.id.action_ex_rcv_icon);
			mbtnRcv.setOnClickListener(
					new View.OnClickListener() {
						@Override
						public void onClick(View view) {
							((ExpressEditActivity) getActivity()).GetCustomer(REQUEST_RCV);
						}
					});
			mbtnSnd = (ImageView) rootView.findViewById(R.id.action_ex_snd_icon);
			mbtnSnd.setOnClickListener(
					new View.OnClickListener() {
						@Override
						public void onClick(View view) {
							((ExpressEditActivity) getActivity()).GetCustomer(REQUEST_SND);
						}
					});
			return rootView;
		}
		
		void RefreshUI(ExpressSheet es){
			mIDView.setText(es.getID());
			displayRcv(es);
			displaySnd(es);
			if(es.getAccepterTime() != null)
				mRcvTimeView.setText(DateFormat.format("yyyy-MM-dd hh:mm:ss", es.getAccepterTime()));
			else
				mRcvTimeView.setText(null);
			if(es.getDeleveTime() != null)
				mSndTimeView.setText(DateFormat.format("yyyy-MM-dd hh:mm:ss", es.getDeleveTime()));
			else
				mSndTimeView.setText(null);
			displayBtn(es);
		}
		
		void displayBtn(ExpressSheet es){	//按钮状态控制
			if(es.getStatus() == 0){
				mbtnRcv.setVisibility(1);
				mbtnSnd.setVisibility(1);
			}
			else{
				mbtnRcv.setVisibility(0);
				mbtnSnd.setVisibility(0);
			}
		}

		void displayRcv(ExpressSheet es){
			if(es.getRecever() != null){
				mRcvNameView.setText(es.getRecever().getName());
				mRcvTelCodeView.setText(es.getRecever().getTelCode());
				mRcvNameView.setTag(es.getRecever());
				mRcvAddrView.setText(es.getRecever().getAddress());
				mRcvDptView.setText(es.getRecever().getDepartment());
				mRcvRegionView.setText(es.getRecever().getRegionString());
			}
			else{
				mRcvNameView.setText(null);
				mRcvTelCodeView.setText(null);
				mRcvNameView.setTag(null);
				mRcvAddrView.setText(null);
				mRcvDptView.setText(null);
				mRcvRegionView.setText(null);
			}
		}
		
		void displaySnd(ExpressSheet es){
			if(es.getSender() != null){
				mSndNameView.setText(es.getSender().getName());
				mSndTelCodeView.setText(es.getSender().getTelCode());
				mSndNameView.setTag(es.getSender());
				mSndAddrView.setText(es.getSender().getAddress());
				mSndDptView.setText(es.getSender().getDepartment());
				mSndRegionView.setText(es.getSender().getRegionString());
			}
			else{
				mSndNameView.setText(null);
				mSndTelCodeView.setText(null);
				mSndNameView.setTag(null);
				mSndAddrView.setText(null);
				mSndDptView.setText(null);
				mSndRegionView.setText(null);
			}
		}
	}

	public static class ExpressEditFragment2 extends Fragment {

		private EditText etType;
		private EditText etWeight;
		private EditText etTransFee;
		private EditText etPackageFee;
		private EditText etInsuFee;
		private EditText etAccepter;

		/**
		 * Returns a new instance of this fragment for the given section number.
		 */
		public static ExpressEditFragment2 newInstance() {
			ExpressEditFragment2 fragment = new ExpressEditFragment2();
//			Bundle args = new Bundle();
//			args.putInt(ARG_SECTION_NUMBER, sectionNumber);
//			fragment.setArguments(args);
			return fragment;
		}

		public ExpressEditFragment2() {
		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			View rootView = inflater.inflate(R.layout.fragment_express_edit2,
					container, false);

			etType = (EditText) rootView.findViewById(R.id.express_expand_et_type);
			etWeight = (EditText) rootView.findViewById(R.id.express_expand_et_weight);
			etTransFee = (EditText) rootView.findViewById(R.id.express_expand_et_transFee);
			etPackageFee = (EditText) rootView.findViewById(R.id.express_expand_et_packageFee);
			etInsuFee = (EditText) rootView.findViewById(R.id.express_expand_et_InsuFee);
			etAccepter = (EditText) rootView.findViewById(R.id.express_expand_et_accepter);
			return rootView;
		}

		void RefreshUI(ExpressSheet es){
			displayExpand(es);
		}
		void displayExpand(ExpressSheet es){
			if(es.getType() != 0){			//判断空,防止闪退
				etType.setText(""+es.getType());
			}
			if(es.getAccepter() != null){
				etAccepter.setText(""+es.getAccepter());
			}
			if(es.getInsuFee() != null){
				etInsuFee.setText(es.getInsuFee().toString());
			}
			if(es.getTranFee() != null){
				etTransFee.setText(es.getTranFee().toString());
			}
			if(es.getPackageFee() != null){
				etPackageFee.setText(es.getPackageFee().toString());
			}
			if(es.getWeight() != null){
				etWeight.setText(es.getWeight().toString());
			}


		}

		void setExpand(ExpressSheet es) {

			if(! "".equals(etType.getText().toString())){			//判断getText 一定要使用toString
				es.setType(Integer.parseInt(etType.getText().toString()));
			}
			if(! "".equals(etInsuFee.getText().toString())){
				es.setInsuFee(Float.parseFloat(etInsuFee.getText().toString()));
			}
			if(! "".equals(etTransFee.getText().toString())){
				es.setTranFee(Float.parseFloat(etTransFee.getText().toString()));
			}
			if(! "".equals(etPackageFee.getText().toString())){
				es.setPackageFee(Float.parseFloat(etPackageFee.getText().toString()));
			}
			if(! "".equals(etWeight.getText().toString())){
				es.setWeight(Float.parseFloat(etWeight.getText().toString()));
			}
			if(!"".equals(etAccepter.getText().toString())){
				es.setAccepter(etAccepter.getText().toString());
			}
		}

	}


	/*
	 * 新增接口实现类
	 * @see extrace.net.MyDataAdapter#packDataSetChanged()
	 */

	@Override
	public void packDataSetChanged() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void unPackDataSetChanged() {
		// TODO Auto-generated method stub
		
	}


}
