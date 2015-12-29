package com.guc.bureau2.fragment;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.android.volley.Response.Listener;
import com.guc.bureau2.R;
import com.guc.bureau2.base.BaseFragment;
import com.guc.bureau2.listener.OrgListener;
import com.guc.bureau2.net.DefaultErrorListener;
import com.guc.bureau2.net.GucNetEnginClient;
import com.guc.bureau2.utils.ToastUtils;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

public class RegisterFragment extends BaseFragment {
	private TextView tv_organization;
	private TextView tv_register;
	private EditText input_moblie;
	private EditText input_password;
	private String org_id="";
	private String orgname = "";

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		return initView(inflater, container, R.layout.fragment_register);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.ll_back:
			mActivity.popBackStack(1);
			break;
		case R.id.tv_organization:
			mActivity.replace("organizationfragment", new OrganizationFragment(new OrgListener() {

				@Override
				public void setCode(String code, String org_name) {
					org_id = code;
					orgname = org_name;
				}
			}), true);
			break;
		case R.id.tv_register:
			checkData();
			break;
		default:
			break;
		}
	}

	@Override
	protected void initData() {
		controlBar(R.string.register, R.string.back, true, false);
		tv_organization.setText(orgname);
	}

	private void checkData() {
		String moblie = input_moblie.getText().toString().trim();
		String password = input_password.getText().toString().trim();
		if (TextUtils.isEmpty(org_id)) {
			ToastUtils.showShort(mActivity, R.string.org_is_not_null);
			return;
		}
		if (TextUtils.isEmpty(moblie)) {
			ToastUtils.showShort(mActivity, R.string.account_not_null);
			return;
		}
		if (TextUtils.isEmpty(password)) {
			ToastUtils.showShort(mActivity, R.string.psssword_not_null);
			return;
		}
		register(moblie, password);

	}

	private void register(String mobile, String password) {
		GucNetEnginClient.register(org_id, mobile, password, new Listener<String>() {

			@Override
			public void onResponse(String response) {
				JSONObject jsonObject = JSON.parseObject(response);
				JSONObject resResult = jsonObject.getJSONObject("RegisterResult");
				String result = resResult.getString("result");
				String errInfo=resResult.getString("errInfo");
				if (result.equals("false")) {
					ToastUtils.showShort(mActivity.getApplicationContext(), errInfo);
				} else {
					result.equals("true");
					ToastUtils.showShort(mActivity.getApplicationContext(), R.string.register_success);
					mActivity.popBackStack(1);
				}
			}
		}, new DefaultErrorListener());
	}

	@Override
	protected void setListeners() {
		ll_back.setOnClickListener(this);
		tv_organization.setOnClickListener(this);
		tv_register.setOnClickListener(this);
	}

	@Override
	protected void initWidget(View view) {
		tv_organization = (TextView) view.findViewById(R.id.tv_organization);
		input_moblie = (EditText) view.findViewById(R.id.input_moblie);
		input_password = (EditText) view.findViewById(R.id.input_password);
		tv_register = (TextView) view.findViewById(R.id.tv_register);
	}

	public static Fragment newInstance(String id, String orgname) {
		RegisterFragment fragment = new RegisterFragment();
		Bundle bundle = new Bundle();
		bundle.putString("id", id);
		bundle.putString("orgname", orgname);
		fragment.setArguments(bundle);
		return fragment;
	}

	public static Fragment newInstance() {
		return new RegisterFragment();
	}

}
