package com.gaofh.lovehym;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.media.MediaParser;
import android.media.MediaPlayer;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Binder;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.contract.ActivityResultContracts;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationCompatExtras;
import androidx.core.app.NotificationManagerCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.NetworkInterface;
import java.util.List;


public class MainActivity extends BaseActivity {
     public static final int notificationId1=1;
     public static final int TAKE_PHOTO=0;
     public static final int CROP_PHOTO=3;
     private Button firstButton;
     private Button secondButton;
     private Button thirdButton;
     private Button fourButton;
     private Button fiveButton;
     private Button sixButton;
     private TextView title;
     private EditText editText;
     private ImageView imageView;
     private Uri imageUri;
     private MainBroadcastReceiver mainBroadcastReceive;
     private MyDatabaseSQLiteHelper dbHelper;
     private SQLiteDatabase database;
     private Context context;


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
        imageView=(ImageView)findViewById(R.id.imageView);
        dbHelper=new MyDatabaseSQLiteHelper(this,"BookStore.db",null,3);
        database=dbHelper.getWritableDatabase();
        context=this;
        /**
         * 创建通知
         */
//      NotificationManager manager=(NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
//       Notification notification=new Notification();
//       NotificationCompat.Builder builder=new NotificationCompat.Builder(this,notification);
//       manager.notify(notificationId1,notification);
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
//                dbHelper.getWritableDatabase();
                /**
                 * 调用摄像头拍照，并把照片显示在ImageView
                 */
              if(ContextCompat.checkSelfPermission(context,Manifest.permission.CAMERA)!=PackageManager.PERMISSION_GRANTED){
                  ActivityCompat.requestPermissions(MainActivity.this,new String[]{Manifest.permission.CAMERA},1);
                  ActivityCompat.requestPermissions(MainActivity.this,new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},1);
                  ActivityCompat.requestPermissions(MainActivity.this,new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},1);
              }
              File outputImage=new File(context.getExternalFilesDir(Environment.DIRECTORY_PICTURES),"tempImage.jpg");
//                File outputImage=new File(Environment.getExternalStorageDirectory(),"tempImage.jpg");
              String paths=outputImage.toString();
              Log.d("gaofh",paths);
                  try {
                      if (outputImage.exists()){
                          outputImage.delete();
                      }
                      outputImage.createNewFile();
                  }catch (Exception e){
                      e.printStackTrace();
              }
                  imageUri= FileProvider.getUriForFile(context,"com.gaofh.lovehym.provider",outputImage);
                  String string=imageUri.toString();
                  Log.d("gaofh",string);
                  Intent intent=new Intent("android.media.action.IMAGE_CAPTURE");
                  intent.putExtra(MediaStore.EXTRA_OUTPUT,imageUri);
                  startActivityForResult(intent,TAKE_PHOTO);
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
//                ContentValues values=new ContentValues();
//                values.put("price",1000);
//                database.update("book",values,"name=?",new String[]{"平凡的世界"});
//                values.clear();
                /**
                 * 创建通知
                 */
                NotificationManagerCompat notificationManager=NotificationManagerCompat.from(context);
                //申请通知的权限
                if(ContextCompat.checkSelfPermission(context, Manifest.permission.POST_NOTIFICATIONS)!= PackageManager.PERMISSION_GRANTED){
                    ActivityCompat.requestPermissions(MainActivity.this,new String[]{Manifest.permission.POST_NOTIFICATIONS},1);
                }
                //判断SDK的版本是否大于28
                if (Build.VERSION.SDK_INT>=Build.VERSION_CODES.O){
                    //以下是通知渠道的相关设置，安卓8以上的设备，无此设置就无法弹出通知
                   String channelId="001";
                   CharSequence channelName="name";
                   String chanelDescription="来自QQ好友的消息";
                   int importance=NotificationManager.IMPORTANCE_DEFAULT;
                    NotificationChannel channel=new NotificationChannel(channelId,channelName,importance);
                    channel.setDescription(chanelDescription);
                    channel.enableVibration(true);
                    channel.enableLights(true);
                    notificationManager.createNotificationChannel(channel);
                }
                /**
                 * 创建调整新闻界面的intent
                 */
                Intent intent=new Intent("com.gaofh.loveym.newsMainActivity_Action_START");
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK);
                PendingIntent pendingIntent=PendingIntent.getActivity(context,0,intent,PendingIntent.FLAG_IMMUTABLE);
                /**
                 *设置通知的铃声
                 */
                Uri uri=Uri.fromFile(new File("/storage/emulated/0/音乐播放器/拔萝卜.mp3"));
                long [] vibrates={0,1000,1000,1000};
             Notification notification= new NotificationCompat.Builder(MainActivity.this,"001")
