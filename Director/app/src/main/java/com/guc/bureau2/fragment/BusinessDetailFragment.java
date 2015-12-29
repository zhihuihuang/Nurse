package com.guc.bureau2.fragment;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.android.volley.VolleyError;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.guc.bureau2.R;
import com.guc.bureau2.base.BaseFragment;
import com.guc.bureau2.domain.InComeDTO;
import com.guc.bureau2.listener.DateListener;
import com.guc.bureau2.net.GucNetEnginClient;
import com.guc.bureau2.utils.ToastUtils;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.Calendar;

public class BusinessDetailFragment extends BaseFragment {
    private TextView base_charge_view;
    private TextView base_rate_view;
    private TextView mz_charge_view;
    private TextView total_charge_view;
    private TextView total_charge_yp_view;
    private TextView total_charge_zl_view;
    private TextView yp_rate_view;
    private TextView zy_charge_view;
    private TextView tv_start_date;
    private TextView tv_end_date;
    private Calendar start_calendar;
    private Calendar end_calendar;
    private TextView tv_query;
    private String hospital_code;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return initView(inflater, container, R.layout.fragment_income_info);
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
                getInComeInfo(start_date, end_date);
                break;
            default:
                break;
        }
    }

    @Override
    protected void initData() {
        Bundle bundle = getArguments();
        hospital_code = bundle.getString("hospital_code");
        String org_name = bundle.getString("org_name");
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
        getInComeInfo(start_str, end_str);
        controlBar(org_name + getResources().getString(R.string.income_info), R.string.back, true, false);
    }


    private String getDateStr(int year, int month, int day) {
        String month_str = (month + 1) + "";
        return year + "-" + month_str + "-" + day;
    }

    @Override
    protected void setListeners() {
        tv_start_date.setOnClickListener(this);
        tv_end_date.setOnClickListener(this);
        tv_query.setOnClickListener(this);
        ll_back.setOnClickListener(this);
    }

    private void getInComeInfo(String dateStart, String dateEnd) {
        mProgressDialog.setMessage(getResources().getString(R.string.is_loading_please_wait));
        mProgressDialog.show();
        GucNetEnginClient.getInComeInfo(hospital_code, dateStart, dateEnd, new Listener<String>() {

            @Override
            public void onResponse(String response) {
                mProgressDialog.dismiss();
                JSONObject jsonObject = JSON.parseObject(response);
                JSONObject obj_res = jsonObject.getJSONObject("GetInComeResult");
                String resultErrInfo = obj_res.getString("resultErrInfo");
                if (resultErrInfo != null) {
                    ToastUtils.showLong(mActivity, resultErrInfo);
                    mActivity.popBackStack(1);
                    return;
                }
                InComeDTO comeDTO = JSON.parseObject(obj_res.toJSONString(), InComeDTO.class);
                setText(comeDTO);
            }
        }, new ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                mProgressDialog.dismiss();
            }
        });
    }

    private void setText(InComeDTO comeDTO) {
        base_charge_view.setText(comeDTO.getBase_charge());
        base_rate_view.setText(comeDTO.getBase_rate());
        mz_charge_view.setText(comeDTO.getMz_charge());
        total_charge_view.setText(comeDTO.getTotal_charge());
        total_charge_yp_view.setText(comeDTO.getTotal_charge_yp());
        total_charge_zl_view.setText(comeDTO.getTotal_charge_zl());
        yp_rate_view.setText(comeDTO.getYp_rate());
        zy_charge_view.setText(comeDTO.getZy_charge());
    }

    @Override
    protected void initWidget(View view) {
        base_charge_view = (TextView) view.findViewById(R.id.base_charge);
        base_rate_view = (TextView) view.findViewById(R.id.base_rate);
        mz_charge_view = (TextView) view.findViewById(R.id.mz_charge);
        total_charge_view = (TextView) view.findViewById(R.id.total_charge);
        total_charge_yp_view = (TextView) view.findViewById(R.id.total_charge_yp);
        total_charge_zl_view = (TextView) view.findViewById(R.id.total_charge_zl);
        yp_rate_view = (TextView) view.findViewById(R.id.yp_rate);
        zy_charge_view = (TextView) view.findViewById(R.id.zy_charge);
        tv_start_date = (TextView) view.findViewById(R.id.tv_start_date);
        tv_end_date = (TextView) view.findViewById(R.id.tv_end_date);
        tv_query = (TextView) view.findViewById(R.id.tv_query);
    }

    public static Fragment newInstance(String hospital_code, String org_name) {
        BusinessDetailFragment comeInfoFragmen = new BusinessDetailFragment();
        Bundle args = new Bundle();
        args.putString("hospital_code", hospital_code);
        args.putString("org_name", org_name);
        comeInfoFragmen.setArguments(args);
        return comeInfoFragmen;
    }
}
