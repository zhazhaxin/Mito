package cn.alien95.alien95library.test.app;

import android.app.Application;

import cn.alien95.alien95library.BuildConfig;
import cn.alien95.set.http.util.DebugUtils;

/**
 * Created by linlongxin on 2015/12/28.
 */
public class App extends Application{

    @Override
    public void onCreate() {
        super.onCreate();
        if(BuildConfig.DEBUG){
            DebugUtils.initialize("NetWork");
            DebugUtils.setDebug(true);
        }
    }
}
