package com.gaofh.lovehym;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Binder;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.NetworkInterface;


public class MainActivity extends BaseActivity {
     private Button firstButton;
     private Button secondButton;
     private Button thirdButton;
     private Button fourButton;
     private Button fiveButton;
     private Button sixButton;
     private TextView title;
     private EditText editText;
     private ImageView imageView;
     private MainBroadcastReceiver mainBroadcastReceive;
     private MyDatabaseSQLiteHelper dbHelper;
     private SQLiteDatabase database;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        //注册界面
        setContentView(R.layout.activity_main);
        firstButton=(Button)findViewById(R.id.firstbotton);
        secondButton=(Button)findViewById(R.id.secoundbutton);
        thirdButton=(Button) findViewById(R.id.thirdbutton);
        fourButton=(Button) findViewById(R.id.fourbutton);
        fiveButton=(Button) findViewById(R.id.fivebutton);
        sixButton=(Button) findViewById(R.id.sixbutton);
        title=(TextView) findViewById(R.id.title);
        editText=(EditText) findViewById(R.id.firstedittext);
        dbHelper=new MyDatabaseSQLiteHelper(this,"BookStore.db",null,3);
        database=dbHelper.getWritableDatabase();
        if(getData()!=null){
          //  editText.setText(getData());
           // editText.setSelection(getData().length());
        }

        imageView=(ImageView)findViewById(R.id.imageView);
        /**
         * 注册广播
         */
        IntentFilter intentFilter=new IntentFilter();
        intentFilter.addAction("android.net.conn.CONNECTIVITY_CHANGE");
        mainBroadcastReceive=new MainBroadcastReceiver();
        registerReceiver(mainBroadcastReceive,intentFilter);
        //处理第一个按钮的点击事件
        firstButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                if(editText.getText().toString()==null)
//                {
//                    Toast.makeText(MainActivity.this, "请先在输入框输入你的文字", Toast.LENGTH_SHORT).show();
//                }
//                String getstring=editText.getText().toString();
//                title.setText(getstring);
//                Intent intent=new Intent("com.gaofh.loveym.newsMainActivity_Action_START");
//                startActivity(intent);
//                Intent intent=new Intent("com.gaofh.loveHym.ForcerOfflineReceiver");
//                intent.setPackage("com.gaofh.lovehym");
//                sendBroadcast(intent);
                ContentValues values=new ContentValues();
                //组装第一条数据
                values.put("name","平凡的世界");
                values.put("author","路遥");
                values.put("pages",654);
                values.put("price",34.6);
                values.put("category_id",23);
                //向数据库插入第一条数据
                database.insert("book",null,values);
                values.clear();
                //组装第二天数据
                values.put("name","从你的全世界路过");
                values.put("author","陈奕迅");
                values.put("pages",99);
                values.put("price",96.67);
                values.put("category_id",65);
                //向数据库插入第二条数据
                database.insert("book",null,values);
                values.clear();
            }
        });
        //处理第二个按钮的点击事件
        secondButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //imageView.setImageResource(R.drawable.enemy_head_01);
//                Intent intent=new Intent("com.gaofh.lovehym.fragment_test_activity_Action_START");
//                startActivity(intent);
                /**
                 *创建数据库book
                 */
                dbHelper.getWritableDatabase();
            }
        });
        //处理第三个按钮的点击事件
        thirdButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Intent intent=new Intent(MainActivity.this, ChatActivity.class);
//                startActivity(intent);
                /**
                 *点击通过sharedPreferences保存数据
                 */
//                SharedPreferences.Editor editor=getSharedPreferences("用户信息",MODE_PRIVATE).edit();
//                editor.putString("姓名","高芳华");
//                editor.putInt("年龄",29);
//                editor.putString("性别","男");
//                editor.commit();
                /**
                 *点击通过sharedPreferences获取数据
                 */
