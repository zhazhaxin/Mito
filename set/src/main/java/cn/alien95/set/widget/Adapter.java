package cn.alien95.set.widget;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by linlongxin on 2015/12/31.
 */
public abstract class Adapter<T> {

    public static Context context;
    private int count = 0;
    private List<T> data = new ArrayList<>();

    public Adapter(Context context){
        this.context = context;
        count = getCount();
    }

    public abstract View getView(ViewGroup parent, int position);

    public int getCount(){
        return count;
    }

    public void add(T object){
        data.add(object);
        count ++ ;
    }

    public void addAll(T[] objects){
        data.addAll(Arrays.asList(objects));
        count += objects.length;
        notifyDataChanged();
    }

    public void notifyDataChanged(){

    }

}
