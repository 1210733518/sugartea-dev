package com.dianjiake.android.view.widget;

import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.os.Handler;
import android.support.annotation.DrawableRes;
import android.support.annotation.StringRes;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewStub;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.dianjiake.android.R;
import com.dianjiake.android.util.UIUtil;


/**
 * 加载状态layout<br/>
 * 以下方法必须执行
 * {@link #setOnReloadListener(OnReloadListener listener)},
 * Created by Fesen on 2015/12/26.
 */
public abstract class LoadingLayout extends FrameLayout {
    public static int STATUS_LOADING = 0x21;
    public static int STATUS_EMPTY = 0x22;
    public static int STATUS_ERROR = 0x23;
    public static int STATUS_SUCCESS = 0x24;

    private ViewStub mStubEmpty;
    private ViewStub mStubError;

    private FrameLayout mHolder;
    private View mViewProgress;
    private View mViewEmpty;
    private View mViewError;
    private View mViewContent;
    protected OnReloadListener mListener;

    private View mLoadingImage;
    private AnimationDrawable mLoadingAnimation = new AnimationDrawable();

    private TextView mEmptyText;
    private ImageView mEmptyImage;
    private int mEmptyTextRes;
    private int mEmptyImageRes;
    private boolean mIsBigEmptyImage;

    private Handler mHandler;

    public LoadingLayout(Context context) {
        super(context);
        init(context);
    }

    public LoadingLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public LoadingLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);

    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
    }

    protected void init(Context context) {
        mHandler = new Handler();
        View view = inflate(context, R.layout.base_loading_layout, this);
        mHolder = (FrameLayout) view.findViewById(R.id.base_loading_holder);

        mViewProgress = view.findViewById(R.id.base_loading_stub_progress);
        //空
        mStubEmpty = (ViewStub) view.findViewById(R.id.base_loading_stub_empty);
        //错误
        mStubError = (ViewStub) view.findViewById(R.id.base_loading_stub_error);

        mLoadingImage = mViewProgress.findViewById(R.id.base_loading_layout_image);
//        mLoadingAnimation = (AnimationDrawable) mLoadingImage.getDrawable();

        setContentView();

        setLoadStatus(STATUS_LOADING);
    }


    /**
     * 设置加载状态
     */
    private void setLoadStatus(final int status) {
        showProgress(STATUS_LOADING == status);
        showEmpty(STATUS_EMPTY == status);
        showError(STATUS_ERROR == status);
        showContent(STATUS_SUCCESS == status);
    }

    /**
     * 是否显示进度条
     *
     * @param show
     */
    private void showProgress(final boolean show) {
        if (show) {
            mLoadingImage.post(new Runnable() {
                @Override
                public void run() {
                    mLoadingAnimation.start();
                }
            });
        } else {
            mLoadingAnimation.stop();
        }
        mViewProgress.setVisibility(show ? VISIBLE : INVISIBLE);
    }

    /**
     * 是否显示错误界面
     *
     * @param show
     */
    private void showError(boolean show) {
        if (!show && mViewError == null) {
            return;
        }

        if (mViewError == null) {
            mViewError = mStubError.inflate();
            mViewError.findViewById(R.id.base_loading_layout_button_reload).setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mListener != null) {
                        setLoadStatus(STATUS_LOADING);
                        mListener.onReload();
                    }
                }
            });
        }
        mViewError.setVisibility(show ? VISIBLE : INVISIBLE);
    }

    /**
     * 是否显示内容为空页面
     *
     * @param show
     */
    private void showEmpty(boolean show) {
        if (!show && mViewEmpty == null) {
            return;
        }

        if (mViewEmpty == null) {
            mViewEmpty = mStubEmpty.inflate();
            mEmptyText = (TextView) mViewEmpty.findViewById(R.id.base_loading_layout_empty_text);
            mEmptyImage = (ImageView) mViewEmpty.findViewById(R.id.base_loading_layout_empty_image);
        }

        if (mEmptyTextRes != 0) {
            mEmptyText.setText(mEmptyTextRes);
        }
        if (mEmptyImageRes != 0) {
            mEmptyImage.setImageResource(mEmptyImageRes);
        }
        if (mIsBigEmptyImage) {
            mEmptyImage.getLayoutParams().width = UIUtil.getDimensionPixelSize(R.dimen.base_loading_view_size);
            mEmptyImage.getLayoutParams().height = UIUtil.getDimensionPixelSize(R.dimen.base_loading_view_size);
            mEmptyImage.requestLayout();
        }
        mViewEmpty.setVisibility(show ? VISIBLE : INVISIBLE);
    }

    /**
     * 是否显示内容页
     *
     * @param show
     */
    private void showContent(final boolean show) {
        if (!show && mViewContent == null) {
            return;
        }
        mViewContent.setVisibility(show ? VISIBLE : INVISIBLE);
    }

    /**
     * 设置内容view
     */
    public void setContentView() {
        mViewContent = provideContentView();
        addView(mViewContent);
        mViewContent.setVisibility(INVISIBLE);
    }

    /**
     * 重新加载接口
     *
     * @param listener
     */
    public void setOnReloadListener(OnReloadListener listener) {
        mListener = listener;
    }


    public void setGravity(int gravity) {
        LayoutParams params = (LayoutParams) mHolder.getLayoutParams();
        if (params != null) {
            params.gravity = gravity;
        }

    }

    /**
     * 设置内容为空时提示文字
     *
     * @param emptyTextRes
     */
    public void setEmptyTextRes(@StringRes int emptyTextRes) {
        mEmptyTextRes = emptyTextRes;
    }

    public void setEmptyView(@DrawableRes int imgRes) {
        mEmptyImageRes = imgRes;
    }

    public void setEmptyView(@DrawableRes int imgRes, boolean isBigImg) {
        mEmptyImageRes = imgRes;
        mIsBigEmptyImage = isBigImg;
    }

    public void statusLoading() {
        setLoadStatus(STATUS_LOADING);
    }

    public void statusEmpty() {
        setLoadStatus(STATUS_EMPTY);
    }

    public void statusError() {
        setLoadStatus(STATUS_ERROR);
    }

    public void statusSuccess() {
        setLoadStatus(STATUS_SUCCESS);
    }

    protected abstract View provideContentView();

    /**
     * 第一次加载或者重新加载
     */
    public interface OnReloadListener {
        void onReload();
    }


}
