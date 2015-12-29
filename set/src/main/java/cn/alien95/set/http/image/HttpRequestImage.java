package cn.alien95.set.http.image;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by linlongxin on 2015/12/26.
 */
public class HttpRequestImage {

    private final String TAG = "HttpRequestImage";

    private static HttpRequestImage instance;
    private Handler handler;

    private HttpRequestImage() {

    }

    public static HttpRequestImage getInstance() {
        if (instance == null) {
            synchronized (HttpRequestImage.class) {
                if (instance == null) {
                    instance = new HttpRequestImage();
                }
            }
        }
        return instance;
    }

    public void requestImage(String url, ImageCallBack callBack) {
        if (loadImageFromMemory(url) != null) {
            Log.i(TAG, "从内存读取图片");
            callBack.success(loadImageFromMemory(url));
        } else {
            Log.i(TAG, "从网络获取图片");
            loadImageFromNet(url, callBack);
        }
    }

    public Bitmap loadImageFromMemory(String key) {
        return MemoryCache.getInstance().getBitmapFromMemCache(key);
    }

    public void loadImageFromNet(String url, ImageCallBack callBack) {
        HttpURLConnection urlConnection = null;
        try {
            urlConnection = (HttpURLConnection) new URL(url).openConnection();
            urlConnection.setRequestMethod("GET");
        } catch (IOException e) {
            e.printStackTrace();
        }
        urlConnection.setDoOutput(true);
        urlConnection.setDoInput(true);
        urlConnection.setConnectTimeout(10 * 1000);
        urlConnection.setReadTimeout(10 * 1000);
        //对HttpURLConnection对象的一切配置都必须要在connect()函数执行之前完成。
        int respondCode;
        try {
            urlConnection.connect();
            InputStream in = urlConnection.getInputStream();
            respondCode = urlConnection.getResponseCode();
            if (respondCode == HttpURLConnection.HTTP_OK) {
                readBitmap(in, callBack);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //读取输入流，转化成Bitmap
    public void readBitmap(InputStream inputStream, final ImageCallBack callBack) {
        final Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
        handler.post(new Runnable() {
            @Override
            public void run() {
                callBack.success(bitmap);
            }
        });
    }
}
