package com.smart.smartutils.base.mvp;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.smart.smartutils.base.support.BaseEditActivity;

/**
 * Created by fengjh on 17/2/2.
 */

public abstract class BaseMVPEditActivity<V, T extends BasePresenter<V>> extends BaseEditActivity {

    protected T mPresenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPresenter = createPresenter();
        mPresenter.attachView((V) this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (null != mPresenter) {
            mPresenter.detachView();
        }
    }

    protected abstract T createPresenter();
}
