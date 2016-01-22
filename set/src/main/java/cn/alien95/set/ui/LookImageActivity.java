package cn.alien95.set.ui;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import alien95.cn.util.Utils;
import cn.alien95.set.R;
import cn.alien95.set.http.image.HttpRequestImage;
import cn.alien95.set.widget.CellView;
import cn.alien95.set.widget.HttpImageView;

/**
 * Created by linlongxin on 2016/1/22.
 */
public class LookImageActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private ViewPager viewPager;
    private TextView number;
    private String[] data;
    private int position;
    private int dataLength;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_look_image);

        data = getIntent().getStringArrayExtra(CellView.IMAGES_DATA);
        position = getIntent().getIntExtra(CellView.IMAGE_NUM, -1);
        dataLength = data.length;

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        viewPager = (ViewPager) findViewById(R.id.view_pager);
        number = (TextView) findViewById(R.id.number);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        viewPager.setAdapter(new ImageAdapter());
        viewPager.setCurrentItem(position);
        number.setText(position + "/" + dataLength);

    }

    class ImageAdapter extends PagerAdapter {

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            HttpImageView img = new HttpImageView(LookImageActivity.this);
            img.setLayoutParams(new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            img.setScaleType(ImageView.ScaleType.CENTER_CROP);
            img.setAdjustViewBounds(true);
            img.setImageUrl(data[position]);
            container.addView(img);
            return img;
        }


        @Override
        public int getCount() {
            return data.length;
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            number.setText(viewPager.getCurrentItem() + 1 + "/" + dataLength);
            return view == object;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_look_image, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        } else if (item.getItemId() == R.id.save) {
            String url = data[viewPager.getCurrentItem()];
            if (HttpRequestImage.getInstance().loadImageFromMemory(url) != null) {
                saveBitmap(HttpRequestImage.getInstance().loadImageFromMemory(url), url);
            } else {
                saveBitmap(HttpRequestImage.getInstance().loadImageFromDisk(url), url);
            }

        }
        return true;
    }


    public void saveBitmap(final Bitmap bitmap, String picName) {
        final File f = new File(Utils.getCacheDir(), Utils.MD5(picName) + ".jpg");
        if (f.exists()) {
            Utils.Toast("文件已存在");
            return;
        }
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    FileOutputStream out = new FileOutputStream(f);
                    bitmap.compress(Bitmap.CompressFormat.PNG, 100, out);
                    out.flush();
                    out.close();
                    Utils.Toast("已保存在APP的缓存目录");
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                    Utils.Log("fuck:" + e.getMessage());
                } catch (IOException e) {
                    e.printStackTrace();
                    Utils.Log("fuck:" + e.getMessage());
                }
            }
        }).start();

    }

}
