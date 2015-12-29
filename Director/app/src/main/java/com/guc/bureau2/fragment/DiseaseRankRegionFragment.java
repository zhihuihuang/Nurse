package com.guc.bureau2.fragment;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.android.volley.Response;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.VolleyError;
import com.guc.bureau2.R;
import com.guc.bureau2.adapter.DiseaseRankRegionAdapter;
import com.guc.bureau2.base.BaseFragment;
import com.guc.bureau2.domain.CheckRegionDTO;
import com.guc.bureau2.domain.DiseaseRankRegionDTO;
import com.guc.bureau2.listener.DateListener;
import com.guc.bureau2.net.GucNetEnginClient;
import com.guc.bureau2.utils.ToastUtils;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;
import android.widget.TextView;

public class DiseaseRankRegionFragment extends BaseFragment {

    private ExpandableListView expandableListView;
    private DiseaseRankRegionAdapter adapter;
    private TextView tv_start_date;
    private TextView tv_end_date;
    private Calendar start_calendar;
    private Calendar end_calendar;
    private boolean isLoad;
    private ArrayList<CheckRegionDTO<ArrayList<DiseaseRankRegionDTO>>> data = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return initView(inflater, container, R.layout.fragment_diseaseinfo);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ll_back:
                mActivity.popBackStack(1);
                break;
            case R.id.tv_start_date:
                for (int i = 0; i <= expandableListView.getCount(); i++) {
                    expandableListView.collapseGroup(i);
                }
                DatePickerFragment start_fragment = new DatePickerFragment(new DateListener() {

                    @Override
                    public void setDate(int year, int monthOfYear, int dayOfMonth) {
                        start_calendar.set(year, monthOfYear, dayOfMonth);
                        tv_start_date.setText(getDateStr(year, monthOfYear, dayOfMonth));
                    }
                });
                start_fragment.show(getChildFragmentManager(), "startfragment");
                break;
            case R.id.tv_end_date:
                for (int i = 0; i <= expandableListView.getCount(); i++) {
                    expandableListView.collapseGroup(i);
                }
                DatePickerFragment end_fragment = new DatePickerFragment(new DateListener() {

                    @Override
                    public void setDate(int year, int monthOfYear, int dayOfMonth) {
                        end_calendar.set(year, monthOfYear, dayOfMonth);
                        tv_end_date.setText(getDateStr(year, monthOfYear, dayOfMonth));
                    }
                });
                end_fragment.show(getChildFragmentManager(), "end_fragment");
                break;
            default:
                break;
        }
    }

    @Override
    protected void initData() {
        Calendar temp = Calendar.getInstance();
        start_calendar = Calendar.getInstance();
        end_calendar = Calendar.getInstance();
        int year = temp.get(Calendar.YEAR);
        int month = temp.get(Calendar.MONTH);
        int day = temp.get(Calendar.DAY_OF_MONTH);
        start_calendar.clear();
        end_calendar.clear();
        start_calendar.set(year, month, day-10);
        end_calendar.set(year, month, day);
        String start_str = getDateStr(year, month, day - 10);
        String end_str = getDateStr(year, month, day);
        tv_start_date.setText(start_str);
        tv_end_date.setText(end_str);
        controlBar(getResources().getString(R.string.disease_rank), R.string.back, true, false);
        adapter = new DiseaseRankRegionAdapter(data);
        expandableListView.setAdapter(adapter);
        if (!isLoad) {
            getDiseaseRankRegion();
        }
    }

    public String getDateStr(int year, int month, int day) {
        String month_str = (month + 1) + "";
        return year + "-" + month_str + "-" + day;
    }

    @Override
    protected void setListeners() {
        tv_start_date.setOnClickListener(this);
        tv_end_date.setOnClickListener(this);
        ll_back.setOnClickListener(this);
        expandableListView.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
            @Override
            public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition, long id) {
                CheckRegionDTO<ArrayList<DiseaseRankRegionDTO>> dto = data.get(groupPosition);
                if (dto.getT().size() <= 0) {
                    String str_start = tv_start_date.getText().toString().trim();
                    String str_end = tv_end_date.getText().toString().trim();
                    getDiseaseRankDetail(dto.getItem_code(), str_start, str_end, groupPosition);
                }
                return false;
            }
        });
        expandableListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
                DiseaseRankRegionDTO dto = data.get(groupPosition).getT().get(childPosition);
                mActivity.replace("hospitalListFragment", HospitalListFragment.newInstance(dto.getHis_code(), "5", dto.getItem_name()), true);
                return false;
            }
        });
    }


    @Override
    protected void initWidget(View view) {
        tv_start_date = (TextView) view.findViewById(R.id.tv_start_date);
        tv_end_date = (TextView) view.findViewById(R.id.tv_end_date);
        view.findViewById(R.id.tv_query).setVisibility(View.GONE);
        expandableListView = (ExpandableListView) view.findViewById(R.id.expandableListView);
    }

    private void getDiseaseRankDetail(String hospital_code, String begin_time, String end_time, final int position) {
        mProgressDialog.setMessage(getResources().getString(R.string.is_loading_please_wait));
        mProgressDialog.show();
        GucNetEnginClient.getDiseaseRankDetail(hospital_code, begin_time, end_time, new Listener<String>() {
            @Override
            public void onResponse(String response) {
                mProgressDialog.dismiss();
                JSONObject jsonObject = JSON.parseObject(response);
                JSONObject res_obj = jsonObject.getJSONObject("getAreaDiseaseDataNewResult");
                String resultErrInfo = res_obj.getString("resultErrInfo");
                if (resultErrInfo != null) {
                    ToastUtils.showLong(mActivity, resultErrInfo);
                    return;
                }
                JSONArray array = res_obj.getJSONArray("list");
                if (array.size() <= 0) {
                    ToastUtils.showLong(mActivity, R.string.not_data);
                    return;
                }
                ArrayList<DiseaseRankRegionDTO> disease_data = new ArrayList<>();
                disease_data.addAll(JSON.parseArray(array.toJSONString(), DiseaseRankRegionDTO.class));
                data.get(position).setT(disease_data);
                adapter.notifyDataSetChanged();
            }
        }, new ErrorListener() {
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
                isLoad = true;
                JSONObject jsonObject = JSON.parseObject(response);
                JSONObject obj_res = jsonObject.getJSONObject("getAreaCodeDataResult");
                String resultErrInfo = obj_res.getString("resultErrInfo");
                if (resultErrInfo == null) {
                    ArrayList<CheckRegionDTO<ArrayList<DiseaseRankRegionDTO>>> temp_data = new ArrayList<>();
                    JSONArray jsonArray = obj_res.getJSONArray("list");
                    List<CheckRegionDTO> temp = JSON.parseArray(jsonArray.toJSONString(), CheckRegionDTO.class);
                    int size = temp.size();
                    for (int i = 0; i < size; i++) {
                        ArrayList<DiseaseRankRegionDTO> in_data = new ArrayList<>();
                        CheckRegionDTO<ArrayList<DiseaseRankRegionDTO>> out_data = new CheckRegionDTO<>();
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

    public static Fragment newInstance() {
        return new DiseaseRankRegionFragment();
    }
}
