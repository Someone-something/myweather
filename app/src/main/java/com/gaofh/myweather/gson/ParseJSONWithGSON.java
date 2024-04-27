package com.gaofh.myweather.gson;

import com.gaofh.myweather.model.Weather;
import com.gaofh.myweather.util.LogUtil;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;

import java.io.DataOutputStream;
import java.util.List;

public class ParseJSONWithGSON {
    public static Weather ParseJson(String jsonString){
        Gson gson=new Gson();
//        LogUtil.d("GAO-----ParseJson",jsonString);
        Weather currentWeather=new Weather();
        List<Weather> weathers=gson.fromJson(jsonString,new TypeToken<List<Weather>>(){}.getType());
        for(Weather weather:weathers){
          currentWeather.setStatus(weather.getStatus());
          currentWeather.setBasic(weather.getBasic());
          currentWeather.setApi(weather.getApi());
          currentWeather.setNow(weather.getNow());
          currentWeather.setSuggestions(weather.getSuggestions());
          currentWeather.setDaily_forecast(weather.getDaily_forecast());
        }
        return currentWeather;
    }
}
