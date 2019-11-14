package extrace.loader;


import android.app.Activity;
import android.util.Log;
import android.widget.Toast;

import com.google.gson.reflect.TypeToken;

import java.util.List;

import extrace.misc.model.ErrorMessage;
import extrace.misc.model.UsersPackage;
import extrace.net.HttpAsyncTask;
import extrace.net.HttpResponseParam;
import extrace.net.IDataAdapter;
import extrace.net.JsonUtils;
import extrace.ui.main.ExTraceApplication;

public class UsersPackagesLoader extends HttpAsyncTask {

    String url;
    IDataAdapter<List<UsersPackage>> adapter;
    private Activity context;

    public UsersPackagesLoader(IDataAdapter<List<UsersPackage>> adpt, Activity context) {
        super(context);
        this.context = context;
        adapter = adpt;
        url = ((ExTraceApplication)context.getApplication()).getMiscServiceUrl();
    }

    @Override
    public void onDataReceive(String class_name, String json_data) {
        // TODO Auto-generated method stub
        if(json_data.equals("Deleted")){
            //adapter.getData().remove(0);	//这个地方不好处理
            Toast.makeText(context, "快件信息已删除!", Toast.LENGTH_SHORT).show();
        }
        else{
            List<UsersPackage> cstm = JsonUtils.fromJson(json_data, new TypeToken<List<UsersPackage>>(){});
            adapter.setData(cstm);
            adapter.notifyDataSetChanged();
            Toast.makeText(context, cstm+"", Toast.LENGTH_SHORT).show();

        }

    }

    @Override
    public void onStatusNotify(HttpResponseParam.RETURN_STATUS status, String str_response) {
        // TODO Auto-generated method stub
        Log.i("onStatusNotify", "onStatusNotify: " + str_response);

    }

    /**
     * 新增
     * @param id
     */
    public void getUserPackages(String id)
    {
        url += "getUserPackages/"+ id + "?_type=json";
        try {
            execute(url, "GET");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
