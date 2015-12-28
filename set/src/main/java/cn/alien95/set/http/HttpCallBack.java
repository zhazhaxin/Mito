package cn.alien95.set.http;

/**
 * Created by linlongxin on 2015/12/26.
 */
public abstract class HttpCallBack {

    public void error(){}

    public abstract void success(String info);

    public void failure(int status, String info){}
}
