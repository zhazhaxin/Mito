package cn.alien95.set.http.image;

import android.graphics.Bitmap;

import cn.alien95.set.http.HttpCallBack;

/**
 * Created by linlongxin on 2015/12/26.
 */
public interface ImageCallBack extends HttpCallBack{

    void success(Bitmap bitmap);
}
