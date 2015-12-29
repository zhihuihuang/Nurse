package com.guc.bureau2.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.TextView;

import com.guc.bureau2.R;
import com.guc.bureau2.adapter.DiseaseStatisticsAdapter;
import com.guc.bureau2.base.BaseFragment;
import com.guc.bureau2.domain.DiseaseRankRegionDTO;
import com.guc.bureau2.listener.DateListener;
import com.guc.bureau2.utils.ToastUtils;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;


public class DiseaseStatisticsFragment extends BaseFragment {
    private ExpandableListView expandableListView;
    private List<DiseaseRankRegionDTO> data = new ArrayList<>();
    private DiseaseStatisticsAdapter adapter;
    private boolean isLoader;
    private TextView tv_right;
    private TextView tv_start_date;
    private TextView tv_end_date;
    private Calendar start_calendar;
    private Calendar end_calendar;
    private TextView tv_query;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return initView(inflater, container, R.layout.fragment_disease_statics);
    }

    private String getDateStr(int year, int month, int day) {
        String month_str = (month + 1) + "";
        return year + "-" + month_str + "-" + day;
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
        tv_end_date.setText(end_str);
        tv_start_date.setText(start_str);
        adapter = new DiseaseStatisticsAdapter(data, R.layout.layout_item_disease_statistice);
        expandableListView.setAdapter(adapter);
        if (!isLoader) {
            isLoader = true;
            getDiseaseStatistics(start_str, end_str);
        }
        controlBar(R.string.disease_statistice, R.string.back, true, true);
        tv_right.setText("机构详情");
    }

    @Override
    protected void setListeners() {
        ll_back.setOnClickListener(this);
        tv_start_date.setOnClickListener(this);
        tv_end_date.setOnClickListener(this);
        tv_query.setOnClickListener(this);
        right_layout.setOnClickListener(this);
    }

    @Override
    protected void initWidget(View view) {
        tv_start_date = (TextView) view.findViewById(R.id.tv_start_date);
        tv_end_date = (TextView) view.findViewById(R.id.tv_end_date);
        tv_query = (TextView) view.findViewById(R.id.tv_query);
        expandableListView = (ExpandableListView) view.findViewById(R.id.expandableListView);
        tv_right = (TextView) view.findViewById(R.id.tv_right);
        ImageView iv_add = (ImageView) view.findViewById(R.id.iv_add);
        iv_add.setVisibility(View.GONE);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ll_back:
                mActivity.popBackStack(1);
                break;
            case R.id.tv_start_date:
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
                DatePickerFragment end_fragment = new DatePickerFragment(new DateListener() {

                    @Override
                    public void setDate(int year, int monthOfYear, int dayOfMonth) {
                        end_calendar.set(year, monthOfYear, dayOfMonth);
                        tv_end_date.setText(getDateStr(year, monthOfYear, dayOfMonth));
                    }
                });
                end_fragment.show(getChildFragmentManager(), "end_fragment");
                break;
            case R.id.tv_query:
                if (start_calendar.getTimeInMillis() > end_calendar.getTimeInMillis()) {
                    ToastUtils.showLong(mActivity, R.string.start_time_not_more_endtime);
                    return;
                }
                String start_date = tv_start_date.getText().toString().trim();
                String end_date = tv_end_date.getText().toString().trim();
                getDiseaseStatistics(start_date, end_date);
                break;
            case R.id.right_layout:
                mActivity.replace("hospitalListFragment", HospitalListFragment.newInstance("5"), true);
                break;
            default:
                break;
        }
    }

    private void getDiseaseStatistics(String start_date, String end_date) {
        mProgressDialog.setMessage(getResources().getString(R.string.is_loading_please_wait));
        mProgressDialog.show();
        data.clear();
//        GucNetEnginClient.getDiseaseStatistics(start_date, end_date, new Response.Listener<String>() {
//            @Override
//            public void onResponse(String response) {
//                mProgressDialog.dismiss();
//                JSONObject jsonObject = JSON.parseObject(response);
//                JSONObject obj_res = jsonObject.getJSONObject("getAreaDiseaseDataResult");
//                String resultErrInfo = obj_res.getString("resultErrInfo");
//                if (resultErrInfo == null) {
//                    JSONArray jsonArray = obj_res.getJSONArray("list");
//                    if (jsonArray.size() <= 0) {
//                        ToastUtils.showLong(mActivity, R.string.not_data);
//                    }
//                    data.addAll(JSON.parseArray(jsonArray.toJSONString(), DiseaseRankRegionDTO.class));
//                }
//                adapter.notifyDataSetChanged();
//            }
//        }, new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError error) {
//                mProgressDialog.dismiss();
//            }
//        });
    }

    public static DiseaseStatisticsFragment newInstance() {
        return new DiseaseStatisticsFragment();
    }

}
