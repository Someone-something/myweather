package com.gaofh.lovehym;

import android.content.Context;
import android.os.AsyncTask;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.ProgressBar;

public class DownloadTask extends BackgroundTask {
    public Context context;
    public ProgressBar progressBar;
    public DownloadTask(Context context){
        super(context);
        this.context=context;
    }

    @Override
    protected void onPreExecute() {
        progressBar= LayoutInflater.from(context).inflate(R.layout.login_layout,null).findViewById(R.id.login_activity_progressBar);
        progressBar.setProgress(50);
        progressBar.setMax(100);
        progressBar.setVisibility(View.INVISIBLE);
    }

    @Override
    protected String doInBackground(Integer... params) {
        return null;
    }

    @Override
    protected void onPostExecute(String result) {

    }

}
