package cn.alien95.alien95library.module;

import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import cn.alien95.util.Utils;


/**
 * Created by linlongxin on 2016/5/1.
 */
public class DivideItemDecoration extends RecyclerView.ItemDecoration {

    private int divede;

    public DivideItemDecoration() {
        divede = Utils.dip2px(4);
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        if (parent.getChildLayoutPosition(view) == 0 || parent.getChildLayoutPosition(view) == 1) {
            outRect.top = divede;
        }
        outRect.right = divede;
        outRect.left = divede;
        outRect.bottom = divede;

    }
}
