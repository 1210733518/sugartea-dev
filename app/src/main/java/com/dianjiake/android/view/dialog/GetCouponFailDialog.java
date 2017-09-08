package com.dianjiake.android.view.dialog;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.dianjiake.android.R;
import com.dianjiake.android.util.IntentUtil;
import com.dianjiake.android.util.UIUtil;
import com.facebook.drawee.view.SimpleDraweeView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by lfs on 2017/7/26.
 */

public class GetCouponFailDialog extends AppCompatActivity {
    private static final int HAVE = 4001;
    private static final int EMPTY = 4002;
    private static final int OUTTIME = 4003;
    private static final int ERROR = 4444;

    @BindView(R.id.bg)
    SimpleDraweeView bg;
    @BindView(R.id.close)
    ImageView close;
    @BindView(R.id.icon)
    ImageView icon;
    @BindView(R.id.text)
    TextView text;
    @BindView(R.id.share_bg)
    View shareBg;
    @BindView(R.id.tips)
    TextView tips;

    int code;

    public static Intent getStartIntent(int code) {
        Intent intent = IntentUtil.getIntent(GetCouponFailDialog.class);
        intent.putExtra("code", code);
        return intent;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
        setContentView(R.layout.dialog_get_coupon_fail);
        ButterKnife.bind(this);
        code = getIntent().getIntExtra("code", ERROR);
        tips.setText(getTips());
    }

    @Override
    public void onResume() {
        super.onResume();
        int width = (int) (UIUtil.getScreenWidth() * 0.7);
        getWindow().setLayout(width, ViewGroup.LayoutParams.WRAP_CONTENT);
    }

    public String getTips() {
        switch (code) {
            case HAVE:
                return "亲，这个优惠券已经领取过了";
            case EMPTY:
                return "亲，这个优惠券已经领取完啦~";
            case OUTTIME:
            case ERROR:
            default:
                return "亲，不要等到过期了才知道它的可贵，\n" +
                        "人生最遗憾的不是过错，而是错过";
        }
    }


    @OnClick(R.id.close)
    void clickClose(View v){
        finish();
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);

    }


}
