package cn.alien95.set.entrance;

import android.content.Context;

import cn.alien95.set.util.Utils;

/**
 * Created by linlongxin on 2015/12/29.
 */
public class AlienSet {

    /**
     * 初始化
     *
     * @param context 建议使用Application实例
     */
    public static void init(Context context) {
        Utils.initialize(context);

    }

    /**
     * 设置调试模式
     *
     * @param isDebug  isDebug 是否开启调试模式，默认是false（关闭状态）
     * @param debugTag debugTag  调试的日志输出tag，如果isDebug = false,意味着这个参数将没有任何意义
     */
    public static void setDebug(boolean isDebug, String debugTag) {
        Utils.setDebug(isDebug, debugTag);
    }
}
