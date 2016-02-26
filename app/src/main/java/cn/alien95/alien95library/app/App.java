package cn.alien95.alien95library.app;

import android.app.Application;
import android.util.Log;

import alien95.cn.util.Utils;
import cn.alien95.alien95library.BuildConfig;

/**
 * Created by linlongxin on 2015/12/28.
 */
public class App extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        Log.i("fuck","Application--onCreate");
        Utils.initialize(this);
        if(BuildConfig.DEBUG){
            Utils.setDebug(true,"Debug");
        }
    }
}
