package com.smart.smartutils.installapp;

import java.util.List;

/**
 * Created by fengjh on 17/4/26.
 */

interface InstallQuery {

    void startQuery();

    void endQuery();

    void onQueryAll(List<InstallAppInfo> appInfoList);

    void onQueryAllSystem(List<InstallAppInfo> appInfoList);

    void onQueryAllThird(List<InstallAppInfo> appInfoList);

    void onQueryAllLaunch(List<InstallAppInfo> appInfoList);

    void onQueryAllSystemLaunch(List<InstallAppInfo> appInfoList);

    void onQueryAllThirdLaunch(List<InstallAppInfo> appInfoList);
}
