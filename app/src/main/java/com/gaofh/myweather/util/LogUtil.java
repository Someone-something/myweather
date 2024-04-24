package com.gaofh.myweather.util;

import android.util.Log;

public class LogUtil {
    public static final int VERSION=0;
    public static final int IFNO=1;
    public static final int DEBUG=2;
    public  static final int WARN=3;
    public  static  final  int ERROR=4;
    public static final int CURRENT=2;
    public static final int NOTHING=6;
    public static void v(String TGA,String MSG){
        if(VERSION>=CURRENT){
            Log.v(TGA,MSG);
        }
    }
    public static void i(String TGA,String MSG){
        if(IFNO>=CURRENT){
            Log.i(TGA,MSG);
        }
    }
    public static void d(String TGA,String MSG){
        if(DEBUG>=CURRENT){
            Log.d(TGA,MSG);
        }
    }
    public static void w(String TGA,String MSG){
        if(WARN>=CURRENT){
            Log.w(TGA,MSG);
        }
    }
    public static void e(String TGA,String MSG){
        if(ERROR>=CURRENT){
            Log.e(TGA,MSG);
        }
    }
}

