package cn.alien95.set.http.request;

import java.util.Map;

import cn.alien95.set.http.Http;
import cn.alien95.set.http.HttpCallBack;
import cn.alien95.set.http.HttpConnection;
import cn.alien95.set.http.HttpQueue;
import cn.alien95.set.http.util.DebugUtils;

/**
 * Created by linlongxin on 2015/12/26.
 */
public class HttpRequest implements Http {

    private static HttpConnection httpConnection;

    private static HttpRequest instance;

    private HttpRequest(){
    }

    public static HttpRequest getInstance(String url){
        if(instance == null){
            synchronized (HttpRequest.class){
                if(instance == null)
                instance = new HttpRequest();
            }
        }
        httpConnection = new HttpConnection(url);
        return instance;
    }

    public void setDebug(boolean isDebug){
        DebugUtils.setDebug(isDebug);
    }

    @Override
    public void get(final HttpCallBack callBack) {
        //请求加入队列，队列通过start()方法自动请求网络
        HttpQueue.getInstance().addQuest(new Runnable() {
            @Override
            public void run() {
                httpConnection.quest(HttpConnection.RequestType.GET, null, callBack);
            }
        });
    }

    @Override
    public void post(final Map<String, String> params, final HttpCallBack callBack) {
        HttpQueue.getInstance().addQuest(new Runnable() {
            @Override
            public void run() {
                httpConnection.quest(HttpConnection.RequestType.POST, params, callBack);
            }
        });
    }

}
