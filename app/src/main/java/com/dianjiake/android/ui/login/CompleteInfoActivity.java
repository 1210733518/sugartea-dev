package com.dianjiake.android.ui.login;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Looper;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.text.InputFilter;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.amap.api.services.core.PoiItem;
import com.dianjiake.android.R;
import com.dianjiake.android.api.Network;
import com.dianjiake.android.api.params.EditUserInfoParams;
import com.dianjiake.android.base.BasePresenter;
import com.dianjiake.android.base.BaseTranslateActivity;
import com.dianjiake.android.common.ActiivtyDataHelper;
import com.dianjiake.android.constant.BSConstant;
import com.dianjiake.android.custom.MyTextWatcher;
import com.dianjiake.android.data.bean.BaseBean;
import com.dianjiake.android.data.bean.BaseListBean;
import com.dianjiake.android.data.bean.OccupationBean;
import com.dianjiake.android.data.db.LoginInfoDBHelper;
import com.dianjiake.android.data.model.LoginInfoModel;
import com.dianjiake.android.ui.searchlocation.SearchLocationActivity;
import com.dianjiake.android.util.CheckEmptyUtil;
import com.dianjiake.android.util.DateUtil;
import com.dianjiake.android.util.FileUtil;
import com.dianjiake.android.util.FrescoUtil;
import com.dianjiake.android.util.IntentUtil;
import com.dianjiake.android.util.LongUtil;
import com.dianjiake.android.util.ToastUtil;
import com.dianjiake.android.util.UIUtil;
import com.dianjiake.android.view.dialog.DatePickerDialog;
import com.dianjiake.android.view.dialog.NormalProgressDialog;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.view.SimpleDraweeView;
import com.facebook.imagepipeline.core.ImagePipeline;


import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import timber.log.Timber;

/**
 * 完善资料
 * Created by lfs on 17/2/17.
 */
public class CompleteInfoActivity extends BaseTranslateActivity {
    private static final String AVATAR_PATH = FileUtil.getExternalPicturesDir() + "/avatar.jpg";
    private static final String MALE = "1";
    private static final String FEMALE = "2";
    private static final int REQUEST_CODE_PHOTO = 121;
    private static final int REQUEST_CODE_MICRODISTRICT = 131;
    private static final int REQUEST_CAPTURE = 0x345;
    private static final int REQUEST_GALLERY = 0x346;
    private static final int REQUEST_CROP_PICTURE = 0x347;
    private static final int REQUEST_LOCATION = 0x357;
    private static final int REQUEST_NAME = 0x3337;
    private static final String TYPE_EDIT = "edit";
    private static final String KEY_NICKNAME = "nickname";
    private static final String KEY_AVATAR = "avatar";
    private static final String KEY_OPENID = "openId";

    @BindView(R.id.toolbar_icon_left)
    ImageView mToolbarBack;
    @BindView(R.id.toolbar_title)
    TextView mToolbarTitle;
    @BindView(R.id.toolbar_divider)
    View mToolbarDivider;
    @BindView(R.id.complete_info_nickname)
    TextView mNickname;
    @BindView(R.id.complete_info_gender_text)
    TextView mGenderText;
    @BindView(R.id.complete_info_gender)
    LinearLayout mGender;
    @BindView(R.id.complete_info_age_text)
    TextView mAgeText;
    @BindView(R.id.complete_info_age)
    LinearLayout mAge;
    @BindView(R.id.complete_info_occupation_text)
    TextView mOccupationText;
    @BindView(R.id.complete_info_occupation)
    LinearLayout mOccupation;
    @BindView(R.id.complete_info_microdistrict_text)
    TextView mMicrodistrictText;
    @BindView(R.id.complete_info_microdistrict)
    LinearLayout mMicrodistrict;
    @BindView(R.id.complete_info_avatar)
    SimpleDraweeView mAvatar;
    @BindView(R.id.complete_info_commit)
    Button mButtonCommit;

    private NormalProgressDialog mCommitDialog;
    private NormalProgressDialog mGetDialog;
    private NormalProgressDialog mGetUserInfoDialog;

    private String mFormNickname;
    private String mFormGender = MALE;
    private String mFormOccupation;
    private String mFormAge;
    private String mFormLongitude;
    private String mFormLatitude;
    private String mFormLocation;
    private String mAvatarPath;
    private String mFormOldAvatar;

    private String mOpenId;
    LoginInfoDBHelper loginInfoDBHelper;
    CompositeDisposable cd;

    public static Intent getStartIntent(String openId, String avatar, String nickname) {
        Intent intent = IntentUtil.getIntent(CompleteInfoActivity.class);
        intent.putExtra(KEY_OPENID, openId);
        intent.putExtra(KEY_AVATAR, avatar);
        intent.putExtra(KEY_NICKNAME, nickname);
        return intent;
    }

