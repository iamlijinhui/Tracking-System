package Client.ui.main;

import Client.ui.domain.PackageDeliverActivity;
import Client.ui.domain.PackagePaisongActivity;
import Client.ui.domain.PackageTransActivity;
import Client.ui.domain.ExpressEditActivity;
import Client.ui.misc.CustomerListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class MainFragment  extends Fragment {
	
    /**
     * Returns a new instance of this fragment for the given section
     * number.
     */
    public static MainFragment newInstance() {
    	MainFragment fragment = new MainFragment();
        Bundle args = new Bundle();
        //args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        fragment.setArguments(args);
        return fragment;
    }

    public MainFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_main, container, false);	//加载布局
        //fragment_main中的操作激发
        rootView.findViewById(R.id.action_ex_receive_icon).setOnClickListener(
				new View.OnClickListener() {
					@Override
					public void onClick(View view) {
						StartReceiveExpress();
					}
				});
        rootView.findViewById(R.id.action_ex_receive).setOnClickListener(
				new View.OnClickListener() {
					@Override
					public void onClick(View view) {
						StartReceiveExpress();
					}
				});												//快件揽收对应的文本框和图标
        
        
        rootView.findViewById(R.id.action_ex_transfer_icon).setOnClickListener(
				new View.OnClickListener() {
					@Override
					public void onClick(View view) {
						PaiSong();
					}
				});
        rootView.findViewById(R.id.action_ex_transfer).setOnClickListener(
				new View.OnClickListener() {
					@Override
					public void onClick(View view) {
						PaiSong();
					}
				});													//快件派送对应的文本框和图标


       /*
        * 新增打包,拆包
        */  
        rootView.findViewById(R.id.action_pk_pkg_icon).setOnClickListener(
				new View.OnClickListener() {
					@Override
					public void onClick(View view) {
						 PackExpress();
					}
				});
        rootView.findViewById(R.id.action_pk_pkg).setOnClickListener(
				new View.OnClickListener() {
					@Override
					public void onClick(View view) {
						 PackExpress();
					}
				});														//打包
        
        rootView.findViewById(R.id.action_pk_exp_icon).setOnClickListener(
				new View.OnClickListener() {
					@Override
					public void onClick(View view) {
						 UnPackExpress();
					}
				});
        rootView.findViewById(R.id.action_pk_exp).setOnClickListener(
				new View.OnClickListener() {
					@Override
					public void onClick(View view) {
						 UnPackExpress();
					}
				});														//拆包





		rootView.findViewById(R.id.action_pk_transport_icon).setOnClickListener(
				new View.OnClickListener() {
					@Override
					public void onClick(View view) {
						TransPackage();
					}
				});
		rootView.findViewById(R.id.action_pk_transport_icon).setOnClickListener(
				new View.OnClickListener() {
					@Override
					public void onClick(View view) {
						TransPackage();
					}
				});







		rootView.findViewById(R.id.action_cu_mng_icon).setOnClickListener(
				new View.OnClickListener() {
					@Override
					public void onClick(View view) {
						StartCustomerList();
					}
				});
        rootView.findViewById(R.id.action_cu_mng).setOnClickListener(
				new View.OnClickListener() {
					@Override
					public void onClick(View view) {
						StartCustomerList();
					}
				});
        																								//客户管理对应的文本框和图标

        rootView.findViewById(R.id.action_ex_qur_icon).setOnClickListener(
				new View.OnClickListener() {
					@Override
					public void onClick(View view) {
						StartQueryExpress();
					}
				});
        rootView.findViewById(R.id.action_ex_qur).setOnClickListener(
				new View.OnClickListener() {
					@Override
					public void onClick(View view) {
						StartQueryExpress();
					}
				});													//快件查询对应的文本框和图标


		rootView.findViewById(R.id.action_ex_history_icon).setOnClickListener(
				new View.OnClickListener() {
					@Override
					public void onClick(View view) {
						esHistory();
					}
				});
		rootView.findViewById(R.id.action_ex_history).setOnClickListener(
				new View.OnClickListener() {
					@Override
					public void onClick(View view) {
						esHistory();
					}
				});												   //运输历史对应的文本框和图标


		return rootView;
    }
    
    void StartReceiveExpress()
    {
		Intent intent = new Intent();
		intent.putExtra("Action","New");
		intent.setClass(this.getActivity(), ExpressEditActivity.class);
		startActivityForResult(intent, 0);  	
    }

    void StartQueryExpress()
    {
		Intent intent = new Intent();
		intent.putExtra("Action","Query");
		intent.setClass(this.getActivity(), ExpressEditActivity.class);
		startActivityForResult(intent, 0);  	
    }

    void StartCustomerList()
    {
		Intent intent = new Intent();
		intent.putExtra("Action","None");
		intent.setClass(this.getActivity(), CustomerListActivity.class);
		startActivityForResult(intent, 0);
    }
    
    //以下新增

	void PaiSong(){
		Intent intent = new Intent();
		intent.setClass(this.getActivity(), PackagePaisongActivity.class);
		startActivityForResult(intent, 0);
	}
    
    void PackExpress()
    {
		Intent intent = new Intent();
		intent.putExtra("Action","pack");
		intent.setClass(this.getActivity(), PackageEditActivity.class);
		startActivityForResult(intent, 0);  	
    }
    void UnPackExpress()
    {
		Intent intent = new Intent();
		intent.putExtra("Action","unPack");
		intent.setClass(this.getActivity(), PackageEditActivity.class);
		startActivityForResult(intent, 0);  	
    }

	void TransPackage()
	{
		Intent intent = new Intent();
		//intent.putExtra("Action","unPack");
		intent.setClass(this.getActivity(), PackageTransActivity.class);
		startActivityForResult(intent, 0);
	}

	void esHistory()
	{
		Intent intent = new Intent();
		intent.putExtra("Action","esHistory");
		intent.setClass(this.getActivity(), PackageDeliverActivity.class);
		startActivityForResult(intent, 0);
	}



}
