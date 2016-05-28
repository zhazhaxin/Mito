package cn.alien95.alien95library.app;

import android.app.Application;

import com.squareup.leakcanary.LeakCanary;

import cn.alien95.alien95library.BuildConfig;
import cn.alien95.resthttp.request.RestHttp;
import cn.alien95.util.Utils;

/**
 * Created by linlongxin on 2015/12/28.
 */
public class App extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        Utils.initialize(this);
        RestHttp.initialize(this);
        LeakCanary.install(this);
        if(BuildConfig.DEBUG){
            Utils.setDebug(true,"Debug");
            RestHttp.setDebug(true,"network");
        }
    }
}
