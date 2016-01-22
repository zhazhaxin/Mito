
package cn.alien95.alien95library.module;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import cn.alien95.alien95library.R;
import cn.alien95.set.widget.Adapter;
import cn.alien95.set.widget.CellView;
import cn.alien95.set.widget.HttpImageView;

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
                "http://img04.sogoucdn.com/app/a/100520093/0e0fd862f51611ae-a90dafa354d6f346-2eb15821bac16a5de96883092146ed10.jpg",
                "http://img03.sogoucdn.com/app/a/100520093/0e0fd862f51611ae-a90dafa354d6f346-34d864f531887a641a149f63ddfad199.jpg",
                "http://img04.sogoucdn.com/app/a/100520093/0e0fd862f51611ae-a90dafa354d6f346-e0a98223db7a0c44924cd50a75dbfc2a.jpg",
                "http://img04.sogoucdn.com/app/a/100520093/0e0fd862f51611ae-a90dafa354d6f346-e9b95f880cdc99b3b9e932817c8efd2b.jpg",
                "http://img03.sogoucdn.com/app/a/100520093/0e0fd862f51611ae-70061ff5f96548be-0a7124d997da6ca9ecf815f8b048a134.jpg",
                "http://img04.sogoucdn.com/app/a/100520093/0e0fd862f51611ae-a90dafa354d6f346-cee9c252ceebb5b6619cfc4a4836181b.jpg",
                "http://img04.sogoucdn.com/app/a/100520093/0e0fd862f51611ae-70061ff5f96548be-2f94ac614942e48b21d9d8cf4272afc8.jpg",
                "http://img02.sogoucdn.com/app/a/100520093/0e0fd862f51611ae-a90dafa354d6f346-40c8601ed85fd017234370240a388bd7.jpg",
                "http://img02.sogoucdn.com/app/a/100520093/0e0fd862f51611ae-70061ff5f96548be-abd4294d2c1f2f85826e3f1ab177f151.jpg",
                "http://img03.sogoucdn.com/app/a/100520093/0e0fd862f51611ae-70061ff5f96548be-ad99aceaad9542aa508afc9bde574b81.jpg",
                "http://img01.sogoucdn.com/app/a/100520093/0e0fd862f51611ae-70061ff5f96548be-305c56682d9e7d683b37f591f4444173.jpg",
                "http://img03.sogoucdn.com/app/a/100520093/0e0fd862f51611ae-70061ff5f96548be-b2903bc30efd27269108c8855da5ffbc.jpg",
                "http://img04.sogoucdn.com/app/a/100520093/0e0fd862f51611ae-70061ff5f96548be-1f5aa92ee715f9ab284b190a81dd117c.jpg",
                "http://img04.sogoucdn.com/app/a/100520093/0e0fd862f51611ae-70061ff5f96548be-cc0116cb6534347ccb6f1e87c8dab026.jpg"
        };
    }

    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container, Bundle savedInstanceState) {
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.fragment_list, null);
        cellView = (CellView) view.findViewById(R.id.cell_view);
        cellView.setBackgroundResource(R.color.colorAccent);
        cellView.setImages(imgUrls);
        return view;
    }

    class CellViewAdapter extends Adapter<String> {

        private HttpImageView httpImageView;

        public CellViewAdapter(Context context) {
            super(context);
        }

        @Override
        public View getView(ViewGroup parent, int position) {
            View item = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_recycler, null);
            httpImageView = (HttpImageView) item.findViewById(R.id.http_image_view);
            httpImageView.setImageUrl(imgUrls[position]);
            return item;
        }

    }


}

