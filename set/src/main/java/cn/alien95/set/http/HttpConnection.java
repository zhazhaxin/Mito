package cn.alien95.set.http;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

import cn.alien95.set.http.image.ImageCallBack;

/**
 * Created by linlongxin on 2015/12/26.
 */
public class HttpConnection {

    public static final String REQUEST_IAMAGE = "REQUEST_IMAGE";

    private Handler handler;
    private URL requestUrl;
    private HttpURLConnection urlConnection;

    public enum RequestType {
        GET("GET"), POST("POST");
        private String requestType;

        RequestType(String type) {
            this.requestType = type;
        }
    }

    public HttpConnection(String url) throws IOException {
        handler = new Handler();
        requestUrl = new URL(url);
        urlConnection = (HttpURLConnection) requestUrl.openConnection();
        urlConnection.setDoOutput(true);
        urlConnection.setDoInput(true);
        urlConnection.setConnectTimeout(10 * 1000);
        urlConnection.setReadTimeout(10 * 1000);

    }

    /**
     * 网络请求
     *
     * @param type     请求方式{POST,GET}
     * @param param    请求的参数，hashmap键值对的形式
     * @param callback 请求返回的回调
     */

    public synchronized void quest(RequestType type, HashMap<String, String> param, boolean isImage, final HttpCallBack callback) {

        int respondCode = 0;
        try {
            urlConnection.setRequestMethod(String.valueOf(type));

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

            }

            /*对HttpURLConnection对象的一切配置都必须要在connect()函数执行之前完成。*/
            urlConnection.connect();
            InputStream in = urlConnection.getInputStream();
            respondCode = urlConnection.getResponseCode();

            if (respondCode != HttpURLConnection.HTTP_OK) {
                if (isImage) {
                    readBitmap(in, (ImageCallBack) callback);
                    return;
                }
                in = urlConnection.getErrorStream();
                final int finalRespondCode = respondCode;
                final String info = readInputStream(in);
                in.close();
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        callback.failure(finalRespondCode, info);
                        DebugUtils.responseLog(info);
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

    private String readInputStream(InputStream in) {
        String result = "";
        String line = "";

        BufferedReader bin = new BufferedReader(new InputStreamReader(in));
        try {
            while ((line = bin.readLine()) != null) {
                result += line;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }

    public void readBitmap(InputStream inputStream, final ImageCallBack callBack) {
        urlConnection.setDoOutput(false);
        final Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
        handler.post(new Runnable() {
            @Override
            public void run() {
                callBack.success(bitmap);
            }
        });
    }
}
