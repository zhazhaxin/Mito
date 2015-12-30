package cn.alien95.set.widget;

import android.content.Context;
import android.net.Uri;
import android.util.AttributeSet;
import android.util.Log;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.facebook.drawee.view.SimpleDraweeView;

import java.util.List;

import cn.alien95.set.util.Utils;

/**
 * Created by linlongxin on 2015/12/28.
 */
public class CellView extends FrameLayout {

    private static final String TAG = "AlienGridView";
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
        childWidth = (Utils.getScreenWidth() - Utils.dip2px(32) - Utils.dip2px(16)) / 3;
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
            layout(deliver, deliver, Utils.dip2px(204), Utils.dip2px(204));
        } else {
            for (int i = 0; i < (childCount > 9 ? 9 : childCount); i++) {
                getChildAt(i).layout(deliver * (i % 3 + 1) + childWidth * (i % 3), i / 3 * childWidth + deliver * (i / 3 + 1), deliver * (i % 3 + 1) + childWidth * (i % 3 + 1), deliver * (i / 3 + 1) + (i / 3 + 1) * childWidth);
            }
        }
    }

    public void setImages(String[] data) {
        SimpleDraweeView img = new SimpleDraweeView(getContext());
        img.setLayoutParams(new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        for (String url : data) {
            img.setImageURI(Uri.parse(url));
            addView(img);
        }

    }

    public void setImages(List<String> data) {
        String[] urls = (String[]) data.toArray();
        setImages(urls);
    }

}
