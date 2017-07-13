package com.smart.smartutils.utils;

import android.util.Log;

/**
 * 日志工具相关类
 * Copyright © 2016 Phoenix New Media Limited All Rights Reserved.
 * Created by fengjh on 2016/1/29.
 */
public class LogUtils {

    private static String TAG = "LogUtil";


    private static final int IS_VERBOSE = 1;
    private static final int IS_INFO = 2;
    private static final int IS_WARN = 3;
    private static final int IS_DEBUG = 4;
    private static final int IS_ERROR = 5;
    private static final int IS_PRINT = 6;
    private static int LEVEL = 0;

    public static void setTag(String tag) {
        TAG = tag;
    }

    public static void setLevel(int level) {
        LEVEL = level;
    }

    public static final void v(String msg) {
        if (LEVEL <= IS_VERBOSE) {
            Log.v(TAG, msg);
        }
    }

    public static final void i(String msg) {
        if (LEVEL <= IS_INFO) {
            Log.i(TAG, msg);
        }
    }

    public static final void w(String msg) {
        if (LEVEL <= IS_WARN) {
            Log.w(TAG, msg);
        }
    }

    public static final void d(String msg) {
        if (LEVEL <= IS_DEBUG) {
            Log.d(TAG, msg);
        }
    }

    public static final void e(String msg) {
        if (LEVEL <= IS_ERROR) {
            Log.e(TAG, msg);
        }
    }

    public static final void v(String msg, Throwable e) {
        if (LEVEL <= IS_VERBOSE) {
            Log.v(TAG, msg, e);
        }
    }

    public static final void i(String msg, Throwable e) {
        if (LEVEL <= IS_INFO) {
            Log.i(TAG, msg, e);
        }
    }

    public static final void w(String msg, Throwable e) {
        if (LEVEL <= IS_WARN) {
            Log.w(TAG, msg, e);
        }
    }

    public static final void d(String msg, Throwable e) {
        if (LEVEL <= IS_DEBUG) {
            Log.d(TAG, msg, e);
        }
    }

    public static final void e(String msg, Throwable e) {
        if (LEVEL <= IS_ERROR) {
            Log.e(TAG, msg, e);
        }
    }

    public static final void v(String tag, String msg) {
        if (LEVEL <= IS_VERBOSE) {
            Log.v(tag, msg);
        }
    }

    public static final void i(String tag, String msg) {
        if (LEVEL <= IS_INFO) {
            Log.i(tag, msg);
        }
    }

    public static final void w(String tag, String msg) {
        if (LEVEL <= IS_WARN) {
            Log.w(tag, msg);
        }
    }

    public static final void d(String tag, String msg) {
        if (LEVEL <= IS_DEBUG) {
            Log.d(tag, msg);
        }
    }

    public static final void e(String tag, String msg) {
        if (LEVEL <= IS_ERROR) {
            Log.e(tag, msg);
        }
    }

    public static final void v(String tag, String msg, Throwable e) {
        if (LEVEL <= IS_VERBOSE) {
            Log.v(tag, msg, e);
        }
    }

    public static final void i(String tag, String msg, Throwable e) {
        if (LEVEL <= IS_INFO) {
            Log.i(tag, msg, e);
        }
    }

    public static final void w(String tag, String msg, Throwable e) {
        if (LEVEL <= IS_WARN) {
            Log.w(tag, msg, e);
        }
    }

    public static final void d(String tag, String msg, Throwable e) {
        if (LEVEL <= IS_DEBUG) {
            Log.d(tag, msg, e);
        }
    }

    public static final void e(String tag, String msg, Throwable e) {
        if (LEVEL <= IS_ERROR) {
            Log.e(tag, msg, e);
        }
    }

    public static final void print(String msg) {
        if (LEVEL <= IS_PRINT) {
            System.out.println(msg);
        }
    }


}

