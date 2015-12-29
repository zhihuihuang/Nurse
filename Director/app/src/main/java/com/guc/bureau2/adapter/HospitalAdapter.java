package com.guc.bureau2.adapter;

import java.util.List;

import com.guc.bureau2.base.GucBaseAdapter;
import com.guc.bureau2.domain.HospitalDTO;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class HospitalAdapter extends GucBaseAdapter {

    private List<HospitalDTO> data;
    private Context context;
    private int layout;

    public HospitalAdapter(Context context, List<HospitalDTO> data, int layout) {
        this.context = context;
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
        ViewHandler viewHandler = null;
        if (convertView == null) {
            viewHandler = new ViewHandler();
            convertView = LayoutInflater.from(context).inflate(layout, null);
            viewHandler.org_name_view = (TextView) convertView.findViewById(android.R.id.text1);
            convertView.setTag(viewHandler);
        } else {
            viewHandler = (ViewHandler) convertView.getTag();
        }

        viewHandler.org_name_view.setText(data.get(position).getOrg_name());
        super.setBackground(convertView, position);
        return convertView;
    }

    class ViewHandler {
        TextView org_name_view;
    }
}
