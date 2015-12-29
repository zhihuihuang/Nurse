package com.guc.bureau2.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.guc.bureau2.R;
import com.guc.bureau2.base.GucBaseAdapter;
import com.guc.bureau2.domain.HospitalizStatisticsDTO;

import java.util.List;

public class HospitalizeAdapter extends GucBaseAdapter {
    private List<HospitalizStatisticsDTO> data;
    private int layout;

    public HospitalizeAdapter(List<HospitalizStatisticsDTO> data, int layout) {
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
        ViewHolder viewHoler;
        if (convertView == null) {
            viewHoler = new ViewHolder();
            convertView = LayoutInflater.from(parent.getContext()).inflate(layout, null);
            viewHoler.tv_name = (TextView) convertView.findViewById(R.id.tv_name);
            viewHoler.tv_mzrs = (TextView) convertView.findViewById(R.id.tv_mzrs);
            viewHoler.tv_rycs = (TextView) convertView.findViewById(R.id.tv_rycs);
            viewHoler.tv_cyrs = (TextView) convertView.findViewById(R.id.tv_cyrs);
            viewHoler.tv_mzfy = (TextView) convertView.findViewById(R.id.tv_mzfy);
            viewHoler.tv_zyfy = (TextView) convertView.findViewById(R.id.tv_zyfy);
            viewHoler.tv_jy = (TextView) convertView.findViewById(R.id.tv_jy);
            viewHoler.tv_mzrjfy = (TextView) convertView.findViewById(R.id.tv_mzrjfy);
            viewHoler.tv_zyrjfy = (TextView) convertView.findViewById(R.id.tv_zyrjfy);
            convertView.setTag(viewHoler);
        } else {
            viewHoler = (ViewHolder) convertView.getTag();
        }
        HospitalizStatisticsDTO dto = data.get(position);
        viewHoler.tv_name.setText(dto.getItem_name());
        viewHoler.tv_mzrs.setText(dto.getOutp_num());
        viewHoler.tv_rycs.setText(dto.getInp_in_num());
        viewHoler.tv_cyrs.setText(dto.getInp_out_num());
        viewHoler.tv_mzfy.setText(dto.getOutp_fee());
        viewHoler.tv_zyfy.setText(dto.getInp_fee());
        viewHoler.tv_jy.setText(dto.getBase_drug_fee());
        viewHoler.tv_mzrjfy.setText(dto.getAvg_outp_fee());
        viewHoler.tv_zyrjfy.setText(dto.getAvg_inp_fee());
        super.setBackground(convertView,position);
        return convertView;
    }

    class ViewHolder {
        private TextView tv_name;
        private TextView tv_mzrs;
        private TextView tv_rycs;
        private TextView tv_cyrs;
        private TextView tv_mzfy;
        private TextView tv_zyfy;
        private TextView tv_jy;
        private TextView tv_mzrjfy;
        private TextView tv_zyrjfy;
    }
}
