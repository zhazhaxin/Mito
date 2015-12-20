package cn.alien95.alien95library;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.util.List;

import cn.alien95.set.listview.ListAdapter;
import cn.alien95.set.listview.ViewHolder;

/**
 * Created by llxal on 2015/12/20.
 */
public class ListFragment extends Fragment {

    private ListView listView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.fragment_list,null);
        listView = (ListView) view.findViewById(R.id.list_view);
        return view;
    }


    class MyAdapter extends ListAdapter<String>{

        public MyAdapter(Context context, int layoutId, List<String> data) {
            super(context, layoutId, data);
        }

        @Override
        public void initView(View view) {

        }
    }

    class MyViewHolder extends ViewHolder{

        public MyViewHolder(Context context, int layoutId) {
            super(context, layoutId);
        }

    }
}

