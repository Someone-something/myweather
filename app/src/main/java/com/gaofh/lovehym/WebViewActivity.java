package com.gaofh.lovehym;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONObject;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.StringReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

public class WebViewActivity extends Activity implements View.OnClickListener {
    public static final int RESPONSE_MSG=1;
    public WebView mWebView;
    public Button requestButton;
    public TextView responseText;
    public  Handler handler;

    @SuppressLint("HandlerLeak")
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
        handler=new Handler(){
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
        //获取从MainActivity传递过来的数据
        Msg msg=(Msg) getIntent().getParcelableExtra("msgs");
        News news=(News)getIntent().getSerializableExtra("news");
        LogUtil.d("GAO","Msg输出的Content是："+msg.getContent()+",输出的type是："+msg.getType());
        LogUtil.d("GAO","news输出的title是："+news.getTitle()+",输出的content是："+news.getContent());
    }
    @Override
    public void onClick(View view){
        switch (view.getId()){
            case R.id.send_request:
//         sendRequestWithHttpUrlConnection();
                HttpUtil.sendHttpRequest("http://192.168.1.2/get_data.json", new HttpCallbackListener() {
                    @Override
                    public void onFinish(String response) {
                        parseJSONWithGson(response);
                    }

                    @Override
                    public void onError(Exception e) {
                        AlertDialog.Builder builder=new AlertDialog.Builder(WebViewActivity.this);
                        builder.setMessage("网络异常");
                        AlertDialog dialog=builder.create();
                        dialog.show();
                    }
                });
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
//             HttpURLConnection urlConnection=null;
//                try {
//                    URL url=new URL("https://www.baidu.com");
//                    urlConnection=(HttpURLConnection) url.openConnection();
//                    urlConnection.setRequestMethod("GET");
//                    urlConnection.setConnectTimeout(8000);
//                    urlConnection.setReadTimeout(8000);
//                    InputStream inputStream=urlConnection.getInputStream();
//                    //下面对获取到的输入流进行读取
//                    BufferedReader reader=new BufferedReader(new InputStreamReader(inputStream));
//                    StringBuilder builder=new StringBuilder();
//                    String line;
//                    while ((line=reader.readLine())!=null){
//                        builder.append(line);
//                    }
//                    Message message=new Message();
//                    message.what=RESPONSE_MSG;
//                    message.obj=builder.toString();
//                    handler.sendMessage(message);
//                    reader.close();
////                    //测试代码
////                    urlConnection.setRequestMethod("POST");
////                    OutputStream outputStream=urlConnection.getOutputStream();
////                    DataOutputStream dataOutputStream=new DataOutputStream(outputStream);
////                    dataOutputStream.writeBytes("userName=gaofh&password=123456");
//
//                } catch (Exception e) {
//                    throw new RuntimeException(e);
//                }finally {
//                    if (urlConnection!=null){
//                        urlConnection.disconnect();
//                    }
//                }
                //访问本机的xml文件
                HttpURLConnection urlConnection=null;
                try {
                    //访问的是xml文件
//                    URL url=new URL("http://192.168.1.2/get_data.xml");
                    //访问的是json文件
                    URL url=new URL("http://192.168.1.2/get_data.json");
                    urlConnection=(HttpURLConnection) url.openConnection();
                    urlConnection.setRequestMethod("GET");
                    urlConnection.setConnectTimeout(8000);
                    urlConnection.setReadTimeout(8000);
                    InputStream inputStream=urlConnection.getInputStream();
                    BufferedReader reader=new BufferedReader(new InputStreamReader(inputStream));
                    String line=null;
                    StringBuilder builder=new StringBuilder();
                    while ((line=reader.readLine())!=null){
                        builder.append(line);
                    }
                    String toParseXml=builder.toString();
                    //使用XmlPullParser解析
//                    parseXMLWithPull(toParseXml);
                    //使用SaxParser解析
//                    xmlParseWithSax(toParseXml);
                    //使用JSONObject解析数据
//                    parseJSONWithJSONObject(toParseXml);
                    //使用Gson解析数据
                    parseJSONWithGson(toParseXml);
                }catch (Exception e){
                    e.printStackTrace();
                }finally {
                    if(urlConnection!=null){
                        urlConnection.disconnect();
                    }
                }
            }
        }).start();
    }
    private void parseXMLWithPull(String xmlData){
        try {
            XmlPullParserFactory factory=XmlPullParserFactory.newInstance();
            XmlPullParser parser=factory.newPullParser();
            parser.setInput(new StringReader(xmlData));
            int eventType=parser.getEventType();
            String id="";
            String name="";
            String version="";
            while (eventType!=XmlPullParser.END_DOCUMENT){
                String nodeName=parser.getName();
                Log.d("while","这次的解析事件是："+eventType+"/这次的节点是："+nodeName);
                switch (eventType){
                    case XmlPullParser.START_TAG:
                        Log.d("XmlPullParser.START_TAG","这次的解析事件是："+eventType+"/这次的节点是："+nodeName);
                      if("id".equals(nodeName)){
                          id=parser.nextText();
                      }else if("name".equals(nodeName)){
                          name=parser.nextText();
                      }else if("version".equals(nodeName)){
                          version=parser.nextText();
                      }
                      break;
                    case XmlPullParser.END_TAG:
                        Log.d("xmlPullParser.END_TAG","这次的解析事件是："+eventType+"/这次的节点是："+nodeName);
                        if("app".equals(nodeName)){
                            Log.d(BaseActivity.TGA,"解析的id是"+id);
                            Log.d(BaseActivity.TGA,"解析的name是"+name);
                            Log.d(BaseActivity.TGA,"解析的version是"+version);
                        }
                    default:
                        break;
                }
                eventType=parser.next();
            }
        }catch (Exception e){
            e.printStackTrace();
        }

    }
    private void xmlParseWithSax(String xmlData) {
        try {
            SAXParserFactory factory=SAXParserFactory.newInstance();
            XMLReader reader=factory.newSAXParser().getXMLReader();
            reader.setContentHandler(new MyHandler());
            reader.parse(new InputSource(new StringReader(xmlData)));
        }catch (Exception e){
           e.printStackTrace();
        }

    }
    private void parseJSONWithJSONObject(String jsonString){
         try {
             JSONArray jsonArray=new JSONArray(jsonString);
             for(int i=0;i<jsonArray.length();i++){
                 JSONObject jsonObject=jsonArray.getJSONObject(i);
                 String id=jsonObject.getString("id");
                 String name=jsonObject.getString("name");
                 String version=jsonObject.getString("version");
                 Log.d("JSONArray","JSONArray的长度是："+jsonArray.length());
                 Log.d("JSON","id是："+id);
                 Log.d("JSON","version是："+version);
                 Log.d("JSON","name是："+name);
             }
         }catch (Exception e){
             e.printStackTrace();
         }

    }
    private void parseJSONWithGson(String jsonString){
        Gson gson=new Gson();
        List<App> apps=gson.fromJson(jsonString,new TypeToken<List<App>>(){}.getType());
        for(App app:apps){
            Log.d("GAO","id是："+app.getId());
            Log.d("GAO","name是："+app.getName());
            Log.d("GAO","version是："+app.getVersion());
        }
    }
    public class App{
        String id;
        String version;
        String name;
     public void setId(String id){
         this.id=id;
     }
     public String getId(){
         return id;
     }
     public void setVersion(String version){
         this.version=version;
     }
     public String getVersion(){
         return version;
     }
     public void setName(String name){
         this.name=name;
     }
     public String getName(){
         return name;
     }
    }

}

