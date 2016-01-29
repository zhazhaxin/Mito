package cn.alien95.alien95library.model;

import android.util.Log;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import alien95.cn.http.request.HttpRequest;
import alien95.cn.http.request.callback.HttpCallBack;
import cn.alien95.alien95library.config.API;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;

/**
 * Created by linlongxin on 2015/12/29.
 */
public class ImageModel {


    public static void getImageForNet(String word, int page, HttpCallBack callBack) {
        try {
            String readWord = URLEncoder.encode(word, "GBK");
            String url = API.GET_IAMGES_BASEURL + "query=" + readWord + "&start=" + page * 20 + "&reqType=ajax&reqFrom=result";
            HttpRequest.getInstance().get(url, callBack);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    public static void getDataFromOkHttp(String word, int page, Callback callback) throws IOException {
        try {
            String readWord = URLEncoder.encode(word, "GBK");
            String url = API.GET_IAMGES_BASEURL + "query=" + readWord + "&start=" + page * 20 + "&reqType=ajax&reqFrom=result";
            OkHttpClient client = new OkHttpClient();
            Request request = new Request.Builder()
                    .url(url)
                    .build();
            client.newCall(request).enqueue(callback);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            Log.i("fuck", "getDataFromOkHttp:" + e.getMessage());
        }
    }
}
