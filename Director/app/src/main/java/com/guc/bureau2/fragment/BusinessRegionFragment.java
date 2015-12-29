package com.guc.bureau2.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.android.volley.Response;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.VolleyError;
import com.guc.bureau2.R;
import com.guc.bureau2.adapter.BussinessAdapter;
import com.guc.bureau2.base.BaseFragment;
import com.guc.bureau2.domain.ArchivesOutDTO;
import com.guc.bureau2.domain.BusinessDTO;
import com.guc.bureau2.domain.CheckRegionDTO;
import com.guc.bureau2.domain.DiseaseRankRegionDTO;
import com.guc.bureau2.domain.ListDTO;
import com.guc.bureau2.net.GucNetEnginClient;
import com.guc.bureau2.utils.ToastUtils;

import java.util.ArrayList;
import java.util.List;

public class BusinessRegionFragment extends BaseFragment {
    private ExpandableListView expandableListView;
    private ArrayList<CheckRegionDTO<ArrayList<BusinessDTO>>> data = new ArrayList<>();
    private BussinessAdapter adapter;
    private boolean isLoad = false;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return initView(inflater, container, R.layout.fragment_exlist);
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
        controlBar(R.string.business_journal, R.string.back, true, false);
        adapter = new BussinessAdapter(data, R.layout.layout_simple_exlist_item_1);
        expandableListView.setAdapter(adapter);
        if (!isLoad) {
            getDiseaseRankRegion();
            isLoad = true;
        }
    }

    @Override
    protected void setListeners() {
        ll_back.setOnClickListener(this);
        expandableListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
                BusinessDTO dto = data.get(groupPosition).getT().get(childPosition);
                    mActivity.replace("businessOrgFragment", BusinessOrgFragment.newInstance(dto.getItem_code(), dto.getItem_name(), data.get(groupPosition).getT().get(childPosition).getItem_type()), true);
                return false;
            }
        });
        expandableListView.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
            @Override
            public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition, long id) {

                if (data.get(groupPosition).getT().size() <= 0) {
                    CheckRegionDTO<ArrayList<BusinessDTO>> temp = data.get(groupPosition);
                    if (temp.getT().size() <= 0) {
                        getInComeInfo(temp.getItem_code(), groupPosition);
                    }
                }
                return false;
            }
        });
//        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                InComeRegionDTO dto = data.get(position);
//                if (action == 0) {
//                    mActivity.replace("", BusinessOrgTableFragment.newInstance(1, dto.getItem_code(), dto.getHospital_name(), dto.getItem_type()), true);
//                }
//
//            }
//        });
    }

    private void getDiseaseRankRegion() {
        GucNetEnginClient.getDrugRankRegion(new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                isLoad = true;
                JSONObject jsonObject = JSON.parseObject(response);
                JSONObject obj_res = jsonObject.getJSONObject("getAreaCodeDataResult");
                String resultErrInfo = obj_res.getString("resultErrInfo");
                if (resultErrInfo == null) {
                    ArrayList<CheckRegionDTO<ArrayList<BusinessDTO>>> temp_data = new ArrayList<>();
                    JSONArray jsonArray = obj_res.getJSONArray("list");
                    List<CheckRegionDTO> temp = JSON.parseArray(jsonArray.toJSONString(), CheckRegionDTO.class);
                    int size = temp.size();
                    for (int i = 0; i < size; i++) {
                        ArrayList<BusinessDTO> in_data = new ArrayList<>();
                        CheckRegionDTO<ArrayList<BusinessDTO>> out_data = new CheckRegionDTO<>();
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

    private void getInComeInfo(String code, final int position) {
        mProgressDialog.setMessage(getResources().getString(R.string.is_loading_please_wait));
        mProgressDialog.show();
        GucNetEnginClient.getInComeRegion(code, new Listener<String>() {

            @Override
            public void onResponse(String response) {
                mProgressDialog.dismiss();
                JSONObject jsonObject = JSON.parseObject(response);
                JSONObject obj_res = jsonObject.getJSONObject("getAreaBusinessDayResult");
                String resultErrInfo = obj_res.getString("resultErrInfo");
                if (resultErrInfo != null) {
                    ToastUtils.showLong(mActivity, resultErrInfo);
                    return;
                }
                JSONArray jsonArray = obj_res.getJSONArray("list");
                ArrayList<BusinessDTO> temp = (ArrayList<BusinessDTO>) JSON.parseArray(jsonArray.toJSONString(), BusinessDTO.class);
                if (temp.size() <= 0) {
                    ToastUtils.showLong(mActivity, R.string.not_data);
                    return;
                }
                data.get(position).setT(temp);
                adapter.notifyDataSetChanged();
            }
        }, new ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                mProgressDialog.dismiss();
            }
        });
    }

//    private void getInComeOrg(String code, String type) {
//        mProgressDialog.setMessage(getResources().getString(R.string.is_loading_please_wait));
//        mProgressDialog.show();
//        GucNetEnginClient.getInComeOrg(code, type, new Listener<String>() {
//
//            @Override
//            public void onResponse(String response) {
//                mProgressDialog.dismiss();
//                JSONObject jsonObject = JSON.parseObject(response);
//                JSONObject obj_res = jsonObject.getJSONObject("getHospitalBusinessDataResult");
//                String resultErrInfo = obj_res.getString("resultErrInfo");
//                if (resultErrInfo != null) {
//                    ToastUtils.showLong(mActivity, resultErrInfo);
//                    return;
//                }
//                JSONArray array = obj_res.getJSONArray("list");
//                if (array.size() <= 0) {
//                    ToastUtils.showLong(mActivity, R.string.not_data);
//                    return;
//                }
//                List<InComeRegionDTO> temp = JSON.parseArray(array.toJSONString(), InComeRegionDTO.class);
//                data.addAll(temp);
//                adapter.notifyDataSetChanged();
//            }
//        }, new ErrorListener() {
//
//            @Override
//            public void onErrorResponse(VolleyError error) {
//                mProgressDialog.dismiss();
//            }
//        });
//    }

    @Override
    protected void initWidget(View view) {
        expandableListView = (ExpandableListView) view.findViewById(R.id.expandableListView);
    }

    public static Fragment newInstance() {
        return new BusinessRegionFragment();
    }

    public static Fragment newInstance(int action, String code, String name, String type) {
        BusinessRegionFragment comeInfoFragmen = new BusinessRegionFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("action", action);
        bundle.putString("code", code);
        bundle.putString("name", name);
        bundle.putString("type", type);
        comeInfoFragmen.setArguments(bundle);
        return comeInfoFragmen;
    }
}
