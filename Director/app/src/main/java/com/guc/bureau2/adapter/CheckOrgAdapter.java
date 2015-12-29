package com.guc.bureau2.adapter;

import java.util.List;

import com.guc.bureau2.base.GucBaseAdapter;
import com.guc.bureau2.domain.CheckOrgDTO;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class CheckOrgAdapter extends GucBaseAdapter {

    private int layout;
    private List<CheckOrgDTO> data;

    public CheckOrgAdapter(int layout, List<CheckOrgDTO> data) {
        this.layout = layout;
        this.data = data;
    }

    @Override
    public int getCount() {

        return data.size();
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
        ViewHolder viewHolder;
        if (convertView == null) {
            viewHolder = new ViewHolder();
            convertView = LayoutInflater.from(parent.getContext()).inflate(layout, parent, false);
            viewHolder.text1 = (TextView) convertView.findViewById(android.R.id.text1);
            viewHolder.text2 = (TextView) convertView.findViewById(android.R.id.text2);
            convertView.setTag(viewHolder);
        }
        viewHolder = (ViewHolder) convertView.getTag();
        viewHolder.text1.setText(data.get(position).getAssess_item_name());
        viewHolder.text2.setText(data.get(position).getAssess_result());
        return convertView;
    }

    class ViewHolder {
        TextView text1;
        TextView text2;
    }
}
