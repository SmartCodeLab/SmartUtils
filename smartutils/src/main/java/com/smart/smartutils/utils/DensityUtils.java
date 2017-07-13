package com.smart.smartutils.utils;

import android.app.Activity;
import android.content.Context;
import android.graphics.Rect;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;

/**
 * Copyright © 2015 Phoenix New Media Limited All Rights Reserved.
 * Created by fengjh on 2015/6/12.
 */
public class DensityUtils {
    /**
     * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
     */
    public static int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    /**
     * 根据手机的分辨率从 px(像素) 的单位 转成为 dp
     */
    public static int px2dip(Context context, float pxValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }

    /**
     * 获取手机屏幕的高度
     */
    public static int screenHeight(Context context){
        int heightPx = context.getResources().getDisplayMetrics().heightPixels;
        return  heightPx;
    }
    /**
     * 获取手机屏幕的宽度
     */
    public static int screenWidth(Context context){
        int widthPixels = context.getResources().getDisplayMetrics().widthPixels;
        return  widthPixels;
    }

    /**
     * 获取控件的宽度
     * @param view 要获取宽度的控件
     * @return 控件的宽度
     */
    public static int viewWidth(View view){
        int w = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
        int h = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
        view.measure(w,h);
        return view.getMeasuredWidth();
    }

    /**
     * 获取控件的高度
     * @param view  要获取高度的控件
     * @return 控件的高度
     */
    public static int viewHeight(View view){
        int w = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
        int h = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
        view.measure(w,h);
        return view.getMeasuredHeight();
    }

    /**
     * 获取控件在窗体中的位置
     * @param view 要获取位置的控件
     * @return 控件位置的数组
     */
    public static int[] getViewLocationInWindow(View view){
        int[] location = new int[2];
        view.getLocationInWindow(location);
        return location;
    }

    /**
     * 获取控件在整个屏幕中的 位置
     * @param view 要获取位置的控件
     * @return 控件位置的数组
     */
    public static int[] getViewLocationInScreen(View view){
        int[] location = new int[2];
        view.getLocationOnScreen(location);
        return location;
    }

    /**
     * 获取屏幕状态栏高度
     * @param activity 获取屏幕高度对应的Activity
     * @return 状态栏高度
     */
    public static int getStatusHeight(Activity activity){
        Rect rect = new Rect();
        activity.getWindow().getDecorView().getWindowVisibleDisplayFrame(rect);
        return rect.top;
    }

    /**
     * 获取设备的尺寸
     * @param context
     * @return
     */
    public static double getDeviceSize(Context context){
        DisplayMetrics dm = context.getResources().getDisplayMetrics();
        double sqrt = Math.sqrt(Math.pow(dm.widthPixels, 2) + Math.pow(dm.heightPixels, 2));
        double screenSize = sqrt/(160*dm.density);
        return screenSize;
    }

    public static DisplayMetrics getDisplayMetrics(Context context){
        if(context==null){
            throw  new IllegalArgumentException("context can't null");
        }
        WindowManager windowManager= (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        Display display=windowManager.getDefaultDisplay();
        DisplayMetrics displayMetrics=new DisplayMetrics();
        display.getMetrics(displayMetrics);
        return displayMetrics;
    }

    public static int getWindowWidth(Context context){
        DisplayMetrics metrics=getDisplayMetrics(context);
        return  metrics.widthPixels;
    }

    public static int getWindowHeight(Context context){
        DisplayMetrics metrics=getDisplayMetrics(context);
        return metrics.heightPixels;
    }
}
