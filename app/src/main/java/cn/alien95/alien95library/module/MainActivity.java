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
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

import alien95.cn.cellview.ui.LookImageActivity;
import alien95.cn.util.Utils;
import cn.alien95.alien95library.R;
import cn.alien95.alien95library.model.ImageModel;
import cn.alien95.alien95library.model.bean.Image;
import cn.alien95.view.RefreshRecyclerView;
import cn.alien95.view.adapter.BaseViewHolder;
import cn.alien95.view.adapter.RecyclerAdapter;
import cn.alien95.view.callback.Action;
import rx.functions.Action1;

public class MainActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private RefreshRecyclerView refreshRecyclerView;
    private MyAdapter adapter;
    private List<Image> data = new ArrayList<>();
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

        intent = new Intent(MainActivity.this, LookImageActivity.class);

        getData("风景", 0, false);

        refreshRecyclerView.refresh(new Action() {
            @Override
            public void onAction() {
                getData(searchWord, 0, true);
                refreshRecyclerView.dismissRefresh();
            }
        });

        refreshRecyclerView.loadMore(new Action() {
            @Override
            public void onAction() {
                getData(searchWord, page, false);
            }
        });


    }


    public void getData(final String searchWord, final int pagerNum, final boolean isRefresh) {

        ImageModel.getImagesFromNet(searchWord, pagerNum)
                .subscribe(new Action1<Image[]>() {
                    @Override
                    public void call(Image[] images) {
                        if (isRefresh) {
                            data.clear();
                            adapter.clear();
                            picUrlData.clear();
                            page = 0;
                        } else {
                            page++;
                        }
                        for (Image image : images) {
                            picUrlData.add(image.getPic_url());
                            data.add(image);
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
                getData(searchWord = query, 1, true);
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
            private ImageView imageView;

            public MyViewHolder(ViewGroup parent, int layoutId) {
                super(parent, layoutId);
                imageView = (ImageView) itemView.findViewById(R.id.image);
            }

            @Override
            public void setData(final Image image) {
                super.setData(image);

                imageView.setLayoutParams(new StaggeredGridLayoutManager.LayoutParams(width, ((int) (float) image.getHeight() * width / image.getWidth())));

                Glide.with(MainActivity.this)
                        .load(image.getThumbUrl())
                        .error(R.drawable.holder)
                        .centerCrop()
                        .into(imageView);
                imageView.invalidate();

                imageView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        intent.putExtra(LookImageActivity.IMAGE_NUM, data.indexOf(image));
                        intent.putExtra(LookImageActivity.IMAGES_DATA_LIST, picUrlData);
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
