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
import android.view.inputmethod.InputMethodManager;

import java.util.ArrayList;

import cn.alien95.alien95library.R;
import cn.alien95.alien95library.model.ImageModel;
import cn.alien95.alien95library.model.bean.Image;
import cn.alien95.resthttp.view.HttpImageView;
import cn.alien95.util.Utils;
import cn.alien95.view.RefreshRecyclerView;
import cn.alien95.view.adapter.BaseViewHolder;
import cn.alien95.view.adapter.RecyclerAdapter;
import cn.alien95.view.callback.Action;
import cn.lemon.multi.ui.ViewImageActivity;
import rx.functions.Action1;

public class MainActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private RefreshRecyclerView refreshRecyclerView;
    private MyAdapter adapter;
    private ArrayList<String> picUrlData = new ArrayList<>();
    private Intent intent;
    private String searchWord = "风景";
    private int page = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        refreshRecyclerView = (RefreshRecyclerView) findViewById(R.id.refresh_recycler_view);
        refreshRecyclerView.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
        refreshRecyclerView.addItemDecoration(new DivideItemDecoration());

        adapter = new MyAdapter(this);
        refreshRecyclerView.setAdapter(adapter);

        intent = new Intent(MainActivity.this, ViewImageActivity.class);

        getData(searchWord, 0);
        refreshRecyclerView.refresh(new Action() {
            @Override
            public void onAction() {
                getData(searchWord, 0);
                refreshRecyclerView.dismissRefresh();
            }
        });

        refreshRecyclerView.loadMore(new Action() {
            @Override
            public void onAction() {
                getData(searchWord, page);
            }
        });

    }

    public void getData(final String searchWord, final int pagerNum) {

        Utils.Log("pager : " + pagerNum);
        ImageModel.getImagesFromNet(searchWord, pagerNum)
                .subscribe(new Action1<Image[]>() {
                    @Override
                    public void call(Image[] images) {
                        if (pagerNum == 0) {
                            adapter.clear();
                            picUrlData.clear();
                            page = 1;
                        } else {
                            page++;
                        }
                        for (Image image : images) {
                            picUrlData.add(image.getPic_url());
                        }
                        adapter.addAll(images);
                    }
                });

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
                closeInputMethod();
                getData(searchWord = query, 0);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });

        return true;
    }


    /**
     * adapter
     */
    class MyAdapter extends RecyclerAdapter<Image> {

        public MyAdapter(Context context) {
            super(context);
        }

        @Override
        public BaseViewHolder<Image> onCreateBaseViewHolder(ViewGroup parent, int viewType) {

            return new MyViewHolder(parent, R.layout.item);
        }

        class MyViewHolder extends BaseViewHolder<Image> {

            private int width = Utils.getScreenWidth() / 2;
            private HttpImageView imageView;

            public MyViewHolder(ViewGroup parent, int layoutId) {
                super(parent, layoutId);
                imageView = (HttpImageView) itemView.findViewById(R.id.image);
            }

            @Override
            public void setData(final Image image) {
                super.setData(image);

                imageView.setLayoutParams(new StaggeredGridLayoutManager.LayoutParams(width, ((int) (float) image.getHeight() * width / image.getWidth())));
                imageView.setLoadImageId(R.drawable.holder);
                imageView.setImageUrl(image.getThumbUrl());
                imageView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        intent.putExtra(ViewImageActivity.IMAGE_NUM, getLayoutPosition());
                        intent.putExtra(ViewImageActivity.IMAGES_DATA_LIST, picUrlData);
                        startActivity(intent);
                    }
                });

            }
        }

    }


    public void closeInputMethod() {
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(MainActivity.this.getCurrentFocus().getWindowToken(),
                InputMethodManager.HIDE_NOT_ALWAYS);
    }

}