    public static Intent getEditIntent() {
        Intent intent = IntentUtil.getIntent(CompleteInfoActivity.class);
        intent.setType(TYPE_EDIT);
        return intent;
    }

    private List<OccupationBean> mOccupations;


    @Override
    public int provideContentView() {
        return R.layout.activity_complete_info;
    }

    @Override
    public void create(@Nullable Bundle savedInstanceState) {
        loginInfoDBHelper = LoginInfoDBHelper.newInstance();
        cd = new CompositeDisposable();

        if (TextUtils.isEmpty(getIntent().getStringExtra("openId"))) {
            mOpenId = loginInfoDBHelper.getLoginInfo().getOpenId();
        } else {
            mOpenId = getIntent().getStringExtra("openId");
        }

        if (TYPE_EDIT.equals(getIntent().getType())) {
            LoginInfoModel loginInfoModel = loginInfoDBHelper.getLoginInfo();
            mToolbarTitle.setText("个人信息");
            mButtonCommit.setText("保存");
            mAvatar.setImageURI(FrescoUtil.getAvatarUri(loginInfoModel.getAvatar()));
            mFormNickname = loginInfoModel.getNickname();
            mFormGender = loginInfoModel.getGender();
            mFormOccupation = loginInfoModel.getOccupationId();
            mFormAge = loginInfoModel.getBirthday();
            mFormOldAvatar = loginInfoModel.getAvatar();
            mFormLocation = loginInfoModel.getLocation();
            mFormLongitude = loginInfoModel.getLongitude();
            mFormLatitude = loginInfoModel.getLatitude();

            mNickname.setText(loginInfoModel.getNickname());
            mGenderText.setText("1".equals(loginInfoModel.getGender()) ? "男" : "女");
            mOccupationText.setText(loginInfoModel.getOccupation());
            mAgeText.setText(DateUtil.getAge(LongUtil.parseLong(loginInfoModel.getBirthday())) + "");
            mMicrodistrictText.setText(loginInfoModel.getLocation());
        } else {
            mToolbarTitle.setText("完善资料");
            mFormNickname = getIntent().getStringExtra(KEY_NICKNAME);
            mAvatar.setImageURI(FrescoUtil.getAvatarUri(getIntent().getStringExtra(KEY_AVATAR)));
            mFormOldAvatar = getIntent().getStringExtra(KEY_AVATAR);
            getUserInfo();
        }


        mOccupations = new ArrayList<>();
        getOccupation();

        setButtonStatus();
    }

    @Override
    public BasePresenter getPresenter() {
        return null;
    }


    @OnClick(R.id.toolbar_icon_left)
    void pressBack(View v) {
        onBackPressed();
    }

    @OnClick(R.id.complete_info_avatar)
    void pressAvatar(View v) {
        avatarPickerDialog();
    }


    @OnClick(R.id.complete_info_gender)
    void pressGender(View v) {
        chooseGender();
    }

    @OnClick(R.id.complete_info_age)
    void pressAge(View v) {
        chooseDate();
    }

    @OnClick(R.id.complete_info_occupation)
    void pressOccupation(View v) {
        chooseOccupation();
    }

    @OnClick(R.id.complete_info_microdistrict)
    void pressMicrodistrict(View v) {
        startActivityForResult(SearchLocationActivity.getChooseLocationIntent(), REQUEST_LOCATION);
    }

    @OnClick(R.id.name_holder)
    void pressName(View v) {
        startActivityForResult(EditNameActivity.getStartIntent(mFormNickname), REQUEST_NAME);
    }

