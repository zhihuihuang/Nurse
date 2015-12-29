package com.guc.bureau2.adapter;

import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import com.guc.bureau2.R;
import com.guc.bureau2.domain.CheckRegionDTO;
import com.guc.bureau2.domain.DrugRankRegionDTO;

import java.util.ArrayList;
import java.util.List;

import javax.security.auth.login.LoginException;


public class DrugRankRegionAdapter extends BaseExpandableListAdapter {
    private List<CheckRegionDTO<List<DrugRankRegionDTO>>> data;

    public DrugRankRegionAdapter(List<CheckRegionDTO<List<DrugRankRegionDTO>>> data) {
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
        ViewHolerGroup viewHandler;
        if (convertView == null) {
            viewHandler = new ViewHolerGroup();
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_simple_exlist_item_1, parent, false);
            viewHandler.text1 = (TextView) convertView.findViewById(android.R.id.text1);
            convertView.setTag(viewHandler);
        } else {
            viewHandler = (ViewHolerGroup) convertView.getTag();
        }
        viewHandler.text1.setText(data.get(groupPosition).getItem_name());
        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        ViewHolerChild viewHolerChild;
        if (convertView == null) {
            viewHolerChild = new ViewHolerChild();
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_item_rank_drug, parent, false);
            viewHolerChild.tv_item_name = (TextView) convertView.findViewById(R.id.tv_item_name);
            viewHolerChild.tv_item_format = (TextView) convertView.findViewById(R.id.tv_item_format);
            viewHolerChild.tv_item_unit = (TextView) convertView.findViewById(R.id.tv_item_unit);
            viewHolerChild.tv_item_num = (TextView) convertView.findViewById(R.id.tv_item_num);
            convertView.setTag(viewHolerChild);
        } else {
            viewHolerChild = (ViewHolerChild) convertView.getTag();
        }
        if(childPosition==0||childPosition%2==0){
            convertView.setBackgroundResource(R.color.white);
        }else{
            convertView.setBackgroundResource(R.color.item_back);
        }
        DrugRankRegionDTO dto = data.get(groupPosition).getT().get(childPosition);
        viewHolerChild.tv_item_name.setText(dto.getItem_name());
        viewHolerChild.tv_item_format.setText(dto.getItem_format());
        viewHolerChild.tv_item_unit.setText(dto.getItem_unit());
        viewHolerChild.tv_item_num.setText(dto.getItem_num());
        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }

    class ViewHolerGroup {
        TextView text1;
    }

    class ViewHolerChild {
        TextView tv_item_name;
        TextView tv_item_format;
        TextView tv_item_unit;
        TextView tv_item_num;
    }
}
