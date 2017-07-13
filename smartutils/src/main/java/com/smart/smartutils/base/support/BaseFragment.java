package com.smart.smartutils.base.support;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

/**
 * Copyright © 2016 Phoenix New Media Limited All Rights Reserved.
 * Created by fengjh on 16/2/18.
 */
public abstract class BaseFragment extends Fragment {

    private View mRootView;
    private Toast mToast;
    protected boolean isFragmentAlive = true;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mRootView = inflater.inflate(getLayoutResource(), container, false);
        return mRootView;
    }

    public abstract int getLayoutResource();

    public <T extends View> T getViewById(@IdRes int id) {
        if (null == mRootView)
            return getViewById(null, id);
        return getViewById(mRootView, id);
    }

    public <T extends View> T getViewById(View view, @IdRes int id) {
        if (null == view) {
            return (T) getActivity().findViewById(id);
        }
        return (T) view.findViewById(id);
    }

    public <T extends Fragment> T findFragmentById(@IdRes int id) {
        FragmentManager manager = getFragmentManager();
        return findFragmentById(manager, id);
    }

    public <T extends Fragment> T findFragmentById(FragmentManager manager, @IdRes int id) {
        return (T) manager.findFragmentById(id);
    }

    public <T extends Fragment> T findChildFragmentById(@IdRes int id) {
        FragmentManager manager = getChildFragmentManager();
        return findChildFragmentById(manager, id);
    }

    public <T extends Fragment> T findChildFragmentById(FragmentManager manager, @IdRes int id) {
        return (T) manager.findFragmentById(id);
    }

    public void openActivity(Class<?> cls) {
        openActivity(cls, null);
    }

    public void openActivity(Class<?> cls, Bundle bundle) {
        Intent intent = new Intent(getActivity(), cls);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        startActivity(intent);
    }

    public void makeToast(String s) {
        if (mToast == null) {
            mToast = Toast.makeText(getActivity(), s, Toast.LENGTH_SHORT);
        } else {
            mToast.setText(s);
        }
        mToast.setGravity(Gravity.CENTER, 0, 0);
        mToast.show();
    }

    public void openActivityForResult(Class<?> cls, Bundle bundle, int requestCode) {
        Intent intent = new Intent(getActivity(), cls);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        startActivityForResult(intent, requestCode);
    }

    @Override
    public void onResume() {
        super.onResume();
        isFragmentAlive = true;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        isFragmentAlive = false;
    }
}
