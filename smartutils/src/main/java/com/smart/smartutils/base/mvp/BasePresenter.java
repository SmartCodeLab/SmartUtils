package com.smart.smartutils.base.mvp;

import java.lang.ref.Reference;
import java.lang.ref.WeakReference;

/**
 * Created by fengjh on 17/2/2.
 */

public class BasePresenter<T> {

    protected Reference<T> mReference;

    public void attachView(T view) {
        mReference = new WeakReference<T>(view);
    }

    public T getView() {
        return mReference.get();
    }

    public boolean isViewAttached() {
        return mReference != null && mReference.get() != null;
    }

    public void detachView() {
        if (mReference != null) {
            mReference.clear();
            mReference = null;
        }
    }
}
