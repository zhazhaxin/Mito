package cn.alien95.set.http;

import java.util.Map;

/**
 * Created by linlongxin on 2015/12/26.
 */
public interface Http {
    void get(HttpCallBack callBack);
    void post(Map<String,String> params, HttpCallBack callBack);
}
