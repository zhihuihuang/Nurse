package com.guc.bureau2.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.guc.bureau2.R;
import com.guc.bureau2.adapter.ArchivesExAdapter;
import com.guc.bureau2.base.BaseFragment;
import com.guc.bureau2.domain.ArchivesInDTO;
import com.guc.bureau2.domain.ArchivesOutDTO;
import com.guc.bureau2.domain.BusinessDTO;
import com.guc.bureau2.domain.CheckRegionDTO;
import com.guc.bureau2.domain.ListDTO;
import com.guc.bureau2.net.GucNetEnginClient;
import com.guc.bureau2.utils.ToastUtils;

import java.util.ArrayList;
import java.util.List;


public class ArchivesRegionFragment extends BaseFragment {
    private ExpandableListView expandableListView;
    private ArrayList<CheckRegionDTO<ArrayList<ArchivesInDTO>>> data = new ArrayList<>();
    private ArchivesExAdapter adapter;
    private boolean isload = false;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return initView(inflater, container, R.layout.fragment_exlist);
    }

    @Override
    protected void initWidget(View view) {
        expandableListView = (ExpandableListView) view.findViewById(R.id.expandableListView);
    }

    @Override
    protected void setListeners() {
        expandableListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
                ArchivesInDTO dto = data.get(groupPosition).getT().get(childPosition);
                CheckRegionDTO dto_out = data.get(groupPosition);
                String code = dto_out.getItem_code();
                String type = dto.getItem_type();
                String name = dto_out.getItem_name();
                mActivity.replace("archivesOrgFragment", ArchivesOrgFragment.newInstance(code, type, name), true);
                return true;
            }
        });
        expandableListView.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
            @Override
            public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition, long id) {
                CheckRegionDTO<ArrayList<ArchivesInDTO>> temp=data.get(groupPosition);
                if (temp.getT().size() <= 0) {
                    getPreView(temp.getItem_code(), groupPosition);
                }
                return false;
            }
        });
        ll_back.setOnClickListener(this);
    }

    @Override
    protected void initData() {
        if (!isload) {
            getDiseaseRankRegion();
            isload = true;
        }
        adapter = new ArchivesExAdapter(R.layout.layout_simple_exlist_item_1, data);
        expandableListView.setAdapter(adapter);
        controlBar(R.string.archive_statistics, R.string.back, true, false);
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

    public static ArchivesRegionFragment newInstance() {
        return new ArchivesRegionFragment();
    }


    private void getPreView(String code, final int position) {
        mProgressDialog.setMessage(getResources().getString(R.string.is_loading_please_wait));
        mProgressDialog.show();
        GucNetEnginClient.getPreview(code, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                mProgressDialog.dismiss();
                JSONObject json = JSON.parseObject(response);
                JSONObject obj_res = json.getJSONObject("getEhrLastDayResultResult");
                String resultErrInfo = obj_res.getString("resultErrInfo");
                if (resultErrInfo == null) {
                    JSONArray jsonArray = obj_res.getJSONArray("list");
                    ArrayList<ArchivesInDTO> temp = (ArrayList<ArchivesInDTO>) JSON.parseArray(jsonArray.toJSONString(), ArchivesInDTO.class);
                    if (temp.size() <= 0) {
                        ToastUtils.showLong(mActivity, R.string.not_data);
                        return;
                    }
                    data.get(position).setT(temp);
                    adapter.notifyDataSetChanged();
                } else {
                    mProgressDialog.dismiss();
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

    private void getDiseaseRankRegion() {
        GucNetEnginClient.getDrugRankRegion(new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                isload = true;
                JSONObject jsonObject = JSON.parseObject(response);
                JSONObject obj_res = jsonObject.getJSONObject("getAreaCodeDataResult");
                String resultErrInfo = obj_res.getString("resultErrInfo");
                if (resultErrInfo == null) {
                    ArrayList<CheckRegionDTO<ArrayList<ArchivesInDTO>>> temp_data = new ArrayList<>();
                    JSONArray jsonArray = obj_res.getJSONArray("list");
                    List<CheckRegionDTO> temp = JSON.parseArray(jsonArray.toJSONString(), CheckRegionDTO.class);
                    int size = temp.size();
                    for (int i = 0; i < size; i++) {
                        ArrayList<ArchivesInDTO> in_data = new ArrayList<>();
                        CheckRegionDTO<ArrayList<ArchivesInDTO>> out_data = new CheckRegionDTO<>();
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
                isload = false;
            }
        });
    }
}
