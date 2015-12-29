package com.guc.bureau2.fragment;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.VolleyError;
import com.guc.bureau2.R;
import com.guc.bureau2.adapter.DrugTopAdapter;
import com.guc.bureau2.base.BaseFragment;
import com.guc.bureau2.domain.DrugDTO;
import com.guc.bureau2.listener.DateListener;
import com.guc.bureau2.net.GucNetEnginClient;
import com.guc.bureau2.utils.ToastUtils;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

public class DrugRankFragment extends BaseFragment {
    private ArrayList<DrugDTO> data = new ArrayList<>();
    private ListView listView;
    private DrugTopAdapter adapter;
    private TextView tv_start_date;
    private TextView tv_end_date;
    private Calendar start_calendar;
    private Calendar end_calendar;
    private TextView tv_query;
    private String hospital_code;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return initView(inflater, container, R.layout.fragment_druginfo);
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
                getDrugTop(start_date, end_date);
                break;
            default:
                break;
        }
    }

    @Override
    protected void initData() {
        Bundle bundle = getArguments();
        hospital_code = bundle.getString("hospital_code");
        String hospital_name = bundle.getString("hospital_name");
        Calendar temp = Calendar.getInstance();
        start_calendar = Calendar.getInstance();
        end_calendar = Calendar.getInstance();
        int year = temp.get(Calendar.YEAR);
        int month = temp.get(Calendar.MONTH);
        int day = temp.get(Calendar.DAY_OF_MONTH);
        start_calendar.clear();
        end_calendar.clear();
        start_calendar.set(year, month, day - 10);
        end_calendar.set(year, month, day);
        String start_str = getDateStr(year, month, day - 10);
        String end_str = getDateStr(year, month, day);
        tv_end_date.setText(end_str);
        tv_start_date.setText(start_str);
        controlBar(hospital_name, R.string.back, true, false);

        adapter = new DrugTopAdapter( data);
        listView.setAdapter(adapter);
        getDrugTop(start_str, end_str);
    }

    private String getDateStr(int year, int month, int day) {
        String month_str = (month + 1) + "";
        return year + "-" + month_str + "-" + day;
    }

    @Override
    protected void setListeners() {
        ll_back.setOnClickListener(this);
        tv_start_date.setOnClickListener(this);
        tv_end_date.setOnClickListener(this);
        tv_query.setOnClickListener(this);
    }

    @Override
    protected void initWidget(View view) {
        tv_start_date = (TextView) view.findViewById(R.id.tv_start_date);
        tv_end_date = (TextView) view.findViewById(R.id.tv_end_date);
        tv_query = (TextView) view.findViewById(R.id.tv_query);
        listView = (ListView) view.findViewById(R.id.listView);
    }

    private void getDrugTop(String begin_time, String end_time) {
        data.clear();
        mProgressDialog.setMessage(getResources().getString(R.string.is_loading_please_wait));
        mProgressDialog.show();
        GucNetEnginClient.getDrugTop(hospital_code, begin_time, end_time, new Listener<String>() {

            @Override
            public void onResponse(String response) {
                mProgressDialog.dismiss();
                JSONObject jsonObject = JSON.parseObject(response);
                JSONObject res_obj = jsonObject.getJSONObject("GetDrugTopResult");
                String resultErrInfo = res_obj.getString("resultErrInfo");
                if (resultErrInfo != null) {
                    ToastUtils.showLong(mActivity, resultErrInfo);
                    return;
                }
                JSONArray array = res_obj.getJSONArray("list");
                if(array.size()<=0){
                    ToastUtils.showLong(mActivity,R.string.not_data);
                    return;
                }
                List<DrugDTO> temp=JSON.parseArray(array.toJSONString(), DrugDTO.class);
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

    public static Fragment newInstance(String hospital_code, String hospital_name) {
        DrugRankFragment fragment = new DrugRankFragment();
        Bundle args = new Bundle();
        args.putString("hospital_code", hospital_code);
        args.putString("hospital_name", hospital_name);
        fragment.setArguments(args);
        return fragment;
    }

}
