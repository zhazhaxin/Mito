package cn.alien95.set.http;

import java.util.HashMap;

import cn.alien95.set.http.image.ImageCallBack;

/**
 * Created by linlongxin on 2015/12/26.
 */
public interface Http {
    void get(HttpCallBack callBack);
    void post(HashMap<String,String> params,HttpCallBack callBack);
    void imageLoader(ImageCallBack callBack);
}
