package cn.alien95.alien95library.test;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import cn.alien95.alien95library.R;
import cn.alien95.set.widget.CellView;

/**
 * Created by llxal on 2015/12/20.
 */
public class ListFragment extends Fragment {

    private CellView cellView;
    private String[] imgUrls;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        imgUrls = new String[]{
                "http://cdn2.infoqstatic.com/statics_s2_20151224-0209/resource/articles/docker-executable-images/zh/smallimage/logo-Small.jpg",
                "http://cdn2.infoqstatic.com/statics_s2_20151224-0209/resource/articles/Java-PERMGEN-Removed/zh/smallimage/logo2.jpg",
                "http://cdn2.infoqstatic.com/statics_s2_20151224-0209/resource/articles/Java-PERMGEN-Removed/zh/smallimage/logo2.jpg",
                "http://cdn4.infoqstatic.com/statics_s2_20151224-0209/resource/articles/Scala-Design/zh/smallimage/HiRes%20(2).jpg",
                "http://cdn4.infoqstatic.com/statics_s2_20151224-0209/resource/articles/javaagent-illustrated/zh/smallimage/java.jpeg",
                "http://cdn2.infoqstatic.com/statics_s2_20151224-0209/resource/articles/why-not-go/zh/smallimage/Figure.jpg",
                "http://cdn4.infoqstatic.com/statics_s2_20151224-0209/resource/articles/websocket-desktop-agility-web-applications/zh/smallimage/logo-cloud.jpg",
                "http://cdn3.infoqstatic.com/statics_s2_20151224-0209/resource/articles/the-multithreading-of-netty-cases/zh/smallimage/logo-tosca.jpg",
                "http://cdn4.infoqstatic.com/statics_s2_20151224-0209/resource/articles/Scala-Design/zh/smallimage/HiRes%20(2).jpg",
                "http://cdn4.infoqstatic.com/statics_s2_20151224-0209/resource/articles/javaagent-illustrated/zh/smallimage/java.jpeg"
        };
    }

    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container, Bundle savedInstanceState) {
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.fragment_list, null);
        cellView = (CellView) view.findViewById(R.id.cell_view);
        cellView.setImageWithCompress(imgUrls, 2);
        return view;
    }


}

