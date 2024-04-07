package com.gaofh.lovehym;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

public class MyBroadcastReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent){
        Toast.makeText(context, "MyBroadcastReceiver收到了一条广播", Toast.LENGTH_SHORT).show();
        Log.d("gaofh","收到了一条广播");
    }
}
