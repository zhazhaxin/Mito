package cn.alien95.set.http.image;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.jakewharton.disklrucache.DiskLruCache;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;

import cn.alien95.set.util.Utils;

/**
 * Created by linlongxin on 2015/12/29.
 */
public class DiskCache {

    private final String IMAGE_CACHE_PATH = "IMAGE_CACHE";
    private DiskLruCache diskLruCache;
    private static DiskCache instance;

    private DiskCache() {
        try {
            File cacheDir = ImageUtils.getDiskCacheDir(IMAGE_CACHE_PATH);
            if (!cacheDir.exists()) {
                cacheDir.mkdirs();
            }
            //10MB硬盘缓存
            diskLruCache = DiskLruCache.open(cacheDir, Utils.getAppVersion(), 1, 10 * 1024 * 1024);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static DiskCache getInstance() {
        if (instance == null) {
            synchronized (DiskCache.class) {
                if (instance == null)
                    instance = new DiskCache();
            }
        }
        return instance;
    }

    /**
     * 写入缓存到硬盘
     *
     * @param imageUrl 图片地址
     */
    public void writeImageToDisk(final String imageUrl, final HttpURLConnection urlConnection) {
        final String key = ImageUtils.MD5(imageUrl);
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    DiskLruCache.Editor editor;
                    editor = diskLruCache.edit(key);
                    if (editor != null) {
                        OutputStream outputStream = editor.newOutputStream(0);
                        if (HttpRequestImage.getInstance().loadImageToStream(urlConnection, outputStream)) {
                            editor.commit();
                        } else {
                            editor.abort();
                        }
                    }
                    diskLruCache.flush();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();

    }

    /**
     * 读取硬盘缓存
     *
     * @param imageUrl 图片地址
     * @return
     */
    public Bitmap readImageFromDisk(String imageUrl) {
        try {
            String key = ImageUtils.MD5(imageUrl);
            DiskLruCache.Snapshot snapShot = diskLruCache.get(key);
            if (snapShot != null) {
                InputStream is = snapShot.getInputStream(0);
                Bitmap bitmap = BitmapFactory.decodeStream(is);
                return bitmap;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
