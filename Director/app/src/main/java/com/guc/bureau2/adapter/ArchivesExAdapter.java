package com.guc.bureau2.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import com.guc.bureau2.R;
import com.guc.bureau2.domain.ArchivesInDTO;
import com.guc.bureau2.domain.ArchivesOutDTO;
import com.guc.bureau2.domain.CheckRegionDTO;

import java.util.ArrayList;
import java.util.List;


public class ArchivesExAdapter extends BaseExpandableListAdapter {
    private int layout;
    private ArrayList<CheckRegionDTO<ArrayList<ArchivesInDTO>>> data;

    public ArchivesExAdapter(int layout, ArrayList<CheckRegionDTO<ArrayList<ArchivesInDTO>>> data) {
        this.layout = layout;
        this.data = data;
    }

    @Override
    public int getGroupCount() {
        return data.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return data.get(groupPosition).getT().size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return null;
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return null;
    }

    @Override
    public long getGroupId(int groupPosition) {
        return 0;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return 0;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null) {
            viewHolder = new ViewHolder();
            convertView = LayoutInflater.from(parent.getContext()).inflate(layout, null);
            viewHolder.text1 = (TextView) convertView.findViewById(android.R.id.text1);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.text1.setText(data.get(groupPosition).getItem_name());
        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        ViewHolderChile viewHolder;
        if (convertView == null) {
            viewHolder = new ViewHolderChile();
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_item_archives_three, parent,false);
            viewHolder.text1 = (TextView) convertView.findViewById(android.R.id.text1);
            viewHolder.text2 = (TextView) convertView.findViewById(android.R.id.text2);
            viewHolder.text3 = (TextView) convertView.findViewById(R.id.text3);
            convertView.setTag(viewHolder);
        }
        viewHolder = (ViewHolderChile) convertView.getTag();
        ArchivesInDTO dto = data.get(groupPosition).getT().get(childPosition);
        viewHolder.text1.setText(dto.getItem_value());
        viewHolder.text2.setText(dto.getItem_total());
        viewHolder.text3.setText(dto.getItem_ratio());
        if(childPosition%2==0){
            convertView.setBackgroundResource(R.color.item_back);
        }else{
            convertView.setBackgroundResource(R.color.white);
        }
        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }

    class ViewHolder {
        private TextView text1;
    }

    class ViewHolderChile {
        private TextView text1;
        private TextView text2;
        private TextView text3;
    }
}
