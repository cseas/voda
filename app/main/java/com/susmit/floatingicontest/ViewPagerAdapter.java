package com.susmit.floatingicontest;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

public class ViewPagerAdapter extends FragmentPagerAdapter {

    FragmentManager fm;
    public ViewPagerAdapter(FragmentManager fm) {
        super(fm);
        this.fm = fm;
    }

    @Override
    public Fragment getItem(int position) {
        AdFragment frag = new AdFragment(position);
        return frag;
    }

    @Override
    public int getCount() {
        return 6;
    }
}
