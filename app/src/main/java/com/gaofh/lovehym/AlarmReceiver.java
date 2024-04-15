package com.gaofh.lovehym;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import java.util.concurrent.Callable;

public class AlarmReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
     Intent intent1=new Intent(context,LongRunningService.class);
     context.startService(intent1);
    }
}
