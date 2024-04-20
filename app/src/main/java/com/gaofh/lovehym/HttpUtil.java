package com.gaofh.lovehym;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class HttpUtil {
    public static void sendHttpRequest(String address,HttpCallbackListener listener){
         new Thread(new Runnable() {
             HttpURLConnection connection=null;
             @Override
             public void run() {
                 try {

                     URL url=new URL(address);
                     connection=(HttpURLConnection) url.openConnection();
                     connection.setRequestMethod("GET");
                     connection.setConnectTimeout(8000);
                     connection.setReadTimeout(8000);
                     connection.setDoInput(true);
                     connection.setDoOutput(true);
                     InputStream inputStream=connection.getInputStream();
                     BufferedReader reader=new BufferedReader(new InputStreamReader(inputStream));
                     StringBuilder builder=new StringBuilder();
                     String line=null;
                     while ((line=reader.readLine())!=null){
                         builder.append(line);
                     }
                     if(listener!=null) {
                         listener.onFinish(builder.toString());
                     }
                 }catch (Exception e){
                     e.printStackTrace();
                     if (listener!=null) {
                         listener.onError(e);
                     }
                 }finally {
                     if (connection!=null)
                         connection.disconnect();
                 }
             }
         }).start();
    }
}
