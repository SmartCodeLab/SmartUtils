package com.smart.smartutils.view;

import android.content.Context;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v4.widget.NestedScrollView;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.ScrollView;

import java.lang.reflect.Field;

/**
 * Created by fengjh on 17/3/28.
 */

public class ViewPagerInScrollView extends ScrollView {
    public ViewPagerInScrollView(Context context) {
        this(context, null);
    }

    public ViewPagerInScrollView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ViewPagerInScrollView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public ViewPagerInScrollView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        boolean ans = super.onInterceptTouchEvent(ev);
        if (ans && ev.getAction() == MotionEvent.ACTION_DOWN) {
            onTouchEvent(ev);
            Field field = null;
            try {
                field = NestedScrollView.class.getDeclaredField("mIsBeingDragged");
            } catch (NoSuchFieldException e) {
                e.printStackTrace();
            }
            if (field != null) {
                field.setAccessible(true);
                try {
                    field.setBoolean(this, false);
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
            return false;
        }
        return ans;
    }
}