//                SharedPreferences dataReader=getSharedPreferences("用户信息",MODE_PRIVATE);
//                StringBuilder stringBuilder=new StringBuilder();
//                String name=dataReader.getString("姓名","张三");
//                String sex=dataReader.getString("性别","女");
//                int age=dataReader.getInt("年龄",18);
//                stringBuilder.append(name);
//                stringBuilder.append(sex);
//                stringBuilder.append(age);
//                editText.setText(stringBuilder.toString());
                //更新数据库的数据
                ContentValues values=new ContentValues();
                values.put("price",1000);
                database.update("book",values,"name=?",new String[]{"平凡的世界"});
                values.clear();
            }
        });
        //处理第四个按钮的点击事件
        fourButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //把价格超过500的数据删掉
                database.delete("book","price>?",new String[]{"500"});
            }
        });
        //处理第五个按钮的点击事件
        fiveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //查询数据
                Cursor cursor=database.query("book",null,null,null,null,null,null);
                if(cursor.moveToFirst()){
                    do {
                        //遍历cursor，取出数据

                       @SuppressLint("Range") String name=cursor.getString(cursor.getColumnIndex("name"));
                        @SuppressLint("Range") String author=cursor.getString(cursor.getColumnIndex("author"));
                        @SuppressLint("Range") double price=cursor.getDouble(cursor.getColumnIndex("price"));
                        @SuppressLint("Range") int pages=cursor.getInt(cursor.getColumnIndex("pages"));
                        Log.d("MainActivity","书名是"+name);
                        Log.d("MainActivity","作者是"+author);
                        Log.d("MainActivity","价格是"+price);
                        Log.d("MainActivity","总书页是"+pages);
                    }while (cursor.moveToNext());
                }
                cursor.close();
            }
        });
        //处理第六个按钮的事件
        sixButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                database.beginTransaction();   //开启事务
                try{
                    database.delete("book",null,null);
//                    if(true){
//                        throw new NullPointerException();  //手动抛出异常，导致事务失败
//                    }
                    ContentValues contentValues=new ContentValues();
                    contentValues.put("name","回到过去");
                    contentValues.put("author","周杰伦");
                    contentValues.put("price",25.3);
                    contentValues.put("pages",652);
                    database.insert("book",null,contentValues);
                    database.setTransactionSuccessful();  //表示事务执行成功
                    contentValues.clear();
                }catch (Exception e){
                    e.printStackTrace();
                }finally {
                    database.endTransaction();
                }

            }
        });
    }
    public class MainBroadcastReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context,Intent intent){
            ConnectivityManager connectivityManager=(ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo networkInfo=connectivityManager.getActiveNetworkInfo();
            if (networkInfo!=null&&networkInfo.isAvailable()){
                Toast.makeText(context,"现在网络是正常的",Toast.LENGTH_SHORT).show();
            }else {
                Toast.makeText(context, "网络异常，请检查后再试试", Toast.LENGTH_SHORT).show();
            }
            }

    }
    /**
     * 往data/data里面存数据
     */
    public void saveData(String string){
        FileOutputStream fileOutputStream=null;
        BufferedWriter bufferedWriter=null;
        try{
               fileOutputStream=openFileOutput("输入的数据",Context.MODE_APPEND);
               bufferedWriter=new BufferedWriter(new OutputStreamWriter(fileOutputStream));
               bufferedWriter.write(string);
        }catch (IOException e){
            e.printStackTrace();
        }finally {
            try {
                if (bufferedWriter!=null){
                    bufferedWriter.close();
                }
            }catch (IOException e){
                e.printStackTrace();
            }
        }
    }
    /**
     * 往data/data里面存数据
     */
    public String getData(){
        FileInputStream fileInputStream=null;
        BufferedReader bufferedReader=null;
        StringBuilder content=new StringBuilder();
        try {
        fileInputStream=openFileInput("输入的数据");
        bufferedReader=new BufferedReader(new InputStreamReader(fileInputStream));
        String line="";
        while ((line=bufferedReader.readLine())!=null){
            content.append(line);
        }
        }catch (IOException e){
            e.printStackTrace();
        }finally {
            if (bufferedReader!=null){
                try {
                    bufferedReader.close();
                }catch (IOException e){
                    e.printStackTrace();
                }
            }
        }
        return content.toString();
    }
    protected void onStart(){
        super.onStart();
//        Toast.makeText(this, "这是在执行onstart的方法", Toast.LENGTH_SHORT).show();
    }

    protected void onResume(){
        super.onResume();
//        Toast.makeText(this, "这是在执行onResume的方法", Toast.LENGTH_SHORT).show();
    }

    protected void onPause(){
        super.onPause();
//        Toast.makeText(this, "这是在执行onPause的方法", Toast.LENGTH_SHORT).show();
    }
    protected void onRestart(){
        super.onRestart();
//        Toast.makeText(this, "这是在执行onRestart的方法", Toast.LENGTH_SHORT).show();
    }
    protected void onStop(){
        super.onStop();
//        Toast.makeText(this, "这是在执行onStop的方法", Toast.LENGTH_SHORT).show();
    }
    protected void onDestroy(){
        super.onDestroy();
        unregisterReceiver(mainBroadcastReceive);
        String saveString=editText.getText().toString();
        saveData(saveString);
//        Toast.makeText(this, "这是在执行onDestroy的方法", Toast.LENGTH_SHORT).show();
    }


}
