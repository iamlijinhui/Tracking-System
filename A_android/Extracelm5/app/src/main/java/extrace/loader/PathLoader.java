package extrace.loader;

import android.app.Activity;
import android.util.Log;
import android.widget.Toast;

import com.google.gson.reflect.TypeToken;

import java.util.List;

import extrace.misc.model.ErrorMessage;
import extrace.misc.model.Path;
import extrace.net.HttpAsyncTask;
import extrace.net.HttpResponseParam;
import extrace.net.IDataAdapter;
import extrace.net.JsonUtils;
import extrace.ui.main.ExTraceApplication;

public class PathLoader extends HttpAsyncTask {

    String url;
    IDataAdapter<List<Path>> adapter;
    private Activity context;

    public PathLoader(IDataAdapter<List<Path>> adpt, Activity context) {
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
        else if(class_data.equals("Message")){

            ErrorMessage errorMessage = JsonUtils.fromJson(json_data, ErrorMessage.class);
            Toast.makeText(context, errorMessage.getMsgInfo()+"", Toast.LENGTH_SHORT).show();

        }
        else{
            //Toast.makeText(context, json_data+"", Toast.LENGTH_SHORT).show();
           // Toast.makeText(context, "#################"+json_data, Toast.LENGTH_SHORT).show();
            List<Path> cstm = JsonUtils.fromJson(json_data, new TypeToken<List<Path>>(){});
            adapter.setData(cstm);
            adapter.notifyDataSetChanged();


        }
    }

    @Override
    public void onStatusNotify(HttpResponseParam.RETURN_STATUS status, String str_response) {
        Log.i("onStatusNotify", "onStatusNotify: " + str_response);
    }


    public void getPath(String id)
    {
        url += "getPath/" + id + "?_type=json";
        try {
            execute(url, "GET");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
