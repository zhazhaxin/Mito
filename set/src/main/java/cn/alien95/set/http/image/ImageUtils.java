package cn.alien95.set.http.image;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;

import java.io.File;
import java.io.InputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Created by linlongxin on 2015/12/29.
 */
public class ImageUtils {

    private static Context mContext;

    public static void init(Context context){
        mContext = context;
    }

    /** 为图片压缩做准备，通过设置inSampleSize参数来压缩
     * 通过reqWidth和reqHeight来计算出合理的inSampleSize
     *
     * @param options   BitmapFactory.Options bitmap参数
     * @param reqWidth  需要设置的宽
     * @param reqHeight 需要设置的高
     * @return  int 返回一个inSampleSize值来压缩图片
     */
    public static int calculateInSampleSize(BitmapFactory.Options options, int reqWidth, int reqHeight) {
        // 源图片的高度和宽度
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;
        if (height > reqHeight || width > reqWidth) {
            // 计算出实际宽高和目标宽高的比率
            final int heightRatio = Math.round((float) height / (float) reqHeight);
            final int widthRatio = Math.round((float) width / (float) reqWidth);
            // 选择宽和高中最小的比率作为inSampleSize的值，这样可以保证最终图片的宽和高
            // 一定都会大于等于目标的宽和高。
            inSampleSize = heightRatio < widthRatio ? heightRatio : widthRatio;
        }
        return inSampleSize;
    }

    /** 压缩从网络获取的图片，加载到内存
     *
     * @param inputStream  网络获取的输入流
     * @param reqWidth    希望获得Bitmap的宽
     * @param reqHeight   希望获得Bitmap的高
     * @return
     */
    public static Bitmap compressBitmapFromInputStream(InputStream inputStream,
                                                         int reqWidth, int reqHeight) {
        // 第一次解析将inJustDecodeBounds设置为true，来获取图片大小
        final BitmapFactory.Options options = new BitmapFactory.Options();
        //设置为true，不给像素分配内存,但是可以获取到图片的宽和高等信息，来根据情况进行压缩
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeStream(inputStream, null, options);
        // 调用上面定义的方法计算inSampleSize值
        options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight);
        // 使用获取到的inSampleSize值再次解析图片
        options.inJustDecodeBounds = false;  //设置了inSampleSize后压缩图片，重新分配内存
        return BitmapFactory.decodeStream(inputStream, null, options);
    }

    /**
     * 获取图片硬盘缓存路径
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
     *
     * @param key  传入加密的字符串
     * @return  返回MD5加密后的字符串
     */
    public static String MD5(String key) {
        String cacheKey;
        try {
            final MessageDigest mDigest = MessageDigest.getInstance("MD5");
            mDigest.update(key.getBytes());
            cacheKey = bytesToHexString(mDigest.digest());
        } catch (NoSuchAlgorithmException e) {
            cacheKey = String.valueOf(key.hashCode());
        }
        return cacheKey;
    }

    private static String bytesToHexString(byte[] bytes) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < bytes.length; i++) {
            String hex = Integer.toHexString(0xFF & bytes[i]);
            if (hex.length() == 1) {
                sb.append('0');
            }
            sb.append(hex);
        }
        return sb.toString();
    }

}
