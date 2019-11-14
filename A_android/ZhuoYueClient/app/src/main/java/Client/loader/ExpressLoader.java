package Client.loader;

import android.app.Activity;
import android.util.Log;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import Client.misc.model.ExpressSheet;
import Client.net.HttpAsyncTask;
import Client.net.HttpResponseParam.RETURN_STATUS;
import Client.net.IDataAdapter;
import Client.net.JsonUtils;
import Client.ui.main.ExTraceApplication;

public class ExpressLoader extends HttpAsyncTask {

	String url;
	IDataAdapter<ExpressSheet> adapter;
	private Activity context;


	public ExpressLoader(Activity context) {
		super(context);
		adapter = new defaultAdapter();
		this.context = context;
		url = ((ExTraceApplication)context.getApplication()).getDomainServiceUrl();
	}

	public ExpressLoader(IDataAdapter<ExpressSheet> adpt, Activity context) {
		super(context);
		this.context = context;
		adapter = adpt;
		url = ((ExTraceApplication)context.getApplication()).getDomainServiceUrl();
	}

	@Override
	public void onDataReceive(String class_name, String json_data) {
		if(class_name.equals("ExpressSheet"))
		{
			ExpressSheet ci = JsonUtils.fromJson(json_data, new TypeToken<ExpressSheet>(){});
			adapter.setData(ci);
			adapter.notifyDataSetChanged();
			//Toast.makeText(context, ci+"快件运单信息已经存在!", Toast.LENGTH_SHORT).show();

			// Gson gson = new Gson();
			Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();
			//("yyyy-MM-ddTHH:mm:ss")

			ExpressSheet ci1 = gson.fromJson(json_data, ExpressSheet.class);
			Toast.makeText(context, json_data+"*****************"+ci1, Toast.LENGTH_SHORT).show();
			Log.i("&&&&&&&&&&", "onDataReceive: "+json_data);



		}
		else if(class_name.equals("E_ExpressSheet"))		//已经存在
		{
			ExpressSheet ci = JsonUtils.fromJson(json_data, new TypeToken<ExpressSheet>(){});
			adapter.setData(ci);
			adapter.notifyDataSetChanged();
			Toast.makeText(context, "快件运单信息已经存在!", Toast.LENGTH_SHORT).show();
		}
		else if(class_name.equals("R_ExpressSheet"))		//保存完成
		{
			ExpressSheet ci = JsonUtils.fromJson(json_data, new TypeToken<ExpressSheet>(){});
			adapter.getData().setID(ci.getID());
			adapter.getData().onSave();
			adapter.notifyDataSetChanged();
			Toast.makeText(context, "快件运单信息保存完成!", Toast.LENGTH_SHORT).show();
		}
		else if(class_name.equals("PACK_Finished"))		//保存完成
		{
			Toast.makeText(context, "该包裹已打包完成,不能继续装入包裹!", Toast.LENGTH_SHORT).show();
		}
	}

	@Override
	public void onStatusNotify(RETURN_STATUS status, String str_response) {
		// TODO Auto-generated method stub
		
	}


	public void unPack(String pId, String eId, String uId)
	{
		url += "unpack/packageId/"+ pId+ "/expressId/" + eId+ "/uId/" + uId + "?_type=json";
		///unpack/PackageId/1/expressId/4/uid/11

		try {
			execute(url, "GET");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void Pack(String uId, String pId, String eId)
	{
		url += "pack/"+ uId+ "/" + pId+ "/" + eId + "?_type=json";
		///unpack/PackageId/1/expressId/4/uid/11

		try {
			execute(url, "GET");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void finishPack(String uId, String pId)
	{
		url += "finishPack/"+ uId+ "/" + pId + "?_type=json";
		///unpack/PackageId/1/expressId/4/uid/11

		try {
			execute(url, "GET");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void Load(String id)
	{
		url += "getExpressSheet/"+ id + "?_type=json";
		try {
			execute(url, "GET");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void New(String id)
	{
		int uid = ((ExTraceApplication)context.getApplication()).getLoginUser().getUID();	//得到用户id
		url += "newExpressSheet/id/"+ id + "/uid/"+ uid + "?_type=json";
		try {
			execute(url, "GET");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void Save(ExpressSheet es)
	{
		String jsonObj = JsonUtils.toJson(es, true);
//		Gson gson = new Gson();
//		String jsonObj = gson.toJson(es);
		Log.i("*********************8", "Save: "+jsonObj);

		url += "saveExpressSheet";
		try {
			execute(url, "POST", jsonObj);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	class defaultAdapter implements IDataAdapter<ExpressSheet> {		//提供一个默认adapter供无参数的构造函数使用

		@Override
		public ExpressSheet getData() {
			return new ExpressSheet();
		}

		@Override
		public void setData(ExpressSheet data) {

		}

		@Override
		public void notifyDataSetChanged() {

		}
	}
}
