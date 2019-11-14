package extrace.loader;


import android.app.Activity;
import android.util.Log;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.util.List;

import extrace.misc.model.ErrorMessage;
import extrace.misc.model.Location;
import extrace.net.HttpAsyncTask;
import extrace.net.HttpResponseParam;
import extrace.net.IDataAdapter;
import extrace.net.JsonUtils;
import extrace.ui.main.ExTraceApplication;

public class LocationLoader extends HttpAsyncTask {

    String url;
    IDataAdapter<List<Location>> adapter;
    private Activity context;

    public LocationLoader(IDataAdapter<List<Location>> adpt, Activity context) {
        super(context);
        this.context = context;
        adapter = adpt;
        url = ((ExTraceApplication)context.getApplication()).getDomainServiceUrl();
    }

    @Override
    public void onDataReceive(String class_data, String json_data) {
        if(json_data.equals("Deleted")){
            //adapter.getData().remove(0);	//这个地方不好处理
            Toast.makeText(context, "快件信息已删除!", Toast.LENGTH_SHORT).show();
        }

        else{
            List<Location> cstm = JsonUtils.fromJson(json_data, new TypeToken<List<Location>>(){});
            adapter.setData(cstm);
            adapter.notifyDataSetChanged();
            Toast.makeText(context, "快件!"+cstm, Toast.LENGTH_SHORT).show();

        }
    }

    @Override
    public void onStatusNotify(HttpResponseParam.RETURN_STATUS status, String str_response) {
        Log.i("onStatusNotify", "onStatusNotify: " + str_response);
    }


    public void uploadLoc(Location location)
    {
        url += "uploadLoc";
        Gson gson = new Gson();
        String jsonObj = gson.toJson(location);
        try {
            execute(url, "POST", jsonObj);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void getPosition(String esId)
    {
        url += "getPosition/"+esId+"?_type=json";
        try {
            execute(url, "GET");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
