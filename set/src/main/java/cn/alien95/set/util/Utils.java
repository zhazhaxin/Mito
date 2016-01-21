package cn.alien95.set.util;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.util.DisplayMetrics;
import android.util.Log;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import cn.alien95.set.BuildConfig;

/**
 * Created by linlongxin on 2015/12/26.
 */
public class Utils {

    private static String TAG = "";

    private static Context mContext;

    public static void initialize(Context context,String tag){
        mContext=context;
        TAG = tag;
    }

    public void setDebug(String TAG){
        this.TAG = TAG;
    }

    public void log(String content){
        if(BuildConfig.DEBUG){
            Log.i(TAG,content);
        }
    }

    public static int getScreenWidth(){
        DisplayMetrics dm = mContext.getResources().getDisplayMetrics();
        return dm.widthPixels;
    }

    public static int getScreenHeith(){
        DisplayMetrics dm = mContext.getResources().getDisplayMetrics();
        return dm.heightPixels;
    }

    public static int dip2px(float dpValue){
        final float scale = mContext.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }
    public static int px2dip(float pxValue){
        final float scale = mContext.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }

    /**
     * 获取版本号
     * @return
     */
    public static int getAppVersion(){
        PackageManager manager = mContext.getPackageManager();
        PackageInfo info = null;
        try {
            info = manager.getPackageInfo(mContext.getPackageName(), 0);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return info.versionCode;
    }

    /**
     * 获取根目录下的cache地址
     * @return
     */
    public static File getCacheDir(){
        return mContext.getExternalCacheDir();
    }

    /**
     * 获取具体硬盘缓存文件地址
     * @param uniqueName
     * @return
     */
    public static File getDiskCacheDir(String uniqueName) {
        String cachePath;
        if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())
                || !Environment.isExternalStorageRemovable()) {
            cachePath = mContext.getExternalCacheDir().getPath();
        } else {
            cachePath = mContext.getCacheDir().getPath();
        }
        return new File(cachePath + File.separator + uniqueName);
    }

    /**
     * file转化为bitmap
     * @param file
     * @return
     */
    public static Bitmap fileToBitmap(File file){
        String filePath=file.getPath();
        Bitmap bitmap= BitmapFactory.decodeFile(filePath, getBitmapOption(2)); //将图片的长和宽缩小味原来的1/2
        return bitmap;
    }

    private static BitmapFactory.Options getBitmapOption(int inSampleSize) {
        System.gc();
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inPurgeable = true;
        options.inSampleSize = inSampleSize;
        return options;
    }

    /**
     * bitmap保存为图片文件
     * @param bitmap
     * @param filePath
     */
    public static void saveBitmapFile(Bitmap bitmap,String filePath){
        File file=new File(filePath);//将要保存图片的路径
        try {
            BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(file));
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bos);
            bos.flush();
            bos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String md5(String url) {
        MessageDigest md5 = null;
        try {
            md5 = MessageDigest.getInstance("MD5");
            try {
                if(url==null||url.isEmpty()){
                    return "123456";
                }
                md5.update(url.getBytes("UTF-8"));
                byte[] encryption = md5.digest();
                StringBuffer strBuf = new StringBuffer();
                for (int i = 0; i < encryption.length; i++) {
                    if (Integer.toHexString(0xff & encryption[i]).length() == 1) {
                        strBuf.append("0").append(Integer.toHexString(0xff & encryption[i]));
                    } else {
                        strBuf.append(Integer.toHexString(0xff & encryption[i]));
                    }
                }
                return strBuf.toString();
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
                return "";
            }
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return "";
        }
    }

}
