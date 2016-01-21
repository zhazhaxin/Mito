package cn.alien95.set.widget;

import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.util.AttributeSet;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import cn.alien95.set.http.image.HttpRequestImage;
import cn.alien95.set.http.image.ImageCallBack;
import cn.alien95.set.util.MessageNotify;
import cn.alien95.set.util.Utils;

/**
 * Created by linlongxin on 2015/12/28.
 */
public class CellView extends FrameLayout {

    private static final String TAG = "CellView";
    private int spacing;
    private int childWidth;
    private List<HttpImageView> items = new ArrayList<>();
    private Adapter adapter;

    public CellView(Context context) {
        super(context);
    }

    public CellView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CellView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        spacing = Utils.dip2px(4);
        childWidth = (Utils.getScreenWidth() - Utils.dip2px(64) - Utils.dip2px(16)) / 3;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        measureChildren(widthMeasureSpec, heightMeasureSpec);
        setMeasuredDimension(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        int childCount = getChildCount();
        if (childCount == 1) {
            getChildAt(0).layout(spacing, spacing, (int) (childWidth * 2.5), (int) (childWidth * 2.5));
        } else if (childCount == 2) {
            getChildAt(0).layout(spacing, spacing, (int) (childWidth * 1.5 + spacing), (int) (childWidth * 1.5 + spacing));
            getChildAt(1).layout((int) (childWidth * 1.5 + spacing * 2), spacing, childWidth * 3 + spacing * 2, (int) (childWidth * 1.5 + spacing));

        } else {
            for (int i = 0; i < (childCount > 9 ? 9 : childCount); i++) {
                getChildAt(i).layout(spacing * (i % 3 + 1) + childWidth * (i % 3), i / 3 * childWidth + spacing * (i / 3 + 1),
                        spacing * (i % 3 + 1) + childWidth * (i % 3 + 1), spacing * (i / 3 + 1) + (i / 3 + 1) * childWidth);
            }
        }
    }

    /**
     * 设置adapter，同时设置注册MessageNotify
     * adapter继承cn.alien95.set.widget.Adapter
     *
     * @param adapter
     */
    public void setAdapter(Adapter adapter) {
        this.adapter = adapter;
        Method method = null;
        try {
            method = CellView.class.getMethod("addViews");
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
        MessageNotify.getInstance().registerEvent(this, method);
        addViews();
    }

    /**
     * 添加adapter中所有的view
     */
    public void addViews() {
        for (int i = 0; i < adapter.getCount(); i++) {
            addView(adapter.getView(this, i));
        }
    }


    /**
     * 当数据是死数据时：推荐使用此方法
     *
     * @param data 数据集合
     */
    public void setImages(String[] data) {
        for (int i = 0; i < data.length; i++) {
            HttpImageView img = new HttpImageView(getContext());
            img.setLayoutParams(new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
            img.setImageURI(Uri.parse(data[i]));
            addView(img);
            items.add(i, img);
        }
    }

    /**
     * 同上
     *
     * @param data
     */
    public void setImages(List<String> data) {
        String[] urls = (String[]) data.toArray();
        setImages(urls);
    }

    /**
     * 请求图片的时候选择压缩显示
     *
     * @param data         图片地址集合
     * @param inSimpleSize 缩放到长和宽的 inSimpleSize 分之一，大小缩小到平方倍分之一
     */
    public void setImagesWithCompress(String[] data, int inSimpleSize) {

        for (int i = 0; i < data.length; i++) {
            final ImageView imageView = new ImageView(getContext());
            imageView.setLayoutParams(new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
            HttpRequestImage.getInstance().requestImageWithCompress(data[i], inSimpleSize, new ImageCallBack() {
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

    /**
     * 同上
     *
     * @param data
     * @param inSimpleSize
     */
    public void setImagesWithCompress(List<String> data, int inSimpleSize) {
        setImagesWithCompress((String[]) data.toArray(), inSimpleSize);
    }

}
