package com.smart.smartutils.base.support;

import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import com.smart.smartutils.utils.DensityUtils;


/**
 * Copyright © 2016 Phoenix New Media Limited All Rights Reserved.
 * Created by fengjh on 16/2/14.
 */
public abstract class BaseEditActivity extends BaseActivity {

    //点击非EditText区域,隐藏键盘
    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (ev.getAction() == MotionEvent.ACTION_DOWN) {
            View v = getCurrentFocus();
            if (isShouldHideKeyboard(v, ev)) {
                InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
                if (imm != null) {
                    imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                }
            }
            return super.dispatchTouchEvent(ev);
        }
        //必不可少，否则所有的组件都不会有TouchEvent
        if (getWindow().superDispatchTouchEvent(ev)) {
            return true;
        }
        return onTouchEvent(ev);
    }

    public boolean isShouldHideKeyboard(View v, MotionEvent event) {
        if (v != null && (v instanceof EditText)) {
            int[] loc = {0, 0};
            v.getLocationInWindow(loc);
            int left = loc[0];
            int top = loc[1];
            int bottom = top + v.getHeight();
            int right = left + v.getWidth();
            int width = DensityUtils.screenWidth(this);
            if (event.getX() > left && event.getX() < width &&
                    event.getY() > top && event.getY() < bottom) {
                //点击的是输入框区域
                return false;
            } else {
                ((EditText) v).clearFocus();
                return true;
            }
        }
        return false;
    }


}
