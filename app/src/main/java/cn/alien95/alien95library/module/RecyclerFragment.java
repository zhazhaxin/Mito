package cn.alien95.alien95library.module;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import cn.alien95.alien95library.R;
import cn.alien95.alien95library.bean.Image;
import cn.alien95.alien95library.bean.ImageRespond;
import cn.alien95.alien95library.model.ImageModel;
import cn.alien95.set.http.request.HttpCallBack;
import cn.alien95.set.recyclerview.BaseViewHolder;
import cn.alien95.set.recyclerview.RecyclerAdapter;
import cn.alien95.set.widget.HttpImageView;

/**
 * Created by llxal on 2015/12/21.
 */
public class RecyclerFragment extends Fragment {

    private List<Image> images;
    private RecyclerView recyclerView;
    private RecyclerAdapter<Image> adapter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        images = new ArrayList<>();
        adapter = new MyAdapter(images);
        ImageModel.getImageForNet(1, new HttpCallBack() {
            @Override
            public void success(String info) {
                Gson gson = new Gson();
                ImageRespond respond = gson.fromJson(info, ImageRespond.class);
                adapter.addAll(respond.getTngou());
            }
        });
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.fragment_recycler, null);
        recyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
//        recyclerView.setAdapter(adapter);
//        recyclerView.setItemAnimator(new DefaultItemAnimator());
        return view;

    }

    class MyAdapter extends RecyclerAdapter<Image> {

        public MyAdapter(List<Image> data) {
            super(data);
        }

        @Override
        public BaseViewHolder<Image> onCreateViewHolder(ViewGroup parent, int viewType) {
            return new MyViewHolder(getActivity(), R.layout.item_recycler);
        }
    }


    class MyViewHolder extends BaseViewHolder<Image> {

        private HttpImageView image;

        public MyViewHolder(Context context, int layoutId) {
            super(context, layoutId);
            image = (HttpImageView) itemView.findViewById(R.id.http_image_view);
        }

        @Override
        public void setData(Image object) {
            super.setData(object);
            image.setImageUrlWithCompress(object.getImg(), 3);
        }
    }

}
