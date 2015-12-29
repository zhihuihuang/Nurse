package com.guc.bureau2.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.guc.bureau2.R;
import com.guc.bureau2.fragment.ImageFragment;

import java.util.List;


public class ArchivesPageAdapter extends FragmentPagerAdapter {

    public ArchivesPageAdapter(FragmentManager fm, List<Fragment> fragments) {
        super(fm);
        this.fragments = fragments;
    }

    private List<Fragment> fragments;

    @Override
    public Fragment getItem(int position) {
        return fragments.get(position);
    }

    @Override
    public int getCount() {
        return fragments.size();
    }

}
