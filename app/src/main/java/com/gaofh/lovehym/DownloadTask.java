package com.gaofh.lovehym;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DownloadTask extends AsyncTask<Void,String,Boolean> {
    public Context context;
    public AlertDialog alertDialog;
    public AlertDialog.Builder builder;
    public TextView textView;
    public String currentTime;
    public Date date;
    public SimpleDateFormat dateFormat;
    public DownloadTask(Context context){
        super();
        this.context=context;
    }

    @Override
    protected void onPreExecute() {
        date=new Date();
        dateFormat=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        currentTime=dateFormat.format(date);
        builder=new AlertDialog.Builder(context);
        View view=LayoutInflater.from(context).inflate(R.layout.alter_dialog_layout,null);
        builder.setCancelable(true);
        builder.setTitle("提示信息");
        builder.setMessage("这是弹框的详细信息");
//        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialogInterface, int i) {
//                alertDialog.cancel();
//            }
//        });
//        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialogInterface, int i) {
//                alertDialog.cancel();
//            }
//        });
        alertDialog=builder.create();
        alertDialog.show();
        LogUtil.d("GAO","这是onPreExecute方法主线程id:"+Thread.currentThread().getId());
        alertDialog.getWindow().setContentView(view);
        textView=view.findViewById(R.id.alert_dialog_message);
    }
    @SuppressLint("SuspiciousIndentation")
    @Override
    protected Boolean doInBackground(Void... voids) {
        int max=2;
        int start=0;
        while (true) {
            if (start<max){
                start++;
                date=new Date();
            currentTime = dateFormat.format(date);
                LogUtil.d("GAO","这是doInBackground子线程id:"+Thread.currentThread().getId());
            // onProgressUpdate(currentTime);
            publishProgress(currentTime);
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }}else {
                Thread.currentThread().interrupt();//停止子线程
                break;
            }
        }
        return true ;
    }
    @Override
    protected void onProgressUpdate(String...values){
//        Log.d("GAO","这是在执行doProgressUpdate方法");
//        Log.d("GAO",currentTime);
        alertDialog.setMessage(currentTime);
        LogUtil.d("GAO","这是onProgressUpdate主线程id:"+Thread.currentThread().getId());
        textView.setText("数据马上就加载完成了，再等等");

    }
    @Override
    protected void onPostExecute(Boolean result){
//        Log.d("GAO","这是在执行PostExecute方法");
        LogUtil.d("GAO","这是onPostExecute主线程id:"+Thread.currentThread().getId());

        alertDialog.cancel();


    }

    protected String doInBackground(Integer... params) {
        new Thread(new Runnable() {
           @Override
           public void run() {
               currentTime=new Date().toString();
           }
       }).start();
        for (int i=1;i<5;i++){
            currentTime=new Date().toString();
        }
        return currentTime;
    }

    protected void onPostExecute(String result) {
      textView.setText(currentTime+result);
    }

}
