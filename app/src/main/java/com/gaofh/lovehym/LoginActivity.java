package com.gaofh.lovehym;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

public class LoginActivity extends BaseActivity{
    private EditText accountEdit;
    private EditText passwordEdit;
    private Button loginButton;
    private CheckBox checkBox;
    private SharedPreferences pref;
    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_layout);
        accountEdit=(EditText) findViewById(R.id.account_editText);
        passwordEdit=(EditText) findViewById(R.id.password_editText);
        loginButton=(Button) findViewById(R.id.login_button);
        checkBox=(CheckBox)findViewById(R.id.login_activity_checkBox);
        pref=getSharedPreferences("账号信息",MODE_PRIVATE);
        boolean isRemember=pref.getBoolean("isRemember",false);
        if (isRemember){
            String account=pref.getString("account","");
            String password=pref.getString("password","");
            accountEdit.setText(account);
            passwordEdit.setText(password);
            checkBox.setChecked(true);
        }
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String account=accountEdit.getText().toString();
                String password=passwordEdit.getText().toString();
                SharedPreferences.Editor editor=pref.edit();
                if (password.equals("123456")){
                    if(checkBox.isChecked()){
                        editor.putString("account",account);
                        editor.putString("password",password);
                        editor.putBoolean("isRemember",true);
                    }else {
                        editor.clear();
                    }
                    editor.apply();
                    Intent intent=new Intent(LoginActivity.this,MainActivity.class);
                   startActivity(intent);
                    finish();
                }else {
                    Toast.makeText(LoginActivity.this, "密码错误，请重试！", Toast.LENGTH_SHORT).show();
                    passwordEdit.setText("");
                }

            }
        });

    }
}
