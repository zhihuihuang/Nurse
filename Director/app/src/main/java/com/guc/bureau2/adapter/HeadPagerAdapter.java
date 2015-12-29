package com.guc.bureau2.adapter;

import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.ArrayList;

public class HeadPagerAdapter extends PagerAdapter {
    private ArrayList<ImageView> views;
    private int[] head_img;

    public HeadPagerAdapter(ArrayList<ImageView> views, int[] head_img) {
        this.views = views;
        this.head_img = head_img;
    }

    @Override
    public int getCount() {
        return views.size()*200;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        int iv_position=position%5;
        if (position % 2 == 0) {
            views.get(iv_position).setImageResource(head_img[1]);
        } else {
            views.get(iv_position).setImageResource(head_img[0]);
        }
        container.addView(views.get(iv_position));
        return views.get(iv_position);
    }


    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        ImageView iv = views.get(position%5);
        container.removeView(iv);
        iv.setImageBitmap(null);
    }
}
