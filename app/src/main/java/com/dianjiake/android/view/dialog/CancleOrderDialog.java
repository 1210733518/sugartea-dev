package com.dianjiake.android.view.dialog;

import android.app.Activity;
import android.app.Dialog;
import android.os.Handler;
import android.os.Looper;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import com.dianjiake.android.R;
import com.dianjiake.android.runnabl.CustomRunnable;

/**
 * Created by guaju on 2017/8/4.
 */

public class CancleOrderDialog {

    private static ListView lv;
    private static Button bt;
    private static ArrayAdapter adapter;
    private static String[] array={"计划有变，没时间消费","重复下单","预约了其他家","已和商家确认取消","其他原因"};

    public static void showCustomDialog(Activity act, final CustomRunnable runnable) {
        final Dialog dialog = new Dialog(act, R.style.custom_dialog_style);
        View dialogView = LayoutInflater.from(act).inflate(R.layout.cancleorder, null);
        //获得dialog的window窗口
        lv = (ListView) dialogView.findViewById(R.id.lv);
        bt = (Button) dialogView.findViewById(R.id.bt);

        bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (dialog!=null&&dialog.isShowing()){
                    dialog.dismiss();
                }
            }
        });
        adapter=new ArrayAdapter<String>(act,R.layout.simple_textview_1,array);
        lv.setAdapter(adapter);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String str=array[i];
                runnable.setInfo(str);
                //todosomething
                Handler handler = new Handler(Looper.getMainLooper());
                handler.post(runnable);
            }
        });
        Window window = dialog.getWindow();
        //设置dialog在屏幕底部
        window.setGravity(Gravity.BOTTOM);
        //设置dialog弹出时的动画效果，从屏幕底部向上弹出
        window.setWindowAnimations(R.style.custom_dialog_anim);
        window.getDecorView().
                setPadding(0, 0, 0, 0);
        //获得window窗口的属性
        android.view.WindowManager.LayoutParams lp = window.getAttributes();
        //设置窗口宽度为充满全屏
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        //设置窗口高度为包裹内容
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        //将设置好的属性set回去
        window.setAttributes(lp);
        //将自定义布局加载到dialog上
        dialog.setContentView(dialogView);
        dialog.show();

    }

}
