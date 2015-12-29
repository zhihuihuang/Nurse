package com.guc.bureau2.base;

import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.guc.bureau2.R;


public class GucBaseAdapter extends BaseAdapter {
    @Override
    public int getCount() {
        return 0;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        return null;
    }
    public void setBackground(View view ,int position){
        if(position%2==0){
            view.setBackgroundResource(R.color.item_back);
        }else{
            view.setBackgroundResource(R.color.white);
        }
    }

}
