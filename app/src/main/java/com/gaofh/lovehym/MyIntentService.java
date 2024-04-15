package com.gaofh.lovehym;

import android.app.IntentService;
import android.app.job.JobInfo;
import android.content.Intent;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.JobIntentService;

public class MyIntentService extends IntentService {

    /**
     * @param name
     * @deprecated
     */
//    public MyIntentService(String name) {
//        super(name);
//    }
    public MyIntentService(){
        super("name");
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
       Log.d(BaseActivity.TGA,"onHandleIntent方法里的线程id:"+Thread.currentThread().getId());
    }
    @Override
    public void onDestroy(){
        Log.d(BaseActivity.TGA,"这是在执行onDestroy方法");
        super.onDestroy();
    }
}
