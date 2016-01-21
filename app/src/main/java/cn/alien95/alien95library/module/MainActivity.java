package cn.alien95.alien95library.module;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import cn.alien95.alien95library.R;
import cn.alien95.set.viewpager.BaseFragmentPagerAdapter;

public class MainActivity extends AppCompatActivity {

    private ViewPager viewPager;
    private BaseFragmentPagerAdapter pagerAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        viewPager = (ViewPager) findViewById(R.id.view_pager);
        pagerAdapter = new BaseFragmentPagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(pagerAdapter);

        pagerAdapter.add(new ListFragment());
        pagerAdapter.add(new RecyclerFragment());
    }
}
