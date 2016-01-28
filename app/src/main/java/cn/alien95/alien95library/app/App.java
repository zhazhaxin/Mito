package cn.alien95.alien95library.app;

import android.app.Application;

import com.facebook.drawee.backends.pipeline.Fresco;

import alien95.cn.http.request.Http;
import cn.alien95.alien95library.BuildConfig;

/**
 * Created by linlongxin on 2015/12/28.
 */
public class App extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        Fresco.initialize(this);
        Http.initialize(this);
        if (BuildConfig.DEBUG) {
            Http.setDebug(true, "NetWork");   //关于网络的调试,日志输出
        }


    }
}
