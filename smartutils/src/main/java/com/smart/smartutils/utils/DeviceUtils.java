package com.smart.smartutils.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.telephony.TelephonyManager;

import java.util.UUID;

/**
 * 获取设备相关的数据的工具类
 * Copyright © 2016 Phoenix New Media Limited All Rights Reserved.
 * Created by fengjh on 2016/1/29.
 */
public class DeviceUtils {

    //移动设备类型
    public static final int PLATFORM_IOS = 1;
    public static final int PLATFORM_ANDROID = 2;
    public static final int PLATFORM_OTHER = 3;
    //移动设备系统类型
    public static final int TYPE_PHONE = 1;
    public static final int TYPE_PAD = 2;
    public static final int TYPE_TV = 3;
    public static final int TYPE_OTHER = 4;

    /**
     * 获取移动设备唯一ID
     *
     * @param ctx
     * @return
     */
    public static String getDeviceID(Context ctx) {
        TelephonyManager mTM = (TelephonyManager) ctx.getSystemService(Context.TELEPHONY_SERVICE);
        String sn = mTM.getDeviceId();
        if (sn == null || sn.equals("")) {
            SharedPreferences prefer = ctx.getSharedPreferences("com.ifeng.hystyle", Context.MODE_PRIVATE);
            sn = prefer.getString("DEVICE_ID", "");
            if ("".equals(sn)) {
                UUID uuid = UUID.randomUUID();
                sn = "IP-" + uuid.toString().substring(0, 12);
                SharedPreferences.Editor editor = prefer.edit();
                editor.putString("DEVICE_ID", sn);
                editor.commit();
            }
        } else {
            /*
             * 能够正常取到sn，一些特殊情况需要自己生成sn:
			 */
            String duplicationRegex = "^(.)\\1*$";
            if (sn.matches(duplicationRegex) || sn.length() < 8 || sn.contains("123456789")) {
                SharedPreferences prefer = ctx.getSharedPreferences("com.ifeng.hystyle", Context.MODE_PRIVATE);
                sn = prefer.getString("DEVICE_ID", "");
                if ("".equals(sn)) {
                    UUID uuid = UUID.randomUUID();
                    sn = "IP-" + uuid.toString().substring(0, 12);
                    SharedPreferences.Editor editor = prefer.edit();
                    editor.putString("DEVICE_ID", sn);
                    editor.commit();
                }
            }
        }
        return sn;
    }

    /**
     * 获取当前系统的SDK_INT
     *
     * @param ctx
     * @return
     */
    public static int getSystemVersion(Context ctx) {
        return Build.VERSION.SDK_INT;
    }

    /**
     * 获取当前系统的版本信息
     *
     * @param ctx
     * @return
     */
    public static String getSystemVersionInfo(Context ctx) {
        return Build.VERSION.RELEASE;
    }

    /**
     * 获取设备型号
     *
     * @param ctx
     * @return
     */
    public static String getModel(Context ctx) {
        return Build.MODEL;
    }

    /**
     * 获取设备品牌
     *
     * @param ctx
     * @return
     */
    public static String getBrand(Context ctx) {
        return Build.BRAND;
    }

    /**
     * 获取应用的版本
     *
     * @param context
     * @return
     */
    public static String getVersionName(Context context) {
        PackageManager packageManager = context.getPackageManager();
        return getVersionName(packageManager, context.getPackageName());
    }

    public static String getVersionName(PackageManager pm, String packageName) {
        String versionName = "";
        try {
            PackageInfo packageInfo = pm.getPackageInfo(packageName, 0);
            versionName = packageInfo.versionName;
        } catch (PackageManager.NameNotFoundException e) {
        }
        return versionName;
    }

    public static String getVersionCode(Context context) {
        PackageManager packageManager = context.getPackageManager();
        return getVersionCode(packageManager, context.getPackageName());
    }

    public static String getVersionCode(PackageManager pm, String packageName) {
        String versionCode = "";
        try {
            PackageInfo packageInfo = pm.getPackageInfo(packageName, 0);
            versionCode = packageInfo.versionCode + "";
        } catch (PackageManager.NameNotFoundException e) {
        }
        return versionCode;
    }
}


