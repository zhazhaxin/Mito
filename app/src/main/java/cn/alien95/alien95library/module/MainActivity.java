package cn.alien95.alien95library.module;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import alien95.cn.cellview.ui.LookImageActivity;
import alien95.cn.http.request.callback.HttpCallBack;
import alien95.cn.http.util.Utils;
import alien95.cn.http.view.HttpImageView;
import alien95.cn.refreshrecyclerview.adapter.RecyclerAdapter;
import alien95.cn.refreshrecyclerview.callback.Action;
import alien95.cn.refreshrecyclerview.view.BaseViewHolder;
import alien95.cn.refreshrecyclerview.view.RefreshRecyclerView;
import cn.alien95.alien95library.R;
import cn.alien95.alien95library.model.ImageModel;

public class MainActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private RefreshRecyclerView refreshRecyclerView;
    private MyAdapter adapter;
    private List<String> data = new ArrayList<>();
    private List<String> picUrlData = new ArrayList<>();
    private Intent intent;
    private String searchWord = "风景";
    private int pager = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        adapter = new MyAdapter(this);
        refreshRecyclerView = (RefreshRecyclerView) findViewById(R.id.refresh_recycler_view);
        refreshRecyclerView.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));

        refreshRecyclerView.setAdapter(adapter);

        intent = new Intent(MainActivity.this, LookImageActivity.class);

        getData("风景", 1, false);

        refreshRecyclerView.refresh(new Action() {
            @Override
            public void onAction() {
                getData(searchWord, 1, true);
            }
        });

        refreshRecyclerView.loadMore(new Action() {
            @Override
            public void onAction() {
                getData(searchWord, pager, false);
            }
        });

    }


    public void getData(String searchWord, final int pagerNum, final boolean isRefresh) {
        ImageModel.getImageForNet(searchWord, pagerNum, new HttpCallBack() {
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
                    if (isRefresh) {
                        adapter.clear();
                        pager = 0;
                    } else {
                        pager++;
                    }
                    adapter.addAll(data);
                    refreshRecyclerView.dismissRefresh();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void failure(int status, String info) {
                super.failure(status, info);
                if (status != 999) {
                    Toast.makeText(MainActivity.this, "网络错误", Toast.LENGTH_SHORT).show();
                }
                refreshRecyclerView.dismissRefresh();
            }
        });
    }

    class MyAdapter extends RecyclerAdapter<String> {

        public MyAdapter(Context context) {
            super(context);
        }

        @Override
        public BaseViewHolder<String> onCreateBaseViewHolder(ViewGroup parent, int viewType) {
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
                searchWord = query;
                ImageModel.getImageForNet(query, 1, new HttpCallBack() {
                    @Override
                    public void success(String info) {
                        try {
                            JSONObject jsonObject = new JSONObject(info);
                            JSONArray jsonArray = jsonObject.getJSONArray("items");
                            if (jsonArray.length() == 0) {
                                Utils.Toast("我好方，搜不到你想要的图片");
                                return;
                            }
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
