package com.smart.smartutils.utils;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.widget.Toast;

public class ToastUtils {

    private static volatile ToastUtils sToastUtil = null;

    private Toast mToast = null;

    /**
     * 获取实例
     *
     * @return
     */
    public static ToastUtils getInstance() {
        if (sToastUtil == null) {
            synchronized (ToastUtils.class) {
                if (sToastUtil == null) {
                    sToastUtil = new ToastUtils();
                }
            }
        }
        return sToastUtil;
    }

    protected Handler handler = new Handler(Looper.getMainLooper());

    /**
     * 显示Toast，多次调用此函数时，Toast显示的时间不会累计，并且显示内容为最后一次调用时传入的内容
     * 持续时间默认为short
     *
     * @param tips 要显示的内容
     *             {@link Toast#LENGTH_LONG}
     */
    public void showToast(Context context, final String tips) {
        showToast(context, tips, Toast.LENGTH_SHORT);
    }

    public void showToast(Context context, final int tips) {
        showToast(context, tips, Toast.LENGTH_SHORT);
    }

    /**
     * 显示Toast，多次调用此函数时，Toast显示的时间不会累计，并且显示内容为最后一次调用时传入的内容
     *
     * @param tips     要显示的内容
     * @param duration 持续时间，参见{@link Toast#LENGTH_SHORT}和
     *                 {@link Toast#LENGTH_LONG}
     */
    public void showToast(final Context context, final String tips, final int duration) {
        if (android.text.TextUtils.isEmpty(tips)) {
            return;
        }
        handler.post(new Runnable() {
            @Override
            public void run() {
                if (mToast == null) {
                    mToast = Toast.makeText(context, tips, duration);
                    mToast.show();
                } else {
                    //mToast.cancel();
                    //mToast.setView(mToast.getView());
                    mToast.setText(tips);
                    mToast.setDuration(duration);
                    mToast.show();
                }
            }
        });
    }

    public void showToast(final Context context, final int tips, final int duration) {
        handler.post(new Runnable() {
            @Override
            public void run() {
                if (mToast == null) {
                    mToast = Toast.makeText(context, tips, duration);
                    mToast.show();
                } else {
                    //mToast.cancel();
                    //mToast.setView(mToast.getView());
                    mToast.setText(tips);
                    mToast.setDuration(duration);
                    mToast.show();
                }
            }
        });
    }
}