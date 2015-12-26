package cn.alien95.set.http.request;

import java.io.IOException;
import java.util.HashMap;

import cn.alien95.set.http.Http;
import cn.alien95.set.http.HttpCallBack;
import cn.alien95.set.http.HttpConnection;
import cn.alien95.set.http.image.ImageCallBack;

/**
 * Created by linlongxin on 2015/12/26.
 */
public class HttpRequest implements Http {

    private HttpConnection httpConnection;

    public HttpRequest(String url) throws IOException {
        httpConnection = new HttpConnection(url);

    }

    @Override
    public void get(HttpCallBack callBack) {
        httpConnection.quest(HttpConnection.RequestType.GET, null,false, callBack);
    }

    @Override
    public void post(HashMap<String, String> params, HttpCallBack callBack) {
        httpConnection.quest(HttpConnection.RequestType.POST,params,false,callBack);
    }

    @Override
    public void imageLoader(ImageCallBack callBack) {
        httpConnection.quest(HttpConnection.RequestType.GET, null,true, callBack);
    }

}
