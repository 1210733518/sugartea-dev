package com.dianjiake.android.util;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.support.annotation.IdRes;
import android.support.v7.widget.AppCompatRadioButton;
import android.util.SparseArray;
import android.widget.RadioGroup;

import com.dianjiake.android.R;
import com.dianjiake.android.data.db.LoginInfoDBHelper;
import com.dianjiake.android.ui.login.LoginChooseActivity;
import com.dianjiake.android.ui.main.MainActivity;
import com.dianjiake.android.ui.main.OrderFragment;


/**
 * Created by lfs on 2017/5/19.
 */

public class TabFragmentManager implements RadioGroup.OnCheckedChangeListener {
    MainActivity activity;
    FragmentManager fm;
    SparseArray<Fragment> fragments;
    boolean checkLogin;
    LoginInfoDBHelper loginInfo;
    RadioGroup radioGroup;
    int lastCheckedId;

    public TabFragmentManager(MainActivity activity,FragmentManager fm, RadioGroup radioGroup, boolean checkLogin) {
        this.activity=activity;
        this.fm = fm;
        this.fragments = new SparseArray<>();
        this.checkLogin = checkLogin;
        this.radioGroup = radioGroup;
        loginInfo = LoginInfoDBHelper.newInstance();
        radioGroup.setOnCheckedChangeListener(this);
    }
    public TabFragmentManager(FragmentManager fm, RadioGroup radioGroup, boolean checkLogin) {
        this.activity=activity;
        this.fm = fm;
        this.fragments = new SparseArray<>();
        this.checkLogin = checkLogin;
        this.radioGroup = radioGroup;
        loginInfo = LoginInfoDBHelper.newInstance();
        radioGroup.setOnCheckedChangeListener(this);
    }
    public void putFragment(@IdRes int id, Fragment fragment) {
        fragments.put(id, fragment);
    }

    public void start() {
        changeTab(fragments.keyAt(0));
    }
    public void addOrderFragment(){
        FragmentTransaction ft = fm.beginTransaction();
        ft.add(R.id.fragment_content,fragments.get(R.id.main_radio_orders),OrderFragment.class.getName());
        ft.hide(fragments.get(R.id.main_radio_orders));
        ft.commit();
        OrderFragment fragment= (OrderFragment) fragments.get(R.id.main_radio_orders);
        fragment.attachMainActivity(activity);

    };
    public void changeTab(@IdRes int id) {
        if (checkLogin && !loginInfo.isLogin() && id != radioGroup.getChildAt(0).getId() && id != radioGroup.getChildAt(1).getId()) {
            //让点击前的radiobutton置为选中状态
            if (lastCheckedId != 0) {
                ((AppCompatRadioButton) radioGroup.findViewById(lastCheckedId)).setChecked(true);
            }
            IntentUtil.startActivity(radioGroup.getContext(), LoginChooseActivity.class);
            return;
        }
        lastCheckedId = id;
        FragmentTransaction ft = fm.beginTransaction();
        if (!fragments.get(id).isAdded()) {
            ft.add(R.id.fragment_content, fragments.get(id), fragments.get(id).getClass().getName());
        }
        ft.show(fragments.get(id));

        for (int i = 0; i < fragments.size(); i++) {
            int key = fragments.keyAt(i);
            if (key == id) {
                continue;
            }
            Fragment f = fragments.get(key);
            if (f.isAdded()) {
                ft.hide(f);
            }
        }

        ft.commitAllowingStateLoss();


    }

    public void destroy() {
        fm = null;
        fragments = null;
    }

    @Override
    public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
        changeTab(checkedId);
    }


}
