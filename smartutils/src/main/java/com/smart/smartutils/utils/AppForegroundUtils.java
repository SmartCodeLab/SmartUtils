package com.smart.smartutils.utils;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.text.TextUtils;

import java.util.HashSet;
import java.util.Set;

/**
 * 判断App是处在前台还是后台,包括是否锁屏.
 */
public enum AppForegroundUtils {
    INSTANCE;
    private static final String TAG = "AppForegroundUtils";

    private Set<OnAppStateChangeListener> mListeners = new HashSet<OnAppStateChangeListener>();
    private AppForegroundState mForegroundState = AppForegroundState.BACKGROUND;
    private ScreenBroadcastReceiver mScreenReceiver = new ScreenBroadcastReceiver();
    private long mStartTime = 0L;
    private long mEndTime = 0L;
    private boolean isAppBackground = false;

    /**
     * screen状态广播接收者
     */
    private class ScreenBroadcastReceiver extends BroadcastReceiver {
        private String action = "";
        @Override
        public void onReceive(Context context, Intent intent) {
            action = intent.getAction();
            if (Intent.ACTION_SCREEN_ON.equals(action)) { // 开屏
                if (mForegroundState == AppForegroundState.FOREGROUND) {
                    notifyOnStateChange(false, null, ScreenState.ON);
                }
            } else if (Intent.ACTION_SCREEN_OFF.equals(action)) { // 锁屏
                if (mForegroundState == AppForegroundState.FOREGROUND) {
                    notifyOnStateChange(false, null, ScreenState.OFF);
                }
                isAppBackground = true;
            }
        }
    }

    public enum AppForegroundState {
        FOREGROUND, //前台
        BACKGROUND  //后台
    }

    public enum ScreenState {
        ON,         //亮屏
        OFF         //锁屏
    }

    public interface OnAppStateChangeListener {
        void onAppForegroundChange(AppForegroundState state);
        void onScreenStateChange(ScreenState state);
    }

    public void init(Context context) {
        registerReceiver(context);
    }

    public void registerListener(OnAppStateChangeListener listener) {
        if (listener != null) {
            mListeners.add(listener);
        }
    }

    public void unRegisterListener(OnAppStateChangeListener listener) {
        if (listener != null) {
            mListeners.remove(listener);
        }
    }

    public void onActivityResume(Activity activity) {
        AppForegroundState currentState = getCurrentAppState(activity);
        if (currentState != mForegroundState) {
            mForegroundState = currentState;
            notifyOnStateChange(true, currentState, null);
        }
    }

    public void onActivityStop(Activity activity) {
        AppForegroundState currentState = getCurrentAppState(activity);
        if (currentState != mForegroundState) {
            if (currentState == AppForegroundState.BACKGROUND) {
                isAppBackground = true;
            }
            mForegroundState = currentState;
            notifyOnStateChange(true, currentState, null);
        }
    }

    private void registerReceiver(Context context) {
        IntentFilter filter = new IntentFilter();
        filter.addAction(Intent.ACTION_SCREEN_ON);
        filter.addAction(Intent.ACTION_SCREEN_OFF);
        filter.addAction(Intent.ACTION_USER_PRESENT);
        context.registerReceiver(mScreenReceiver, filter);
    }

    private void notifyOnStateChange(boolean isForegroundChange, AppForegroundState state, ScreenState _state) {
        if (mListeners != null && mListeners.size() > 0) {
            for(OnAppStateChangeListener listener : mListeners) {
                if (isForegroundChange) {
                    listener.onAppForegroundChange(state);
                }else {
                    listener.onScreenStateChange(_state);
                }
            }
        }
    }

    private AppForegroundState getCurrentAppState(Context context) {
        String topActivity = PackageUtils.getTopActivityName(context);
        if (TextUtils.isEmpty(topActivity)) {
            return AppForegroundState.BACKGROUND;
        }else {
            if (topActivity.startsWith(context.getPackageName()) || topActivity.equals("com.sina.weibo.composerinde.OriginalComposerActivity")
                    || topActivity.startsWith("com.ifeng.commons") || topActivity.startsWith("com.ifeng.images")
                    || topActivity.startsWith("com.ifeng.photopicker") || topActivity.equals("com.mob.tools.MobUIShell")
                    || topActivity.equals("com.tencent.mm.ui.transmit.SelectConversationUI") || topActivity.startsWith("com.ifeng.loginsharesdk")
                    || topActivity.equals("com.tencent.mm.plugin.sns.ui.SnsUploadUI") || topActivity.equals("com.tencent.mobileqq.activity.ForwardRecentActivity")) {
                return AppForegroundState.FOREGROUND;
            }else {
                return AppForegroundState.BACKGROUND;
            }
        }
    }

    public long getStartTime() {
        return mStartTime;
    }

    public void setStartTime(long mStartTime) {
        this.mStartTime = mStartTime;
    }

    public long getEndTime() {
        return mEndTime;
    }

    public void setEndTime(long mEndTime) {
        this.mEndTime = mEndTime;
    }

    public boolean isAppBackground() {
        return isAppBackground;
    }

    public void setAppBackground(boolean isAppBackground) {
        this.isAppBackground = isAppBackground;
    }
}
