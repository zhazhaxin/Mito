package cn.alien95.alien95library.test;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import cn.alien95.alien95library.R;
import cn.alien95.set.widget.Adapter;
import cn.alien95.set.widget.CellView;
import cn.alien95.set.widget.HttpImageView;

/**
 * Created by llxal on 2015/12/20.
 */
public class ListFragment extends Fragment {

    private HttpImageView httpImageView;
    private CellView cellView;
    private String[] imgUrls;
    private final String imageUrl = "http://img01.sogoucdn.com/app/a/100520093/0e0fd862f51611ae-70061ff5f96548be-911366d3272119c455bdb8c98dae50ae.jpg";

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
//        httpImageView = (HttpImageView) view.findViewById(R.id.http_image_view);
//        httpImageView.setImageUrl(imageUrl);
        cellView.setAdapter(new MyAdapter(getActivity()));
        return view;
    }

    class MyAdapter extends Adapter<String>{

        public MyAdapter(Context context) {
            super(context);
        }

        @Override
        public View getView(ViewGroup parent, int position) {
            TextView textView = new TextView(getActivity());
            textView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
            textView.setText("CAO");
            textView.setBackgroundResource(R.color.colorAccent);
            return textView;
        }

        @Override
        public int getCount() {
            return 8;
        }
    }


}

