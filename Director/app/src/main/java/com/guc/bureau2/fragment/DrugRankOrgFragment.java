package com.guc.bureau2.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ExpandableListView;
import android.widget.ListView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.guc.bureau2.R;
import com.guc.bureau2.adapter.DrugRankOrgAdapter;
import com.guc.bureau2.adapter.DrugRankRegionAdapter;
import com.guc.bureau2.base.BaseFragment;
import com.guc.bureau2.domain.CheckRegionDTO;
import com.guc.bureau2.domain.DrugOrgDTO;
import com.guc.bureau2.domain.DrugRankRegionDTO;
import com.guc.bureau2.net.GucNetEnginClient;
import com.guc.bureau2.utils.ToastUtils;

import java.util.ArrayList;
import java.util.List;


public class DrugRankOrgFragment extends BaseFragment {
    private boolean isLoad;
    private ArrayList<DrugOrgDTO> data = new ArrayList<>();
    private ListView listView;
    private DrugRankOrgAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return initView(inflater, container, R.layout.fragment_drug_rank_org);
    }

    @Override
    protected void initData() {
        if (!isLoad) {
            Bundle args = getArguments();
            String area_code = args.getString("area_code");
            String item_code = args.getString("item_code");
            getDrugRankOrg(area_code, item_code);
        }
        adapter = new DrugRankOrgAdapter(data, R.layout.layout_item_drug_org);
        listView.setAdapter(adapter);
        controlBar("机构" + getResources().getString(R.string.drug_rank), R.string.back, true, false);
    }

    @Override
    protected void setListeners() {
        ll_back.setOnClickListener(this);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                DrugOrgDTO dto=data.get(position);
                mActivity.replace("DrugRankFragment",DrugRankFragment.newInstance(dto.getHos_code(),dto.getHos_name()),true);
            }
        });
    }

    @Override
    protected void initWidget(View view) {
        listView = (ListView) view.findViewById(R.id.listView);

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
    private void getDrugRankOrg(String area_code, String item_code) {
        GucNetEnginClient.getDrugOrg(area_code, item_code, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                isLoad = true;
                JSONObject jsonObject = JSON.parseObject(response);
                JSONObject obj_res = jsonObject.getJSONObject("getAreaMedicalHospitalTopResult");
                String resultErrInfo = obj_res.getString("resultErrInfo");
                if (resultErrInfo == null) {
                    JSONArray jsonArray = obj_res.getJSONArray("list");
                    if(jsonArray.size()<=0){
                        ToastUtils.showLong(mActivity, R.string.not_data);
                        return ;
                    }
                    List<DrugOrgDTO> temp = JSON.parseArray(jsonArray.toJSONString(), DrugOrgDTO.class);
                    data.addAll(temp);
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

    public static DrugRankOrgFragment newInstance(String area_code, String item_code) {
        Bundle args = new Bundle();
        args.putString("area_code", area_code);
        args.putString("item_code", item_code);
        DrugRankOrgFragment fragment = new DrugRankOrgFragment();
        fragment.setArguments(args);
        return fragment;
    }
}