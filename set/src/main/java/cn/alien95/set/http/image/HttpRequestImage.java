package cn.alien95.set.http.image;

import java.io.IOException;

import cn.alien95.set.http.HttpConnection;
import cn.alien95.set.http.request.HttpRequest;

/**
 * Created by linlongxin on 2015/12/26.
 */
public class HttpRequestImage {

    private HttpConnection httpConnection;

    public HttpRequestImage(String url) throws IOException {
        httpConnection = new HttpConnection(url);
    }

    public void requestImage(String url,ImageCallBack callBack) throws IOException {
        new HttpRequest(url).imageLoader(callBack);
    }
}
