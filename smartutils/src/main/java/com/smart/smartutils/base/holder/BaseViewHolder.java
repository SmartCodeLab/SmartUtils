package com.smart.smartutils.base.holder;

import android.support.annotation.IdRes;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by fengjh on 17/6/2.
 */

public abstract class BaseViewHolder extends RecyclerView.ViewHolder {

    public BaseViewHolder(View itemView) {
        super(itemView);
    }

    protected <T extends View> T findViewById(@IdRes int id) {
        return (T) itemView.findViewById(id);
    }

    protected <T extends View> T findViewById(View view, @IdRes int id) {
        return (T) view.findViewById(id);
    }
}
