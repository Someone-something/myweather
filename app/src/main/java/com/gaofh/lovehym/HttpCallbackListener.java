package com.gaofh.lovehym;

public interface HttpCallbackListener {
    void onFinish(String response);
    void onError(Exception e);
}
