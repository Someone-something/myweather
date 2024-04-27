package com.gaofh.myweather.view;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.PersistableBundle;
import android.util.Log;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentActivity;

import com.gaofh.myweather.R;
import com.gaofh.myweather.gson.ParseJSONWithGSON;
import com.gaofh.myweather.interfaces.HttpCallBackListener;
import com.gaofh.myweather.model.Weather;
import com.gaofh.myweather.util.HttpUtil;
import com.gaofh.myweather.util.LogUtil;
import com.gaofh.myweather.util.UrlUtil;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends FragmentActivity {
    public static final int MSG_1=1;    //判断网络请求工作已完成
    private TextView textView;
    private Weather weather;
    private Handler handler;
    private String jsonString;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity_layout);
        //执行获取天气的对象的方法
       // getWeather();
        handler=new Handler(new Handler.Callback() {
            @Override
            public boolean handleMessage(@NonNull Message message) {
                switch (message.what){
                    case MSG_1:
                        weather=(Weather) message.obj;
                        textView.setText(weather.getSuggestions());
                    break;
                    default:
                        break;
                }
                return true;
            }
        });
    }
    /**
     *获取天气的对象
     */
    public void  getWeather()  {
        HttpUtil.getHttpResponse(UrlUtil.weatherUrl,getUrlParams(),new HttpCallBackListener(){
            @Override
            public void onFinish(String response) {
                LogUtil.d("GAO--------onFinish","请求解析的字符串是："+response);
                Message message=new Message();
                message.what=MSG_1;
                message.obj=ParseJSONWithGSON.ParseJson(response); //通过JSON字符串，返回Weather对象
                handler.sendMessage(message);
            }

            @Override
            public void onError(Exception e) {

            }
        });
    }
    /**
     *构建请求天气情况的Map对象
     */
    public Map<String,String> getUrlParams(){
        Map<String,String> parmas=new HashMap<>();
        parmas.put("cityid","CN101190401");
        parmas.put("key","85137a95d3004e109e56c63433a757b2");
        return parmas;
    }


}
