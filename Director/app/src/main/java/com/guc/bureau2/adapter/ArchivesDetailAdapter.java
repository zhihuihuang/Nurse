package com.guc.bureau2.adapter;

import java.util.ArrayList;
import java.util.Map;

import com.guc.bureau2.R;
import com.guc.bureau2.base.GucBaseAdapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class ArchivesDetailAdapter extends GucBaseAdapter {


    private ArrayList<Map<String, String>> mList;
    private Context mcontext;
    private LayoutInflater layoutInflater;

    public ArchivesDetailAdapter(Context context, ArrayList<Map<String, String>> list) {
        this.mcontext = context;
        this.mList = list;
        layoutInflater = LayoutInflater.from(mcontext);
    }

    @Override
    public int getCount() {

        return mList.size();
    }

    @Override
    public Object getItem(int position) {

        return mList.get(position);
    }

    @Override
    public long getItemId(int position) {

        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null) {
            viewHolder = new ViewHolder();
            convertView = layoutInflater.inflate(R.layout.layout_item_archives_detail, parent, false);
            viewHolder.item_type_view = (TextView) convertView.findViewById(R.id.item_type);
            viewHolder.item_name1_view = (TextView) convertView.findViewById(R.id.item_name1);
            viewHolder.item_name2_view = (TextView) convertView.findViewById(R.id.item_name2);
            viewHolder.item_name3_view = (TextView) convertView.findViewById(R.id.item_name3);
            viewHolder.item_result1_view = (TextView) convertView.findViewById(R.id.item_result1);
            viewHolder.item_result2_view = (TextView) convertView.findViewById(R.id.item_result2);
            viewHolder.item_result3_view = (TextView) convertView.findViewById(R.id.item_result3);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.item_type_view.setText(mList.get(position).get("item_type"));
        if (mList.get(position).get("item_name1") != null) {
            viewHolder.item_name1_view.setVisibility(View.VISIBLE);
            viewHolder.item_name1_view.setText(mList.get(position).get("item_name1"));
        } else {
            viewHolder.item_name1_view.setVisibility(View.GONE);
        }
        viewHolder.item_name2_view.setText(mList.get(position).get("item_name2"));
        viewHolder.item_name3_view.setText(mList.get(position).get("item_name3"));
        if (mList.get(position).get("item_result1") != null) {
            viewHolder.item_result1_view.setVisibility(View.VISIBLE);
            viewHolder.item_result1_view.setText(mList.get(position).get("item_result1"));
        } else {
            viewHolder.item_result1_view.setVisibility(View.GONE);
        }
        viewHolder.item_result2_view.setText(mList.get(position).get("item_result2"));
        viewHolder.item_result3_view.setText(mList.get(position).get("item_result3"));
        return convertView;
    }

    class ViewHolder {
        TextView item_type_view;
        TextView item_name1_view;
        TextView item_name2_view;
        TextView item_name3_view;
        TextView item_result1_view;
        TextView item_result2_view;
        TextView item_result3_view;
    }

}
