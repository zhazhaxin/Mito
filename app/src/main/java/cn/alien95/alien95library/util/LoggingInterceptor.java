package cn.alien95.alien95library.util;

import android.util.Log;

import java.io.IOException;

import alien95.cn.util.Utils;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by linlongxin on 2016/2/26.
 */
public class LoggingInterceptor implements Interceptor {

    private static final String TAG = "network";

    @Override
    public Response intercept(Chain chain) {
        Request request = chain.request();
        Log.i(TAG, "all-info:" + request.toString());
        Log.i(TAG, "method:" + request.method());
        Log.i(TAG, "header:" + request.headers().toString());

        Response response = null;
        try {
            response = chain.proceed(request);
            if (response.isSuccessful()) {
                Log.i(TAG, "response:" + response.toString());
            } else {
                Utils.Toast("网络错误");
            }
        } catch (IOException e) {
            Log.i(TAG, "网络错误");
            Utils.Toast("网络错误");
            Utils.Log("log网络错误");
            e.printStackTrace();

        }

        return response;
    }

}
