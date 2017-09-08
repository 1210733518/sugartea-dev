package com.dianjiake.android.view.widget;

import android.support.v7.widget.RecyclerView;
import android.view.View;


import com.dianjiake.android.common.LifeCycle;

import butterknife.ButterKnife;

/**
 * 有network，有butterknife
 * Created by lfs on 16/11/24.
 */

public abstract class BaseViewHolder extends RecyclerView.ViewHolder implements LifeCycle {


    public BaseViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }


}
