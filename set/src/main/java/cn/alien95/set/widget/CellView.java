package cn.alien95.set.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;

import java.util.List;

import cn.alien95.set.util.Utils;

/**
 * Created by linlongxin on 2015/12/28.
 */
public class CellView extends FrameLayout {

    private static final String TAG = "AlienGridView";
    private View[] chidren;
    private int deliver;
    private int childWidth;

    public CellView(Context context) {
        this(context, null);
    }

    public CellView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CellView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        deliver = Utils.dip2px(4);
        childWidth = (Utils.getScreenWidth() -Utils.dip2px(32) - Utils.dip2px(16)) / 3;
        if (chidren != null) {
            for (int i = 0; i < chidren.length; i++) {
                addView(chidren[i]);
            }
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        measureChildren(widthMeasureSpec, widthMeasureSpec);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        int childCount = getChildCount();
        Log.i(TAG, "childcount:" + childCount);
        if (childCount == 1) {
            layout(left, top, right, bottom);
        } else {
            for (int i = 0; i < (childCount > 9 ? 9 : childCount); i++) {
                getChildAt(i).layout(deliver * (i % 3 + 1) + childWidth * (i % 3), i / 3 * childWidth + deliver * (i / 3 + 1), deliver * (i % 3 + 1) + childWidth * (i % 3 + 1), deliver * (i / 3 + 1) + (i / 3 + 1) * childWidth);
            }
        }
    }

    public void setImages(String[] data) {


    }

    public void setImages(List<String> data) {

    }


    public void setViews(View... items) {
        for (View item : items) {
            if (item != null)
                addView(item);
        }
    }
}
