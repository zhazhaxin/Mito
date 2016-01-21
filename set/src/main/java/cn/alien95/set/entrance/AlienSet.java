package cn.alien95.set.entrance;

import android.content.Context;

import cn.alien95.set.http.image.ImageUtils;
import cn.alien95.set.util.Utils;

/**
 * Created by linlongxin on 2015/12/29.
 */
public class AlienSet {

    public static void init(Context context,String debugTag){
        Utils.initialize(context,debugTag);
        ImageUtils.init(context);
    }
}
