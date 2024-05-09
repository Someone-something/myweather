package com.gaofh.myweather.view;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentActivity;

import com.gaofh.myweather.R;

public class MainActivity extends FragmentActivity {
    public static final int MSG_1 = 1;    //判断网络请求工作已完成
    private TextView textView;
    private Handler handler;
    private String jsonString;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity_layout);
        SharedPreferences prefs= PreferenceManager.getDefaultSharedPreferences(this);
        if (prefs.getString("weather",null)!=null){
            Intent intent=new Intent("com.gaofh.myweather.weather.activity");
            startActivity(intent);
            finish();
        }
    }
}