    void avatarPickerDialog() {
        if (isFinishing()) return;
        AlertDialog dialog = new AlertDialog.Builder(this)
                .setItems(new String[]{"拍照", "相册选取"}, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (which == 0) {
                            openCamera();
                        } else {
                            openGallery();
                        }
                    }
                })
                .show();
    }

    void openCamera() {
        //打开相机拍照
        Intent intentFromCapture = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        File file = new File(AVATAR_PATH);
        if (!file.exists()) {
            file.delete();
        }
        Uri uri = Uri.fromFile(file);
        intentFromCapture.putExtra(MediaStore.EXTRA_OUTPUT, uri);
        startActivityForResult(intentFromCapture, REQUEST_CAPTURE);
    }

    void openGallery() {
        Intent intentFromGallery = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intentFromGallery, REQUEST_GALLERY);
    }

    void chooseGender() {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
        alertDialog.setTitle("请选择性别")
                .setItems(new String[]{"男", "女"}, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        mGenderText.setText(which == 0 ? "男" : "女");
                        mFormGender = which == 0 ? MALE : FEMALE;
                        setButtonStatus();
                    }
                });
        alertDialog.show();
    }

    void chooseOccupation() {
        if (CheckEmptyUtil.isEmpty(mOccupations)) {
            ToastUtil.showShortToast("职业列表正在加载，请稍候...");
            return;
        }

        String[] os = new String[mOccupations.size()];
        for (int i = 0; i < mOccupations.size(); i++) {
            os[i] = mOccupations.get(i).getMname();
        }
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
        alertDialog.setTitle("请选择职业")
                .setItems(os, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        mFormOccupation = mOccupations.get(which).getId();
                        mOccupationText.setText(mOccupations.get(which).getMname());
                        setButtonStatus();
                    }
                });
        alertDialog.show();

    }

    void chooseDate() {
        DatePickerDialog datePickerDialog = DatePickerDialog.newInstance("请选择生日");
        datePickerDialog.showDialog(getFragmentManager(), null);
        datePickerDialog.setOnPickListener(new DatePickerDialog.OnPickListener() {
            @Override
            public void onPick(long timestamp) {
                if (timestamp >= System.currentTimeMillis()) {
                    ToastUtil.showShortToast("日期选择有误");
                    return;
                }
                mAgeText.setText(DateUtil.getAge(timestamp) + "");

                mFormAge = String.valueOf(timestamp / 1000);
                setButtonStatus();
            }
        });
    }

    void getOccupation() {

        Network.getInstance().occupationList(BSConstant.OCCUPATION_LIST)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribeWith(new Observer<BaseListBean<OccupationBean>>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                        cd.add(d);
                    }

                    @Override
                    public void onNext(@NonNull BaseListBean<OccupationBean> occupation) {
                        if (occupation.getCode() == 200) {
                            mOccupations.clear();
                            mOccupations.addAll(occupation.getObj().getList());
                        }
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    @OnClick(R.id.complete_info_commit)
    void pressCommit(View v) {
        if (TextUtils.isEmpty(mAvatarPath) && !TYPE_EDIT.equals(getIntent().getType())) {
            ToastUtil.showShortToast("请添加头像");
        } else if (TextUtils.isEmpty(mFormNickname)) {
            ToastUtil.showShortToast("请填写昵称");
        } else if (TextUtils.isEmpty(mFormAge)) {
            ToastUtil.showShortToast("请完善年龄信息");
        } else if (TextUtils.isEmpty(mFormOccupation)) {
            ToastUtil.showShortToast("请选择职业");
        } else if (CheckEmptyUtil.isEmpty(mFormLocation)) {
            ToastUtil.showShortToast("请选择小区");
        } else {
            commit();
        }
    }

    void setButtonStatus() {
        mButtonCommit.setTextColor(checkButtonStatus() ? UIUtil.getColor(R.color.text_white) : UIUtil.getColor(R.color.text_white_second));
    }

    boolean checkButtonStatus() {
        if (TextUtils.isEmpty(mAvatarPath) && !TYPE_EDIT.equals(getIntent().getType())) {
            return false;
        } else if (TextUtils.isEmpty(mFormNickname)) {
            return false;
        } else if (TextUtils.isEmpty(mFormAge)) {
            return false;
        } else if (TextUtils.isEmpty(mFormOccupation)) {
            return false;
        } else if (CheckEmptyUtil.isEmpty(mFormLocation)) {
            return false;
        } else {
            return true;
        }
    }


    void commit() {
        showCommitDialog();
        EditUserInfoParams infoParams = new EditUserInfoParams();
        if (!CheckEmptyUtil.isEmpty(mAvatarPath)) {
            infoParams.setAvatar(new File(mAvatarPath));
        }
        infoParams.setOpenId(loginInfoDBHelper.getLoginInfo().getOpenId());
        infoParams.setNickname(mFormNickname);
        infoParams.setSex(mFormGender);
        infoParams.setBirthday(mFormAge);
        infoParams.setLocation(mFormLocation);
        infoParams.setlongitude(mFormLongitude);
        infoParams.setlatitude(mFormLatitude);
        infoParams.setProfession(mFormOccupation);
        infoParams.setTouxiang(mFormOldAvatar);
        Network.getInstance().editUserInfo(infoParams.getRequestParams())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribeWith(new Observer<BaseBean>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                        cd.add(d);
                    }

                    @Override
                    public void onNext(@NonNull BaseBean baseBean) {
                        dismissCommitDialog();
                        if (baseBean.getCode() == 200) {
                            if (TYPE_EDIT.equals(getIntent())) {
                            } else {
                                LoginActivitiesManger.newInstance().completeInfoSuccess();
                            }
                            finish();
                        } else {
                            ToastUtil.showShortToast("资料提交失败Code" + baseBean.getCode());
                        }
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        dismissCommitDialog();
                        ToastUtil.showShortToast("资料提交失败");
                    }

                    @Override
                    public void onComplete() {

                    }
                });

    }

    void getUserInfo() {
        showGetDialog();
//        Network.getInstance().getUserInfo(mOpenId, new Callback<UserInfoBean>() {
//            @Override
//            public void onResponse(Call<UserInfoBean> call, Response<UserInfoBean> response) {
//
//                dismissGetDialog();
//                setButtonStatus();
//                if (response.body() != null && response.body().getCode() == 200) {
//                    BaseUserBean userBean = response.body().getObj().getUser();
//                    ImageUtil.loadAvatar(BaseInfo.AVATAR_URL + userBean.getAvatar(), mAvatar);
//                    mFormNickname = userBean.getNickname();
//                    mFormGender = userBean.getSex() + "";
//                    mFormOccupation = userBean.getZhiyeid() + "";
//                    mFormAge = userBean.getBirthday();
//                    mSelectMico.setId(userBean.getShequid() + "");
//                    mSelectMico.setMingcheng(userBean.getLocation());
//                    mFormAvatar = userBean.getAvatar();
//
//                    mNickname.setText(userBean.getNickname());
//                    mGenderText.setText(1 == userBean.getSex() ? "男" : "女");
//                    mOccupationText.setText(userBean.getProfession());
//                    mAgeText.setText(DateUtil.getAge(LongUtil.parseLong(userBean.getBirthday())) + "");
//                    mMicrodistrictText.setText(userBean.getLocation());
//                }
//
//
//            }
//
//            @Override
//            public void onFailure(Call<UserInfoBean> call, Throwable t) {
//                if (!call.isCanceled()) {
//                    dismissGetDialog();
//                }
//            }
//        });
    }

    void showCommitDialog() {
        if (mCommitDialog == null) {
            mCommitDialog = NormalProgressDialog.newInstance("正在提交,请稍候...");
        }
        mCommitDialog.showDialog(getFragmentManager(), "show");
    }

    void dismissCommitDialog() {
        if (mCommitDialog != null) {
            mCommitDialog.dismissAllowingStateLoss();
        }
    }

    void showGetDialog() {
        if (mGetDialog == null) {
            mGetDialog = NormalProgressDialog.newInstance("正在获取用户资料，请稍候...");
        }
        mGetDialog.showDialog(getFragmentManager(), "show");
    }

    void dismissGetDialog() {
        if (mGetDialog != null) {
            mGetDialog.dismissAllowingStateLoss();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            if (requestCode == REQUEST_CAPTURE) {
                cropPicture(Uri.fromFile(new File(AVATAR_PATH)));
            } else if (requestCode == REQUEST_GALLERY) {
                cropPicture(data.getData());
            } else if (requestCode == REQUEST_CROP_PICTURE) {
                saveAvatar(data);
            } else if (requestCode == REQUEST_LOCATION) {
                PoiItem poiItem = ActiivtyDataHelper.getPoiItem(data);
                mFormLatitude = poiItem.getLatLonPoint().getLatitude() + "";
                mFormLongitude = poiItem.getLatLonPoint().getLongitude() + "";
                mFormLocation = poiItem.getTitle();
                mMicrodistrictText.setText(poiItem.getTitle());
            } else if (requestCode == REQUEST_NAME) {
                mFormNickname = ActiivtyDataHelper.getText(data);
                mNickname.setText(mFormNickname);
                setButtonStatus();
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
        setButtonStatus();
    }


    /**
     * 裁剪图片
     *
     * @param uri
     */
    private void cropPicture(Uri uri) {
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri, "image/*");
        intent.putExtra("crop", "true");
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        intent.putExtra("outputX", 300);
        intent.putExtra("outputY", 300);
        intent.putExtra("return-data", true);
        startActivityForResult(intent, REQUEST_CROP_PICTURE);
    }

    void saveAvatar(final Intent data) {
        Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(@NonNull ObservableEmitter<String> e) throws Exception {
                File file = new File(AVATAR_PATH);
                Bitmap bitmap = data.getExtras().getParcelable("data");
                FileOutputStream fos = null;
                try {
                    fos = new FileOutputStream(file);
                    bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos);
                    fos.flush();
                    e.onNext(file.getAbsolutePath());
                } catch (FileNotFoundException e1) {
                    e1.printStackTrace();
                } catch (IOException e2) {
                    e2.printStackTrace();
                } finally {
                    if (fos != null) {
                        fos.close();
                    }
                }
                boolean isMain = Looper.getMainLooper() == Looper.myLooper();
                Timber.d("is main thread:" + isMain);
            }
        })

                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<String>() {
                    @Override
                    public void accept(@NonNull String s) throws Exception {
                        ImagePipeline imagePipeline = Fresco.getImagePipeline();
                        imagePipeline.evictFromCache(FrescoUtil.getFileUri(s));
                        imagePipeline.clearCaches();
                        mAvatar.setImageURI(FrescoUtil.getFileUri(s));
                        mAvatarPath = AVATAR_PATH;
                    }
                });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
