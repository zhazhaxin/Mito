package cn.alien95.set.http;

import android.util.Log;

import cn.alien95.set.BuildConfig;


/**
 * Created by alien on 2015/8/6.
 */
public class DebugUtils {

    static int times=0;

    static int responseTimes=0;

    private static String DEBUG_TAG="";

    public static void initialize(String tag){
        DEBUG_TAG=tag;
    }

    public static void Log(String info){
        if(BuildConfig.DEBUG){
            Log.i(DEBUG_TAG,info);
        }
    }

    public static synchronized void questLog(String info){
        if(BuildConfig.DEBUG){
            Log.i(DEBUG_TAG,times+" times quest:"+info);
            responseTimes=times++;
        }
    }

    public static synchronized void responseLog(String info){
        if(BuildConfig.DEBUG){
            Log.i(DEBUG_TAG,responseTimes+" times response:"+info);
        }
    }
}
