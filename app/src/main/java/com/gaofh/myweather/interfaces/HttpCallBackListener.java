package com.gaofh.myweather.interfaces;

public interface HttpCallBackListener {
    void onFinish(String response);
    void onError(Exception e);
}
