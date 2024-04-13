package com.gaofh.lovehym;

import android.content.Context;
import android.os.Handler;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public abstract class BackgroundTask {
    private final Context context;
    private final Handler handler;
    private ExecutorService THREAD_POOL_EXECUTOR;
    public BackgroundTask(Context context){
        this.context=context;
         handler=new Handler(context.getMainLooper());
         THREAD_POOL_EXECUTOR= Executors.newFixedThreadPool(5);
    }
    private interface Callback<R>{
        void onComplete(R result);
    }
    private <R> void executeAsync(final Callable<R> callable,final Callback<R> callback){
        THREAD_POOL_EXECUTOR.submit(new Runnable() {
            @Override
            public void run() {
                final R result;
                try {
                    result=callable.call();
                }catch (Exception e){
                    throw new RuntimeException();
                }
                runUI(new Runnable() {
                    @Override
                    public void run() {
                        callback.onComplete(result);
                    }
                });
            }
        });
    }
    private void runUI(Runnable runnable){
        if (Thread.currentThread()!=context.getMainLooper().getThread()){
            handler.post(runnable);
        }else {
            runnable.run();
        }

    }
    public void shutdown(){
    THREAD_POOL_EXECUTOR.shutdown();
    }
    public void executeOnExecutor(){
      runUI(new Runnable() {
          @Override
          public void run() {
              onPreExecute();
          }
      });
      executeAsync(new Callable<String>() {
          @Override
          public String call() {
              return doInBackground();
          }
      }, new Callback<String>() {
          @Override
          public void onComplete(String result) {
              onPostExecute(result);
          }
      });
    }
    protected abstract void onPreExecute();
    protected abstract String doInBackground(Integer...params);
    protected abstract void onPostExecute(String result);

}
