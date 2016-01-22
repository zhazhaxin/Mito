package cn.alien95.alien95library.app;

import android.app.Application;


import cn.alien95.alien95library.BuildConfig;
import cn.alien95.set.entrance.AlienSet;
import cn.alien95.set.http.request.HttpRequest;

/**
 * Created by linlongxin on 2015/12/28.
 */
public class App extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        AlienSet.init(this);  //工具的调试输出
        if (BuildConfig.DEBUG) {
            HttpRequest.setDebug(true, "NetWork");   //关于网络的调试,日志输出
            AlienSet.setDebug(true, "Library");
        }


    }
}
