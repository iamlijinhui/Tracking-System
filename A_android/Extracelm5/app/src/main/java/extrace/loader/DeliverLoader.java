package extrace.loader;


import android.app.Activity;
import android.util.Log;
import android.widget.Toast;

import com.google.gson.reflect.TypeToken;

import java.util.List;

import extrace.misc.model.ErrorMessage;
import extrace.net.HttpAsyncTask;
import extrace.net.HttpResponseParam;
import extrace.net.IDataAdapter;
import extrace.net.JsonUtils;
import extrace.ui.main.ExTraceApplication;

public class DeliverLoader extends HttpAsyncTask {

    String url;
    IDataAdapter<List<String>> adapter;
    private Activity context;


    public DeliverLoader(Activity context) {
        super(context);
        adapter = new defaultAdapter();
        this.context = context;
        url = ((ExTraceApplication)context.getApplication()).getDomainServiceUrl();
    }

    public DeliverLoader(IDataAdapter<List<String>> adpt, Activity context) {
        super(context);
        this.context = context;
        adapter = adpt;
        url = ((ExTraceApplication)context.getApplication()).getDomainServiceUrl();
    }

    @Override
    public void onDataReceive(String class_data, String json_data) {
        if(class_data.equals("Message")){
            //adapter.getData().remove(0);	//这个地方不好处理
            ErrorMessage errorMessage = JsonUtils.fromJson(json_data, ErrorMessage.class);
            Toast.makeText(context, errorMessage.getMsgInfo()+"", Toast.LENGTH_SHORT).show();

        }

        else{
            List<String> cstm = JsonUtils.fromJson(json_data, new TypeToken<List<String>>(){});
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

    public void DispatchExpressSheet(String id, String uid)
    {
        url += "dispatchExpressSheetId/" +"id/"+ id  +"/uid/"+ uid + "?_type=json";
        try {
            execute(url, "GET");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void deliveryExpressSheetId(String id, String uid)
    {
        url += "deliveryExpressSheetId/" +"id/"+ id  +"/uid/"+ uid + "?_type=json";
        try {
            execute(url, "GET");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    class defaultAdapter implements IDataAdapter<List<String>> {
        @Override
        public List<String> getData() {
            return null;
        }

        @Override
        public void setData(List<String> data) {

        }

        @Override
        public void notifyDataSetChanged() {

        }        //提供一个默认adapter供无参数的构造函数使用


    }
}
