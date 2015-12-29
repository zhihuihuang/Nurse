package com.guc.bureau2.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import com.guc.bureau2.R;
import com.guc.bureau2.domain.CheckRegionDTO;
import com.guc.bureau2.domain.DiseaseRankRegionDTO;

import java.util.ArrayList;


public class DiseaseRankRegionAdapter extends BaseExpandableListAdapter {
    ArrayList<CheckRegionDTO<ArrayList<DiseaseRankRegionDTO>>> data;

    public DiseaseRankRegionAdapter(ArrayList<CheckRegionDTO<ArrayList<DiseaseRankRegionDTO>>> data) {
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
        ViewHolderGroup viewHolderGroup;
        if (convertView == null) {
            viewHolderGroup = new ViewHolderGroup();
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_simple_exlist_item_1, parent, false);
            viewHolderGroup.text1 = (TextView) convertView.findViewById(android.R.id.text1);
            convertView.setTag(viewHolderGroup);
        } else {
            viewHolderGroup = (ViewHolderGroup) convertView.getTag();
        }
        viewHolderGroup.text1.setText(data.get(groupPosition).getItem_name());
        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        ViewHolderChild viewHolderChild;
        if (convertView == null) {
            viewHolderChild = new ViewHolderChild();
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_simple_exlist_item_child_2, parent, false);
            viewHolderChild.text1 = (TextView) convertView.findViewById(android.R.id.text1);
            viewHolderChild.text2 = (TextView) convertView.findViewById(android.R.id.text2);
            convertView.setTag(viewHolderChild);
        } else {
            viewHolderChild = (ViewHolderChild) convertView.getTag();
        }
        DiseaseRankRegionDTO dto = data.get(groupPosition).getT().get(childPosition);
        viewHolderChild.text1.setText(dto.getItem_name().trim());
        viewHolderChild.text2.setText(dto.getItem_value().trim());
        if (childPosition % 2 == 0) {
            convertView.setBackgroundResource(R.color.white);
        } else {
            convertView.setBackgroundResource(R.color.item_back);
        }
        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }

    class ViewHolderGroup {
        private TextView text1;
    }

    class ViewHolderChild {
        private TextView text1;
        private TextView text2;
    }
}
