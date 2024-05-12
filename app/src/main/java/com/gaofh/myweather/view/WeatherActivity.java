package com.gaofh.myweather.view;

import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentActivity;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.bumptech.glide.Glide;
import com.gaofh.myweather.R;
import com.gaofh.myweather.gson.Basic;
import com.gaofh.myweather.gson.Forecast;
import com.gaofh.myweather.gson.Now;
import com.gaofh.myweather.gson.Weather;
import com.gaofh.myweather.service.AutoUpdateService;
import com.gaofh.myweather.util.HttpUtil;
import com.gaofh.myweather.util.LogUtil;
import com.gaofh.myweather.util.UrlUtil;
import com.gaofh.myweather.util.Utility;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class WeatherActivity extends AppCompatActivity {
    private ScrollView weatherLayout;
    private TextView titleCity;
    private TextView titleUpdateTime;
    private TextView degreeText;
    private TextView weatherInfoText;
    private LinearLayout forecastLayout;
    private TextView aqiText;
    private TextView pm25Text;
    private TextView comfortText;
    private TextView carWashText;
    private TextView sportText;
    private ImageView bingPicImg;
    public SwipeRefreshLayout swipeRefresh;
    public DrawerLayout drawerLayout;
    private Button chooseArea;

    public void onCreate(Bundle saveInstanceState){
        super.onCreate(saveInstanceState);
//        if(Build.VERSION.SDK_INT>=21){
//            View decorView=getWindow().getDecorView();
//            decorView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN|View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
//        }
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_weather_layout);
        initView();
        SharedPreferences prefs= PreferenceManager.getDefaultSharedPreferences(this);
        String weatherString=prefs.getString("weather",null);
        final String weatherId;
        if (!TextUtils.isEmpty(weatherString)){
            //有缓存直接解析天气数据
            Weather weather= Utility.handleWeatherResponse(weatherString);
            weatherId=weather.basic.weatherId;
            showWeatherInfo(weather);
        }else {
            //从服务器取数据
            weatherId=getIntent().getStringExtra("weather_id");
            weatherLayout.setVisibility(View.INVISIBLE);
            requestWeather(weatherId);
        }
        swipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                requestWeather(weatherId);
            }
        });
        String bingPic=prefs.getString("bing_pic",null);
            if (!TextUtils.isEmpty(bingPic)){
                Glide.with(this).load(bingPic).into(bingPicImg);
            }else {
                loadBingPic();
            }
            chooseArea.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    drawerLayout.openDrawer(GravityCompat.START);
                }
            });

        }

    /**
     * 加载每日一图
     */
    public void loadBingPic(){
       HttpUtil.sendOkHttpRequest(UrlUtil.requestBingPic, new Callback() {
           @Override
           public void onFailure(Call call, IOException e) {
               Toast.makeText(WeatherActivity.this,"图片加载失败",Toast.LENGTH_SHORT).show();
           }

           @Override
           public void onResponse(Call call, Response response) throws IOException {
                 final String bingPic=response.body().string();
                 String bingPicUrl=Utility.handleBingPicResponse(bingPic);
                 LogUtil.d("GAOFH",bingPic);
                 SharedPreferences.Editor editor=PreferenceManager.getDefaultSharedPreferences(WeatherActivity.this).edit();
                 editor.putString("bing_pic",bingPicUrl);
                 editor.apply();
                 runOnUiThread(new Runnable() {
                     @Override
                     public void run() {
                         Glide.with(WeatherActivity.this).load(bingPicUrl).into(bingPicImg);
                     }
                 });
           }
       });
    }
    public void initView(){
        weatherLayout=findViewById(R.id.weather_layout);
        titleCity=findViewById(R.id.title_city);
        titleUpdateTime=findViewById(R.id.title_update_time);
        degreeText=findViewById(R.id.degree_text);
        weatherInfoText=findViewById(R.id.weather_info_text);
        forecastLayout=findViewById(R.id.forecast_layout);
        aqiText=findViewById(R.id.api_text);
        pm25Text=findViewById(R.id.pm25_text);
        comfortText=findViewById(R.id.comfort_text);
        carWashText=findViewById(R.id.car_wash_text);
        sportText=findViewById(R.id.sport_text);
        bingPicImg=findViewById(R.id.bing_pic_img);
        swipeRefresh=findViewById(R.id.swipe_refresh);
        swipeRefresh.setColorSchemeColors(getResources().getColor(R.color.purple_200));
        drawerLayout=findViewById(R.id.drawer_layout);
        chooseArea=findViewById(R.id.nav_button);
    }
    /**
     * 处理和展示weather类的数据
     *
     */
    public void showWeatherInfo(Weather weather){
        if(weather!=null&"ok".equals(weather.status)) {
            String cityName = weather.basic.cityName;
            String updateTime = weather.basic.update.updateTime.split(" ")[0];
            String degree = weather.now.temperature + "℃";
            LogUtil.d("获取到当前的温度", weather.now.temperature);
            String weatherInfo = weather.now.more.info;
            titleCity.setText(cityName);
            titleUpdateTime.setText(updateTime);
            degreeText.setText(degree);
            weatherInfoText.setText(weatherInfo);
            forecastLayout.removeAllViews();
            for (Forecast forecast : weather.forecastList) {
                View view = LayoutInflater.from(this).inflate(R.layout.forecast_item, forecastLayout, false);
                TextView dateText = view.findViewById(R.id.date_text);
                TextView infoText = view.findViewById(R.id.info_text);
                TextView maxText = view.findViewById(R.id.max_text);
                TextView minText = view.findViewById(R.id.min_text);
                dateText.setText(forecast.date);
                infoText.setText(forecast.more.info);
                maxText.setText(forecast.temperature.max);
                minText.setText(forecast.temperature.min);
                forecastLayout.addView(view);
            }
            if (weather.aqi != null) {
                aqiText.setText(weather.aqi.city.aqi);
                pm25Text.setText(weather.aqi.city.pm25);
            }
            String comfort = "舒适度：" + weather.suggestion.comfort.info;
            String carWash = "洗车指数：" + weather.suggestion.carWash.info;
            String sport = "运动指数：" + weather.suggestion.sport.info;
            comfortText.setText(comfort);
            carWashText.setText(carWash);
            sportText.setText(sport);
            weatherLayout.setVisibility(View.VISIBLE);
            loadBingPic();
            //启动后台服务
            Intent intent=new Intent(this, AutoUpdateService.class);
            startService(intent);
        }else {
            Toast.makeText(this,"天气信息加载失败",Toast.LENGTH_SHORT).show();
        }
    }
    /**
     * 根据天气id查询天气信息
     */
    public void requestWeather(final String weatherId){
        String weatherUrl= UrlUtil.weatherUrl+"?cityid="+weatherId+"&key="+UrlUtil.weatherKey;
        LogUtil.d("拼接请求天气的URL",weatherUrl);
        HttpUtil.sendOkHttpRequest(weatherUrl, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        swipeRefresh.setRefreshing(false);
                        Toast.makeText(WeatherActivity.this,"执行了onFail方法",Toast.LENGTH_SHORT).show();
                    }
                });
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
              final String responseText=response.body().string();
                LogUtil.d("返回的JSON数据",responseText);
              final Weather weather=Utility.handleWeatherResponse(responseText);
              runOnUiThread(new Runnable() {
                  @Override
                  public void run() {
                      if (weather!=null&&"ok".equals(weather.status)){
                          SharedPreferences.Editor editor=PreferenceManager.getDefaultSharedPreferences(WeatherActivity.this).edit();
                          editor.putString("weather",responseText);
                          editor.apply();
                          showWeatherInfo(weather);
                          swipeRefresh.setRefreshing(false);
                      }else {
                          Toast.makeText(WeatherActivity.this,"获取天气数据失败，请检查网络或重新试试吧",Toast.LENGTH_SHORT).show();
                      }
                  }
              });
            }
        });
    }
}
