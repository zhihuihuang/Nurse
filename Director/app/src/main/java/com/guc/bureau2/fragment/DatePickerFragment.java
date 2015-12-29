package com.guc.bureau2.fragment;

import java.util.Calendar;

import com.guc.bureau2.R;
import com.guc.bureau2.activity.MainActivity;
import com.guc.bureau2.listener.DateListener;
import com.guc.bureau2.utils.ToastUtils;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.DatePicker.OnDateChangedListener;

public class DatePickerFragment extends DialogFragment implements OnClickListener, OnDateChangedListener {
	private int year;
	private int monthOfYear;
	private int dayOfMonth;
	private long currentTimeInMillis;
	private MainActivity context;
	private DateListener mCarryData;

	public DatePickerFragment(DateListener mCarryData) {
		this.mCarryData = mCarryData;
	}

	@Override
	public void onAttach(Activity context) {
		super.onAttach(context);
		this.context=((MainActivity)context);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);
		View view = inflater.inflate(R.layout.fragment_dialog_datepicker, null);
		initView(view);
		return view;
	}

	private void initView(View view) {
		Button btn_ok = (Button) view.findViewById(R.id.btn_ok);
		Button btn_cancel = (Button) view.findViewById(R.id.btn_cancel);
		DatePicker datePicker = (DatePicker) view.findViewById(R.id.datePicker);
		btn_ok.setOnClickListener(this);
		btn_cancel.setOnClickListener(this);
		Calendar mCalendar = Calendar.getInstance();
		mCalendar.clear();
		Calendar calendar = Calendar.getInstance();
		year=calendar.get(Calendar.YEAR);
		monthOfYear=calendar.get(Calendar.MONTH);
		dayOfMonth=calendar.get(Calendar.DAY_OF_MONTH);
		mCalendar.set(year, monthOfYear,dayOfMonth);
		currentTimeInMillis = mCalendar.getTimeInMillis();
		datePicker.init(year, monthOfYear,dayOfMonth, this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btn_ok:
			Calendar calendar = Calendar.getInstance();
			calendar.clear();
			calendar.set(year, monthOfYear, dayOfMonth);
			long choseTime = calendar.getTimeInMillis();
			if (choseTime > currentTimeInMillis) {
				ToastUtils.showShort(getActivity(), R.string.not_more_current_time);
				return ;
			}
			mCarryData.setDate(year, monthOfYear, dayOfMonth);
//			FragmentManager fragmentManager=context.getSupportFragmentManager();
//			ChooseDateFragment chooseDateFragment=(ChooseDateFragment)fragmentManager.findFragmentByTag("choosedatefragment");
//			chooseDateFragment.setDate(year, monthOfYear, dayOfMonth);
			this.dismiss();
			break;
		case R.id.btn_cancel:
			this.dismiss();
			break;
		default:
			break;
		}
	}

	@Override
	public void onDateChanged(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
		this.year = year;
		this.monthOfYear = monthOfYear;
		this.dayOfMonth = dayOfMonth;
	}
}
