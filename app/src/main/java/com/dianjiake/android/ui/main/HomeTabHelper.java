package com.dianjiake.android.ui.main;

import android.support.annotation.IdRes;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lfs on 2017/7/19.
 */

public class HomeTabHelper implements RadioGroup.OnCheckedChangeListener {
    List<RadioGroup> rgs = new ArrayList<>();
    int lastCheckId;

    RadioGroup.OnCheckedChangeListener checkedChangeListener;


    public void addRG(RadioGroup rg) {
        rgs.add(rg);
        rg.setOnCheckedChangeListener(this);
    }

    public void setCheckedChangeListener(RadioGroup.OnCheckedChangeListener checkedChangeListener) {
        this.checkedChangeListener = checkedChangeListener;
    }

    @Override
    public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
        for (int i = 0; i < rgs.size(); i++) {
            for (int j = 0; j < rgs.get(i).getChildCount(); j++) {
                RadioButton rb = (RadioButton) rgs.get(i).getChildAt(j);
                if (rb.getId() == checkedId && !rb.isChecked()) {
                    rb.setChecked(true);
                    if (checkedChangeListener != null) {
                        checkedChangeListener.onCheckedChanged(group, checkedId);
                    }
                }
            }
        }
    }
}
