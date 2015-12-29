package cn.alien95.set.http.request;

import java.util.Map;

import cn.alien95.set.http.util.DebugUtils;

/**
 * Created by linlongxin on 2015/12/26.
 */
public class HttpRequest implements Http {

    private static HttpConnection httpConnection;

    private static HttpRequest instance;

    private HttpRequest(){
    }

    public static HttpRequest getInstance(){
        if(instance == null){
            synchronized (HttpRequest.class){
                if(instance == null)
                instance = new HttpRequest();
            }
        }
        httpConnection = HttpConnection.getInstance();
        return instance;
    }

    public static void setDebug(boolean isDebug,String tag){
        DebugUtils.setDebug(isDebug);
        DebugUtils.initialize(tag);
    }

    public void setHttpHeader(Map<String,String> header){
        httpConnection.setHttpHeader(header);
    }

    @Override
    public void get(final String url, final HttpCallBack callBack) {
        //请求加入队列，队列通过start()方法自动请求网络
        HttpQueue.getInstance().addQuest(new Runnable() {
            @Override
            public void run() {
                httpConnection.quest(url,HttpConnection.RequestType.GET, null, callBack);
            }
        });
    }

    @Override
    public void post(final String url, final Map<String, String> params, final HttpCallBack callBack) {
        HttpQueue.getInstance().addQuest(new Runnable() {
            @Override
            public void run() {
                httpConnection.quest(url,HttpConnection.RequestType.POST, params, callBack);
            }
        });
    }

}
