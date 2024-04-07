package com.gaofh.lovehym;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.view.Window;

import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;

public class NewsContentActivity extends FragmentActivity {
     public static void actionStart(Context context,String newsTitle,String newsContent){
         Intent intent=new Intent(context, NewsContentActivity.class);
         intent.putExtra("news_title",newsTitle);
         intent.putExtra("news_content",newsContent);
         context.startActivity(intent);
     }
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState, @Nullable PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.news_content);
        String newsTitle=getIntent().getStringExtra("news_title");
        String newsContent=getIntent().getStringExtra("news_content");
        @SuppressWarnings("all")
        NewsContentFragment newsContentFragment=(NewsContentFragment)getSupportFragmentManager().findFragmentById(R.id.news_content_fragment);
        newsContentFragment.refresh(newsTitle,newsContent);
    }
}
