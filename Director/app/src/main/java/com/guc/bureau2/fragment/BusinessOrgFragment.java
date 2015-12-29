package com.guc.bureau2.fragment;

import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.VolleyError;
import com.guc.bureau2.R;
import com.guc.bureau2.base.BaseFragment;
import com.guc.bureau2.domain.InComeRegionDTO;
import com.guc.bureau2.net.GucNetEnginClient;
import com.guc.bureau2.utils.ToastUtils;

import java.util.ArrayList;
import java.util.List;

public class BusinessOrgFragment extends BaseFragment {
    private ArrayList<InComeRegionDTO> data = new ArrayList<>();
    private boolean isLoad = false;
    private PopupWindow popupWindow;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return initView(inflater, container, R.layout.fragment_archives_org);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ll_back:
                mActivity.popBackStack(1);
                break;
            case R.id.tv_graph:
                popupWindow.dismiss();
                replace("businessorgchatfragment", BusinessOrgChatFragment.newInstance(data));
                break;
            case R.id.tv_table:
                popupWindow.dismiss();
                replace("businessorgtablefragment", BusinessOrgTableFragment.newInstance(data));
                break;
            case R.id.right_layout:
                popWindows(right_layout);
                break;
            default:
                break;
        }
    }

    public void replace(String tag, Fragment fragment) {
        getChildFragmentManager().beginTransaction().replace(R.id.fragment_content, fragment, tag).commitAllowingStateLoss();
    }

    @Override
    protected void initData() {
        Bundle bundle = getArguments();
        String code = bundle.getString("code");
        String type = bundle.getString("type");
        String name = bundle.getString("name");
        controlBar(name, R.string.back, true, true);
        if (!isLoad) {
            getInComeOrg(code, type);
            isLoad = true;
        }
    }

    public void popWindows(View parent) {
        View view = LayoutInflater.from(mActivity).inflate(R.layout.layout_list, null);
        popupWindow = new PopupWindow(view, (int) getResources().getDimension(R.dimen.y100), (int) getResources().getDimension(R.dimen.y60));
        popupWindow.setFocusable(true);
        popupWindow.setOutsideTouchable(true);
        popupWindow.setBackgroundDrawable(new BitmapDrawable());
        popupWindow.showAsDropDown(parent, 0, 0);
        TextView tv_graph = (TextView) view.findViewById(R.id.tv_graph);
        TextView tv_table = (TextView) view.findViewById(R.id.tv_table);
        tv_graph.setOnClickListener(this);
        tv_table.setOnClickListener(this);
    }

    @Override
    protected void setListeners() {
        ll_back.setOnClickListener(this);
        right_layout.setOnClickListener(this);
    }

    private void getInComeOrg(String code, String type) {
        mProgressDialog.setMessage(getResources().getString(R.string.is_loading_please_wait));
        mProgressDialog.show();
        GucNetEnginClient.getInComeOrg(code, type, new Listener<String>() {

            @Override
            public void onResponse(String response) {
                mProgressDialog.dismiss();
                JSONObject jsonObject = JSON.parseObject(response);
                JSONObject obj_res = jsonObject.getJSONObject("getHospitalBusinessDataResult");
                String resultErrInfo = obj_res.getString("resultErrInfo");
                if (resultErrInfo != null) {
                    ToastUtils.showLong(mActivity, resultErrInfo);
                    return;
                }
                JSONArray array = obj_res.getJSONArray("list");
                if (array.size() <= 0) {
                    ToastUtils.showLong(mActivity, R.string.not_data);
                    return;
                }
                List<InComeRegionDTO> temp = JSON.parseArray(array.toJSONString(), InComeRegionDTO.class);
                data.addAll(temp);
                replace("businessorgtablefragment", BusinessOrgTableFragment.newInstance(data));
            }
        }, new ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                mProgressDialog.dismiss();
            }
        });
    }

    @Override
    protected void initWidget(View view) {
    }

    public static Fragment newInstance(int type) {
        BusinessOrgFragment comeInfoFragmen = new BusinessOrgFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("type", type);
        comeInfoFragmen.setArguments(bundle);
        return comeInfoFragmen;
    }

    public static Fragment newInstance(String code, String name, String type) {
        BusinessOrgFragment comeInfoFragmen = new BusinessOrgFragment();
        Bundle bundle = new Bundle();
        bundle.putString("code", code);
        bundle.putString("name", name);
        bundle.putString("type", type);
        comeInfoFragmen.setArguments(bundle);
        return comeInfoFragmen;
    }
}
