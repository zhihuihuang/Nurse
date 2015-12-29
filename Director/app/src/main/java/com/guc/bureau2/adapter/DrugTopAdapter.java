package com.guc.bureau2.adapter;

import java.util.List;

import com.guc.bureau2.R;
import com.guc.bureau2.base.BaseActivity;
import com.guc.bureau2.base.GucBaseAdapter;
import com.guc.bureau2.domain.DrugDTO;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class DrugTopAdapter extends GucBaseAdapter {

	private List<DrugDTO> data;

	public DrugTopAdapter(List<DrugDTO> data) {
		this.data = data;
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
		ViewHolder viewHolder ;
		if (convertView == null) {
			viewHolder = new ViewHolder();
			convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_item_drugtopinfo, parent, false);
			viewHolder.drugname_view = (TextView) convertView.findViewById(R.id.drugname);
			viewHolder.specification_view = (TextView) convertView.findViewById(R.id.specification);
			viewHolder.amount_view = (TextView) convertView.findViewById(R.id.amount);
			convertView.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) convertView.getTag();
		}
		DrugDTO dto=data.get(position);
		viewHolder.drugname_view.setText(dto.getDrugname().trim());
		viewHolder.specification_view.setText(dto.getSpecification().trim());
		viewHolder.amount_view.setText(dto.getAmount().trim());
		super.setBackground(convertView,position);
		return convertView;
	}


	class ViewHolder {
		TextView drugname_view;
		TextView specification_view;
		TextView amount_view;
	}

}
