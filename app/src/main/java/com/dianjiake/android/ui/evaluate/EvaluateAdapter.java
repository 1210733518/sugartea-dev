package com.dianjiake.android.ui.evaluate;

import android.text.InputFilter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.dianjiake.android.R;
import com.dianjiake.android.custom.MyTextWatcher;
import com.dianjiake.android.data.bean.OrderServiceBean;
import com.dianjiake.android.ui.common.OrderViewType;
import com.dianjiake.android.util.FrescoUtil;
import com.dianjiake.android.util.UIUtil;
import com.dianjiake.android.view.widget.BaseLoadMoreAdapter;
import com.dianjiake.android.view.widget.BaseViewHolder;
import com.dianjiake.android.view.widget.RatingBar;
import com.facebook.drawee.view.SimpleDraweeView;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by lfs on 2017/7/20.
 */

public class EvaluateAdapter extends BaseLoadMoreAdapter<OrderServiceBean> {
    EvaluateContract.Presenter presenter;


    public EvaluateAdapter(List<OrderServiceBean> items, EvaluateContract.Presenter presenter) {
        super(items);
        this.presenter = presenter;
    }

    @Override
    public void myOnBindViewHolder(BaseViewHolder holder, int position) {
        if (myGetItemViewType(position) == OrderViewType.NORMAL) {
            ViewHolder vh = (ViewHolder) holder;
            vh.setItem(getItem(position));
        }
    }

    @Override
    public BaseViewHolder myOnCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == EvaluateType.BUTTON) {
            return new SubmitVH(UIUtil.inflate(R.layout.item_evaluate_button, parent), presenter);
        } else {
            return new ViewHolder(UIUtil.inflate(R.layout.item_evaluate, parent), presenter);

        }
    }

    @Override
    public int myGetItemViewType(int position) {
        return getItem(position).getViewType();
    }

    public static class ViewHolder extends BaseViewHolder {
        @BindView(R.id.evaluate_logo)
        SimpleDraweeView evaluateLogo;
        @BindView(R.id.evaluate_name)
        TextView evaluateName;
        @BindView(R.id.evaluate_price)
        TextView evaluatePrice;
        @BindView(R.id.evaluate_unit)
        TextView evaluateUnit;
        @BindView(R.id.evaluate_old)
        TextView evaluateOld;
        @BindView(R.id.evaluate_service_holder)
        RelativeLayout evaluateServiceHolder;
        @BindView(R.id.evaluate_rating)
        RatingBar evaluateRating;
        @BindView(R.id.evaluate_desc)
        TextView evaluateDesc;
        @BindView(R.id.evaluate_input)
        EditText evaluateInput;
        @BindView(R.id.evaluate_count)
        TextView evaluateCount;

        EvaluateContract.Presenter presenter;
        int mFormScore = 0;

        public ViewHolder(View itemView, EvaluateContract.Presenter presenter) {
            super(itemView);
            this.presenter = presenter;
            evaluateInput.setFilters(new InputFilter[]{new InputFilter.LengthFilter(140)});
            evaluateInput.addTextChangedListener(mInputWatcher);
            setInputCount(0);
            evaluateRating.setOnRatingChangeListener(new RatingBar.OnRatingChangeListener() {
                @Override
                public void onRatingChange(float ratingCount) {
                    setRatingDesc((int) ratingCount);
                }
            });
            evaluateRating.setStar(5);


        }

        MyTextWatcher mInputWatcher = new MyTextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                setInputCount(s.length());
                presenter.setComment(s.toString(), getAdapterPosition());

            }
        };

        public void setItem(OrderServiceBean service) {
            evaluateLogo.setImageURI(FrescoUtil.getServiceUri(service.getPhoto()));
            evaluateName.setText(service.getFuwumingcheng());
            evaluatePrice.setText("￥" + service.getDanjia());
            if (service.getTempStar() == 0) {
                evaluateRating.setStar(5);
            } else {
                evaluateRating.setStar(service.getTempStar());
            }
//            evaluateUnit.setText(service.getDanwei());
//            long nowTime = System.nanoTime();
//            if ("1".equals(service.getCuxiao())) {
//                mPrice.setText(service.getCuxiaojia());
//                mOld.setVisibility(VISIBLE);
//                mOld.setText(service.getJine() + service.getDanwei());
//                mOld.setPaintFlags(Paint.STRIKE_THRU_TEXT_FLAG | Paint.ANTI_ALIAS_FLAG);
//            } else {
//                mPrice.setText(service.getJine());
//                mOld.setVisibility(GONE);
//            }
        }

        void setInputCount(int length) {
            evaluateCount.setText(String.format("%d/140", length));
        }

        void setRatingDesc(int num) {
            mFormScore = num;
            presenter.setRate(num, getAdapterPosition());
            switch (num) {
                case 1:
                    evaluateDesc.setText("非常不满意，各方面都比较差");
                    break;
                case 2:
                    evaluateDesc.setText("不满意，比较差");
                    break;
                case 3:
                    evaluateDesc.setText("一般，还需改善");
                    break;
                case 4:
                    evaluateDesc.setText("比较满意，还不错");
                    break;
                case 5:
                    evaluateDesc.setText("非常满意，无可挑剔");
                    break;
            }
        }

        @Override
        public void destroy() {

        }
    }

    public static class SubmitVH extends BaseViewHolder {
        @BindView(R.id.button)
        Button button;
        EvaluateContract.Presenter presenter;

        public SubmitVH(View itemView, EvaluateContract.Presenter presenter) {
            super(itemView);
            this.presenter = presenter;
        }

        @OnClick(R.id.button)
        void onClick(View v) {
            presenter.submit();
        }

        @Override
        public void destroy() {

        }
    }
}
