package com.dianjiake.android.view.widget;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.dianjiake.android.R;
import com.dianjiake.android.data.bean.ServiceBean;
import com.dianjiake.android.data.bean.UserInfoBean;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Sacowiw on 2017/6/15.
 */

public class ServiceItemView extends LinearLayout implements View.OnClickListener {

    @BindView(R.id.service_name)
    TextView serviceName;
    @BindView(R.id.service_holder)
    LinearLayout serviceHolder;
    @BindView(R.id.staff_name)
    TextView staffName;
    @BindView(R.id.staff_holder)
    LinearLayout staffHolder;
    @BindView(R.id.service_arrow)
    ImageView serviceArrow;
    @BindView(R.id.staff_arrow)
    ImageView staffArrow;

    ServiceBean chooseServiceBean;
    UserInfoBean chooseStaffBean;

    OnChooseClickListener listener;

    public ServiceItemView(Context context) {
        super(context);
        init();
    }

    public ServiceItemView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public ServiceItemView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        setOrientation(VERTICAL);
        View view = inflate(getContext(), R.layout.service_item, this);
        ButterKnife.bind(this, view);
        serviceHolder.setOnClickListener(this);
        staffHolder.setOnClickListener(this);
    }


    public void initServiceAndStaff(ServiceBean service, UserInfoBean userInfo) {
        if (service != null) {
            serviceHolder.setClickable(false);
            chooseServiceBean = service;
            serviceArrow.setVisibility(INVISIBLE);
            serviceName.setText(service.getName());
        } else {
            serviceArrow.setVisibility(VISIBLE);
            serviceHolder.setClickable(true);
        }

        if (userInfo != null) {
            staffArrow.setVisibility(INVISIBLE);
            chooseStaffBean = userInfo;
            staffHolder.setClickable(false);
            staffName.setText(userInfo.getShanghunicheng());
        } else {
            staffArrow.setVisibility(VISIBLE);
            staffHolder.setClickable(true);
        }
    }

    public void setService(ServiceBean service) {
        chooseServiceBean = service;
        serviceName.setText(service.getName());
    }

    public void setStaff(UserInfoBean staff) {
        chooseStaffBean = staff;
        staffName.setText(staff.getShanghunicheng());
    }

    public void setListener(OnChooseClickListener listener) {
        this.listener = listener;
    }

    @Override
    public void setTag(Object tag) {
        super.setTag(tag);
        serviceHolder.setTag(tag);
        staffHolder.setTag(tag);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.service_holder && listener != null) {
            listener.onChooseServiceClick(v);
        } else if (v.getId() == R.id.staff_holder && listener != null) {
            listener.onChooseStaffClick(v);
        }
    }

    public static interface OnChooseClickListener {
        void onChooseServiceClick(View v);

        void onChooseStaffClick(View v);
    }
}
