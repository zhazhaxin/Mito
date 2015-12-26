package cn.alien95.set.http;

/**
 * Created by linlongxin on 2015/12/26.
 */
public interface HttpCallBack {

    void error();

    void success(String info);

    void failure(int status, String info);
}
