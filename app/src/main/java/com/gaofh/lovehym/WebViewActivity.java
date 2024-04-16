package com.gaofh.lovehym;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.TextView;

import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class WebViewActivity extends Activity implements View.OnClickListener {
    public static final int RESPONSE_MSG=1;
    public WebView mWebView;
    public Button requestButton;
    public TextView responseText;

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.web_view_layout);
        requestButton=findViewById(R.id.send_request);
        responseText=findViewById(R.id.request_textView);
        requestButton.setOnClickListener(this);
        mWebView=(WebView) findViewById(R.id.webView);
        mWebView.getSettings().setJavaScriptEnabled(true);
        mWebView.setWebViewClient(new WebViewClient(){
            @Override
            public boolean shouldOverrideUrlLoading(WebView view,String url){
                view.loadUrl(url);
                return true;
            }
        });
        mWebView.loadUrl("https://www.baidu.com");
        @SuppressLint("HandlerLeak") Handler handler=new Handler(){
            @Override
            public void handleMessage(Message message){
                switch (message.what){
                    case RESPONSE_MSG:
                    String response=(String) message.obj;
                    responseText.setText(response);
                        break;
                    default:
                        break;
                }
            }
        };
    }
    @Override
    public void onClick(View view){
        switch (view.getId()){
            case R.id.send_request:

                break;
            default:
                break;
        }
    }
    private  void sendRequestWithHttpUrlConnection(){
        //开启线程来发起网络请求
        new Thread(new Runnable() {
            @Override
            public void run() {
             HttpURLConnection urlConnection=null;

            }
        }).start();
    }
}

