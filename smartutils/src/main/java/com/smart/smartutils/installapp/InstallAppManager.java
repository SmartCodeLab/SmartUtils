package com.smart.smartutils.installapp;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;

import com.smart.smartutils.utils.AppUtils;
import com.smart.smartutils.utils.DeviceUtils;
import com.smart.smartutils.utils.EncryptionUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by fengjh on 17/4/26.
 */

public class InstallAppManager {

    private InstallAppQuery mInstallAppQuery;

    public InstallAppManager(InstallAppQuery installAppQuery) {
        this.mInstallAppQuery = installAppQuery;
    }

    public void getInstallAllApp(Context context) {
        mInstallAppQuery.startQuery();
        ArrayList<InstallAppInfo> allAppList = new ArrayList<>();
        ArrayList<InstallAppInfo> systemAppList = new ArrayList<>();
        ArrayList<InstallAppInfo> thirdAppList = new ArrayList<>();
        PackageManager pm = context.getPackageManager();
        List<PackageInfo> packageInfoList = pm.getInstalledPackages(0);
        if (packageInfoList != null && packageInfoList.size() > 0) {
            for (PackageInfo info : packageInfoList) {
                boolean systemInstall = AppUtils.systemInstall(info);
                InstallAppInfo appInfo = new InstallAppInfo();
                appInfo.setIcon(pm.getApplicationIcon(info.applicationInfo));
                appInfo.setName(pm.getApplicationLabel(info.applicationInfo).toString());
                appInfo.setPackageName(info.applicationInfo.packageName);
                appInfo.setVersionCode("" + info.versionCode);
                appInfo.setVersionName(info.versionName);
                appInfo.setSystem(systemInstall);
                appInfo.setMd5(EncryptionUtils.getAppMD5Sign(context, info.applicationInfo.packageName));
                if (systemInstall) {
                    systemAppList.add(appInfo);
                } else {
                    thirdAppList.add(appInfo);
                }
                allAppList.add(appInfo);
            }
        }
        mInstallAppQuery.endQuery();
        mInstallAppQuery.onQueryAll(allAppList);
        mInstallAppQuery.onQueryAllSystem(systemAppList);
        mInstallAppQuery.onQueryAllThird(thirdAppList);
    }

    public void getInstallAllLaunchApp(Context context) {
        mInstallAppQuery.startQuery();
        ArrayList<InstallAppInfo> allAppList = new ArrayList<>();
        ArrayList<InstallAppInfo> systemAppList = new ArrayList<>();
        ArrayList<InstallAppInfo> thirdAppList = new ArrayList<>();
        PackageManager pm = context.getPackageManager();
        Intent mainIntent = new Intent(Intent.ACTION_VIEW, null);
        mainIntent.addCategory(Intent.CATEGORY_LAUNCHER);
        List<ResolveInfo> resolveInfoList = pm.queryIntentActivities(mainIntent, 0);
        if (resolveInfoList != null && resolveInfoList.size() > 0) {
            Collections.sort(resolveInfoList, new ResolveInfo.DisplayNameComparator(pm));
            for (ResolveInfo reInfo : resolveInfoList) {
                String packageName = reInfo.activityInfo.packageName;
                boolean systemInstall = AppUtils.systemInstall(pm, packageName);
                InstallAppInfo appInfo = new InstallAppInfo();
                appInfo.setName(reInfo.loadLabel(pm).toString());
                appInfo.setPackageName(packageName);
                appInfo.setIcon(reInfo.loadIcon(pm));
                appInfo.setSystem(systemInstall);
                appInfo.setVersionCode(DeviceUtils.getVersionCode(pm, packageName));
                appInfo.setVersionName(DeviceUtils.getVersionName(pm, packageName));
                appInfo.setMd5(EncryptionUtils.getAppMD5Sign(context, packageName));
                if (systemInstall) {
                    systemAppList.add(appInfo);
                } else {
                    thirdAppList.add(appInfo);
                }
                allAppList.add(appInfo);
            }
        }
        mInstallAppQuery.endQuery();
        mInstallAppQuery.onQueryAllLaunch(allAppList);
        mInstallAppQuery.onQueryAllSystemLaunch(systemAppList);
        mInstallAppQuery.onQueryAllThirdLaunch(thirdAppList);
    }
}
