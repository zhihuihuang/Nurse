package com.guc.bureau2.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.guc.bureau2.R;
import com.guc.bureau2.base.GucBaseAdapter;
import com.guc.bureau2.domain.ArchivesInDTO;

import java.util.List;

public class ArchivesTabChildAdapter extends GucBaseAdapter {
    private List<ArchivesInDTO> data;

    public ArchivesTabChildAdapter(List<ArchivesInDTO> data) {
        this.data = data;
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolderChile viewHolder;
        if (convertView == null) {
            viewHolder = new ViewHolderChile();
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_item_archives_three, null);
            viewHolder.text1 = (TextView) convertView.findViewById(android.R.id.text1);
            viewHolder.text2 = (TextView) convertView.findViewById(android.R.id.text2);
            viewHolder.text3 = (TextView) convertView.findViewById(R.id.text3);
            convertView.setTag(viewHolder);
        }
        viewHolder = (ViewHolderChile) convertView.getTag();
        ArchivesInDTO dto = data.get(position);
        viewHolder.text1.setText(dto.getItem_value());
        viewHolder.text2.setText(dto.getItem_total());
        viewHolder.text3.setText(dto.getItem_ratio());
//        if(convertView!=null&&position%2==0){
//            convertView.setBackgroundResource(R.color.gainsboro);
//        }
//        else{
//            convertView.setBackgroundResource(R.color.white);
//        }
        return convertView;
    }

    class ViewHolderChile {
        private TextView text1;
        private TextView text2;
        private TextView text3;
    }
}
