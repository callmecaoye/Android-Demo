package com.caoye.fragmentdemo;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

/**
 * Created by admin on 10/18/16.
 */
public class MyFragmentPagerAdapter extends FragmentPagerAdapter {

    //Fragment Object
    private List<Fragment> fgList;

    public MyFragmentPagerAdapter(FragmentManager fm, List<Fragment> fragmentList) {
        super(fm);
        this.fgList = fragmentList;
    }

    @Override
    public int getCount() {
        return fgList.size();
    }

    @Override
    public Fragment getItem(int position) {
        return fgList.get(position);
    }
}
