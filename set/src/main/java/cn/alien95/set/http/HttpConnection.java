package cn.alien95.set.http;

import android.os.Handler;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

import cn.alien95.set.http.util.DebugUtils;

/**
 * Created by linlongxin on 2015/12/26.
 */
public class HttpConnection {

    private Handler handler = new Handler();
    private URL requestUrl;
    private HttpURLConnection urlConnection;
    private String logUrl;

    public enum RequestType {
        GET("GET"), POST("POST");
        private String requestType;

        RequestType(String type) {
            this.requestType = type;
        }
    }

    public HttpConnection(String url) {
        try {
            requestUrl = new URL(url);
            logUrl = url;
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }

    /**
     * 网络请求
     *
     * @param type     请求方式{POST,GET}
     * @param param    请求的参数，HashMap键值对的形式
     * @param callback 请求返回的回调
     */

    public synchronized void quest(RequestType type, HashMap<String, String> param, final HttpCallBack callback) {

        final int respondCode;
        try {
            //连接
            urlConnection = (HttpURLConnection) requestUrl.openConnection();
            urlConnection.setDoOutput(true);
            urlConnection.setDoInput(true);
            urlConnection.setConnectTimeout(10 * 1000);
            urlConnection.setReadTimeout(10 * 1000);
            urlConnection.setRequestMethod(String.valueOf(type));

            //POST请求参数：因为POST请求的参数在写在流里面
            if (type.equals(RequestType.POST)) {
                String s = "";
                if (param != null) {
                    for (Map.Entry<String, String> map : param.entrySet()) {
                        s += "&" + URLEncoder.encode(map.getKey(), "UTF-8") + "=" + URLEncoder.encode(map.getValue(), "UTF-8");
                    }
                }
                OutputStream ops = urlConnection.getOutputStream();
                ops.write(s.getBytes());
                ops.flush();
                ops.close();

                logUrl += s;
            }

            //对HttpURLConnection对象的一切配置都必须要在connect()函数执行之前完成。
            urlConnection.connect();
            InputStream in = urlConnection.getInputStream();
            respondCode = urlConnection.getResponseCode();

            //请求失败
            if (respondCode != HttpURLConnection.HTTP_OK) {
                in = urlConnection.getErrorStream();
                final int finalRespondCode = respondCode;
                final String info = readInputStream(in);
                in.close();
                //回调：错误信息返回主线程
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        callback.failure(finalRespondCode, info);
                        DebugUtils.requestLog(logUrl);   //打印log，请求的参数，地址
                        DebugUtils.responseLog("code:" + respondCode + "   " + info);
                    }
                });
                return;
            } else {
                final String result = readInputStream(in);
                in.close();
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        callback.success(result);
                        DebugUtils.requestLog(logUrl);   //打印log，请求的参数，地址
                        DebugUtils.responseLog(result);
                    }
                });
            }

        } catch (final IOException e1) {
            e1.printStackTrace();
            handler.post(new Runnable() {
                @Override
                public void run() {
                    callback.error();
                }
            });
        }
    }

    //读取输入流信息，转化成String
    private String readInputStream(InputStream in) {
        String result = "";
        String line;
        if (in != null) {
            BufferedReader bin = new BufferedReader(new InputStreamReader(in));
            try {
                while ((line = bin.readLine()) != null) {
                    result += line;
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return result;
    }


}
