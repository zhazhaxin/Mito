package cn.alien95.set.http;

import android.util.Log;

import cn.alien95.set.BuildConfig;


/**
 * Created by alien on 2015/8/6.
 */
public class DebugUtils {

    static int requestTimes=0;

    static int responseTimes=0;

    private static String DEBUG_TAG="";

    private static boolean isDebug = true;  //是否开启debug模式，默认开启

    public static void initialize(String tag){
        DEBUG_TAG=tag;
    }

    public static void setDebug(boolean debug){
        isDebug = debug;
    }

    public static synchronized void Log(String info){
        if(BuildConfig.DEBUG && isDebug){
            Log.i(DEBUG_TAG,info);
        }
    }

    public static synchronized void requestLog(String info){
        if(BuildConfig.DEBUG && isDebug){
            Log.i(DEBUG_TAG,requestTimes+" times quest:"+info);
            responseTimes=requestTimes++;
        }
    }

    public static synchronized void responseLog(String info){
        if(BuildConfig.DEBUG && isDebug){
            Log.i(DEBUG_TAG,responseTimes+" times response:"+info);
        }
    }
}
