package Client.loader;

import com.google.gson.Gson;

import android.app.Activity;
import android.util.Log;
import android.widget.Toast;

import Client.misc.model.UserInfo;
import Client.net.HttpAsyncTask;
import Client.net.HttpResponseParam.RETURN_STATUS;
import Client.net.IDataAdapter;
import Client.net.JsonUtils;
import Client.ui.main.ExTraceApplication;

public class UserInfoLoader extends HttpAsyncTask {
	String url;// = "http://192.168.7.100:8080/TestCxfHibernate/REST/Misc/";
	IDataAdapter<UserInfo> adapter;
	private Activity context;

	public UserInfoLoader(Activity context) {
		super(context);
		adapter = new defaultAdapter();
		this.context = context;
		url = ((ExTraceApplication)context.getApplication()).getMiscServiceUrl();
	}
	
	public UserInfoLoader(IDataAdapter<UserInfo> adpt, Activity context) {
		super(context);
		this.context = context;
		adapter = adpt;
		url = ((ExTraceApplication)context.getApplication()).getMiscServiceUrl();
	}

	@Override
	public void onDataReceive(String class_name, String json_data) {
		
		if(class_name.equals("W_UserInfo")){
			//对应服务器中数据("账号或密码错误，请重新输入！").header("EntityClass", "W_UserInfo").build();
			Toast.makeText(context, "用户信息不存在!", Toast.LENGTH_SHORT).show();
		}
		else if(class_name.equals("S_UserInfo")) {		//更新用户信息
			Toast.makeText(context, "更新成功!", Toast.LENGTH_SHORT).show();
			UserInfo user = JsonUtils.fromJson(json_data, UserInfo.class);
			adapter.setData(user);
			adapter.notifyDataSetChanged();
			((ExTraceApplication)context.getApplication()).setUserInfo(user); //给application传入用户信息	
		}
		else{
		
			if(json_data == null || json_data.length() == 0)//暂时没有用
			{
				Toast.makeText(context, "没有符合条件的快递员信息!", Toast.LENGTH_SHORT).show();
				adapter.setData(null);
				adapter.notifyDataSetChanged();
			}
			else
			{
				//Toast.makeText(context, class_name+"********"+json_data, Toast.LENGTH_SHORT).show();
				UserInfo user = JsonUtils.fromJson(json_data, UserInfo.class);
				adapter.setData(user);
				adapter.notifyDataSetChanged();
				((ExTraceApplication)context.getApplication()).setUserInfo(user); //给application传入用户信息
			}
		}
	}

	@Override
	public void onStatusNotify(RETURN_STATUS status, String str_response) {
		Log.i("onStatusNotify", "onStatusNotify: " + str_response);
	}
	
	public void LoadUserInfoByID(String ID, String PWD)
	{
		//url += "getUserInfo/"+ ID + "?_type=json";
		url += "doLogin/"+ ID + "/" + PWD + "?_type=json";
		try {
			execute(url, "GET");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void SaveUser(UserInfo user)
	{
		//String jsonObj = JsonUtils.toJson(user, true);  原有的转化为json字符串的方法出错
		Gson gson = new Gson();
	    String jsonObj = gson.toJson(user);
		//Toast.makeText(context, user+"##############"+jsonObj, Toast.LENGTH_SHORT).show();	

		url += "saveUserInfo";
		try {
			execute(url, "POST", jsonObj);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
//
//	public void LoadCustomerListByName(String name)
//	{
//		url += "getCustomerListByName/"+ name + "?_type=json";
//		try {
//			execute(url, "GET");
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//	}
//
//	public void DeleteCustomer(int id)
//	{
//		url += "deleteCustomerInfo/"+ id + "?_type=json";
//		try {
//			execute(url, "GET");
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//	}
//

	class defaultAdapter implements IDataAdapter<UserInfo> {		//提供一个默认adapter供无参数的构造函数使用

		@Override
		public UserInfo getData() {
			return new UserInfo();
		}

		@Override
		public void setData(UserInfo data) {

		}

		@Override
		public void notifyDataSetChanged() {

		}
	}
	
}
