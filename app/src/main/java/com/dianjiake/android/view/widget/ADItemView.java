package com.dianjiake.android.view.widget;

import android.content.Context;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.dianjiake.android.R;
import com.dianjiake.android.api.Network;
import com.dianjiake.android.constant.BSConstant;
import com.dianjiake.android.constant.Constant;
import com.dianjiake.android.data.bean.ADItemBean;
import com.dianjiake.android.data.db.AppInfoDBHelper;
import com.dianjiake.android.data.model.AppInfoModel;
import com.dianjiake.android.ui.shopdetail.ShopDetailActivity;
import com.dianjiake.android.ui.simpleactivity.SimpleActivity;
import com.dianjiake.android.util.FrescoUtil;
import com.dianjiake.android.util.IntentUtil;
import com.facebook.drawee.view.SimpleDraweeView;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;


/**
 * Created by lfs on 17/3/8.
 */

public class ADItemView extends FrameLayout {
    SimpleDraweeView mImage;
    ADItemBean itemBean;

    public ADItemView(Context context) {
        this(context, null);
    }

    public ADItemView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ADItemView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        View view = inflate(context, R.layout.ad_item_view, this);
        mImage = (SimpleDraweeView) view.findViewById(R.id.ad_item_view_image);
        setLayoutParams(new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        mImage.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {

                if (itemBean != null && !TextUtils.isEmpty(itemBean.getUrl())) {
                    if ("1".equals(itemBean.getLeixing())) {
                        IntentUtil.startActivity(v, ShopDetailActivity.getStartIntent(itemBean.getShanghuid()));
                    } else if ("2".equals(itemBean.getLeixing())) {
                        IntentUtil.startActivity(v.getContext(), SimpleActivity.getStartIntent(itemBean.getUrl(), itemBean.getTitle()));
                    } else {
                        IntentUtil.startActivity(v.getContext(), SimpleActivity.getStartIntent(Constant.ROOT + "msg.php?id=" + itemBean.getId(), itemBean.getTitle()));
                    }
                }

            }
        });

    }

    public static ADItemView newInstance(Context context) {
        ADItemView view = new ADItemView(context);
        return view;
    }

    public void load(ADItemBean adItemBean) {
        this.itemBean = adItemBean;
        mImage.setImageURI(FrescoUtil.getADUri(adItemBean.getPic()));
    }


}
