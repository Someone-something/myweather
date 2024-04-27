package com.gaofh.myweather.util;

import android.os.Build;

import com.gaofh.myweather.interfaces.HttpCallBackListener;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.StringJoiner;

import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;

public class HttpUtil {
    /**
     *使用okhttp请求数据
     */
    public static void sendOkHttpRequest(String address, Callback callback){
        OkHttpClient okHttpClient=new OkHttpClient();
        Request request=new Request.Builder().url(address).build();
        okHttpClient.newCall(request).enqueue(callback);

    }
    /**
     * 拼接url
     */
     public static String builderUrl(String address, Map<String,String> params) {
         StringBuilder builder=new StringBuilder();
         for(Map.Entry<String,String> map:params.entrySet()){
                 builder.append("&").append(map.getKey()).append("=").append(map.getValue());
         }
         return address+"?"+builder.toString();
     }
    /**
     * 传入url返回请求结果
     */
    public static void getHttpResponse(String address, Map<String,String> params, HttpCallBackListener listener) {
        new Thread(new Runnable() {
            String urlAddress=builderUrl(address,params);
            HttpURLConnection urlConnection=null;
            @Override
            public void run() {
                StringBuilder builder=new StringBuilder();
                try {
                    URL url=new URL(urlAddress);
                    urlConnection=(HttpURLConnection) url.openConnection();
                    urlConnection.setRequestMethod("GET");
                    urlConnection.setConnectTimeout(8000);
                    urlConnection.setReadTimeout(8000);
                    InputStream inputStream=urlConnection.getInputStream();
                    BufferedReader reader=new BufferedReader(new InputStreamReader(inputStream));
                    String line=null;
                    while ((line=reader.readLine())!=null){
                        builder.append(line);
                    }
                    listener.onFinish(builder.toString());
                }catch (Exception e){
                    e.printStackTrace();
                    listener.onError(e);
                }finally {
                    if(urlConnection!=null){
                        urlConnection.disconnect();
                    }
                }
            }
        }).start();

    }
}
