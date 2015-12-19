package cn.alien95.set.listview;

import android.content.Context;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;

/**
 * Created by llxal on 2015/12/19.
 */
public class ViewHolder {

    private int layoutId;
    private Context mContext;
    private static View mConvertView;
    private SparseArray<View> cache;

    public ViewHolder(Context context, int layoutId){
        cache = new SparseArray<>();
        mContext = context;
        this.layoutId = layoutId;
        mConvertView = LayoutInflater.from(context).inflate(layoutId,null);
        mConvertView.setTag(this);
    }

    public static ViewHolder getViewHlder(Context context, View convertView, int layoutId){
        if(convertView == null){
            return new ViewHolder(context,layoutId);
        }else {
            mConvertView = convertView;  //必须加上这句话，不然item显示有bug
            return (ViewHolder) convertView.getTag();
        }
    }

    public View getViewById(int id){
        View view = cache.get(id);
        if(view == null){
            view = mConvertView.findViewById(id);
            cache.put(id,view);
        }
        return view;
    }

    public View getConvertView(){
        return mConvertView;
    }
}
