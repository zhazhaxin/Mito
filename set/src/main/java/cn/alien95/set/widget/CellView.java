package cn.alien95.set.widget;

import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.util.AttributeSet;
import android.util.Log;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.facebook.drawee.view.SimpleDraweeView;

import java.util.List;

import cn.alien95.set.http.image.HttpRequestImage;
import cn.alien95.set.http.image.ImageCallBack;
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

    /**
     * 设置加载图片地址集合
     * @param data
     */
    public void setImages(String[] data) {
        for (String url : data) {
            SimpleDraweeView img = new SimpleDraweeView(getContext());
            img.setLayoutParams(new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
            img.setImageURI(Uri.parse(url));
            addView(img);
        }
    }

    public void setImages(List<String> data) {
        String[] urls = (String[]) data.toArray();
        setImages(urls);
    }

    /**
     * 请求图片的时候选择压缩显示
     * @param data  图片地址集合
     * @param inSimpleSize  缩放到长和宽的 inSimpleSize 分之一，大小缩小到平方倍分之一
     */
    public void setImageWithCompress(String[] data, int inSimpleSize) {

        for (String url : data) {
            final ImageView imageView = new ImageView(getContext());
            imageView.setLayoutParams(new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
            HttpRequestImage.getInstance().requestImageWithCompress(url, inSimpleSize, new ImageCallBack() {
                @Override
                public void success(Bitmap bitmap) {
                    imageView.setImageBitmap(bitmap);
                }

                @Override
                public void failure() {

                }
            });
            addView(imageView);
        }
    }

    public void setImagesWithCompress(List<String> data, int inSimpleSize) {
        setImageWithCompress((String[]) data.toArray(), inSimpleSize);
    }

}
