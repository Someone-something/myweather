package com.gaofh.lovehym;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.View;
import android.view.WindowManager;

public class ForceOfflineReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(final Context context,Intent intent){
        Activity currentActivity=ActivityCollector.getmCurrentActivity();
        AlertDialog.Builder dialogBuilder=new AlertDialog.Builder(currentActivity);
        dialogBuilder.setTitle("警告");
        dialogBuilder.setMessage("账号在别的地方登录，你被强制下线");
        dialogBuilder.setCancelable(false);
        dialogBuilder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                ActivityCollector.finishActivityAll();
                Intent intent=new Intent(currentActivity,LoginActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                currentActivity.startActivity(intent);
            }
        });
        AlertDialog alertDialog=dialogBuilder.create();
        //不能设置弹框的类型，不然报错
//        alertDialog.getWindow().setType(WindowManager.LayoutParams.TYPE_TOAST);
        alertDialog.show();
    }
}
