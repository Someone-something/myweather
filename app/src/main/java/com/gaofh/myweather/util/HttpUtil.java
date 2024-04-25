package com.gaofh.myweather.util;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class HttpUtil {

    public static String getHttpResponse(String address){
        HttpURLConnection urlConnection=null;
        StringBuilder builder=new StringBuilder();
        try {
            URL url=new URL(address);
            urlConnection=(HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.setConnectTimeout(8000);
            urlConnection.setReadTimeout(8000);
            InputStream inputStream=urlConnection.getInputStream();
            BufferedReader reader=new BufferedReader(new InputStreamReader(inputStream));
            String line="";
            while ((line=reader.readLine())!=null){
                builder.append(line);
            }
        }catch (Exception e){
          e.printStackTrace();
        }finally {
            if(urlConnection!=null){
                urlConnection.disconnect();
            }
        }
       return builder.toString();
    }
}
