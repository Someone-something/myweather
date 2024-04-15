package com.gaofh.lovehym;

import android.annotation.SuppressLint;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.os.SystemClock;
import android.util.Log;

import androidx.annotation.Nullable;

import java.util.Date;

public class LongRunningService extends Service {
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
    public void onCreate(){
        super.onCreate();
    }
    @SuppressLint("ScheduleExactAlarm")
    public int onStartCommand(Intent intent, int flags, int startId){
        new Thread(new Runnable() {
            @Override
            public void run() {
                Log.d(BaseActivity.TGA,"LongRunningService的onStartCommand方法在"+new Date().toString()+"开始运行");
            }
        }).start();
        AlarmManager alarmManager=(AlarmManager)getSystemService(Context.ALARM_SERVICE);
        //一个小时的毫秒数
        int anHour=1000*60;
        long triggerAtTime= SystemClock.elapsedRealtime()+anHour;
        Intent intent1=new Intent(this,AlarmReceiver.class);
        PendingIntent pendingIntent=PendingIntent.getBroadcast(this,0,intent1,PendingIntent.FLAG_IMMUTABLE);
        alarmManager.setExact(AlarmManager.ELAPSED_REALTIME_WAKEUP,triggerAtTime,pendingIntent);
        return super.onStartCommand(intent,flags,startId);
    }
}
