package com.guc.bureau2.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;
import android.widget.LinearLayout;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.VolleyError;
import com.guc.bureau2.R;
import com.guc.bureau2.adapter.CheckRegionAdapter;
import com.guc.bureau2.base.BaseFragment;
import com.guc.bureau2.domain.CheckOrgDTO;
import com.guc.bureau2.domain.CheckRegionDTO;
import com.guc.bureau2.net.GucNetEnginClient;
import com.guc.bureau2.utils.ToastUtils;

import java.util.ArrayList;
import java.util.List;

public class CheckRegionFragment extends BaseFragment {
    private ExpandableListView expandableListView;
    private List<CheckRegionDTO<List<CheckOrgDTO>>> data = new ArrayList<>();
    private CheckRegionAdapter adapter;
    private LinearLayout layout_null;
    private boolean isLoad;

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
        controlBar(R.string.health_check, R.string.back, true, false);
        adapter = new CheckRegionAdapter(data);
        expandableListView.setAdapter(adapter);
        if (!isLoad) {
            getData();
        }
    }

    private void getData() {
        GucNetEnginClient.getCheckRegion(new Listener<String>() {
            @Override
            public void onResponse(String response) {
                JSONObject jsonObject = JSON.parseObject(response);
                JSONObject obj_res = jsonObject.getJSONObject("getAreaDataResult");
                String resultErrInfo = obj_res.getString("resultErrInfo");
                if (resultErrInfo != null) {
                    ToastUtils.showLong(mActivity, resultErrInfo);
                    mActivity.popBackStack(1);
                } else {
                    isLoad = true;
                    JSONArray array = obj_res.getJSONArray("list");
                    if (array.size() <= 0) {
                        layout_null.setVisibility(View.VISIBLE);
                    } else {
                        List<CheckRegionDTO<List<CheckOrgDTO>>> temp_data = new ArrayList<>();
                        List<CheckRegionDTO> temp = JSON.parseArray(array.toJSONString(), CheckRegionDTO.class);
                        int size = temp.size();
                        for (int i = 0; i < size; i++) {
                            List<CheckOrgDTO> in_data = new ArrayList<>();
                            CheckRegionDTO<List<CheckOrgDTO>> out_data = new CheckRegionDTO<>();
                            out_data.setT(in_data);
                            out_data.setItem_code(temp.get(i).getItem_code());
                            out_data.setItem_name(temp.get(i).getItem_name());
                            temp_data.add(out_data);
                        }
                        data.addAll(temp_data);
                        adapter.notifyDataSetChanged();
                    }
                }
            }
        }, new ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                isLoad = false;
            }
        });
    }

    @Override
    protected void setListeners() {
        ll_back.setOnClickListener(this);
//        expandableListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
//                AlertDialog.Builder builder = new AlertDialog.Builder(mActivity).setItems(new String[]{"区域总览", "机构详情"}, new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
//                        if (which == 0) {
//                            mActivity.replace("CheckOrgFragment", CheckOrgFragment.newInstance(data.get(position).getItem_code(), data.get(position).getItem_name()), true);
//                        } else {
//                            mActivity.replace("HospitalListFragment", HospitalListFragment.newInstance(1), true);
//                        }
//                    }
//                });
//                builder.show();
//            }
//        });
        expandableListView.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
            @Override
            public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition, long id) {
                if (data.get(groupPosition).getT().size() <= 0) {
                    getData(data.get(groupPosition).getItem_code(), groupPosition);
                }
                return false;
            }
        });
        expandableListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
                mActivity.replace("hospitalListFragment", HospitalListFragment.newInstance(data.get(groupPosition).getItem_code(), "1", data.get(groupPosition).getItem_name()), true);
                return false;
            }
        });
    }

    private void getData(String org_code, final int positison) {
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
                    ToastUtils.showLong(mActivity, R.string.not_data);
                } else {
                    data.get(positison).setT(JSON.parseArray(array.toJSONString(), CheckOrgDTO.class));
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
    protected void initWidget(View view) {
        expandableListView = (ExpandableListView) view.findViewById(R.id.expandableListView);
        layout_null = (LinearLayout) view.findViewById(R.id.layout_null);
    }

    public static Fragment newInstance() {
        return new CheckRegionFragment();
    }
}
