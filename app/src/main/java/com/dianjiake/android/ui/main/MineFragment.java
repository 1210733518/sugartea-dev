package com.dianjiake.android.ui.main;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.dianjiake.android.R;
import com.dianjiake.android.base.BaseFragment;
import com.dianjiake.android.data.bean.UserInfoBean;
import com.dianjiake.android.event.CheckMsgUnreadEvent;
import com.dianjiake.android.ui.colloction.CollectionActivity;
import com.dianjiake.android.ui.coupon.CouponActivity;
import com.dianjiake.android.ui.login.CompleteInfoActivity;
import com.dianjiake.android.ui.msg.MsgActivity;
import com.dianjiake.android.ui.setting.SettingActivity;
import com.dianjiake.android.ui.simpleactivity.SimpleActivity;
import com.dianjiake.android.ui.vip.VipActivity;
import com.dianjiake.android.util.CheckEmptyUtil;
import com.dianjiake.android.util.EventUtil;
import com.dianjiake.android.util.FrescoUtil;
import com.dianjiake.android.util.IntentUtil;
import com.dianjiake.android.view.dialog.NormalAlertDialog;
import com.dianjiake.android.view.widget.MsgIconView;
import com.dianjiake.android.view.widget.ToolbarSpaceView;
import com.facebook.drawee.view.SimpleDraweeView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by lfs on 2017/7/19.
 */

public class MineFragment extends BaseFragment<MineContract.Presenter> implements MineContract.View {
    @BindView(R.id.bg)
    ImageView bg;
    @BindView(R.id.toolbar_space)
    ToolbarSpaceView toolbarSpace;
    @BindView(R.id.toolbar_icon_left)
    ImageView toolbarIconLeft;
    @BindView(R.id.avatar)
    SimpleDraweeView avatar;
    @BindView(R.id.name)
    TextView name;
    @BindView(R.id.num_mine_order_text)
    TextView num_mineOrderText;
    @BindView(R.id.mine_order)
    LinearLayout mineOrder;
    @BindView(R.id.num_mine_collection_text)
    TextView num_mineCollectionText;
    @BindView(R.id.mine_collection)
    LinearLayout mineCollection;
    @BindView(R.id.num_mine_cards_text)
    TextView num_mineCardsText;
    @BindView(R.id.mine_cards)
    LinearLayout mineCards;
    @BindView(R.id.num_mine_vip_text)
    TextView num_mineVipText;
    @BindView(R.id.mine_vip)
    LinearLayout mineVip;
    @BindView(R.id.mine_call)
    LinearLayout mineCall;
    @BindView(R.id.item_holder)
    LinearLayout itemHolder;
    @BindView(R.id.toolbar_icon_right)
    MsgIconView msgIconView;
    private UserInfoBean listInfo;

    @Override
    public void setPresenter(MineContract.Presenter presenter) {

    }

    @Override
    protected int provideLayout() {
        return R.layout.fragment_mine;
    }

    @Override
    protected MineContract.Presenter getPresenter() {
        return new MinePresenter(this);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        EventBus.getDefault().register(this);
        toolbarIconLeft.setImageResource(R.drawable.ic_mine_setting);
        checkUnread(null);
    }


    @Override
    public void setViews(UserInfoBean userInfoBean) {
        if (userInfoBean != null) {
            setAvatar(userInfoBean.getAvatar());
            setName(userInfoBean.getNickname(), userInfoBean.getPhone());
            /*
            guaju
             */
            setListInfo(userInfoBean);
        }
    }

    @Override
    public void setAvatar(String avatar) {
        this.avatar.setImageURI(FrescoUtil.getAvatarUri(avatar));
    }

    @Override
    public void setName(String name, String phone) {
        if (CheckEmptyUtil.isEmpty(name)) {
            this.name.setText(formatPhone(phone));
        } else {
            this.name.setText(name);
        }
    }

    public String formatPhone(String phone) {
        String result = "";
        try {
            result = phone.substring(0, 3) + "****" + phone.substring(7);
        } catch (Exception e) {
        }
        return result;
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (!hidden) {
//            presenter.load();
            Log.e("hehe", "onHiddenChanged: hehehe" );
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        presenter.load();
    }

    @OnClick(R.id.mine_call)
    void clickCall(View v) {
        NormalAlertDialog dialog = NormalAlertDialog.newInstance("确定呼叫：010-57206260？", true, true);
        dialog.setOnButtonClick(new NormalAlertDialog.OnButtonClick() {
            @Override
            public void click(int which) {
                if (which == DialogInterface.BUTTON_POSITIVE) {
                    Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:01057206260"));
                    startActivity(intent);
                }
            }
        });
        dialog.showDialog(getFragmentManager(), "call");
    }

    @OnClick(R.id.avatar)
    void clickAvatar(View v) {
        startActivity(CompleteInfoActivity.getEditIntent());
    }

    @OnClick(R.id.toolbar_icon_left)
    void clickCancel(View v) {
        startActivity(IntentUtil.getIntent(SettingActivity.class));
    }

    @OnClick(R.id.mine_order)
    void clickOrder(View v) {
        EventUtil.postToOrder();
    }

    @OnClick(R.id.mine_vip)
    void clickVip(View v) {
        startActivity(IntentUtil.getIntent(VipActivity.class));
    }

    @OnClick(R.id.mine_collection)
    void clickCollection(View v) {
        startActivity(IntentUtil.getIntent(CollectionActivity.class));
    }

    @OnClick(R.id.mine_cards)
    void clickCoupon(View v) {
        startActivity(IntentUtil.getIntent(CouponActivity.class));
    }

    @OnClick(R.id.mine_join)
    void clickJoin(View v) {
        startActivity(SimpleActivity.getStartIntent("http://www.quanminlebang.com/msg.php?id=4", "商家入驻"));
    }

    @OnClick(R.id.toolbar_msg_holder)
    void clickMsg(View v) {
        startActivity(MsgActivity.getStartIntent());
    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void checkUnread(CheckMsgUnreadEvent event) {
        msgIconView.setShowRedIcon(presenter.haveUnreadMsg());
    }
    @Override
    public void onDestroyView() {
        EventBus.getDefault().unregister(this);
        super.onDestroyView();
    }

    public void setListInfo(UserInfoBean user) {
           if (user!=null){
               if (!TextUtils.isEmpty(user.getXiadanshu())){
                   num_mineOrderText.setText(user.getXiadanshu());
               }
               if (!TextUtils.isEmpty(user.getYouhuiquan())){
                   num_mineCardsText.setText(user.getYouhuiquan());
               }
                 if (!TextUtils.isEmpty(user.getShoucangshanghu())){
                   num_mineCollectionText.setText(user.getShoucangshanghu());
               }
                 if (!TextUtils.isEmpty(user.getHuiyuanka())){
                   num_mineVipText.setText(user.getHuiyuanka());
               }

           }

    }
}
