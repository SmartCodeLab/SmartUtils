package com.smart.smartutils.view;

import android.content.Context;
import android.support.v4.view.MotionEventCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.ViewConfiguration;

/**
 * Created by fengjh on 17/3/28.
 */

public class ViewPagerInSwipeRefreshLayout extends SwipeRefreshLayout {

    //非法按键
    private static final int INVALID_POINTER = -1;

    //dispatch方法记录第一次按下的x
    private float mInitialDisPatchDownX;

    //dispatch方法记录第一次按下的y
    private float mInitialDisPatchDownY;

    //dispatch方法记录的手指
    private int mActiveDispatchPointerId = INVALID_POINTER;

    //是否请求拦截
    private boolean hasRequestDisallowIntercept = false;

    private int mTouchSlop;

    public ViewPagerInSwipeRefreshLayout(Context context) {
        this(context, null);
    }

    public ViewPagerInSwipeRefreshLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        mTouchSlop = ViewConfiguration.get(context).getScaledTouchSlop();
    }

    @Override
    public void requestDisallowInterceptTouchEvent(boolean b) {
        hasRequestDisallowIntercept = b;
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                mActiveDispatchPointerId = MotionEventCompat.getPointerId(ev, 0);
                final float initialDownX = getMotionEventX(ev, mActiveDispatchPointerId);
                if (initialDownX != INVALID_POINTER) {
                    mInitialDisPatchDownX = initialDownX;
                }
                final float initialDownY = getMotionEventY(ev, mActiveDispatchPointerId);
                if (mInitialDisPatchDownY != INVALID_POINTER) {
                    mInitialDisPatchDownY = initialDownY;
                }
                break;
            case MotionEvent.ACTION_MOVE:
                if (hasRequestDisallowIntercept) {
                    //解决viewPager滑动冲突问题
                    final float x = getMotionEventX(ev, mActiveDispatchPointerId);
                    final float y = getMotionEventY(ev, mActiveDispatchPointerId);
                    if (mInitialDisPatchDownX != INVALID_POINTER && x != INVALID_POINTER &&
                            mInitialDisPatchDownY != INVALID_POINTER && y != INVALID_POINTER) {
                        final float xDiff = Math.abs(x - mInitialDisPatchDownX);
                        final float yDiff = Math.abs(y - mInitialDisPatchDownY);
                        if (xDiff > mTouchSlop && xDiff * 0.7f > yDiff) {
                            //横向滚动不需要拦截
                            super.requestDisallowInterceptTouchEvent(true);
                        } else {
                            super.requestDisallowInterceptTouchEvent(false);
                        }
                    } else {
                        super.requestDisallowInterceptTouchEvent(false);
                    }
                }
                break;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
                if (ev.getAction() == MotionEvent.ACTION_UP || ev.getAction() == MotionEvent.ACTION_CANCEL) {
                    hasRequestDisallowIntercept = false;
                }
                break;
        }

        return super.dispatchTouchEvent(ev);
    }

    private float getMotionEventY(MotionEvent ev, int activePointerId) {
        final int index = MotionEventCompat.findPointerIndex(ev, activePointerId);
        if (index < 0) {
            return -1;
        }
        return MotionEventCompat.getY(ev, index);
    }

    private float getMotionEventX(MotionEvent ev, int activePointerId) {
        final int index = MotionEventCompat.findPointerIndex(ev, activePointerId);
        if (index < 0) {
            return -1;
        }
        return MotionEventCompat.getX(ev, index);
    }
}
