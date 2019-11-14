package extrace.net;
import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.util.Log;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.params.ConnManagerParams;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;

import extrace.net.HttpResponseParam;
import extrace.ui.main.ExTraceApplication;

//HTTP应答返回参数

public abstract class HttpUtil extends
        AsyncTask<String, Integer, HttpResponseParam> {
    private static final int REGISTRATION_TIMEOUT = 3 * 1000;
    private static final int WAIT_TIMEOUT = 5 * 1000;

    private static final String TAG = "ExTraceHttpUtils";
    private static final String USER_AGENT = "Mozilla/4.5";

    //private Activity context;

    public HttpUtil() {

    }

    protected String retrieveInputStream(HttpEntity httpEntity) {	//m HttpEntity实体即可以是流也可以是字符串形式。
        int length = (int) httpEntity.getContentLength();
        if (length < 0)
            length = 10000;
        StringBuffer stringBuffer = new StringBuffer(length);
        try {
            InputStreamReader inputStreamReader = new InputStreamReader(
                    httpEntity.getContent(), HTTP.UTF_8);
            char buffer[] = new char[length];
            int count;
            while ((count = inputStreamReader.read(buffer, 0, length - 1)) > 0) {
                stringBuffer.append(buffer, 0, count);
            }
        } catch (UnsupportedEncodingException e) {
            Log.e(TAG, e.getMessage());
        } catch (IllegalStateException e) {
            Log.e(TAG, e.getMessage());
        } catch (IOException e) {
            Log.e(TAG, e.getMessage());
        }
        return stringBuffer.toString();
    }

    @Override
    protected HttpResponseParam doInBackground(String... params) {//m Java多参数方法，就是说在不清楚参数应该放多少情况下，只写一个方法就能实现。
        String http_uri;		//第一个参数是url 第二个参数是请求类型

        HttpResponseParam responseObj = new HttpResponseParam();
        boolean isPost = (params[1] == "POST") ? true : false; // GET/POST

        try {
            http_uri = params[0];

            HttpClient client = new DefaultHttpClient();
            final HttpParams http_params = client.getParams();
            HttpConnectionParams.setConnectionTimeout(http_params,
                    REGISTRATION_TIMEOUT);
            HttpConnectionParams.setSoTimeout(http_params, WAIT_TIMEOUT);
            ConnManagerParams.setTimeout(http_params, WAIT_TIMEOUT);

            publishProgress(30);

            HttpResponse response;

            if (!isPost) {
                HttpGet getMethod = new HttpGet(http_uri);
                getMethod.setHeader("User-Agent", USER_AGENT);
                getMethod.setHeader("Accept", "application/json");
                getMethod.setHeader("token", ExTraceApplication.TOKEN);


                // HttpParams params = new HttpParams();

                // 添加用户密码验证信息
                // client.getCredentialsProvider().setCredentials(
                // new AuthScope(null, -1),
                // new UsernamePasswordCredentials(mUsername, mPassword));
                response = client.execute(getMethod);
            } else {
                String str_json = params[2];
                HttpPost postMethod = new HttpPost(http_uri);
                postMethod.setHeader("User-Agent", USER_AGENT);
                postMethod.addHeader("Content-Type", "application/json");
                postMethod.setHeader("Accept", "application/json");

                postMethod.setHeader("token", ExTraceApplication.TOKEN);			//加token


                System.out.println(str_json); // ============================================================
                StringEntity entity = new StringEntity(str_json, "UTF-8");
                entity.setContentType("application/json");
                postMethod.setEntity(entity);

//				List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
//				if(params.length > 2){
//					for(int i=2; i<params.length; i++){
//						nameValuePairs.add(new BasicNameValuePair(params[i],params[i+1]));
//						System.out.println(nameValuePairs); // ============================================================
//					}
//					postMethod.setEntity(new UrlEncodedFormEntity(nameValuePairs, "UTF-8"));
//				}

                // List<NameValuePair> nameValuePairs = new
                // ArrayList<NameValuePair>(6);
                // nameValuePairs.add(new BasicNameValuePair("uid",params[1]));
                // nameValuePairs.add(new BasicNameValuePair("upw",params[2]));
                // nameValuePairs.add(new BasicNameValuePair("ver6",params[3]));
                // nameValuePairs.add(new BasicNameValuePair("vid6",params[4]));
                // nameValuePairs.add(new BasicNameValuePair("mid6",params[5]));
                // nameValuePairs.add(new
                // BasicNameValuePair("smbtn",params[6]));
                // post.setEntity(new UrlEncodedFormEntity(nameValuePairs));
                response = client.execute(postMethod);
            }
            publishProgress(50);
            HttpEntity entity;
            switch (response.getStatusLine().getStatusCode()) {
                case 200:
                    entity = response.getEntity();
                    Header[] head = response.getHeaders("EntityClass");
                    if (head.length > 0) {
                        responseObj.responseClassName = head[0].getValue(); // 当有多种返回的对象可能时,用这个名字来区分
                    } else {
                        responseObj.responseClassName = "";
                    }

                    responseObj.responseString = EntityUtils.toString(entity,
                            "utf8");
                    responseObj.statusCode = HttpResponseParam.RETURN_STATUS.Ok;
                    break;
                case 201:
                    entity = response.getEntity();
                    responseObj.responseString = EntityUtils.toString(entity,
                            "utf8");
                    responseObj.statusCode = HttpResponseParam.RETURN_STATUS.Saved;
                    break;
                case 400:
                    responseObj.responseString = "服务器未能识别请求。"
                            + response.getStatusLine().toString();
                    responseObj.statusCode = HttpResponseParam.RETURN_STATUS.ResponseException;
                    break;
                case 404:
                    responseObj.responseString = "服务器拒绝满足请求。: "
                            + response.getStatusLine().toString();
                    responseObj.statusCode = HttpResponseParam.RETURN_STATUS.ResponseException;
                    break;
                case 500:
                    entity = response.getEntity();
                    responseObj.responseString = EntityUtils.toString(entity,
                            "utf8");
                    responseObj.statusCode = HttpResponseParam.RETURN_STATUS.ServerException;
                    break;
                default:
                    responseObj.responseString = "服务器错误: "
                            + response.getStatusLine().toString();
                    responseObj.statusCode = HttpResponseParam.RETURN_STATUS.RequestException;
                    break;
            }
        } catch (IOException e) {
            e.printStackTrace();
            responseObj.responseString = "网络连接错误: " + e.getMessage();
            responseObj.statusCode = HttpResponseParam.RETURN_STATUS.NetworkException;
        } catch (Exception e) {
            e.printStackTrace();
            responseObj.responseString = "运行时错误: " + e.getMessage();
            responseObj.statusCode = HttpResponseParam.RETURN_STATUS.Unknown;
        }
        publishProgress(100);
        return responseObj;
    }

    @Override
    protected void onCancelled() {
        super.onCancelled();

    }

    @Override
    protected void onPostExecute(HttpResponseParam result) {	//m onPostExecute(Result), 在doInBackground完成之后调用，一般是设置结果，取消第一个方法显示的进度条。
        if (result.statusCode == HttpResponseParam.RETURN_STATUS.Ok) {

        } else {
            // Toast.makeText(context.getApplicationContext(),
            // "服务请求失败!"+result.responseString, Toast.LENGTH_SHORT).show();
            onStatusNotify(result.statusCode, result.responseString);
        }
        // dialog.dismiss();
    }

    @Override
    protected void onPreExecute() {	//m onPreExecute(), 高负载代码执行之前调用 ，通常用来显示一个进度条，在主线程中执行

    }

    @Override
    protected void onProgressUpdate(Integer... values) {	// m 一般用来更新第一个方法显示的进度条，什么下载了50% 51%
        // 更新进度											AsyncTask的生命周期方法，你自己不要调用
    }


    public abstract void onStatusNotify(HttpResponseParam.RETURN_STATUS status,
                                        String str_response);
}

