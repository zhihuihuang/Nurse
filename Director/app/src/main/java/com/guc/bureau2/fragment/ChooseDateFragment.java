package com.guc.bureau2.fragment;

import java.util.Calendar;

import com.guc.bureau2.R;
import com.guc.bureau2.base.BaseFragment;
import com.guc.bureau2.listener.DateListener;
import com.guc.bureau2.utils.ToastUtils;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class ChooseDateFragment extends BaseFragment implements DateListener {
    private RelativeLayout begin_layout;
    private RelativeLayout end_layout;
    private LinearLayout check_layout;
    private Calendar startCalendar;
    private Calendar endCalendar;
    private boolean isStart=true;
    private TextView tv_begin_time;
    private TextView tv_end_time;
    private int item_id;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return initView(inflater, container, R.layout.fragment_choose_date);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.begin_layout:
                isStart = true;
                popDialog();
                break;
            case R.id.end_layout:
                isStart = false;
                popDialog();
                break;
            case R.id.check_layout:
                checkData();
                break;
            case R.id.ll_back:
                mActivity.popBackStack(1);
                break;
            default:
                break;
        }
    }

    @Override
    protected void initData() {
        startCalendar = Calendar.getInstance();
        endCalendar = Calendar.getInstance();
        int year = startCalendar.get(Calendar.YEAR);
        int month = startCalendar.get(Calendar.MONTH);
        int day = startCalendar.get(Calendar.DAY_OF_MONTH);
        startCalendar.clear();
        endCalendar.clear();
        startCalendar.set(year, month, day);
        endCalendar.set(year, month, day);
        String str = year + "-" + (month + 1) + "-" + +day + "";
        tv_begin_time.setText(str);
        tv_end_time.setText(str);
        Bundle bundle = getArguments();
//        hospital_code = bundle.getString("hospital_code");
//        hospital_name = bundle.getString("hospital_name");
        item_id = bundle.getInt("item_id");
        controlBar(getResources().getString(R.string.choose_date), R.string.back, true, false);
    }

    private void checkData() {
        if (startCalendar.getTimeInMillis() > endCalendar.getTimeInMillis()) {
            ToastUtils.showShort(mActivity, R.string.start_time_not_more_endtime);
            return;
        }
        String dateStart = tv_begin_time.getText().toString().trim();
        String dateEnd = tv_end_time.getText().toString().trim();
        switch (item_id) {
            case 2:
               // mActivity.replace("visitorstatisticsfragment", BusinessDetailFragment.newInstance(dateStart, dateEnd,hospital_code,hospital_name), true);
                break;
            case 3:
                //mActivity.replace("visitorstatisticsfragment", BusinessStatisticsFragment.newInstance(dateStart, dateEnd), true);
                break;
            case 4:
                //mActivity.replace("drugrankfragment", DrugRankFragment.newInstance(hospital_code, hospital_name, dateStart, dateEnd), true);
                break;
            case 5:
                //mActivity.replace("diseasestatisticsfragment", DiseaseStatisticsFragment.newInstance(dateStart, dateEnd), true);
                break;
            default:
                break;
        }
    }

    @Override
    protected void setListeners() {
        begin_layout.setOnClickListener(this);
        end_layout.setOnClickListener(this);
        check_layout.setOnClickListener(this);
        ll_back.setOnClickListener(this);
    }

    @Override
    protected void initWidget(View view) {
        begin_layout = (RelativeLayout) view.findViewById(R.id.begin_layout);
        end_layout = (RelativeLayout) view.findViewById(R.id.end_layout);
        check_layout = (LinearLayout) view.findViewById(R.id.check_layout);
        tv_begin_time = (TextView) view.findViewById(R.id.tv_begin_time);
        tv_end_time = (TextView) view.findViewById(R.id.tv_end_time);
    }

    private void popDialog() {
        //DatePickerFragment datePickerFragment = new DatePickerFragment();
        //datePickerFragment.show(mActivity.getSupportFragmentManager(), "datePickerFragment");
       // datePickerFragment.setCancelable(false);

    }

    @Override
    public void setDate(int year, int monthOfYear, int dayOfMonth) {
        if (isStart) {
            if (year != 0) {
                startCalendar.set(year, monthOfYear, dayOfMonth);
                String dateStart = startCalendar.get(Calendar.YEAR) + "-" + (startCalendar.get(Calendar.MONTH) + 1) + "-" + startCalendar.get(Calendar.DAY_OF_MONTH) + "";
                tv_begin_time.setText(dateStart);
            }
        } else {
            if (year != 0) {
                endCalendar.set(year, monthOfYear, dayOfMonth);
                String dateEnd = endCalendar.get(Calendar.YEAR) + "-" + (endCalendar.get(Calendar.MONTH) + 1) + "-" + endCalendar.get(Calendar.DAY_OF_MONTH) + "";
                tv_end_time.setText(dateEnd);
            }
        }
    }

    public static Fragment newInstance(String hospital_code, String hospital_name, int item_id) {
        ChooseDateFragment comeFragment = new ChooseDateFragment();
        Bundle args = new Bundle();
        args.putString("hospital_code", hospital_code);
        args.putInt("item_id", item_id);
        args.putString("hospital_name", hospital_name);
        comeFragment.setArguments(args);
        return comeFragment;
    }

    public static Fragment newInstance(int item_id) {
        ChooseDateFragment comeFragment = new ChooseDateFragment();
        Bundle args = new Bundle();
        args.putInt("item_id", item_id);
        comeFragment.setArguments(args);
        return comeFragment;
    }
}
