package com.guc.bureau2.fragment;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.VolleyError;
import com.guc.bureau2.R;
import com.guc.bureau2.adapter.HospitalizeAdapter;
import com.guc.bureau2.base.BaseFragment;
import com.guc.bureau2.domain.HospitalizStatisticsDTO;
import com.guc.bureau2.listener.DateListener;
import com.guc.bureau2.net.GucNetEnginClient;
import com.guc.bureau2.utils.ToastUtils;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class BusinessStatisticsFragment extends BaseFragment {
    private ListView listView;
    private List<HospitalizStatisticsDTO> data = new ArrayList<>();
    private HospitalizeAdapter adapter;
    private boolean isLast = false;
    private boolean isLoader = false;
    private TextView tv_start_date;
    private TextView tv_end_date;
    private Calendar start_calendar;
    private Calendar end_calendar;
    private TextView tv_query;
    private String code;
    private String str_start_date;
    private String str_end_date;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return initView(inflater, container, R.layout.fragment_visitors_statistics);
    }

    private String getDateStr(int year, int month, int day) {
        String month_str = (month + 1) + "";
        return year + "-" + month_str + "-" + day;
    }

    @Override
    public void onClick(View arg0) {
        switch (arg0.getId()) {
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
                data.clear();
                if (start_calendar.getTimeInMillis() > end_calendar.getTimeInMillis()) {
                    ToastUtils.showLong(mActivity, R.string.start_time_not_more_endtime);
                    return;
                }
                str_start_date = tv_start_date.getText().toString().trim();
                str_end_date = tv_end_date.getText().toString().trim();
                if (code == null) {
                    getHospitaliz(str_start_date, str_end_date);
                } else {
                    getHospitalizTwo(str_start_date, str_end_date);
                }
                break;
            default:
                break;
        }
    }

    @Override
    protected void initData() {
        Bundle bundle = getArguments();
        String name = bundle.getString("name");
        code = bundle.getString("code");
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
        str_start_date= getDateStr(year, month, day - 10);
        str_end_date = getDateStr(year, month, day);
        tv_start_date.setText(str_start_date);
        tv_end_date.setText(str_end_date);
        if (code == null) {
            if (!isLoader) {
                getHospitaliz(str_start_date, str_end_date);
                isLoader = true;
            }
            controlBar(R.string.business_statistics, R.string.back,true, false);
        } else {
            controlBar(name, R.string.back, true, false);
            if (!isLoader) {
                getHospitalizTwo(str_start_date, str_end_date);
                isLoader = true;
            }
            isLast = true;
        }
        adapter = new HospitalizeAdapter(data, R.layout.layout_item_visitors_statistics);
        listView.setAdapter(adapter);
    }

    @Override
    protected void setListeners() {
        ll_back.setOnClickListener(this);
        tv_start_date.setOnClickListener(this);
        tv_end_date.setOnClickListener(this);
        tv_query.setOnClickListener(this);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                HospitalizStatisticsDTO dto = data.get(position);
                if (!isLast) {
                    mActivity.replace("BusinessStatisticsFragment", BusinessStatisticsFragment.newInstance(dto.getItem_code(), dto.getItem_name()), true);
                } else {
                    mActivity.replace("BusinessStatisticsDetailFragment", BusinessStatisticsDetailFragment.newInstance(dto.getItem_code(), dto.getItem_name(), str_start_date, str_end_date), true);
                }
            }
        });
    }

    @Override
    protected void initWidget(View view) {
        listView = (ListView) view.findViewById(R.id.listView);
        tv_start_date = (TextView) view.findViewById(R.id.tv_start_date);
        tv_end_date = (TextView) view.findViewById(R.id.tv_end_date);
        tv_query = (TextView) view.findViewById(R.id.tv_query);
    }

    private void getHospitaliz(String data_start, String data_end) {
        mProgressDialog.setMessage(getResources().getString(R.string.is_loading_please_wait));
        mProgressDialog.show();
        GucNetEnginClient.getHospitaliz(data_start, data_end, new Listener<String>() {

            @Override
            public void onResponse(String response) {
                mProgressDialog.dismiss();
                JSONObject jsonObject = JSON.parseObject(response);
                JSONObject obj_res = jsonObject.getJSONObject("getAreaVisitCountResult");
                String resultErrInfo = obj_res.getString("resultErrInfo");
                if (resultErrInfo != null) {
                    ToastUtils.showLong(mActivity, resultErrInfo);
                    return;
                }
                JSONArray jsonArray = obj_res.getJSONArray("list");
                if (jsonArray.size() <= 0) {
                    ToastUtils.showLong(mActivity, R.string.not_data);
                    return;
                }
                List<HospitalizStatisticsDTO> temp = JSON.parseArray(jsonArray.toJSONString(), HospitalizStatisticsDTO.class);
                data.addAll(temp);
                adapter.notifyDataSetChanged();
            }
        }, new ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                mProgressDialog.dismiss();
            }
        });
    }

    private void getHospitalizTwo(String data_start, String data_end) {
        mProgressDialog.setMessage(getResources().getString(R.string.is_loading_please_wait));
        mProgressDialog.show();
        GucNetEnginClient.getHospitalizTwo(code, data_start, data_end, new Listener<String>() {

            @Override
            public void onResponse(String response) {
                mProgressDialog.dismiss();
                JSONObject jsonObject = JSON.parseObject(response);
                JSONObject obj_res = jsonObject.getJSONObject("getHospitalVisitCountResult");
                String resultErrInfo = obj_res.getString("resultErrInfo");
                if (resultErrInfo != null) {
                    ToastUtils.showLong(mActivity, resultErrInfo);
                    return;
                }
                JSONArray jsonArray = obj_res.getJSONArray("list");
                if (jsonArray.size() <= 0) {
                    ToastUtils.showLong(mActivity, R.string.not_data);
                    return;
                }
                List<HospitalizStatisticsDTO> temp = JSON.parseArray(jsonArray.toJSONString(), HospitalizStatisticsDTO.class);
                data.addAll(temp);
                adapter.notifyDataSetChanged();
            }
        }, new ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                mProgressDialog.dismiss();
            }
        });
    }

    public static Fragment newInstance() {
        BusinessStatisticsFragment fragment = new BusinessStatisticsFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    public static Fragment newInstance(String code, String name) {
        BusinessStatisticsFragment fragment = new BusinessStatisticsFragment();
        Bundle args = new Bundle();
        args.putString("code", code);
        args.putString("name", name);
        fragment.setArguments(args);
        return fragment;
    }

}
