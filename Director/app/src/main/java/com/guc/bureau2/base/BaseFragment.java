package com.guc.bureau2.base;

import com.guc.bureau2.R;
import com.guc.bureau2.listener.ChangeTitle;

import android.app.ProgressDialog;
import android.content.Context;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

public abstract class BaseFragment extends Fragment implements OnClickListener, ChangeTitle {
    protected BaseActivity mActivity;
    protected LinearLayout right_layout;
    protected TextView tv_title;
    protected LinearLayout ll_back;
    protected TextView tv_back;
    public ProgressDialog mProgressDialog;

    @Override
    public void onAttach(Context activity) {
        super.onAttach(activity);
        mActivity = (BaseActivity) activity;
        mProgressDialog = new ProgressDialog(activity);
    }

    protected View initView(LayoutInflater inflater, ViewGroup container, int layout) {
        View view = inflater.inflate(layout, container,false);
        initBar(view);
        initWidget(view);
        setListeners();
        initData();
        return view;
    }

    public void showDialog(int str) {
        mProgressDialog.setMessage(getResources().getString(str));
        mProgressDialog.show();
    }

    public void dismiss() {
        if (mProgressDialog.isShowing()) {
            mProgressDialog.dismiss();
        }
    }

    private void initBar(View view) {
        ll_back = (LinearLayout) view.findViewById(R.id.ll_back);
        right_layout = (LinearLayout) view.findViewById(R.id.right_layout);
        tv_title = (TextView) view.findViewById(R.id.tv_title);
        tv_back = (TextView) view.findViewById(R.id.tv_back);
        if (tv_title != null) {
            tv_title.requestFocus();
        }
    }

    protected abstract void initData();

    protected abstract void setListeners();

    protected abstract void initWidget(View view);

    @Override
    public void controlBar(int resId, int backId, boolean isLeft, boolean isRight) {
        tv_title.setText(getResources().getString(resId));
        tv_back.setText(getResources().getString(backId));
        ll_back.setVisibility(isLeft ? View.VISIBLE : View.GONE);
        right_layout.setVisibility(isRight ? View.VISIBLE : View.GONE);
    }

    @Override
    public void controlBar(int resId, String backId, boolean isLeft, boolean isRight) {
        tv_title.setText(getResources().getString(resId));
        tv_back.setText(backId);
        ll_back.setVisibility(isLeft ? View.VISIBLE : View.GONE);
        right_layout.setVisibility(isRight ? View.VISIBLE : View.GONE);
    }

    @Override
    public void controlBar(String resId, int backId, boolean isLeft, boolean isRight) {
        tv_title.setText(resId);
        tv_back.setText(getResources().getString(backId));
        ll_back.setVisibility(isLeft ? View.VISIBLE : View.GONE);
        right_layout.setVisibility(isRight ? View.VISIBLE : View.GONE);
    }

    @Override
    public void controlBar(String resId, String backId, boolean isLeft, boolean isRight) {
        tv_title.setText(resId);
        tv_back.setText(backId);
        ll_back.setVisibility(isLeft ? View.VISIBLE : View.GONE);
        right_layout.setVisibility(isRight ? View.VISIBLE : View.GONE);
    }
}