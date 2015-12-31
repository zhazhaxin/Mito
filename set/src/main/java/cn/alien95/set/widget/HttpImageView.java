package cn.alien95.set.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.widget.ImageView;

import cn.alien95.set.R;

/**
 * Created by linlongxin on 2015/12/31.
 */
public class HttpImageView extends ImageView {


    public HttpImageView(Context context) {
        super(context);
    }

    public HttpImageView(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public HttpImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(attrs);
    }

    public void init(AttributeSet attributeSet){
        TypedArray typedArray = getContext().obtainStyledAttributes(attributeSet, R.styleable.HttpImageView);
        int inSimpleSize = typedArray.getInteger(R.styleable.,1);
    }

}
