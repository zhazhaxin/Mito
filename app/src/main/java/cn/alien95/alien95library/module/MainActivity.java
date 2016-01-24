package cn.alien95.alien95library.module;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import cn.alien95.alien95library.R;
import cn.alien95.alien95library.model.ImageModel;
import cn.alien95.set.http.request.HttpCallBack;
import cn.alien95.set.recyclerview.BaseViewHolder;
import cn.alien95.set.recyclerview.RecyclerAdapter;
import cn.alien95.set.ui.LookImageActivity;
import cn.alien95.set.widget.HttpImageView;

public class MainActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private RecyclerView recyclerView;
    private MyAdapter adapter;
    private List<String> data = new ArrayList<>();
    private List<String> picUrlData = new ArrayList<>();
    private Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        adapter = new MyAdapter();
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));

        recyclerView.setAdapter(adapter);

        intent = new Intent(MainActivity.this, LookImageActivity.class);

        ImageModel.getImageForNet("漂亮", 1, new HttpCallBack() {
            @Override
            public void success(String info) {
                try {
                    JSONObject jsonObject = new JSONObject(info);
                    JSONArray jsonArray = jsonObject.getJSONArray("items");
                    for (int i = 0; i < jsonArray.length(); i++) {
                        data.add((String) jsonArray.getJSONObject(i).get("thumbUrl"));
                        picUrlData.add((String) jsonArray.getJSONObject(i).get("pic_url"));
                    }
                    intent.putExtra(LookImageActivity.IMAGES_DATA_LIST, (Serializable) picUrlData);
                    adapter.addAll(data);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        });

    }

    class MyAdapter extends RecyclerAdapter<String> {

        @Override
        public BaseViewHolder<String> onCreateViewHolder(ViewGroup parent, int viewType) {
            return new MyViewHolder(parent.getContext(), R.layout.item_recycler);
        }

        class MyViewHolder extends BaseViewHolder<String> {

            private HttpImageView imageView;

            public MyViewHolder(Context context, int layoutId) {
                super(context, layoutId);
                imageView = (HttpImageView) itemView.findViewById(R.id.http_image_view);
            }

            @Override
            public void setData(final String object) {
                super.setData(object);
                imageView.setImageUrl(object);
                itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        intent.putExtra(LookImageActivity.IMAGE_NUM, data.indexOf(object));
                        startActivity(intent);
                    }
                });
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.search_view, menu);
        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        MenuItem mSearchMenuItem = menu.findItem(R.id.search_view);
        SearchView searchView = (SearchView) MenuItemCompat.getActionView(mSearchMenuItem);
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        searchView.setQueryHint("搜索");
        searchView.setIconifiedByDefault(true);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                ImageModel.getImageForNet(query, 1, new HttpCallBack() {
                    @Override
                    public void success(String info) {
                        try {
                            JSONObject jsonObject = new JSONObject(info);
                            JSONArray jsonArray = jsonObject.getJSONArray("items");
                            data.clear();
                            picUrlData.clear();
                            for (int i = 0; i < jsonArray.length(); i++) {
                                data.add((String) jsonArray.getJSONObject(i).get("thumbUrl"));
                                picUrlData.add((String) jsonArray.getJSONObject(i).get("pic_url"));
                            }
                            adapter.clear();
                            adapter.addAll(data);
                            intent.putExtra(LookImageActivity.IMAGES_DATA_LIST, (Serializable) picUrlData);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                });
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
        return true;
    }
}
