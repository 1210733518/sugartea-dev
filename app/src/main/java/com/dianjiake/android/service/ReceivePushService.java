package com.dianjiake.android.service;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.support.v7.app.NotificationCompat;

import com.dianjiake.android.R;
import com.dianjiake.android.data.bean.BaseBean;
import com.dianjiake.android.data.bean.BaseListBean;
import com.dianjiake.android.data.model.MsgModel;
import com.dianjiake.android.data.db.AppInfoDBHelper;
import com.dianjiake.android.data.db.MsgDBHelper;
import com.dianjiake.android.ui.open.OpenActivity;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.igexin.sdk.GTIntentService;
import com.igexin.sdk.message.GTCmdMessage;
import com.igexin.sdk.message.GTTransmitMessage;

import timber.log.Timber;

/**
 * Created by lfs on 2017/6/5.
 */

public class ReceivePushService extends GTIntentService {
    private static final String DAY_TITLE = "每日业绩";
    private static final String DAY_DESC = "业绩";

    @Override
    public void onReceiveServicePid(Context context, int i) {

    }

    @Override
    public void onReceiveClientId(Context context, String s) {
        Timber.d("push_cid:" + s);
        AppInfoDBHelper.newInstance().saveCid(s);
    }

    @Override
    public void onReceiveMessageData(Context context, GTTransmitMessage gtTransmitMessage) {
        Timber.d("push_msg:" + new String(gtTransmitMessage.getPayload()));

        if (gtTransmitMessage.getPayload() != null) {
            String msg = new String(gtTransmitMessage.getPayload());
            try {
                Gson gson = new Gson();
                BaseBean<MsgModel> baseBean = gson.fromJson(msg, new TypeToken<BaseBean<MsgModel>>() {
                }.getType());
                if (baseBean.getObj() != null) {
                    MsgModel msgModel = baseBean.getObj();
                    MsgDBHelper.newInstance().save(msgModel);
                    PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, OpenActivity.getStartIntent(msgModel), PendingIntent.FLAG_UPDATE_CURRENT);
                    createNotification(context, msgModel.getBiaoti(), msgModel.getMiaoshu(), pendingIntent);
                }
            } catch (Exception e) {
                Timber.d("employee_push_error:" + e.toString());

            }
        }
    }

    @Override
    public void onReceiveOnlineState(Context context, boolean b) {

    }

    @Override
    public void onReceiveCommandResult(Context context, GTCmdMessage gtCmdMessage) {

    }

    public void createNotification(Context context, String title, String desc, PendingIntent pendingIntent) {
        NotificationCompat.Builder builder;
        builder = new NotificationCompat.Builder(context);
        builder.setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle(title)
                .setContentText(desc)
                .setAutoCancel(true)
                .setDefaults(Notification.DEFAULT_ALL)
                .setContentIntent(pendingIntent);
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(1024, builder.build());
    }
}
