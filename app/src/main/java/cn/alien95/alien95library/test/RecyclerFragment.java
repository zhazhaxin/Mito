package cn.alien95.alien95library.test;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import cn.alien95.alien95library.R;
import cn.alien95.alien95library.bean.User;
import cn.alien95.set.recyclerview.BaseViewHolder;
import cn.alien95.set.recyclerview.RecyclerAdapter;

/**
 * Created by llxal on 2015/12/21.
 */
public class RecyclerFragment extends Fragment {

    private List<User> users;
    private RecyclerView recyclerView;
    private RecyclerAdapter<User> adapter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        users = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            users.add(new User(R.mipmap.ic_launcher, "android"));
        }
        adapter = new MyAdapter(users);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.fragment_recycler,null);
        recyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(adapter);
        return view;

    }

    class MyAdapter extends RecyclerAdapter<User>{

        public MyAdapter(List<User> data) {
            super(data);
        }

        @Override
        public BaseViewHolder<User> onCreateViewHolder(ViewGroup parent, int viewType) {
            return new MyViewHolder(getActivity(),R.layout.item_list);
        }
    }


    class MyViewHolder extends BaseViewHolder<User>{

        private TextView name;
        private ImageView face;

        public MyViewHolder(Context context, int layoutId) {
            super(context, layoutId);

            name = (TextView) itemView.findViewById(R.id.name);
            face = (ImageView) itemView.findViewById(R.id.face);
        }

        @Override
        public void setData(User object) {
            super.setData(object);
            name.setText(object.getName());
            face.setImageResource(object.getFace());
        }
    }

}
