package cn.alien95.alien95library.utils;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import cn.alien95.alien95library.BuildConfig;

/**
 * Created by linlongxin on 2016/1/15.
 */
public class Utils {

    private static Context mContext;
    private static String DEBUGTAG;

    public static void init(Context context) {
        mContext = context;
    }

    public static void setDebugtag(String debugtag) {
        DEBUGTAG = debugtag;
    }

    public static void Log(String content) {
        if (BuildConfig.DEBUG) {
            Log.i(DEBUGTAG, content);
        }
    }

    public static void Toast(String content) {
        Toast.makeText(mContext, content, Toast.LENGTH_SHORT).show();
    }

    public static void ToastLong(String content) {
        Toast.makeText(mContext, content, Toast.LENGTH_LONG).show();
    }

//    public static void SackbarShort(View view, String hintStr) {
//        Snackbar.make(view, hintStr, Snackbar.LENGTH_SHORT)
//                .setAction("", null).show();
//    }
//    public static void SackbarLong(View view, String hintStr, String actionStr, View.OnClickListener clickListener) {
//        Snackbar.make(view, hintStr, Snackbar.LENGTH_LONG)
//                .setAction(actionStr, clickListener).show();
//    }


}
