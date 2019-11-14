package Client.loader;

import android.app.Activity;
import android.util.Log;
import android.widget.Toast;

import com.google.gson.reflect.TypeToken;

import java.util.List;

import Client.misc.model.MissingExpressSheet;
import Client.net.HttpAsyncTask;
import Client.net.HttpResponseParam;
import Client.net.IDataAdapter;
import Client.net.JsonUtils;
import Client.ui.main.ExTraceApplication;


public class MissExpressSheetLoader extends HttpAsyncTask {

    String url;
    IDataAdapter<List<MissingExpressSheet>> adapter;
    private Activity context;

    public MissExpressSheetLoader(IDataAdapter<List<MissingExpressSheet>> adpt, Activity context) {
        super(context);
        this.context = context;
        adapter = adpt;
        url = ((ExTraceApplication)context.getApplication()).getDomainServiceUrl();

    }

    @Override
    public void onDataReceive(String class_data, String json_data) {
        if(json_data.equals("Deleted")){
            //adapter.getData().remove(0);	//这个地方不好处理
            Toast.makeText(context, "客户信息已删除!", Toast.LENGTH_SHORT).show();
        }
        else{
            if(json_data == null || json_data.length() == 0)
            {
                Toast.makeText(context, "没有符合条件的客户信息!", Toast.LENGTH_SHORT).show();
                adapter.setData(null);
                adapter.notifyDataSetChanged();
            }
            else
            {
                List<MissingExpressSheet> cstm = JsonUtils.fromJson(json_data, new TypeToken<List<MissingExpressSheet>>(){});
                adapter.setData(cstm);
                adapter.notifyDataSetChanged();
                Toast.makeText(context, cstm+"没有符合条件的客户信息!", Toast.LENGTH_SHORT).show();


            }
        }
    }

    @Override
    public void onStatusNotify(HttpResponseParam.RETURN_STATUS status, String str_response) {
        Log.i("onStatusNotify", "onStatusNotify: " + str_response);
    }



    public void getMissExpressSheet()
    {
        url += "getMissExpressSheet?_type=json";
        try {
            execute(url, "GET");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
