package com.guc.bureau2.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.guc.bureau2.R;
import com.guc.bureau2.base.GucBaseAdapter;
import com.guc.bureau2.domain.DrugOrgDTO;

import java.util.ArrayList;


public class DrugRankOrgAdapter extends GucBaseAdapter {
    private ArrayList<DrugOrgDTO> data;
    private int layout;

    public DrugRankOrgAdapter(ArrayList<DrugOrgDTO> data, int layout) {
        this.data = data;
        this.layout = layout;
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null) {
            viewHolder = new ViewHolder();
            convertView = LayoutInflater.from(parent.getContext()).inflate(layout, parent, false);
            viewHolder.tv_hos_name = (TextView) convertView.findViewById(R.id.tv_hos_name);
            viewHolder.tv_item_name = (TextView) convertView.findViewById(R.id.tv_item_name);
            viewHolder.tv_item_num = (TextView) convertView.findViewById(R.id.tv_item_num);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        DrugOrgDTO dto = data.get(position);
        viewHolder.tv_hos_name.setText(dto.getHos_name());
        viewHolder.tv_item_name.setText(dto.getItem_name());
        viewHolder.tv_item_num.setText(dto.getItem_num());
        super.setBackground(convertView,position);
        return convertView;
    }

    class ViewHolder {
        private TextView tv_hos_name;
        private TextView tv_item_name;
        private TextView tv_item_num;
    }
}
