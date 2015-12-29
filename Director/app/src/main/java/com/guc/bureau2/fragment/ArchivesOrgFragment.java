package com.guc.bureau2.fragment;

import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.guc.bureau2.R;
import com.guc.bureau2.base.BaseFragment;
import com.guc.bureau2.domain.ArchivesDTO;
import com.guc.bureau2.domain.ArchivesInDTO;
import com.guc.bureau2.net.GucNetEnginClient;
import com.guc.bureau2.utils.ToastUtils;

import java.util.ArrayList;


public class ArchivesOrgFragment extends BaseFragment {
    private ArrayList<ArchivesDTO> data = new ArrayList<>();
    private boolean isload = false;
    private PopupWindow popupWindow;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return initView(inflater, container, R.layout.fragment_archives_org);
    }

    @Override
    protected void initWidget(View view) {

    }

    @Override
    protected void setListeners() {
        ll_back.setOnClickListener(this);
        right_layout.setOnClickListener(this);
    }

    @Override
    protected void initData() {
        Bundle bundle = getArguments();
        String code = bundle.getString("code");
        String type = bundle.getString("type");
        String name = bundle.getString("name");
        if (!isload) {
            isload = true;
            getPreViewDetail(code, type);
        }
        controlBar(name, R.string.back, true, true);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ll_back:
                mActivity.popBackStack(1);
                break;
            case R.id.right_layout:
                popWindows(right_layout);
                break;
            case R.id.tv_graph:
                popupWindow.dismiss();
                replace("ArchivesOrgTableFragment", ArchivesOrgChartFragment.newInstance(data));
                break;
            case R.id.tv_table:
                popupWindow.dismiss();
                replace("ArchivesOrgTableFragment", ArchivesOrgTableFragment.newInstance(data));
                break;
            default:
                break;
        }
    }

    public void replace(String tag, Fragment fragment) {
        getChildFragmentManager().beginTransaction().replace(R.id.fragment_content, fragment, tag).commitAllowingStateLoss();
    }

    public static ArchivesOrgFragment newInstance(String code, String type, String name) {
        ArchivesOrgFragment fragment = new ArchivesOrgFragment();
        Bundle bundle = new Bundle();
        bundle.putString("code", code);
        bundle.putString("type", type);
        bundle.putString("name", name);
        fragment.setArguments(bundle);
        return fragment;
    }

    public void popWindows(View parent) {
        View view = LayoutInflater.from(mActivity).inflate(R.layout.layout_list, null);
        popupWindow = new PopupWindow(view, (int)getResources().getDimension(R.dimen.y100), (int)getResources().getDimension(R.dimen.y60));
        popupWindow.setFocusable(true);
        popupWindow.setOutsideTouchable(true);
        popupWindow.setBackgroundDrawable(new BitmapDrawable());
        popupWindow.showAsDropDown(parent, 0, 0);
        TextView tv_graph = (TextView) view.findViewById(R.id.tv_graph);
        TextView tv_table = (TextView) view.findViewById(R.id.tv_table);
        tv_graph.setOnClickListener(this);
        tv_table.setOnClickListener(this);
    }

    private void getPreViewDetail(String code, final String type) {
        mProgressDialog.setMessage(getResources().getString(R.string.is_loading_please_wait));
        mProgressDialog.show();
        GucNetEnginClient.getPreviewDetal(code, type, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                mProgressDialog.dismiss();
                JSONObject json = JSON.parseObject(response);
                JSONObject obj_res = json.getJSONObject("getEhrHospitalDataResult");
                String resultErrInfo = obj_res.getString("resultErrInfo");
                if (resultErrInfo == null) {
                    JSONArray jsonArray = obj_res.getJSONArray("list");
                    if (jsonArray.size() <= 0) {
                        ToastUtils.showLong(mActivity, R.string.not_data);
                        return;
                    }
                    ArrayList<ArchivesDTO> temp = (ArrayList) JSON.parseArray(jsonArray.toJSONString(), ArchivesDTO.class);
                    data.addAll(temp);
                    replace("ArchivesOrgTableFragment", ArchivesOrgTableFragment.newInstance(data));
                } else {
                    ToastUtils.showLong(mActivity, resultErrInfo);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                mProgressDialog.dismiss();
            }
        });
    }

    public static ArchivesOrgFragment newInstance(ArrayList<ArchivesInDTO> data) {
        Bundle args = new Bundle();
        args.putSerializable("data", data);
        ArchivesOrgFragment fragment = new ArchivesOrgFragment();
        fragment.setArguments(args);
        return fragment;
    }
}
