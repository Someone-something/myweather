package com.gaofh.lovehym;

import android.content.Context;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.ProgressBar;

import androidx.appcompat.app.AlertDialog;

public class DownloadTask extends BackgroundTask {
    public Context context;
    public AlertDialog alertDialog;
    public DownloadTask(Context context){
        super(context);
        this.context=context;
    }

    @Override
    protected void onPreExecute() {
        AlertDialog.Builder builder=new AlertDialog.Builder(context);
        builder.setView(R.layout.progress_bar_layout);
        builder.setCancelable(true);
        builder.setTitle("提示");
        builder.setMessage("数据下载中....");
        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                alertDialog.cancel();
            }
        });
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                alertDialog.cancel();
            }
        });
        alertDialog=builder.create();
        alertDialog.show();
    }

    @Override
    protected String doInBackground(Integer... params) {
        return null;
    }

    @Override
    protected void onPostExecute(String result) {

    }

}
