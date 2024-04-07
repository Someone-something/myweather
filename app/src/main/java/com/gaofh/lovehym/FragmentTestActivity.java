package com.gaofh.lovehym;


import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentContainer;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

public class FragmentTestActivity extends FragmentActivity  {
    @Override
    public void onCreate(Bundle savedInstancedState){
        super.onCreate(savedInstancedState);
        setContentView(R.layout.fragment_test_activity);
//        Button button=View.inflate(this,R.layout.fragment_first,null).findViewById(R.id.button_first);
        /**
         * 这里新建一个实例
         */
        SecondFragment secondFragment=(SecondFragment) getSupportFragmentManager().findFragmentById(R.id.fragment_right);
        @SuppressWarnings("all")
        Button button=(Button)findViewById(R.id.button_first);
        /**
         * 右边的button，是在FrameLayout里面的，如果用主界面的布局去找，找不到，就会出现报错提示；
         */
//        Button button2=(Button)findViewById(R.id.button_second);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
             //   Toast.makeText(FragmentTestActivity.this, "我点了这个按钮", Toast.LENGTH_SHORT).show();
                       ThirdFragment thirdFragment=new ThirdFragment();
       FragmentManager fragmentManager=getSupportFragmentManager();
       FragmentTransaction fragmentTransaction=fragmentManager.beginTransaction();
//       fragmentTransaction.replace(R.id.fragment_right_layout,thirdFragment);
                fragmentTransaction.add(R.id.fragment_right_layout,thirdFragment);
                fragmentTransaction.hide(secondFragment);
                fragmentTransaction.show(thirdFragment);
                fragmentTransaction.addToBackStack(null);
       fragmentTransaction.commit();
            }
        });
//        button.setOnClickListener(this);


    }
//      public  void onClick(View view){
//          Toast.makeText(this, "我点了这个按钮", Toast.LENGTH_SHORT).show();
//       ThirdFragment thirdFragment=new ThirdFragment();
//       FragmentManager fragmentManager=getSupportFragmentManager();
//       FragmentTransaction fragmentTransaction=fragmentManager.beginTransaction();
//       fragmentTransaction.replace(R.id.fragment_right_layout,thirdFragment);
//       fragmentTransaction.commit();
//
//
//      };
}
