package com.smart.smartutils.utils;

import android.content.Context;
import android.view.Gravity;
import android.widget.Toast;

/**
 * Created by fengjh on 16/9/20.
 */

public class ToastCenterUtils {

    public static void makeText(Context context, int resId) {
        makeText(context, resId);
    }

    public static void makeText(Context context, int resId, int duration) {
        Toast toast = Toast.makeText(context, resId, Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.show();
    }

    public static void makeText(Context context, String message) {
        makeText(context, message, Toast.LENGTH_SHORT);
    }

    public static void makeText(Context context, String message, int duration) {
        Toast toast = Toast.makeText(context, message, Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.show();
    }
}
