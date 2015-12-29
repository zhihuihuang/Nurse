package com.guc.bureau2.base;

import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.View.OnClickListener;

import com.guc.bureau2.listener.PopBackStack;
import com.guc.bureau2.listener.Replace;
import com.guc.bureau2.listener.Status;

public abstract class BaseActivity extends AppCompatActivity implements OnClickListener, PopBackStack, Replace, Status {

    @Override
    public void setContentView(int layoutResID) {
        super.setContentView(layoutResID);
        initWidget();
        setListeners();
    }

    protected abstract void setListeners();

    protected abstract void initWidget();

    @Override
    public void onClick(View v) {

    }
}