//                     .setStyle(new NotificationCompat.BigTextStyle().bigText("这是一个展开的大标题"))
                     .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                     .setContentTitle("这是通知的标题")
                     .setContentInfo("这是通知的信息")
                     .setContentText("这是通知的文本")
                     .setSound(uri)
                     .setSmallIcon(R.drawable.enemy_head_01)
                     .setContentIntent(pendingIntent)
                     .setAutoCancel(true)
                     .setLights(Color.RED,1000,2000)
                     .setVibrate(vibrates)
                     .setLargeIcon(BitmapFactory.decodeResource(getResources(),R.drawable.enemy_head_04))
                     .build();
              notificationManager.notify(notificationId1,notification);
//              Toast.makeText(context,"这是点了通知的按钮",Toast.LENGTH_SHORT).show();
            }
        });
        //处理第四个按钮的点击事件
        fourButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //把价格超过500的数据删掉
//                database.delete("book","price>?",new String[]{"500"});
                //从小学同步的数据表取数据并打印出来
                ContentResolver resolver=getContentResolver();
                Uri uri=Uri.parse("content://com.noahedu.synclearning.provider/*");
                Cursor cursor=resolver.query(uri,null,null,null,null);
                if (cursor!=null) {
                  Toast.makeText(MainActivity.this,"cursor不是空的",Toast.LENGTH_SHORT).show();
                    try {
                        while (cursor.moveToNext()) {
                            @SuppressLint("Range") String bookId = cursor.getString(cursor.getColumnIndex("book_id"));
                            Log.d("TGA", bookId);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();

                    } finally {
                        cursor.close();

                    }
                }
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
    @Override
    protected void onActivityResult(int requestCode,int resultCode,Intent data){
           switch (requestCode){
               case TAKE_PHOTO:
                   if(resultCode==RESULT_OK){
                       /**
                        *先获得裁剪的权限
                        */
//                       Toast.makeText(this,"拍照成功的回调方法",Toast.LENGTH_SHORT).show();
//                       List<ResolveInfo> resolveInfo=getPackageManager().queryIntentActivities(data,PackageManager.MATCH_DEFAULT_ONLY);
//                       for(ResolveInfo info:resolveInfo){
//                           String packagename=info.activityInfo.packageName;
//                           Log.d("GAO",packagename);
//                           grantUriPermission(packagename,imageUri,Intent.FLAG_GRANT_WRITE_URI_PERMISSION|Intent.FLAG_GRANT_READ_URI_PERMISSION);
//                       }
                       Intent intent=new Intent("com.android.camera.action.CROP");
                       intent.setDataAndType(imageUri,"image/*");
                       intent.putExtra("crop",true);
                       intent.putExtra("scale",true);
                       intent.putExtra("return-data",true);
                       intent.putExtra(MediaStore.EXTRA_OUTPUT,imageUri);
                       intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);  //加了这个获取权限的代码才能成功调用裁剪
                       startActivityForResult(intent,CROP_PHOTO);
                   }
                   break;
               case CROP_PHOTO:
//                  if(resultCode==RESULT_OK){

                      try {
                          Toast.makeText(this,"裁剪成功的回调方法，resultCode是"+resultCode,Toast.LENGTH_SHORT).show();
                          Bitmap bitmap=BitmapFactory.decodeStream(getContentResolver().openInputStream(imageUri));
                          imageView.setImageBitmap(bitmap);
                      }catch (Exception e){
                          e.printStackTrace();
                      }
//                  }
                  break;
               default:
                   break;
           }
    }
    public class MainBroadcastReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context,Intent intent){
            ConnectivityManager connectivityManager=(ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo networkInfo=connectivityManager.getActiveNetworkInfo();
            if (networkInfo!=null&&networkInfo.isAvailable()){
//                Toast.makeText(context,"现在网络是正常的",Toast.LENGTH_SHORT).show();
            }else {
//                Toast.makeText(context, "网络异常，请检查后再试试", Toast.LENGTH_SHORT).show();
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
