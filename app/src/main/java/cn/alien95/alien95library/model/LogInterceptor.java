package cn.alien95.alien95library.model;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Response;

/**
 * Created by linlongxin on 2016/1/31.
 */
public class LogInterceptor implements Interceptor {

    @Override
    public Response intercept(Chain chain) throws IOException {
        chain.request().body();
        return null;
    }
}
