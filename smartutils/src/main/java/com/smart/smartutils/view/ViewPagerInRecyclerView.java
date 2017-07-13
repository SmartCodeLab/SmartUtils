package com.smart.smartutils.view;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.MotionEvent;

import java.lang.reflect.Field;

/**
 * Created by fengjh on 17/3/28.
 */

public class ViewPagerInRecyclerView extends RecyclerView {
    public ViewPagerInRecyclerView(Context context) {
        this(context, null);
    }

    public ViewPagerInRecyclerView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ViewPagerInRecyclerView(Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent e) {
        //isScrolling 为 true 表示是 Fling 状态
        boolean isScrolling = getScrollState() == SCROLL_STATE_SETTLING;
        boolean ans = super.onInterceptTouchEvent(e);
        if (ans && isScrolling && e.getAction() == MotionEvent.ACTION_DOWN) {
            //先调用 onTouchEvent() 使 RecyclerView 停下来
            onTouchEvent(e);
            //反射恢复 ScrollState
            try {
                Field field = RecyclerView.class.getDeclaredField("mScrollState");
                field.setAccessible(true);
                field.setInt(this, SCROLL_STATE_IDLE);
            } catch (NoSuchFieldException e1) {
                e1.printStackTrace();
            } catch (IllegalAccessException e1) {
                e1.printStackTrace();
            }
            return false;
        }
        return ans;
    }
}
