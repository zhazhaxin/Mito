package cn.alien95.set.http.image;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.util.Log;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
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
        handler = new Handler();
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
        }else if (loadImageFromDisk(url) != null){
            Log.i(TAG, "硬盘读取图片");
            callBack.success(loadImageFromDisk(url));
        }else {
            Log.i(TAG, "从网络获取图片");
            loadImageFromNet(url, callBack);
        }
    }

    /**
     * 从内存缓存中获取已经从网络获取过的图片
     * @param key
     * @return
     */
    private Bitmap loadImageFromMemory(String key) {
        return MemoryCache.getInstance().getBitmapFromMemCache(key);
    }

    /**
     * 从硬盘缓存中加载图片
     * @param imageUrl
     * @return
     */
    private Bitmap loadImageFromDisk(String imageUrl){
        return DiskCache.getInstance().readImageFromDisk(imageUrl);
    }

    /**
     * 从网络加载图片
     * @param url
     * @param callBack
     */
    private void loadImageFromNet(final String url, final ImageCallBack callBack) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                HttpURLConnection urlConnection = null;
                try {
                    urlConnection = (HttpURLConnection) new URL(url).openConnection();
                    urlConnection.setRequestMethod("GET");
                } catch (IOException e) {
                    e.printStackTrace();
                }
//                urlConnection.setDoOutput(true);   //沃日，为毛请求图片不能添加这句
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
                        final Bitmap bitmap = BitmapFactory.decodeStream(in);
                        final HttpURLConnection finalUrlConnection = urlConnection;
                        handler.post(new Runnable() {
                            @Override
                            public void run() {
                                callBack.success(bitmap);
                                MemoryCache.getInstance().putBitmapToCache(url,bitmap);
                                DiskCache.getInstance().writeImageToDisk(url, finalUrlConnection);
                            }
                        });
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();

    }

    /**
     * 下载图片到硬盘
     * @param outputStream
     * @return
     */
    public boolean loadImageToStream(HttpURLConnection urlConnection, OutputStream outputStream) {
        BufferedOutputStream out = null;
        BufferedInputStream in = null;
        try {
            in = new BufferedInputStream(urlConnection.getInputStream(), 8 * 1024);
            out = new BufferedOutputStream(outputStream, 8 * 1024);
            int b;
            while ((b = in.read()) != -1) {
                out.write(b);
            }
            return true;
        } catch (final IOException e) {
            e.printStackTrace();
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            try {
                if (out != null) {
                    out.close();
                }
                if (in != null) {
                    in.close();
                }
            } catch (final IOException e) {
                e.printStackTrace();
            }
        }
        return false;
    }
}
