package cn.alien95.set.widget;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.Serializable;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;

import alien95.cn.util.MessageNotify;
import alien95.cn.util.Utils;
import cn.alien95.set.http.image.HttpRequestImage;
import cn.alien95.set.http.image.ImageCallBack;
import cn.alien95.set.ui.LookImageActivity;

/**
 * Created by linlongxin on 2015/12/28.
 */
public class CellView extends FrameLayout {

    private static final String TAG = "CellView";
    private int spacing;
    private int childWidth;
    private Adapter adapter;
    private List<String> imagesData;

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

        /**
         * 这里这样写是为了解决在ScrollView下面高度的问题。
         */
        int measureMode = MeasureSpec.getMode(widthMeasureSpec);
        int width = MeasureSpec.getSize(widthMeasureSpec);
        int childCount = getChildCount();
        if (childCount == 1) {
            heightMeasureSpec = MeasureSpec.makeMeasureSpec((int) (childWidth * 2.5), measureMode);
        } else if (childCount == 2) {
            heightMeasureSpec = MeasureSpec.makeMeasureSpec((int) (childWidth * 1.5 + spacing), measureMode);
        } else {
            if (childCount % 3 == 0) {
                heightMeasureSpec = MeasureSpec.makeMeasureSpec(childWidth * childCount / 3 + spacing * (childCount / 3 + 1), measureMode);
            } else
                heightMeasureSpec = MeasureSpec.makeMeasureSpec(childWidth * (childCount / 3 + 1) + spacing * (childCount / 3 + 2), measureMode);
        }
        int height = MeasureSpec.getSize(heightMeasureSpec);
        setMeasuredDimension(width, height);
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
     * 添加adapter中所有的view,这里必须是public，因为抵用了MessageNotify.getInstance().registerEvent()，不然其他类不能调用
     */
    public void addViews() {
        if (adapter.getCount() > 9) {
            for (int i = 0; i < 8; i++) {
                addView(adapter.getView(this, i));
            }
            setEndView(adapter.getView(this, 8), adapter.getCount());
        } else
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
        setImages(Arrays.asList(data));
    }

    /**
     * 获取一个HttpImageView
     *
     * @param url
     * @return
     */
    public HttpImageView getHttpImageView(String url, final int position) {
        HttpImageView img = new HttpImageView(getContext());
        img.setLayoutParams(new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        img.setScaleType(ImageView.ScaleType.CENTER_CROP);
        img.setImageUrl(url);
        img.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), LookImageActivity.class);
                intent.putExtra(LookImageActivity.IMAGE_NUM, position);
                intent.putExtra(LookImageActivity.IMAGES_DATA_LIST, (Serializable) imagesData);
                getContext().startActivity(intent);
            }
        });
        return img;
    }

    /**
     * 同上
     *
     * @param data
     */
    public void setImages(List<String> data) {
        setImages(data);
        if (data.size() > 9) {
            for (int i = 0; i < 8; i++) {
                addView(getHttpImageView(data.get(i), i));
            }
            setEndView(getHttpImageView(data.get(8), 8), data.size());
        }
        for (int i = 0; i < data.size(); i++) {
            addView(getHttpImageView(data.get(i), i));
        }
        imagesData = data;
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

    /**
     * 设置最后一个View
     */
    private void setEndView(View view, int count) {
        FrameLayout frameLayout = new FrameLayout(getContext());
        frameLayout.setLayoutParams(new LayoutParams(childWidth, childWidth));
        TextView textView = new TextView(getContext());
        textView.setLayoutParams(new LayoutParams(childWidth, childWidth));
        textView.setTextSize(28);
        textView.setText(count - 9 + ">");
        textView.setTextColor(Color.parseColor("#ffffff"));
        textView.setBackgroundColor(Color.parseColor("#33000000"));
        textView.setGravity(Gravity.CENTER);
        frameLayout.addView(view);
        frameLayout.addView(textView);
        addView(frameLayout);
        frameLayout.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), LookImageActivity.class);
                intent.putExtra(LookImageActivity.IMAGE_NUM, 8);
                intent.putExtra(LookImageActivity.IMAGES_DATA_LIST, (Serializable) imagesData);
                getContext().startActivity(intent);
            }
        });
    }
}
