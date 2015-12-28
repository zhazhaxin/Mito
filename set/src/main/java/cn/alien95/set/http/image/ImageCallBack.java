package cn.alien95.set.http.image;

import android.graphics.Bitmap;

import cn.alien95.set.http.HttpCallBack;

/**
 * Created by linlongxin on 2015/12/26.
 */
public abstract class ImageCallBack extends HttpCallBack{

    public abstract void success(Bitmap bitmap);

    @Override
    public void success(String info) {

    }
}
