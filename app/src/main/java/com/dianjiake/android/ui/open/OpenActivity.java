package com.dianjiake.android.ui.open;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.dianjiake.android.data.model.MsgModel;
import com.dianjiake.android.data.db.MsgDBHelper;
import com.dianjiake.android.ui.coupon.CouponActivity;
import com.dianjiake.android.ui.main.MainActivity;
import com.dianjiake.android.ui.msg.MsgType;
import com.dianjiake.android.ui.orderdetail.OrderDetailActivity;
import com.dianjiake.android.ui.vip.VipActivity;
import com.dianjiake.android.util.IntentUtil;

/**
 * Created by lfs on 2017/7/26.
 */

public class OpenActivity extends AppCompatActivity {
    public static Intent getStartIntent(MsgModel msgModel) {
        Intent intent = IntentUtil.getIntent(OpenActivity.class);
        intent.putExtra("msg", msgModel);
        return intent;
    }

    MsgModel msgModel;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.msgModel = getIntent().getParcelableExtra("msg");
        if (msgModel == null) {
            finish();
        } else {
            MsgDBHelper.newInstance().delete(msgModel);
            switch (msgModel.getLeixing()) {
                case MsgType.COUPON:
                    jump(IntentUtil.getIntent(CouponActivity.class));
                    break;
                case MsgType.MONEY:
                case MsgType.NEW_CARD:
                    jump(IntentUtil.getIntent(VipActivity.class));
                    break;
                case MsgType.ORDER_CANCEL:
                case MsgType.ORDER_COMPLETE:
                case MsgType.ORDER_CONFIRM:
                    jump(OrderDetailActivity.getStartIntent(msgModel.getShanghuid(), msgModel.getXinxiid()));
                    break;
            }
        }
    }

    public void jump(Intent intent) {
        Intent mainIntent = IntentUtil.getIntent(MainActivity.class);
        startActivities(new Intent[]{mainIntent, intent});
        finish();
    }
}
