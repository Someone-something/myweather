package com.gaofh.myweather.service;

import static android.app.PendingIntent.FLAG_MUTABLE;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.IBinder;
import android.os.SystemClock;
import android.preference.PreferenceManager;
import android.text.TextUtils;

import androidx.annotation.Nullable;

import com.gaofh.myweather.gson.Weather;
import com.gaofh.myweather.util.HttpUtil;
import com.gaofh.myweather.util.UrlUtil;
import com.gaofh.myweather.util.Utility;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class AutoUpdateService extends Service {
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
    @Override
    public int onStartCommand(Intent intent, int flags,int startId){
        updateWeather();
        updateBingPic();
        AlarmManager alarmManager=(AlarmManager) getSystemService(ALARM_SERVICE);
        int anHour=1*60*60*1000; //这是一小时的毫秒数
        long triggerAtTime= SystemClock.elapsedRealtime()+anHour;
        Intent i=new Intent(this, AutoUpdateService.class);
        PendingIntent pIntent=PendingIntent.getService(this,0,i,FLAG_MUTABLE);
        alarmManager.cancel(pIntent);
        alarmManager.set(AlarmManager.ELAPSED_REALTIME_WAKEUP,triggerAtTime,pIntent);
        return super.onStartCommand(intent,flags,startId);
    }
    /**
     * 更新天气信息
     */
    public void updateWeather(){
        SharedPreferences pres= PreferenceManager.getDefaultSharedPreferences(this);
        String weatherString=pres.getString("weather",null);
        if (!TextUtils.isEmpty(weatherString)){
            Weather weather= Utility.handleWeatherResponse(weatherString);
            String weatherId=weather.basic.weatherId;
            String weatherUrl= UrlUtil.weatherUrl+"?cityid="+weatherId+"&key="+UrlUtil.weatherKey;
            HttpUtil.sendOkHttpRequest(weatherUrl, new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {

                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                       String responseText=response.body().string();
                       Weather weather1=Utility.handleWeatherResponse(responseText);
                       if (weather1!=null&"ok".equals(weather1.status)){
                           SharedPreferences.Editor editor=PreferenceManager.getDefaultSharedPreferences(AutoUpdateService.this).edit();
                           editor.putString("weather",responseText);
                           editor.apply();

                       }
                }
            });

        }
    }
    public  void updateBingPic(){
             HttpUtil.sendOkHttpRequest(UrlUtil.requestBingPic, new Callback() {
                 @Override
                 public void onFailure(Call call, IOException e) {

                 }

                 @Override
                 public void onResponse(Call call, Response response) throws IOException {
                      String responseText=response.body().string();
                      String bingPicUrl=Utility.handleBingPicResponse(responseText);
                      SharedPreferences.Editor editor=PreferenceManager.getDefaultSharedPreferences(AutoUpdateService.this).edit();
                      editor.putString("bing_pic",bingPicUrl);
                      editor.apply();
                 }
             });
         }

}
