package com.gaofh.lovehym;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

public class MyDatabaseSQLiteHelper extends SQLiteOpenHelper {
    public static final String CREATE_BOOK="create table book("
            + "id integer primary key autoincrement,"
            + "name text,"
            + "author text,"
            + "price real,"
            + "pages integer,"
            + "category_id integer)";
    public static final String CREATE_CATEGORY="create table category("
            + "id integer primary key autoincrement,"
            + "category_name text,"
            + "category_code text)";
    private Context mContext;
    public MyDatabaseSQLiteHelper(Context context, String name, SQLiteDatabase.CursorFactory factory,int version){
        super(context,name,factory,version);
        mContext=context;
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_BOOK);
        db.execSQL(CREATE_CATEGORY);
  //      Toast.makeText(mContext, "数据库已创建", Toast.LENGTH_SHORT).show();
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
//        db.execSQL("drop table if exists book");
//        db.execSQL("drop table if exists category");
//         onCreate(db);
        switch (oldVersion){
            case 1:
                db.execSQL(CREATE_CATEGORY);
            case 2:
                db.execSQL("alter table book add column category_id integer");
            default:
        }
    }

}
