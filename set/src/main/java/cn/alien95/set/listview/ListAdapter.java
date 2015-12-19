package cn.alien95.set.listview;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.Arrays;
import java.util.List;

/**
 * Created by llxal on 2015/12/19.
 */
public class ListAdapter<T> extends BaseAdapter {

    private List<T> data;
    private Context mContext;
    private int layoutId;

    public ListAdapter(Context context,int layoutId,List<T> data){
        this.data = data;
        mContext = context;
        this.layoutId = layoutId;
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int position) {
        return data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        return ViewHolder.getViewHlder(mContext,convertView, layoutId).getConvertView();
    }

    public void add(T object){
        data.add(object);
        notifyDataSetChanged();
    }

    public void insert(T object,int position){
        data.add(position,object);
        notifyDataSetChanged();
    }

    public void addAll(List<T> list){
        data.addAll(list);
        notifyDataSetChanged();
    }

    public void addAll(T[] objects){
        data.addAll(Arrays.asList(objects));
        notifyDataSetChanged();
    }

    public void replace(T object,int position){
        data.set(position,object);
        notifyDataSetChanged();
    }

    public void delete(T object){
        data.remove(object);
        notifyDataSetChanged();
    }

    public void delete(int position){
        data.remove(position);
        notifyDataSetChanged();
    }

}
