package cn.alien95.alien95library.test;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import cn.alien95.alien95library.R;
import cn.alien95.set.http.HttpCallBack;
import cn.alien95.set.http.request.HttpRequest;

/**
 * Created by llxal on 2015/12/20.
 */
public class ListFragment extends Fragment {

    private TextView content;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container, Bundle savedInstanceState) {
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.fragment_list, null);
        content = (TextView) view.findViewById(R.id.content);
            HttpRequest.getInstance("http://www.baidu.com").get(new HttpCallBack() {
                @Override
                public void error() {

                }

                @Override
                public void success(String info) {
                    content.setText(info);
                }

                @Override
                public void failure(int status, String info) {

                }
            });
        return view;
    }




}

