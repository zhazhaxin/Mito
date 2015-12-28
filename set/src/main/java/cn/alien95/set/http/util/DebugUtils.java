package cn.alien95.set.http.util;

import android.util.Log;



/**
 * Created by alien on 2015/8/6.
 */
public class DebugUtils {

    public static int requestTimes=0;

    public static int responseTimes=0;

    private static String DEBUG_TAG="";

    private static boolean isDebug = false;  //是否开启debug模式，默认关闭

    public static void initialize(String tag){
        DEBUG_TAG=tag;
    }

    public static void setDebug(boolean debug){
        isDebug = debug;
    }

    public static synchronized void Log(String info){
        if(isDebug){
            Log.i(DEBUG_TAG,info);
        }
    }

    public static synchronized void requestLog(String info){
        if(isDebug){
            Log.i(DEBUG_TAG,requestTimes+" times quest:"+info);
            requestTimes++;
        }
    }

    public static synchronized void responseLog(int requestTimes,String info){
        if(isDebug){
            Log.i(DEBUG_TAG,requestTimes+" times response:"+info);
        }
    }
}
