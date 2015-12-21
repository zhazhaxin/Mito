package cn.alien95.alien95library;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import cn.alien95.alien95library.bean.User;
import cn.alien95.set.listview.ListAdapter;
import cn.alien95.set.listview.ViewHolder;

/**
 * Created by llxal on 2015/12/20.
 */
public class ListFragment extends Fragment {

    private ListView listView;
    private List<User> users;
    private MyAdapter myAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        users = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            users.add(new User(R.mipmap.ic_launcher, "android"));
        }
        myAdapter = new MyAdapter(getActivity(), R.layout.item_list, users);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.fragment_list, null);
        listView = (ListView) view.findViewById(R.id.list_view);
        listView.setAdapter(myAdapter);

        return view;
    }


    class MyAdapter extends ListAdapter<User> {

        private ImageView face;
        private TextView name;

        public MyAdapter(Context context, int layoutId, List<User> data) {
            super(context, layoutId, data);
        }

        @Override
        public void initView(ViewHolder viewHolder, User object) {
            face = (ImageView) viewHolder.getViewById(R.id.face);
            name = (TextView) viewHolder.getViewById(R.id.name);
            face.setImageResource(object.getFace());
            name.setText(object.getName());
        }


    }

}

