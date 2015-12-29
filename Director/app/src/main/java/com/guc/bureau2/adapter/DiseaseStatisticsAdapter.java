package com.guc.bureau2.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.guc.bureau2.R;
import com.guc.bureau2.base.GucBaseAdapter;
import com.guc.bureau2.domain.DiseaseRankRegionDTO;

import java.util.List;


public class DiseaseStatisticsAdapter extends GucBaseAdapter {
    private List<DiseaseRankRegionDTO> data;
    private int layout;

    public DiseaseStatisticsAdapter(List<DiseaseRankRegionDTO> data, int layout) {
        this.data = data;
        this.layout = layout;
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
            convertView = LayoutInflater.from(parent.getContext()).inflate(layout, null);
            viewHolder.tv_name = (TextView) convertView.findViewById(R.id.tv_name);
            viewHolder.tv_value = (TextView) convertView.findViewById(R.id.tv_value);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        DiseaseRankRegionDTO dto = data.get(position);
        viewHolder.tv_name.setText(dto.getItem_name());
        viewHolder.tv_value.setText(dto.getItem_value());
        return convertView;
    }

    class ViewHolder {
        private TextView tv_name;
        private TextView tv_value;
    }
}
