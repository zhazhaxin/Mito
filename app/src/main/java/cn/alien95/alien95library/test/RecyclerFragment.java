package cn.alien95.alien95library.test;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import cn.alien95.alien95library.R;
import cn.alien95.alien95library.bean.Image;
import cn.alien95.alien95library.bean.ImageRespond;
import cn.alien95.alien95library.model.ImageModel;
import cn.alien95.set.http.image.HttpRequestImage;
import cn.alien95.set.http.image.ImageCallBack;
import cn.alien95.set.http.request.HttpCallBack;
import cn.alien95.set.recyclerview.BaseViewHolder;
import cn.alien95.set.recyclerview.RecyclerAdapter;

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
        ImageModel.getImageForNet(2, new HttpCallBack() {
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
        recyclerView.setAdapter(adapter);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        return view;

    }

    class MyAdapter extends RecyclerAdapter<Image> {

        public MyAdapter(List<Image> data) {
            super(data);
        }

        @Override
        public BaseViewHolder<Image> onCreateViewHolder(ViewGroup parent, int viewType) {
            return new MyViewHolder(getActivity(), R.layout.item_list);
        }
    }


    class MyViewHolder extends BaseViewHolder<Image> {

        private ImageView image;

        public MyViewHolder(Context context, int layoutId) {
            super(context, layoutId);
            image = (ImageView) itemView.findViewById(R.id.image);
        }

        @Override
        public void setData(Image object) {
            super.setData(object);
            HttpRequestImage.getInstance().requestImageWithCompress(object.getImg(),
                    3, new ImageCallBack() {
                        @Override
                        public void success(Bitmap bitmap) {
                            image.setImageBitmap(bitmap);
                        }

                        @Override
                        public void failure() {

                        }
                    });

        }
    }

}
