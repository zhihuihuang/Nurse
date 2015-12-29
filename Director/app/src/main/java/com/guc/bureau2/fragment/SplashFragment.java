package com.guc.bureau2.fragment;

import com.guc.bureau2.R;
import com.guc.bureau2.base.BaseFragment;

import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class SplashFragment extends BaseFragment {
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		return initView(inflater, container, R.layout.activity_splash);
	}
	@Override
	public void onStart() {
		super.onStart();
	}

	@Override
	public void onResume() {
		super.onResume();
		Handler handler=new Handler();
		handler.postDelayed(new Runnable() {

			@Override
			public void run() {
				mActivity.replace("loginfragment", LoginFragment.newInstance(), false);
			}
		}, 1500);
	}

	@Override
	public void onClick(View v) {

	}

	@Override
	protected void initData() {

	}


	@Override
	protected void setListeners() {

	}

	@Override
	protected void initWidget(View view) {

	}
	public static Fragment newInstance(){
		return new SplashFragment();
	}
}
