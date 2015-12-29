package com.guc.bureau2.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.guc.bureau2.R;
import com.guc.bureau2.base.GucBaseAdapter;
import com.guc.bureau2.domain.ArchivesDTO;

import java.util.List;

public class ArchivesAdapter extends GucBaseAdapter {
    private List<ArchivesDTO> data;
    private int layout;
    private Context context;

    public ArchivesAdapter(List<ArchivesDTO> data, int layout, Context context) {
        this.data = data;
        this.layout = layout;
        this.context = context;
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
            convertView = LayoutInflater.from(context).inflate(layout, null);
            viewHolder = new ViewHolder();
            viewHolder.text1 = (TextView) convertView.findViewById(R.id.text1);
            viewHolder.text2 = (TextView) convertView.findViewById(R.id.text2);
            viewHolder.text3 = (TextView) convertView.findViewById(R.id.text3);
            viewHolder.text4 = (TextView) convertView.findViewById(R.id.text4);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        ArchivesDTO dto = data.get(position);
        viewHolder.text1.setText(dto.getItem_name());
        viewHolder.text2.setText(dto.getItem_value().trim());
        viewHolder.text3.setText(dto.getItem_total().trim());
        viewHolder.text4.setText(dto.getItem_ratio().trim());
        super.setBackground(convertView, position);
        return convertView;
    }

    class ViewHolder {
        private TextView text1;
        private TextView text2;
        private TextView text3;
        private TextView text4;
    }
}
