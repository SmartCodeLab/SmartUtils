package com.smart.smartutils.utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.support.v4.content.FileProvider;

import java.io.File;

/**
 * Created by fengjh on 17/4/26.
 */

public class AppUtils {

    public static void launchAppStore(Context context) {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.parse("market://details?id=" + context.getPackageName()));
        context.startActivity(intent);
    }

    public static void launchApp(Context context, String packageName) {
        launchApp(context, packageName, false);
    }

    public static void launchApp(Context context, String packageName, boolean finishCurrentActivity) {
        PackageManager pm = context.getPackageManager();
        Intent intent = pm.getLaunchIntentForPackage(packageName);
        context.startActivity(intent);
        if (finishCurrentActivity) {
            if (context instanceof Activity) {
                ((Activity) context).finish();
            } else {
                throw new RuntimeException("context is not instanceof Activity");
            }
        }
    }

    public static boolean systemInstall(Context context, String pkg) {
        return systemInstall(context.getPackageManager(), pkg);
    }

    public static boolean systemInstall(PackageManager pm, String pkg) {
        boolean systemInstall;
        try {
            PackageInfo packageInfo = pm.getPackageInfo(pkg, 0);
            systemInstall = systemInstall(packageInfo);
        } catch (PackageManager.NameNotFoundException e) {
            systemInstall = false;
        }
        return systemInstall;
    }

    public static boolean systemInstall(PackageInfo packageInfo) {
        boolean systemInstall;
        if ((packageInfo.applicationInfo.flags & packageInfo.applicationInfo.FLAG_SYSTEM) <= 0) {
            //第三方应用
            systemInstall = false;
        } else {
            //系统应用
            systemInstall = true;
        }
        return systemInstall;
    }

    public static void launchInstall(Context context, File apkFile, String fileProviderAuthority) {
        String type = "application/vnd.android.package-archive";
        Intent installIntent = new Intent();
        installIntent.setAction(Intent.ACTION_VIEW);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            installIntent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            Uri uri = FileProvider.getUriForFile(context, fileProviderAuthority, apkFile);
            installIntent.setDataAndType(uri, type);
        } else {
            installIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            installIntent.setDataAndType(Uri.fromFile(apkFile), type);
        }
        context.startActivity(installIntent);
    }
}
