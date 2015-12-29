package com.guc.bureau2.fragment;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.VolleyError;
import com.guc.bureau2.R;
import com.guc.bureau2.application.GucApplication;
import com.guc.bureau2.base.BaseFragment;
import com.guc.bureau2.base.GucBaseAdapter;
import com.guc.bureau2.domain.BureauInfoResponseDTO;
import com.guc.bureau2.net.GucNetEnginClient;
import com.guc.bureau2.utils.GucPreferenceUtils;
import com.guc.bureau2.utils.StrUtil;
import com.guc.bureau2.utils.ToastUtils;

import android.content.Context;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.InputType;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;
import android.widget.TextView;

public class LoginFragment extends BaseFragment implements OnCheckedChangeListener {
    private TextView tv_login;
    private TextView tv_register;
    private EditText input_account;
    private EditText input_password;
    private CheckBox cb_save_password;
    private CheckBox cb_show_password;
    private boolean isRemember;
    private String account;
    private String password;
    private boolean isShowPwd;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return initView(inflater, container, R.layout.fragment_login);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_login:
                getDbid();
                hideSoftInput();
                break;
            case R.id.tv_register:
                mActivity.replace("registerfragment", RegisterFragment.newInstance(), true);
                break;
            default:
                break;
        }
    }

    private void hideSoftInput() {
        InputMethodManager imm = (InputMethodManager) mActivity.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(input_account.getWindowToken(), 0);
    }

    @Override
    protected void initData() {
        controlBar(R.string.login, R.string.login, false, false);
        if (GucPreferenceUtils.getInstance().getIsSavePwd()) {
            cb_save_password.setChecked(true);
            input_account.setText(GucApplication.getInstance().getUserName());
            input_password.setText(GucApplication.getInstance().getPassword());
        }
        if (GucPreferenceUtils.getInstance().getIsShowPwd()) {
            cb_show_password.setChecked(true);
        }
        GucApplication.status = 1;
    }

    @Override
    protected void setListeners() {
        tv_login.setOnClickListener(this);
        tv_register.setOnClickListener(this);
        cb_save_password.setOnCheckedChangeListener(this);
        cb_show_password.setOnCheckedChangeListener(this);

    }

    @Override
    protected void initWidget(View view) {
        input_account = (EditText) view.findViewById(R.id.input_account);
        input_password = (EditText) view.findViewById(R.id.input_password);
        cb_save_password = (CheckBox) view.findViewById(R.id.cb_save_password);
        cb_show_password = (CheckBox) view.findViewById(R.id.cb_show_password);
        tv_register = (TextView) view.findViewById(R.id.tv_register);
        tv_login = (TextView) view.findViewById(R.id.tv_login);
        tv_register.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG);
        tv_register.getPaint().setAntiAlias(true);
    }

    private void getDbid() {
        account = input_account.getText().toString().trim();
        password = input_password.getText().toString().trim();
        //account="18988262866";
        account = "18087309777";
        password = "superman12345";
        //account = "13800138000";
        //password = "hiswosun";
        if (TextUtils.isEmpty(account)) {
            ToastUtils.showShort(mActivity, R.string.account_not_null);
            return;
        }
        if (TextUtils.isEmpty(password) || !StrUtil.checkPwd(password)) {
            ToastUtils.showShort(mActivity, R.string.psssword_not_null);
            return;
        }
        mProgressDialog.setMessage(getResources().getString(R.string.Is_landing));
        mProgressDialog.show();
        GucNetEnginClient.getDbid(account, new Listener<String>() {
            @Override
            public void onResponse(String response) {
                JSONObject jsonObject = JSON.parseObject(response);
                JSONObject resobj = jsonObject.getJSONObject("getDBIDResult");
                String errInfo = resobj.getString("errInfo");
                if (errInfo == null) {
                    GucNetEnginClient.DBID = resobj.getString("result");
                    /** get bureau info */
                    getBureauInfo(account, password);
                } else {
                    mProgressDialog.dismiss();
                    ToastUtils.showShort(mActivity, errInfo);
                }
            }
        }, new ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                mProgressDialog.dismiss();
            }
        });
    }

    private void getBureauInfo(final String account, final String password) {
        GucNetEnginClient.getBureauInfo(account, password, new Listener<String>() {
            @Override
            public void onResponse(String response) {
                mProgressDialog.dismiss();
                JSONObject jsonObject = JSON.parseObject(response);
                JSONObject result = jsonObject.getJSONObject("GetBureauInfoResult");
                String errInfo = result.getString("resultErrInfo");
                if (errInfo == null) {
                    BureauInfoResponseDTO responseDTO = JSON.parseObject(result.toJSONString(), BureauInfoResponseDTO.class);
                    GucNetEnginClient.ORG_CODE = responseDTO.getOrg_code();
                    saveSharData();
                    mActivity.replace("mainfragment", MainFragment.newInstance(), false);
                } else {
                    mProgressDialog.dismiss();
                    ToastUtils.showLong(mActivity, errInfo);
                }
            }
        }, new ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                mProgressDialog.dismiss();
            }
        });
    }

    private void saveSharData() {
        GucPreferenceUtils.getInstance().setIsSavePwd(isRemember);
        GucPreferenceUtils.getInstance().setIsShowPwd(isShowPwd);
        GucApplication.getInstance().setUserName(account);
        GucApplication.getInstance().setPassword(password);
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        switch (buttonView.getId()) {
            case R.id.cb_show_password:
                if (isChecked) {
                    input_password.setInputType(InputType.TYPE_CLASS_TEXT);
                    isShowPwd = true;
                } else {
                    input_password.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                    isShowPwd = false;
                }
                break;
            case R.id.cb_save_password:
                isRemember = isChecked;
                break;
            default:
                break;
        }
    }

    public static Fragment newInstance() {
        return new LoginFragment();
    }
}
