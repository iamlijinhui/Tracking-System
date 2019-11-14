package extrace.loader;

import java.util.List;

import android.app.Activity;
import android.util.Log;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import extrace.misc.model.ErrorMessage;
import extrace.misc.model.ExpressSheet;
import extrace.misc.model.Location;
import extrace.net.HttpAsyncTask;
import extrace.net.HttpResponseParam.RETURN_STATUS;
import extrace.net.IDataAdapter;
import extrace.net.JsonUtils;
import extrace.ui.main.ExTraceApplication;

public class ExpressListLoader extends HttpAsyncTask {

	String url;
	IDataAdapter<List<ExpressSheet>> adapter;
	private Activity context;
	
	public ExpressListLoader(IDataAdapter<List<ExpressSheet>> adpt, Activity context) {
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
			List<ExpressSheet> cstm = JsonUtils.fromJson(json_data, new TypeToken<List<ExpressSheet>>(){});
			adapter.setData(cstm);
			adapter.notifyDataSetChanged();

		}
	}

	@Override
	public void onStatusNotify(RETURN_STATUS status, String str_response) {
		Log.i("onStatusNotify", "onStatusNotify: " + str_response);
	}
	
	public void LoadExpressList()
	{
		url += "getExpressList/ID/like/6?_type=json";
		try {
			execute(url, "GET");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void LoadExpressListInPackage(String pkgId)
	{
		url += "getExpressListInPackage/PackageId/"+pkgId+"?_type=json";
		try {
			execute(url, "GET");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 上传位置
	 * @param location
	 */

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

	public void finishDelivery(String pkgId, String uid)
	{
		url += "finishDelivery/packageId/" + pkgId + "/uid/" + uid +"?_type=json";
		try {
			execute(url, "GET");
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
