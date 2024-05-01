package com.gaofh.myweather.view;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentActivity;

import com.gaofh.myweather.R;
import com.gaofh.myweather.interfaces.HttpCallBackListener;
import com.gaofh.myweather.model.Weather;
import com.gaofh.myweather.util.HttpUtil;
import com.gaofh.myweather.util.LogUtil;
import com.gaofh.myweather.util.UrlUtil;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends FragmentActivity {
    public static final int MSG_1 = 1;    //判断网络请求工作已完成
    private TextView textView;
    private Weather weather;
    private Handler handler;
    private String jsonString;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity_layout);
    }
}
