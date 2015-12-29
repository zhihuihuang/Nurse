package com.guc.bureau2.fragment;

import java.util.ArrayList;
import java.util.List;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.VolleyError;
import com.guc.bureau2.R;
import com.guc.bureau2.adapter.CheckOrgAdapter;
import com.guc.bureau2.base.BaseFragment;
import com.guc.bureau2.domain.CheckOrgDTO;
import com.guc.bureau2.net.GucNetEnginClient;
import com.guc.bureau2.utils.ToastUtils;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ListView;

public class CheckOrgFragment extends BaseFragment {
	private ListView mListView;
	private List<CheckOrgDTO> data = new ArrayList<>();
	private CheckOrgAdapter adapter;
	private LinearLayout layout_null;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		return initView(inflater, container, R.layout.fragment_list);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.ll_back:
			mActivity.popBackStack(1);
			break;
		default:
			break;
		}
	}

	@Override
	protected void initData() {
		String org_code = getArguments().getString("org_code");
		String org_name = getArguments().getString("org_name");
		controlBar(org_name, R.string.back, true, false);
		getData(org_code);
		adapter = new CheckOrgAdapter(R.layout.layout_simple_list_item_2, data);
		mListView.setAdapter(adapter);
	}

	private void getData(String org_code) {
		showDialog(R.string.is_loading_please_wait);
		GucNetEnginClient.getCheckOrg(org_code, new Listener<String>() {
			@Override
			public void onResponse(String response) {
				dismiss();
				JSONObject jsonObject = JSON.parseObject(response);
				JSONObject obj_res = jsonObject.getJSONObject("GetAssessReportResult");
				String resultErrInfo = obj_res.getString("resultErrInfo");
				if (resultErrInfo != null) {
					ToastUtils.showLong(mActivity, resultErrInfo);
					mActivity.popBackStack(1);
					return;
				}
				JSONArray array = obj_res.getJSONArray("assessItemList");
				if (array.size() <= 0) {
					layout_null.setVisibility(View.VISIBLE);
				} else {
					data.addAll(JSON.parseArray(array.toJSONString(), CheckOrgDTO.class));
					adapter.notifyDataSetChanged();
				}
			}
		}, new ErrorListener() {

			@Override
			public void onErrorResponse(VolleyError error) {
				dismiss();
			}
		});
	}

	@Override
	protected void setListeners() {
		ll_back.setOnClickListener(this);
	}

	@Override
	protected void initWidget(View view) {
		mListView = (ListView) view.findViewById(R.id.listView);
		layout_null = (LinearLayout) view.findViewById(R.id.layout_null);
	}

	public static Fragment newInstance(String org_code, String org_name) {
		CheckOrgFragment archivesFragment = new CheckOrgFragment();
		Bundle bundle = new Bundle();
		bundle.putString("org_code", org_code);
		bundle.putString("org_name", org_name);
		archivesFragment.setArguments(bundle);
		return archivesFragment;
	}
}
