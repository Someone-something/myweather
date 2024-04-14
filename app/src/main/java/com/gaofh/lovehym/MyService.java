package com.gaofh.lovehym;

import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;

public class MyService extends Service {
    public DownloadBinder downloadBinder=new DownloadBinder();
    class DownloadBinder extends Binder{
        public void startDownload(){
            Log.d("GAO","这是DownloadBinder的startDownload方法");
        }
        public int getProgress(){
            Log.d("GAO","这是DownloadBinder的getProgress方法");
            return 0;
        }
        public boolean finishDownload(){
            Log.d("GAO","这是DownloadBinder的finishDownload方法");
            return true;
        }

    }
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        Log.d("GAO","这是MyService的onBind方法");
        return downloadBinder;
    }
    @Override
    public void onCreate(){
        Log.d("GAO","这是MyService的onCreate方法");
        super.onCreate();
//        Notification notification=new Notification(R.drawable.enemy_head_02,"这是一条服务通知",System.currentTimeMillis());
        Intent intent=new Intent(this,MainActivity.class);
        PendingIntent pendingIntent=PendingIntent.getActivity(this,PendingIntent.FLAG_CANCEL_CURRENT,intent,PendingIntent.FLAG_IMMUTABLE);
        Notification notification= new NotificationCompat.Builder(this,"001")
                .setSmallIcon(R.drawable.enemy_head_02)
                .setContentTitle("前台通知")
                .setContentText("这是一条前台通知")
                .setContentIntent(pendingIntent)
                .build();
        startForeground(1,notification);

    }
    @Override
    public int onStartCommand(Intent intent,int flags,int startId){
        Log.d("GAO","这是MyService的onStartCommand方法");
        return super.onStartCommand(intent,flags,startId);
    }
    public void onDestroy(){
        Log.d("GAO","这是MyService的onDestroy方法");
        super.onDestroy();
    }
}
