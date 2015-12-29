package com.guc.bureau2.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import com.guc.bureau2.R;
import com.guc.bureau2.domain.CheckOrgDTO;
import com.guc.bureau2.domain.CheckRegionDTO;

import java.util.List;


public class CheckRegionAdapter extends BaseExpandableListAdapter {

    List<CheckRegionDTO<List<CheckOrgDTO>>> data;

    public CheckRegionAdapter(List<CheckRegionDTO<List<CheckOrgDTO>>> data) {
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
        ViewHandler viewHandler;
        if (convertView == null) {
            viewHandler = new ViewHandler();
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_simple_exlist_item_1, parent, false);
            viewHandler.org_name_view = (TextView) convertView.findViewById(android.R.id.text1);
            convertView.setTag(viewHandler);
        } else {
            viewHandler = (ViewHandler) convertView.getTag();
        }
        viewHandler.org_name_view.setText(data.get(groupPosition).getItem_name());
        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null) {
            viewHolder = new ViewHolder();
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_simple_exlist_item_2, parent, false);
            viewHolder.text1 = (TextView) convertView.findViewById(android.R.id.text1);
            viewHolder.text2 = (TextView) convertView.findViewById(android.R.id.text2);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.text1.setText(data.get(groupPosition).getT().get(childPosition).getAssess_item_name());
        viewHolder.text2.setText(data.get(groupPosition).getT().get(childPosition).getAssess_result());
        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }

    class ViewHolder {
        TextView text1;
        TextView text2;
    }

    //
//    @Override
//    public int getCount() {
//        return data.size();
//    }
//
//
//    @Override
//    public View getView(int position, View convertView, ViewGroup parent) {
//        ViewHandler viewHandler = null;
//        if (convertView == null) {
//            viewHandler = new ViewHandler();
//            convertView = LayoutInflater.from(parent.getContext()).inflate(layout, null);
//            viewHandler.org_name_view = (TextView) convertView.findViewById(android.R.id.text1);
//            convertView.setTag(viewHandler);
//        } else {
//            viewHandler = (ViewHandler) convertView.getTag();
//        }
//
//        viewHandler.org_name_view.setText(data.get(position).getItem_name());
//
//        return convertView;
//    }
//
    class ViewHandler {
        TextView org_name_view;
    }
}
