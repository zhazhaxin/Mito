package cn.alien95.set.recyclerview;

import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by llxal on 2015/12/19.
 */
public abstract class RecyclerAdapter<T> extends RecyclerView.Adapter<BaseViewHolder<T>> {

    private List<T> data = new ArrayList<>();

    public RecyclerAdapter() {
    }

    public RecyclerAdapter(List<T> data) {
        this.data = data;
    }

    @Override
    public abstract BaseViewHolder<T> onCreateViewHolder(ViewGroup parent, int viewType);

    @Override
    public void onBindViewHolder(BaseViewHolder<T> holder, int position) {
        holder.setData(data.get(position));
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public void add(T object) {
        data.add(object);
        notifyDataSetChanged();
    }

    public void insert(T object, int position) {
        data.add(position, object);
        notifyDataSetChanged();
    }

    public void addAll(List<T> list) {
        data.addAll(list);
        notifyDataSetChanged();
    }

    public void addAll(T[] objects) {
        data.addAll(Arrays.asList(objects));
        notifyDataSetChanged();
    }

    public void replace(T object, int position) {
        data.set(position, object);
        notifyDataSetChanged();
    }

    public void delete(T object) {
        data.remove(object);
        notifyDataSetChanged();
    }

    public void delete(int position) {
        data.remove(position);
        notifyDataSetChanged();
    }

    public void clear() {
        data.clear();
        notifyDataSetChanged();
    }
}
