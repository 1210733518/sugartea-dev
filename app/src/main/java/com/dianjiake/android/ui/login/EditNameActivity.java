package com.dianjiake.android.ui.login;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.text.InputFilter;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.dianjiake.android.R;
import com.dianjiake.android.base.BasePresenter;
import com.dianjiake.android.base.BaseTranslateActivity;
import com.dianjiake.android.common.ActiivtyDataHelper;
import com.dianjiake.android.custom.MyTextWatcher;
import com.dianjiake.android.util.IntentUtil;
import com.dianjiake.android.util.UIUtil;
import com.dianjiake.android.view.widget.ToolbarSpaceView;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by lfs on 2017/7/21.
 */

public class EditNameActivity extends BaseTranslateActivity {
    public static Intent getStartIntent(String name) {
        Intent intent = IntentUtil.getIntent(EditNameActivity.class);
        intent.putExtra("name", name);
        return intent;
    }

    @BindView(R.id.toolbar_space)
    ToolbarSpaceView toolbarSpace;
    @BindView(R.id.toolbar_icon_left)
    ImageView toolbarIconLeft;
    @BindView(R.id.toolbar_title)
    TextView toolbarTitle;
    @BindView(R.id.toolbar_text_right)
    TextView toolbarTextRight;
    @BindView(R.id.toolbar_divider)
    ImageView toolbarDivider;
    @BindView(R.id.toolbar_holder)
    ConstraintLayout toolbarHolder;
    @BindView(R.id.input)
    EditText input;

    MyTextWatcher watcher = new MyTextWatcher() {
        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            toolbarTextRight.setEnabled(s.length() > 0);
        }
    };

    @Override
    public int provideContentView() {
        return R.layout.activity_edit_name;
    }

    @Override
    public void create(@Nullable Bundle savedInstanceState) {
        input.addTextChangedListener(watcher);
        toolbarTitle.setText("昵称");
        toolbarTextRight.setText("保存");
        toolbarTextRight.setFilters(new InputFilter[]{new InputFilter.LengthFilter(6)});
        input.setText(getIntent().getStringExtra("name"));

    }

    @Override
    public BasePresenter getPresenter() {
        return null;
    }

    @OnClick(R.id.toolbar_text_right)
    void clickRight(View v) {
        setResult(RESULT_OK, ActiivtyDataHelper.getTextIntent(input.getText().toString()));
        finish();
    }

    @OnClick(R.id.toolbar_icon_left)
    void clickBack(View v) {
        finish();
    }

    @Override
    protected void onDestroy() {
        input.removeTextChangedListener(watcher);
        super.onDestroy();
    }
}
