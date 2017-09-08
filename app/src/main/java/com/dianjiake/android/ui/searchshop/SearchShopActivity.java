package com.dianjiake.android.ui.searchshop;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.dianjiake.android.R;
import com.dianjiake.android.base.App;
import com.dianjiake.android.base.BaseTranslateActivity;
import com.dianjiake.android.common.FragmentFactory;
import com.dianjiake.android.ui.common.SearchHistoryAdapter;
import com.dianjiake.android.util.EventUtil;
import com.dianjiake.android.util.UIUtil;
import com.dianjiake.android.view.widget.ToolbarSpaceView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by lfs on 2017/7/12.
 */

public class SearchShopActivity extends BaseTranslateActivity<SearchShopContract.Presenter> implements SearchShopContract.View {
    @BindView(R.id.toolbar_space)
    ToolbarSpaceView toolbarSpace;
    @BindView(R.id.toolbar_input)
    EditText toolbarInput;
    @BindView(R.id.toolbar_title)
    TextView toolbarTitle;
    @BindView(R.id.toolbar_right)
    TextView toolbarRight;
    @BindView(R.id.history)
    ListView history;
    @BindView(R.id.history_holder)
    FrameLayout historyHolder;
    @BindView(R.id.tab)
    TabLayout tabLayout;
    @BindView(R.id.view_pager)
    ViewPager viewPager;
    @BindView(R.id.toolbar_divider)
    View toolbarDivider;

    InputMethodManager imm;
    boolean searchFocus;
    SearchHistoryAdapter searchHistoryAdapter;


    @Override
    public void setPresenter(SearchShopContract.Presenter presenter) {

    }

    @Override
    public int provideContentView() {
        return R.layout.activity_search_shop;
    }

    @Override
    public void create(@Nullable Bundle savedInstanceState) {

        imm = (InputMethodManager) App.getInstance().getSystemService(INPUT_METHOD_SERVICE);

        toolbarTitle.setHint("搜索感兴趣的服务和商家");
        toolbarInput.setHint("搜索感兴趣的服务和商家");
        toolbarDivider.setVisibility(View.GONE);
        searchHistoryAdapter = new SearchHistoryAdapter();
        history.setAdapter(searchHistoryAdapter);
        history.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                search(searchHistoryAdapter.getItem(position).getSearch());
            }
        });

        List<Fragment> fragments = new ArrayList<>();
        fragments.add(FragmentFactory.createFragment(ServiceResultFragment.class));
        fragments.add(FragmentFactory.createFragment(ShopResultFragment.class));
        SearchShopAdapter adapter = new SearchShopAdapter(getFragmentManager(), fragments);
        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);
        UIUtil.setUpIndicatorWidth(tabLayout);
        toolbarInput.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    search(v.getText().toString());
                }
                return false;
            }
        });
        setSearchFocus(true);



    }


    @Override
    public SearchShopContract.Presenter getPresenter() {
        return new SearchShopPresenter(this);
    }


    void setSearchFocus(boolean focus) {
        searchFocus = focus;
        if (focus) {
            imm.toggleSoftInput(0, InputMethodManager.SHOW_IMPLICIT);
        } else {
            imm.hideSoftInputFromWindow(toolbarInput.getWindowToken(), 0);
        }

        toolbarTitle.setVisibility(focus ? View.GONE : View.VISIBLE);
        toolbarInput.setVisibility(focus ? View.VISIBLE : View.GONE);
        if (focus) {
            toolbarInput.requestFocus();
        }
        historyHolder.setVisibility(focus ? View.VISIBLE : View.GONE);

        searchHistoryAdapter.setItems(presenter.getSearchHistory());
    }

    void search(String search) {
        setSearchFocus(false);
        toolbarTitle.setText(search);
        presenter.addSearchHistory(search);
        EventUtil.postSearchShopEvent(search);
    }

    @OnClick(R.id.toolbar_title)
    void clickSearch(View v) {
        setSearchFocus(true);
    }

    @OnClick(R.id.history_holder)
    void clickHistoryHolder(View v) {
        setSearchFocus(false);
    }

    @OnClick(R.id.toolbar_right)
    void clickCancel(View v) {
        finish();
    }

    @Override
    public void onBackPressed() {
        if (!searchFocus) {
            setSearchFocus(true);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    protected void onDestroy() {
        imm = null;
        super.onDestroy();
    }


}
