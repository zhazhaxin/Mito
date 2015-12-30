package cn.alien95.alien95library.test;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.gson.Gson;

import cn.alien95.alien95library.R;
import cn.alien95.alien95library.bean.ImageRespond;
import cn.alien95.alien95library.config.API;
import cn.alien95.set.http.request.HttpCallBack;
import cn.alien95.set.http.request.HttpRequest;
import cn.alien95.set.widget.CellView;

/**
 * Created by llxal on 2015/12/20.
 */
public class ListFragment extends Fragment {

    private CellView cellView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container, Bundle savedInstanceState) {
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.fragment_list, null);
        cellView = (CellView) view.findViewById(R.id.cell_view);
            HttpRequest.getInstance().get(API.getImage,new HttpCallBack() {
                @Override
                public void error() {

                }

                @Override
                public void success(String info) {
                    Gson gson = new Gson();
                    ImageRespond respond = gson.fromJson(info,ImageRespond.class);
//                    cellView.setImages(respond.getTngou());
                }

                @Override
                public void failure(int status, String info) {

                }
            });
        return view;
    }




}

