package com.smart.smartutils.base.support;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.View;
import android.widget.Toast;

import com.smart.smartutils.utils.AppForegroundUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * Copyright © 2016 Phoenix New Media Limited All Rights Reserved.
 * Created by fengjh on 16/2/14.
 */
public abstract class BaseActivity extends AppCompatActivity {

    protected boolean isActivityAlive = true;
    private Toast mToast;
    private long mLastBackClickTime = 0L;
    private long mExitInterval = 2000L;
    private boolean doubleBack = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutResource());
        isActivityAlive = true;
    }

    public <T extends View> T getViewById(@IdRes int id) {
        return getViewById(null, id);
    }

    public <T extends View> T getViewById(View view, @IdRes int id) {
        if (null == view) {
            return (T) findViewById(id);
        }
        return (T) view.findViewById(id);
    }

    public <T extends Fragment> T findFragmentById(@IdRes int id) {
        FragmentManager manager = getSupportFragmentManager();
        return findFragmentById(manager, id);
    }

    public <T extends Fragment> T findFragmentById(FragmentManager manager, @IdRes int id) {
        return (T) manager.findFragmentById(id);
    }

    public abstract int getLayoutResource();

    public void openExtraActivity(Class<?> cls, String scheme) {
        Intent intent = new Intent(this, cls);
        intent.setAction(Intent.ACTION_VIEW);
        intent.setData(Uri.parse(scheme));
        startActivity(intent);
    }

    public void openExtraActivity(Class<?> cls, HashMap<String, String> data) {
        Intent intent = new Intent(this, cls);
        intent.setAction(Intent.ACTION_VIEW);
        for (Map.Entry<String, String> entry : data.entrySet()) {
            intent.putExtra(entry.getKey(), entry.getValue());
        }
        startActivity(intent);
    }

    public void openActivity(Class<?> cls) {
        openActivity(cls, null);
    }

    public void openActivity(Class<?> cls, Bundle bundle) {
        Intent intent = new Intent(this, cls);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        startActivity(intent);
    }

    public void makeToast(String s) {
        if (mToast == null) {
            mToast = Toast.makeText(this, s, Toast.LENGTH_SHORT);
        } else {
            mToast.setText(s);
        }
        mToast.setGravity(Gravity.CENTER, 0, 0);
        mToast.show();
    }

    public void openActivityForResult(Class<?> cls, Bundle bundle, int requestCode) {
        Intent intent = new Intent(this, cls);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        startActivityForResult(intent, requestCode);
    }

    @Override
    protected void onResume() {
        super.onResume();
        AppForegroundUtils.INSTANCE.onActivityResume(this);
    }

    @Override
    protected void onStop() {
        super.onStop();
        AppForegroundUtils.INSTANCE.onActivityStop(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        isActivityAlive = false;
    }

    public void setDoubleBack(boolean doubleBack) {
        this.doubleBack = doubleBack;
    }

    @Override
    public void onBackPressed() {
        if (doubleBack) {
            if ((System.currentTimeMillis() - mLastBackClickTime) > mExitInterval) {
                Toast.makeText(this, "再按一次退出程序", Toast.LENGTH_SHORT).show();
                mLastBackClickTime = System.currentTimeMillis();
            } else {
                super.onBackPressed();
            }
        } else {
            super.onBackPressed();
        }
    }

}
