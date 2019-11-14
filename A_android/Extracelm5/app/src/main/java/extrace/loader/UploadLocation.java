package extrace.loader;

import android.app.Activity;
import android.util.Log;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.List;

import extrace.misc.model.ExpressSheet;
import extrace.misc.model.Location;
import extrace.net.HttpAsyncTask;
import extrace.net.HttpResponseParam;
import extrace.net.HttpUtil;
import extrace.net.IDataAdapter;
import extrace.net.JsonUtils;
import extrace.ui.main.ExTraceApplication;


//将后来的上传数据操作放入此包中

public class UploadLocation extends HttpUtil {

    String url;

    public UploadLocation() {
        url = ExTraceApplication.nowURL;
    }


    @Override
    public void onStatusNotify(HttpResponseParam.RETURN_STATUS status, String str_response) {
        Log.i("onStatusNotify", "onStatusNotify: " + str_response);
    }



    /**
     * 上传位置
     * @param location
     */

    public void uploadLoc(Location location)
    {
        url += "uploadPosition";
        Gson gson = new Gson();
        String jsonObj = gson.toJson(location);
        try {
            execute(url, "POST", jsonObj);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public void expressSheetMiss(String packageId,String expressSheetId)
    {
        url += "expressSheetMiss/"+ packageId + "/" + expressSheetId + "?_type=json";
        try {
            execute(url, "GET");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
