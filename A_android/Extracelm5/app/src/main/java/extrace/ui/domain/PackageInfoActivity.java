package extrace.ui.domain;

import android.content.Intent;
import android.content.pm.PackageInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import extrace.loader.TransPackageLoader;
import extrace.misc.model.TransPackage;
import extrace.net.MyDataAdapter;
import extrace.ui.main.PackageEditActivity;
import extrace.ui.main.R;
import extrace.ui.misc.RegionListActivity;

public class PackageInfoActivity extends AppCompatActivity implements MyDataAdapter<TransPackage>,AdapterView.OnItemSelectedListener {

    TextView tvSave;
    TextView tvBack;
    TextView tvId;
    TextView tvSource;
    TextView tvTarget;
    TextView tvCreateTime;
   // EditText etStatus; 废弃
    ImageView ivSearchSource;
    ImageView ivSearchTarget;

    private Spinner spinner ;
    private ArrayAdapter<CharSequence> adapter ;    //下拉框

    int LOCATIONTYPE;   //判断地点 类型
    String pid;            //包裹号

    private String sourceId;    //源网点
    private String targetId;    //目标网点

    private TransPackage transPackage;
    private TransPackageLoader mLoader;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_package_info);
        findId();
        setLisenter();
        Intent intent = getIntent();
        pid = intent.getStringExtra("packageId");
        tvId.setText(pid);
        mLoader = new TransPackageLoader(PackageInfoActivity.this,PackageInfoActivity.this);
        mLoader.Load(pid);


        /***
         * 下拉框
         */
        this.spinner =(Spinner) this.findViewById(R.id.package_info_spinner_package);
        //两种方式给下拉列表注册适配器
        //2 :.createFromResource(content对象,xml中数组id,样式ID)
        adapter = ArrayAdapter.createFromResource(PackageInfoActivity.this,
                R.array.package_status,android.R.layout.simple_spinner_dropdown_item );
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        //注册监听器
        spinner.setOnItemSelectedListener(this);
    }

    private void findId(){
        tvSave = (TextView) findViewById(R.id.package_info_tv_save);
        tvBack = (TextView) findViewById(R.id.package_info_tv_back);
        tvId = (TextView) findViewById(R.id.package_info_tv_id);
        tvSource = (TextView) findViewById(R.id.package_info_tv_source);
        tvTarget = (TextView) findViewById(R.id.package_info_tv_target);
        tvCreateTime = (TextView) findViewById(R.id.package_info_tv_time);

       // etStatus = (EditText) findViewById(R.id.package_info_et_status);
        ivSearchSource = (ImageView) findViewById(R.id.package_info_iv_ssearch);
        ivSearchTarget = (ImageView) findViewById(R.id.package_info_iv_tsearch);

    }

    private void setLisenter(){

        tvSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //transPackage.setStatus(Integer.parseInt(etStatus.getText().toString()));

                transPackage.setSourceNode(sourceId);
                transPackage.setTargetNode(targetId);

                Toast.makeText(PackageInfoActivity.this, transPackage+"id号", Toast.LENGTH_SHORT).show();


                mLoader = new TransPackageLoader(PackageInfoActivity.this,PackageInfoActivity.this);
                mLoader.Save(transPackage);
            }
        });

        ivSearchSource.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LOCATIONTYPE = 0;
                GetRegion();
            }
        });


        ivSearchTarget.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LOCATIONTYPE = 1;
                GetRegion();
            }
        });
    }

    private void GetRegion() {      //查询
        Intent intent = new Intent();
        intent.setClass(this, RegionListActivity.class);
        try{

//            String rCode = tvSource.getTag().toString();
//            String rString = tvSource.getText().toString();
//            intent.putExtra("RegionId", rCode);
//            intent.putExtra("RegionString", rString);

        }
        catch(Exception e){

        }
        startActivityForResult(intent, 0);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {     //回调
        switch (resultCode) {
            case RESULT_OK:
                String regionId,regionString;
                if (data.hasExtra("RegionId")) {
                    regionId = data.getStringExtra("RegionId");
                    regionString = data.getStringExtra("RegionString");
                } else {
                    regionId = "";
                    regionString = "";
                }

                if(LOCATIONTYPE == 0){          //设置源网点
                    tvSource.setTag(regionId);
                    sourceId = regionId;
                    tvSource.setText(regionString);
                }else if(LOCATIONTYPE == 1){    //设置目标网点
                    tvTarget.setTag(regionId);
                    targetId = regionId;
                    tvTarget.setText(regionString);
                }

                Toast.makeText(this, regionId+"id号", Toast.LENGTH_SHORT).show();

                break;
            default:
                break;
        }
    }

    @Override
    public void packDataSetChanged() {

    }

    @Override
    public void unPackDataSetChanged() {

    }

    @Override
    public TransPackage getData() {
        return transPackage;
    }

    @Override
    public void setData(TransPackage data) {
        transPackage = data;
    }

    @Override
    public void notifyDataSetChanged() {
        Toast.makeText(this, transPackage+"id号", Toast.LENGTH_SHORT).show();
        //etStatus.setText(transPackage.getStatus()+"");
        tvSource.setText(transPackage.getSourceNode()+"");
        tvTarget.setText(transPackage.getTargetNode()+"");
        tvCreateTime.setText(transPackage.getCreateTime()+"");
        spinner.setSelection(transPackage.getStatus());// 设置下拉框默认选中项


        sourceId = transPackage.getSourceNode();   //记录原本id 防止保存空数据
        targetId = transPackage.getTargetNode();
    }


    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
        String item = (String) spinner.getItemAtPosition(position);
       // Toast.makeText(PackageInfoActivity.this, "您选中了"+item+"选项"+position, Toast.LENGTH_SHORT).show();
       // transPackage.setStatus(position);   闪退原因
        TextView tv = (TextView)view;
        tv.setTextColor(getResources().getColor(R.color.gray));    //设置颜色
        tv.setTextSize(15.0f);    //设置大小

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}
