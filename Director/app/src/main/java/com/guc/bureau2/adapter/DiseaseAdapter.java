package com.guc.bureau2.adapter;

import java.util.List;

import com.guc.bureau2.R;
import com.guc.bureau2.base.GucBaseAdapter;
import com.guc.bureau2.domain.DiseaseDTO;
import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class DiseaseAdapter extends GucBaseAdapter {

	private List<DiseaseDTO> mlist;
	private Context mContext;
	private LayoutInflater layoutInflater;

	public DiseaseAdapter(Context context, List<DiseaseDTO> list) {
		this.mContext = context;
		this.mlist = list;
		layoutInflater = LayoutInflater.from(mContext);
	}

	@Override
	public int getCount() {

		return mlist.size();
	}

	@Override
	public Object getItem(int position) {

		return mlist.get(position);
	}

	@Override
	public long getItemId(int position) {

		return position;
	}

	@SuppressLint("InflateParams")
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder viewHolder = null;
		if (convertView == null) {
			viewHolder = new ViewHolder();
			convertView = layoutInflater.inflate(R.layout.layout_item_diseasetopinfo, null);
			viewHolder.diagname_view = (TextView) convertView.findViewById(R.id.diagname);
			viewHolder.amount_view = (TextView) convertView.findViewById(R.id.amount);
			convertView.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) convertView.getTag();
		}
		viewHolder.diagname_view.setText(mlist.get(position).getDiagname().trim());
		viewHolder.amount_view.setText(mlist.get(position).getAmount().trim());
		super.setBackground(convertView,position);
		return convertView;
	}

	class ViewHolder {
		TextView diagname_view;
		TextView amount_view;
	}

}
