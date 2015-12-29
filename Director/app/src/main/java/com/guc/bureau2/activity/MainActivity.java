package com.guc.bureau2.activity;

import java.io.File;
import java.util.Timer;
import java.util.TimerTask;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.guc.bureau2.R;
import com.guc.bureau2.application.GucApplication;
import com.guc.bureau2.base.BaseActivity;
import com.guc.bureau2.fragment.SplashFragment;
import com.guc.bureau2.net.DefaultErrorListener;
import com.guc.bureau2.net.GucNetEnginClient;
import com.guc.bureau2.utils.DownLoadManager;
import com.guc.bureau2.utils.ToastUtils;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.FragmentManager.OnBackStackChangedListener;
import android.text.TextUtils;
import android.view.KeyEvent;

public class MainActivity extends BaseActivity {
    private FragmentManager mFragmentManager;
    private boolean isLogin = false;
    private boolean isExit;
    private boolean isBack;
    private int cur_version;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setListeners();
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                checkUpdate();
            }
        }, 2000);
    }

    @Override
    protected void initWidget() {
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (GucApplication.status != 2) {
            replace(SplashFragment.newInstance(), false);
        }
    }

    @Override
    public void popBackStack(int i) {
        for (int j = 0; j < i; j++) {
            mFragmentManager.popBackStack();
        }
    }

    @Override
    public void replace(Fragment fragment, boolean isaddToBackStack) {
        FragmentTransaction fragmentTransaction = mFragmentManager.beginTransaction();
        if (isaddToBackStack) {
            fragmentTransaction.replace(android.R.id.content, fragment).addToBackStack(null).commitAllowingStateLoss();
        } else {
            fragmentTransaction.replace(android.R.id.content, fragment).commitAllowingStateLoss();
        }
    }

    @Override
    public void replace(String tag, Fragment fragment, boolean isaddToBackStack) {
        FragmentTransaction fragmentTransaction = mFragmentManager.beginTransaction();
        if (isaddToBackStack) {
            fragmentTransaction.replace(android.R.id.content, fragment, tag).addToBackStack(null).commitAllowingStateLoss();
        } else {
            fragmentTransaction.replace(android.R.id.content, fragment, tag).commitAllowingStateLoss();
        }
    }

    @Override
    protected void setListeners() {
        mFragmentManager = getSupportFragmentManager();
        mFragmentManager.addOnBackStackChangedListener(new OnBackStackChangedListener() {
            @Override
            public void onBackStackChanged() {
                int stackCount = mFragmentManager.getBackStackEntryCount();
                isBack = stackCount > 0;
            }
        });
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && !isBack) {
            if (GucApplication.status == 0) {
                return true;
            } else if (GucApplication.status == 2 && !isBack) {
                exitBy2Click();
                return true;
            }
        }
        return super.onKeyDown(keyCode, event);
    }

    private void exitBy2Click() {
        Timer tExit;
        if (isExit) {
            finish();
            System.exit(0);
        } else {
            isExit = true;
            ToastUtils.showShort(getApplicationContext(), R.string.click_two_exit);
            tExit = new Timer();
            tExit.schedule(new TimerTask() {
                @Override
                public void run() {
                    isExit = false;
                }
            }, 2000);
        }
    }

    @Override
    public void setStatus(boolean status) {
        isLogin = status;
    }

    private void checkUpdate() {
        GucNetEnginClient.getVersion(new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                JSONObject object = JSON.parseObject(response);
                JSONObject obj_res = object.getJSONObject("getVersionResult");
                String cur_version_str = obj_res.getString("result");
                String errInfo = obj_res.getString("errInfo");
                if (errInfo != null) {
                    ToastUtils.showLong(MainActivity.this, errInfo);
                    return;
                }
                if (!TextUtils.isEmpty(cur_version_str)) {
                    int loaction = Integer.parseInt(getVersionName());
                    cur_version = Integer.parseInt(cur_version_str);
                    if (cur_version > loaction) {
                        showUpdateDialog();
                    }
                }
            }
        }, new DefaultErrorListener());
    }

    protected void showUpdateDialog() {
        AlertDialog dialog;
        AlertDialog.Builder builer = new AlertDialog.Builder(this);
        builer.setTitle("版本升级");
        builer.setMessage("检测到到新版本，请及时更新!");
        builer.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                getAppURL();
            }
        });
        builer.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        dialog = builer.create();
        dialog.show();
    }

    private void getAppURL() {
        GucNetEnginClient.getAppUrl(cur_version + "", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                JSONObject jsonObject = JSON.parseObject(response);
                JSONObject obj_res = jsonObject.getJSONObject("getAppURLResult");
                String url = obj_res.getString("result");
                downLoadApk(url);
            }
        }, new DefaultErrorListener());
    }

    protected void downLoadApk(final String url) {
        final ProgressDialog pd;
        pd = new ProgressDialog(this);
        pd.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        pd.setMessage("正在下载更新");
        pd.show();
        new Thread() {
            @Override
            public void run() {
                try {
                    File file = DownLoadManager.getFileFromServer(url, pd);
                    sleep(3000);
                    installApk(file);
                    pd.dismiss(); //结束掉进度条对话框
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }

    //安装apk
    protected void installApk(File file) {
        Intent intent = new Intent();
        //执行动作
        intent.setAction(Intent.ACTION_VIEW);
        //执行的数据类型
        intent.setDataAndType(Uri.fromFile(file), "application/vnd.android.package-archive");
        startActivity(intent);
    }

    private String getVersionName() {
        PackageManager packageManager = getPackageManager();
        PackageInfo packInfo;
        try {
            packInfo = packageManager.getPackageInfo(getPackageName(), 0);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            return "";
        }
        return packInfo.versionName;
    }
}
