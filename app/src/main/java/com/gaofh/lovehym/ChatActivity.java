package com.gaofh.lovehym;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class ChatActivity extends Activity {
    private ListView msgListView;
    private EditText inputText;
    private Button send;
    private MsgAdapter adapter;
    private List<Msg> msgList=new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.chat_main);
        initMsgs();
        adapter=new MsgAdapter(this,R.layout.msg_item,msgList);
        inputText=(EditText) findViewById(R.id.input_text);
        send=(Button) findViewById(R.id.send_message);
        msgListView=(ListView) findViewById(R.id.msg_list_view);
        msgListView.setAdapter(adapter);
        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String content=inputText.getText().toString();
                if (!"".equals(content)){
                    Msg msg=new Msg(content,Msg.TYPE_SEND);
                    msgList.add(msg);
                    //当有新消息时，通知刷新列表；
                    adapter.notifyDataSetChanged();
                    //将listview定位到最后一行；
                    msgListView.setSelection(msgList.size());
                    inputText.setText("");  //清空输入框
                }
            }
        });
    }
    private void initMsgs(){
        Msg msg1=new Msg("我是宝宝",Msg.TYPE_RECEIVED);
        msgList.add(msg1);
        Msg msg2=new Msg("你好宝宝，吃饭了嘛",Msg.TYPE_SEND);
        msgList.add(msg2);
        Msg msg3=new Msg("还没有呢，咋了",Msg.TYPE_RECEIVED);
        msgList.add(msg3);
        Msg msg4=new Msg("没事呀，就问问",Msg.TYPE_SEND);
        msgList.add(msg4);
    }
}
