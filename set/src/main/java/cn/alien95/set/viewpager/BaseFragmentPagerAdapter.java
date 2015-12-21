package cn.alien95.set.viewpager;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by llxal on 2015/12/21.
 */
public class BaseFragmentPagerAdapter extends FragmentStatePagerAdapter {

    private List<Fragment> fragments;

    public BaseFragmentPagerAdapter(FragmentManager fm) {
        super(fm);
        fragments = new ArrayList<>();
    }

    @Override
    public Fragment getItem(int position) {
        return fragments.get(position);
    }

    @Override
    public int getCount() {
        return fragments.size();
    }

    public void add(Fragment object){
        fragments.add(object);
        notifyDataSetChanged();
    }

    public void insert(Fragment object,int position){
        fragments.add(position,object);
        notifyDataSetChanged();
    }

    public void addAll(List<Fragment> list){
        fragments.addAll(list);
        notifyDataSetChanged();
    }

    public void addAll(Fragment[] objects){
        fragments.addAll(Arrays.asList(objects));
        notifyDataSetChanged();
    }

    public void replace(Fragment object,int position){
        fragments.set(position,object);
        notifyDataSetChanged();
    }

    public void delete(Fragment object){
        fragments.remove(object);
        notifyDataSetChanged();
    }

    public void delete(int position){
        fragments.remove(position);
        notifyDataSetChanged();
    }

}
