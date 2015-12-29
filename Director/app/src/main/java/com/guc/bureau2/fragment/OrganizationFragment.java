package com.guc.bureau2.fragment;

import java.util.ArrayList;
import java.util.List;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.android.volley.Response.Listener;
import com.guc.bureau2.R;
import com.guc.bureau2.adapter.RegisterOrganizationItemAdapter;
import com.guc.bureau2.base.BaseFragment;
import com.guc.bureau2.listener.OrgListener;
import com.guc.bureau2.domain.OrganizationDTO;
import com.guc.bureau2.net.DefaultErrorListener;
import com.guc.bureau2.net.GucNetEnginClient;
import com.guc.bureau2.utils.PingYinUtil;
import com.guc.bureau2.utils.ToastUtils;

import android.app.Activity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

public class OrganizationFragment extends BaseFragment {
	private ListView organization_listView;
	private List<OrganizationDTO> list = new ArrayList<OrganizationDTO>();;
	private RegisterOrganizationItemAdapter adapter;
	private String[] ids;
	private String[] orgnames;
	private String[] pingyins;
	private EditText input_search;
	private TextView tv_null;
	private ImageView iv_clear;
	private OrgListener carrData;

	public OrganizationFragment(OrgListener carrData) {

		this.carrData = carrData;

	}

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);

	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		return initView(inflater, container, R.layout.fragment_organization);
	}

	@Override
	public void onClick(View v) {

	}

	@Override
	protected void initData() {
		GucNetEnginClient.getOrganization(  new Listener<String>() {

			@Override
			public void onResponse(String response) {
				JSONObject jsonObject = JSON.parseObject(response);
				JSONObject obj_res = jsonObject.getJSONObject("getOrganizationResult");
				String errInfo=obj_res.getString("errInfo");
				if(errInfo==null){
					JSONArray jsonArray = obj_res.getJSONArray("organizationList");
					list.addAll(JSON.parseArray(jsonArray.toJSONString(), OrganizationDTO.class));
					initIds();
					adapter = new RegisterOrganizationItemAdapter(mActivity, ids, orgnames, pingyins);
					organization_listView.setAdapter(adapter);
				}else{
					ToastUtils.showLong(mActivity,errInfo);
				}
			}
		}, new DefaultErrorListener());

	}

	private void initIds() {
		ids = new String[list.size()];
		orgnames = new String[list.size()];
		pingyins = new String[list.size()];
		for (int i = 0; i < list.size(); i++) {
			ids[i] = list.get(i).getId();
			orgnames[i] = list.get(i).getOrgname();
			pingyins[i] = PingYinUtil.getPinYinHeadChar(list.get(i).getOrgname());
		}
	}

	@Override
	protected void setListeners() {
		input_search.addTextChangedListener(new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
				adapter.searchTextChanged(input_search.getText().toString(), tv_null, iv_clear);
				adapter.notifyDataSetChanged();
			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count, int after) {

			}

			@Override
			public void afterTextChanged(Editable s) {

			}
		});

		iv_clear.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				iv_clear.setVisibility(View.GONE);
				input_search.setText("");
			}
		});
		organization_listView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				String _id = list.get(position).getId();
				String orgname = list.get(position).getOrgname();
				mActivity.popBackStack(1);
				carrData.setCode(_id, orgname);
			}
		});
	}

	@Override
	protected void initWidget(View view) {
		organization_listView = (ListView) view.findViewById(R.id.organization_listView);
		input_search = (EditText) view.findViewById(R.id.input_search);
		tv_null = (TextView) view.findViewById(R.id.tv_null);
		iv_clear = (ImageView) view.findViewById(R.id.iv_clear);
	}
}
