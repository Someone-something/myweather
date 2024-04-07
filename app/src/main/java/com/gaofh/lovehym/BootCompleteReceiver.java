package com.gaofh.lovehym;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

public class BootCompleteReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context,Intent intent){
        intent.setAction("android.intent.action.MAIN");
        context.startActivity(intent);
        Toast.makeText(context, "收到了一条开机广播", Toast.LENGTH_SHORT).show();
    }
}
