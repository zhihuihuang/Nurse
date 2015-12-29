package com.guc.bureau2.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.guc.bureau2.R;
import com.guc.bureau2.adapter.DrugRankRegionAdapter;
import com.guc.bureau2.base.BaseFragment;
import com.guc.bureau2.domain.CheckRegionDTO;
import com.guc.bureau2.domain.DrugRankRegionDTO;
import com.guc.bureau2.net.GucNetEnginClient;
import com.guc.bureau2.utils.StrUtil;
import com.guc.bureau2.utils.ToastUtils;

import java.util.ArrayList;
import java.util.List;


public class DrugRankRegionFragment extends BaseFragment {
    private boolean isLoad;
    private List<CheckRegionDTO<List<DrugRankRegionDTO>>> data = new ArrayList<>();
    private ExpandableListView expandableListView;
    private DrugRankRegionAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return initView(inflater, container, R.layout.fragment_exlist);
    }

    @Override
    protected void initData() {
        if (!isLoad) {
            getDrugRankRegion();
        }
        controlBar(getResources().getString(R.string.drug_rank) + "前100", R.string.back, true, false);
    }

    @Override
    protected void setListeners() {
        ll_back.setOnClickListener(this);
        expandableListView.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
            @Override
            public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition, long id) {
                Log.e("groupPosition", groupPosition + "");
                if (data.get(groupPosition).getT().size() <= 0) {
                    Log.e("getDrugRankRegionDetail", "getDrugRankRegionDetail+");
                    getDrugRankRegionDetail(data.get(groupPosition).getItem_code(), groupPosition);
                }
                return false;
            }
        });
        expandableListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
                DrugRankRegionDTO dto = data.get(groupPosition).getT().get(childPosition);
                if (!TextUtils.isEmpty(dto.getArea_code())) {
                    mActivity.replace("DrugRankOrgFragment", DrugRankOrgFragment.newInstance(dto.getArea_code(), dto.getItem_code()), true);
                }
                return false;
            }
        });
    }

    @Override
    protected void initWidget(View view) {
        expandableListView = (ExpandableListView) view.findViewById(R.id.expandableListView);
        adapter = new DrugRankRegionAdapter(data);
        expandableListView.setAdapter(adapter);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ll_back:
                mActivity.popBackStack(1);
                break;
        }
    }

    // {"getAreaCodeDataResult":{"resultCode":0,"list":[{"item_name":"成都市","item_code":"000000"},{"item_name":"双流县","item_code":"510122"},{"item_name":"天府新区","item_code":"510123"}],"resultErrInfo":null}}
    private void getDrugRankRegion() {
        GucNetEnginClient.getDrugRankRegion(new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                isLoad = true;
                JSONObject jsonObject = JSON.parseObject(response);
                JSONObject obj_res = jsonObject.getJSONObject("getAreaCodeDataResult");
                String resultErrInfo = obj_res.getString("resultErrInfo");
                if (resultErrInfo == null) {
                    List<CheckRegionDTO<List<DrugRankRegionDTO>>> temp_data = new ArrayList<>();
                    JSONArray jsonArray = obj_res.getJSONArray("list");
                    if (jsonArray.size() <= 0) {
                        ToastUtils.showLong(mActivity, R.string.not_data);
                        return;
                    }
                    List<CheckRegionDTO> temp = JSON.parseArray(jsonArray.toJSONString(), CheckRegionDTO.class);
                    int size = temp.size();
                    for (int i = 0; i < size; i++) {
                        List<DrugRankRegionDTO> in_data = new ArrayList<>();
                        CheckRegionDTO<List<DrugRankRegionDTO>> out_data = new CheckRegionDTO<>();
                        out_data.setT(in_data);
                        out_data.setItem_code(temp.get(i).getItem_code());
                        out_data.setItem_name(temp.get(i).getItem_name());
                        temp_data.add(out_data);
                    }
                    data.addAll(temp_data);
                    adapter.notifyDataSetChanged();
                } else {
                    ToastUtils.showLong(mActivity, resultErrInfo);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                isLoad = false;
            }
        });
    }

    private void getDrugRankRegionDetail(String code, final int position) {
        mProgressDialog.setMessage(getResources().getString(R.string.is_loading_please_wait));
        mProgressDialog.show();
        GucNetEnginClient.getDrugRankRegionDetail(code, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                mProgressDialog.dismiss();
                JSONObject jsonObject = JSON.parseObject(response);
                JSONObject obj_res = jsonObject.getJSONObject("getAreaMedicalTopResult");
                String resultErrInfo = obj_res.getString("resultErrInfo");
                if (resultErrInfo == null) {
                    JSONArray jsonArray = obj_res.getJSONArray("list");
                    if (jsonArray.size() <= 0) {
                        ToastUtils.showLong(mActivity, R.string.not_data);
                        return;
                    }
                    List<DrugRankRegionDTO> temp_data = new ArrayList<>();
                    temp_data.add(0, new DrugRankRegionDTO(getResources().getString(R.string.amount), getResources().getString(R.string.unit), getResources().getString(R.string.specification), getResources().getString(R.string.drugname)));
                    temp_data.addAll(JSON.parseArray(jsonArray.toJSONString(), DrugRankRegionDTO.class));
                    data.get(position).getT().addAll(temp_data);
                    // data.get(position).setT(temp_data);
                    adapter.notifyDataSetChanged();
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

    public static DrugRankRegionFragment newInstance() {
        return new DrugRankRegionFragment();
    }
}